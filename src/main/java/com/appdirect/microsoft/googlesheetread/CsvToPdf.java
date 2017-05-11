package com.appdirect.microsoft.googlesheetread;

import java.io.FileOutputStream;
import java.io.FileReader;
import com.appdirect.sendbydrive.GoogleDriveAccess;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import au.com.bytecode.opencsv.CSVReader;

@SuppressWarnings("resource")
public class CsvToPdf {
	public GoogleDriveAccess csvToPdf() throws Exception {

		String inputCSVFile = "EditionSuccessfulOnFinding.csv";
		CSVReader reader = new CSVReader(new FileReader(inputCSVFile));
		String[] nextLine;
		Document my_pdf_data = new Document();
		String finalPDFFile = "EditionPriceSuccessList.pdf";

		PdfWriter.getInstance(my_pdf_data, new FileOutputStream(finalPDFFile));
		my_pdf_data.open();
		PdfPTable my_first_table = new PdfPTable(10);

		my_first_table.setTotalWidth(new float[] { 150, 600, 1000, 220, 220, 220, 220, 220, 220, 220, 220 });
		PdfPCell table_cell;
		while ((nextLine = reader.readNext()) != null) {
			for (int i = 0; i < nextLine.length; i++) {
				table_cell = new PdfPCell(new Phrase(nextLine[i], FontFactory.getFont(FontFactory.HELVETICA, 8)));
				my_first_table.addCell(table_cell);
			}
		}
		my_pdf_data.add(my_first_table);
		my_pdf_data.close();
		return new GoogleDriveAccess(finalPDFFile);
	}
}