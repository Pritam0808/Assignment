package com.assignment1.pdfgenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.assignment1.pdfgenerator.InvoiceData.Item;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;


@Service
public class PdfGenerationService {
	public byte[] generatePdf(InvoiceData invoiceData) throws IOException {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			PdfWriter writer = new PdfWriter(outputStream);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf, PageSize.A4);

			// Create a table for seller and buyer info
			Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}))
					.setHorizontalAlignment(HorizontalAlignment.CENTER) // Align the table in the center
					.setWidth(UnitValue.createPercentValue(100)); // Set the width of the table to fit the page

			// Add seller info
			Cell sellerCell = new Cell(1, 2).add(new Paragraph("Seller: \n"  + invoiceData.getSeller()+ "\n"+  invoiceData.getSellerAddress()+ "\n"+  "Seller GSTIN: " + invoiceData.getSellerGstin()));
			table.addCell(sellerCell);

			// Add buyer info
			Cell buyerCell = new Cell(1, 2).add(new Paragraph("Buyer: \n" + invoiceData.getBuyer()+ "\n"+ invoiceData.getBuyerAddress()+ "\n"+  "Buyer GSTIN: " + invoiceData.getBuyerGstin()));
			table.addCell(buyerCell);

			// Add headers
			table.addCell(new Cell().add(new Paragraph("Item")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
			table.addCell(new Cell().add(new Paragraph("Quantity")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
			table.addCell(new Cell().add(new Paragraph("Rate")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
			table.addCell(new Cell().add(new Paragraph("Amount")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));

			// Add rows
			for (Item item : invoiceData.getItems()) {
				table.addCell(new Cell().add(new Paragraph(item.getName())).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
				table.addCell(new Cell().add(new Paragraph(item.getQuantity())).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
				table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
				table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
			}
			Cell blankCell = new Cell(1,4).add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
			blankCell.setHeight(50); // Set the height of the cell
			table.addCell(blankCell);
			document.add(table);
			document.close();
			return outputStream.toByteArray();
		}
	}
}
