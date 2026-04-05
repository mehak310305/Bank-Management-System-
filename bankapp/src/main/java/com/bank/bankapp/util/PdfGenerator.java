package com.bank.bankapp.util;

import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.bank.bankapp.entity.Transaction;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator {
      public void generate (List<Transaction> transactions,HttpServletResponse response)
      throws IOException ,DocumentException{
        Document document =new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        Font fontTitle=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph =new Paragraph("HDFC Clone-Transaction Statement",fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table= new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);



        table.addCell("Date");
          table.addCell("Transaction Type"); // Isme Deposit/Withdraw/Transfer aayega
             table.addCell("Amount");

     // 2. Data Rows 
      for (Transaction t : transactions) {
                    table.addCell(t.getCreatedAt().toString()); // Date
                    table.addCell(t.getType().name());                 // Type (Deposit, Withdraw, etc.)
                    table.addCell(t.getAmount().toString());    // Amount

            }
        document.add(table);
        document.close();
      }
}
