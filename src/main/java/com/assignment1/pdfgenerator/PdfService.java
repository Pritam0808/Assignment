package com.assignment1.pdfgenerator;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class PdfService {
	private final PdfStorageService pdfStorageService;
    private final PdfGenerationService pdfGenerationService;

    @Autowired
    public PdfService(PdfStorageService pdfStorageService, PdfGenerationService pdfGenerationService) {
        this.pdfStorageService = pdfStorageService;
        this.pdfGenerationService = pdfGenerationService;
    }

    public byte[] generatePdf(InvoiceData invoiceData) throws IOException {
        String invoiceDataHash = calculateHash(invoiceData); // Calculate a hash for the invoice data
        if (pdfStorageService.existsPdf(invoiceDataHash)) {
            return pdfStorageService.loadPdfContent(invoiceDataHash);
        } else {
            byte[] pdfContent = pdfGenerationService.generatePdf(invoiceData);
            pdfStorageService.storePdf(pdfContent, invoiceDataHash); // Store PDF with the hash
            return pdfContent;
        }
    }

    public Resource loadPdfAsResource(String invoiceDataHash) throws IOException {
        return pdfStorageService.loadPdfAsResource(invoiceDataHash);
    }

    // Helper method to calculate hash for invoice data
    private String calculateHash(InvoiceData invoiceData) throws IOException {
        String serializedInvoiceData = serializeInvoiceDataToJson(invoiceData);
        // Use a proper hash function here (e.g., MD5, SHA-256)
        return Integer.toString(serializedInvoiceData.hashCode());
    }

    // Method to serialize InvoiceData object to JSON
    private String serializeInvoiceDataToJson(InvoiceData invoiceData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        return objectMapper.writeValueAsString(invoiceData);
    }
}