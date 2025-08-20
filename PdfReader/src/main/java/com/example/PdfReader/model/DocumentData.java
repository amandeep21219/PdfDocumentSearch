package com.example.PdfReader.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor

public class DocumentData {
    private final String filename;

    private final List<String> pages;

    private final long uploadTimestamp;
}