package com.neuroforge.rag.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {

    private final EmbeddingStoreIngestor ingestor;

    public String ingestDocument(MultipartFile file) throws IOException {
        log.info("Ingesting file: {}", file.getOriginalFilename());

        // Save uploaded file to a temp location on disk
        Path tempFile = Files.createTempFile("rag-", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());

        try {
            // LangChain4j reads and parses the file automatically
            Document document = FileSystemDocumentLoader.loadDocument(tempFile);
            document.metadata().put("filename", file.getOriginalFilename());

            // split → embed → store — all in one call
            ingestor.ingest(document);

            log.info("Successfully ingested: {}", file.getOriginalFilename());
            return "Ingested: " + file.getOriginalFilename();

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    public String ingestText(String text, String source) {
        log.info("Ingesting text from: {}", source);

        Document document = Document.from(text, Metadata.from("source", source));
        ingestor.ingest(document);

        return "Ingested text from: " + source;
    }
}