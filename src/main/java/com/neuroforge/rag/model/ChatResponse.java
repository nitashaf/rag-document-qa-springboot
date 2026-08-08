package com.neuroforge.rag.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponse {
    private String question;
    private String answer;
    private List<String> contexts;
    private LocalDateTime timestamp;
}
