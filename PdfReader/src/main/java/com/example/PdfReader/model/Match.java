package com.example.PdfReader.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Match {
    private int pageNumber;

    private String snippet;

    private double score;

}