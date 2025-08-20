package com.example.PdfReader.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
@Data
@AllArgsConstructor


public class UploadResponse {
    private UUID fileId;

    private String filename;

    private int totalPages;



}