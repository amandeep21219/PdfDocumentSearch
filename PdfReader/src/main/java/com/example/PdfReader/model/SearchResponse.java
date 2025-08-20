package com.example.PdfReader.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor

public class SearchResponse {

    private UUID fileId;

    private String filename;

    private String query;

    private List<Match> matches;

    private int totalMatches;

}