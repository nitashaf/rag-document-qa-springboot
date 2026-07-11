package com.neuroforge.rag.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatResponse {
    private String question;
    private String answer;
    private LocalDateTime timestamp;
}