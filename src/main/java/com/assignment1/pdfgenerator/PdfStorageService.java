package com.assignment1.pdfgenerator;

//import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfStorageService {
    private final String storageLocation = "pdfs/";
    private final Map<String, String> pdfFileNameMap = new HashMap<>();

    public void storePdf(byte[] pdfContent, String invoiceDataHash) throws IOException {
        String pdfFileName = getFileNameForHash(invoiceDataHash);
        Path pdfPath = Paths.get(storageLocation).resolve(pdfFileName);
        try (FileOutputStream fos = new FileOutputStream(pdfPath.toFile())) {
            fos.write(pdfContent);
        }
    }

    public Resource loadPdfAsResource(String invoiceDataHash) throws IOException {
        String pdfFileName = getFileNameForHash(invoiceDataHash);
        Path pdfPath = Paths.get(storageLocation).resolve(pdfFileName);
        byte[] pdfData = Files.readAllBytes(pdfPath);
        return new ByteArrayResource(pdfData);
    }

    public boolean existsPdf(String invoiceDataHash) {
        return pdfFileNameMap.containsKey(invoiceDataHash);
    }

    public byte[] loadPdfContent(String invoiceDataHash) throws IOException {
        String pdfFileName = getFileNameForHash(invoiceDataHash);
        Path pdfPath = Paths.get(storageLocation).resolve(pdfFileName);
        return Files.readAllBytes(pdfPath);
    }

    private String getFileNameForHash(String invoiceDataHash) {
        return pdfFileNameMap.computeIfAbsent(invoiceDataHash, key -> generateFileName());
    }

    private String generateFileName() {
        return "generated_invoice_" + System.currentTimeMillis() + ".pdf";
    }
}