package com.miniproject.library.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.miniproject.library.entity.Book;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookReport {
    public void bookReport(List<Book> bookReport, HttpServletResponse response) throws DocumentException, IOException {
        try (Document document = new Document(PageSize.A2);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();

            // Your existing PDF generation logic
            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTitle.setSize(20);

            Paragraph paragraph1 = new Paragraph("Book Report", fontTitle);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph1);

            PdfPTable table = new PdfPTable(10);
            // Setting width of the table, its columns and spacing
            table.setWidthPercentage(100f);
            table.setSpacingBefore(5);

            PdfPCell cell = new PdfPCell();
            // Setting the background color and padding of the table cell
            cell.setBackgroundColor(Color.DARK_GRAY);
            cell.setPadding(5);
            float[] columnWidths = {50f, 150f, 100f, 150f, 100f, 100f, 80f, 50f, 50f, 60f};

            table.setTotalWidth(columnWidths);
            table.setLockedWidth(true); // Lock the width of the table

            // Iterating through the header cell creation
            String[] headers = {"No", "Title", "Author", "Summary", "Publication Date", "Publisher", "Category", "Stock", "Read", "Wishlist"};
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            font.setColor(Color.WHITE);

            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell();
                headerCell.setBackgroundColor(Color.DARK_GRAY);
                headerCell.setPadding(5);
                headerCell.setPhrase(new Phrase(header, font));
                headerCell.setUseAscender(true);
                headerCell.setUseDescender(true);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);
            }

            int idCounter = 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("en", "EN"));
            // Iterating the list of books
            for (Book book: bookReport) {
                cell = new PdfPCell();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPhrase(new Phrase(String.valueOf(idCounter)));
                table.addCell(cell);
                cell.setPhrase(new Phrase(book.getTitle()));
                table.addCell(cell);
                cell.setPhrase(new Phrase(book.getAuthor()));
                table.addCell(cell);
                cell.setPhrase(new Phrase(book.getSummary()));
                table.addCell(cell);
                String formattedDate = dateFormat.format(book.getPublicationDate());
                cell.setPhrase(new Phrase(formattedDate));
                table.addCell(cell);
                cell.setPhrase(new Phrase(book.getPublisher()));
                table.addCell(cell);
                cell.setPhrase(new Phrase(book.getCategory().getName()));
                table.addCell(cell);
                cell.setPhrase(new Phrase(String.valueOf(book.getStock())));
                table.addCell(cell);
                cell.setPhrase(new Phrase(String.valueOf(book.getRead())));
                table.addCell(cell);
                cell.setPhrase(new Phrase(String.valueOf(book.getWishlist())));
                table.addCell(cell);
                idCounter++;
            }
            // Adding the created table to the document
            document.add(table);

            document.close();
            // Write the content to the response output stream
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=BookReport.pdf");

            // Write the content to the response output stream
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();  // Log the exception for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            try {
                response.getWriter().write("Error generating PDF");
                response.getWriter().flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();  // Log the exception for debugging
            }
        }
    }
}
