package com.example.PdfReader.pdfService;

import com.example.PdfReader.exception.ApiException;
import com.example.PdfReader.model.*;
import com.example.PdfReader.pdfUtils.FileValidators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Service
public class PdfService {

    @Autowired
    private PdfTextExtractor pdfTextExtractor;

    @Autowired
    private SearchService searchService;

    @Autowired
    private FileValidators fileValidators;
     //temp memory and used concurrent hashmap for better thread safety
    private final Map<UUID, DocumentData> documents = new ConcurrentHashMap<>();

    public UploadResponse uploadPdf(MultipartFile file) {
        try {
            log.info("[PdfService] :: {uploadPdf} :: Validating the uploaded document ");
            FileValidators.validatePdfFile(file);
            UUID fileId = UUID.randomUUID();
            log.info("[PdfService] :: {uploadPdf} :: Extracting content for File ID {} ",fileId);
            List<String> pages = pdfTextExtractor.extractTextByPages(file);
            DocumentData docData = new DocumentData(file.getOriginalFilename(), pages, System.currentTimeMillis());
            documents.put(fileId, docData);
            log.info("[PdfService] :: {uploadPdf} :: File uploaded with ID {} and Name{} ",fileId,file.getOriginalFilename());
            return new UploadResponse(fileId, file.getOriginalFilename(), pages.size());
        } catch (Exception e) {
            throw new ApiException("Failed to upload PDF: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public SearchResponse searchInUploaded(UUID fileId, String query, Boolean caseSensitive, Integer contextChars) {
        log.info("[PdfService] :: searchInUploaded :: Starting search for file ID: {}, query: '{}', caseSensitive: {}, contextChars: {}",
                fileId, query, caseSensitive, contextChars);
        DocumentData document = Optional.ofNullable(documents.get(fileId))
                .orElseThrow(() -> new ApiException("PDF not found with ID: " + fileId, HttpStatus.NOT_FOUND));
        log.info("[PdfService] :: searchInUploaded :: Document found: {} with {} pages",
                document.getFilename(), document.getPages().size());
        List<Match> matches = searchService.searchInDocument(document, query, caseSensitive,contextChars);
        log.info("[PdfService] :: searchInUploaded :: Search completed successfully for file ID: {}", fileId);
        return new SearchResponse(fileId, document.getFilename(), query, matches, matches.size());
    }

    public SearchResponse uploadAndSearch(MultipartFile file, String query, Boolean caseSensitive, Integer contextChars) {
        log.info("[PdfService] :: uploadAndSearch :: Starting upload and search for file: {}, query: '{}'",
                Objects.nonNull(file) ? file.getOriginalFilename() : "null", query);
        try {
            log.info("[PdfService] :: uploadAndSearch :: Validating uploaded document: {}", file.getOriginalFilename());
            FileValidators.validatePdfFile(file);
            log.info("[PdfService] :: uploadAndSearch :: Starting text extraction for temporary processing");
            List<String> pages = pdfTextExtractor.extractTextByPages(file);
            DocumentData document = new DocumentData(file.getOriginalFilename(), pages, System.currentTimeMillis());
            log.info("[PdfService] :: uploadAndSearch :: Starting search operation for query: '{}'", query);
            List<Match> matches = searchService.searchInDocument(document, query,caseSensitive, contextChars );
            log.info("[PdfService] :: uploadAndSearch :: Upload and search operation completed successfully for file: {}",
                    file.getOriginalFilename());
            return new SearchResponse(null, file.getOriginalFilename(), query, matches, matches.size());

        } catch (Exception e) {
            throw new ApiException("Failed to upload and search PDF: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}