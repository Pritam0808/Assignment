package com.assignment1.pdfgenerator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PdfGeneratorApplicationTests {

	@Test
    void generatePdf_ReturnsPdfByteArray() {
        // Arrange
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setSeller("Seller");
        // Set other invoice data

        PdfGenerationService pdfGenerationService = new PdfGenerationService();

        // Act
        byte[] result = null;
		try {
			result = pdfGenerationService.generatePdf(invoiceData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Assert
        assertNotNull(result);
        assertTrue(result.length > 0);
    }
}