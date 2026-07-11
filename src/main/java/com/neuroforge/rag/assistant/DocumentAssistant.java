package com.neuroforge.rag.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@AiService
public interface DocumentAssistant {

    @SystemMessage("""
            You are a helpful document assistant.
            Answer questions ONLY based on the provided document context.
            If the answer is not in the documents, say:
            "I don't have information about that in the provided documents."
            Be concise and accurate.
            """)
    String chat(@UserMessage String question);
}