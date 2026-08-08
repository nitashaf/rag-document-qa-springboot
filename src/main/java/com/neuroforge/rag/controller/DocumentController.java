package com.neuroforge.rag.controller;

import com.neuroforge.rag.assistant.DocumentAssistant;
import com.neuroforge.rag.model.ChatRequest;
import com.neuroforge.rag.model.ChatResponse;
import com.neuroforge.rag.model.TextIngestRequest;
import com.neuroforge.rag.service.DocumentService;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentAssistant assistant;
    private final DocumentService documentService;
    private final ContentRetriever contentRetriever;

    @PostMapping("/documents/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file) {
        try {
            String result = documentService.ingestDocument(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            log.error("Failed to ingest: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/documents/text")
    public ResponseEntity<String> ingestText(
            @RequestBody TextIngestRequest request) {
        String result = documentService.ingestText(request.getText(), request.getSource());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request) {
        log.info("Question: {}", request.getQuestion());
        List<String> contexts = contentRetriever.retrieve(Query.from(request.getQuestion()))
                .stream()
                .map(Content::textSegment)
                .map(segment -> segment.text())
                .toList();
        String answer = assistant.chat(request.getQuestion());
        return ResponseEntity.ok(
            new ChatResponse(request.getQuestion(), answer, contexts, LocalDateTime.now())
        );
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("RAG app is running!");
    }
}
