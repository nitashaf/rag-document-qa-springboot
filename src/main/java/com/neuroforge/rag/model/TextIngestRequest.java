package com.neuroforge.rag.model;

import lombok.Data;

@Data
public class TextIngestRequest {
    private String text;
    private String source;
}