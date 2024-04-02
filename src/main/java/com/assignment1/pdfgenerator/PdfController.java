package com.assignment1.pdfgenerator;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<?> generatePdf(@RequestBody InvoiceData invoiceData) {
        try {
            byte[] pdfContent = pdfService.generatePdf(invoiceData);
            return ResponseEntity.ok().body(new ByteArrayResource(pdfContent));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate PDF.");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadPdf(@RequestParam String pdfFileName) {
        try {
            ByteArrayResource resource = (ByteArrayResource) pdfService.loadPdfAsResource(pdfFileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFileName);
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PDF not found.");
        }
    }
}