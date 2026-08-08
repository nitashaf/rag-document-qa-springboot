package com.neuroforge.rag.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@AiService
public interface DocumentAssistant {

    @SystemMessage("""
                You are a precise document assistant.
    			Answer ONLY from the provided context.
    			If the context contains the answer, you MUST use it.
    			Never say 'I don't have information' if relevant 
    			context is provided to you.
    			Be direct and specific.
    			""")
    String chat(@UserMessage String question);
}