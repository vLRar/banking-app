package com.banking.service;

import com.banking.model.Transaction;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PdfGeneratorService {

    public void export(HttpServletResponse response, List<Transaction> transactions) throws IOException {
        // Setăm dimensiunea paginii și marginile
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // 1. Titlu Personalizat
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph title = new Paragraph("Extras de Cont - Toth David", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // 2. Subtitlu / Dată
        Font fontSub = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph subtitle = new Paragraph("Generat la data: 05-05-2026", fontSub);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        document.add(new Paragraph(" ")); // Spațiu gol între titlu și tabel

        // 3. Configurare Tabel
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Stil pentru Header
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        table.addCell(new Phrase("Data", fontHeader));
        table.addCell(new Phrase("Descriere", fontHeader));
        table.addCell(new Phrase("Suma (RON)", fontHeader));

        // 4. Populare Tabel
        for (Transaction t : transactions) {
            table.addCell(t.getDate());
            table.addCell(t.getDescription());
            table.addCell(String.valueOf(t.getAmount()));
        }

        document.add(table);
        document.close();
    }
}