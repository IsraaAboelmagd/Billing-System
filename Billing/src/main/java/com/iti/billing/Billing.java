/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iti.billing;

import com.iti.cdr.CDR;
import com.iti.cdr.DB;
import java.util.List;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


/**
 *
 * @author Admin
 */
public class Billing {
    
    
    public static void main(String[] args) {
        
//            CDR cdr = new CDR();
//            List<CDR> c=cdr.readCDR();
//            DB db = new DB();
//            db.insertCDR(c);
//            
                  try {
            // Create a new document
            PDDocument document = new PDDocument();

            // Create a new page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Start a new content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            // Set coordinates for text placement
            float startX = 50;
            float startY = page.getMediaBox().getHeight() - 50;
            float cellWidth = 100;
            float cellHeight = 20;

            // Write bill information headers
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText("Item");
            contentStream.newLineAtOffset(cellWidth, 0);
            contentStream.showText("Quantity");
            contentStream.newLineAtOffset(cellWidth, 0);
            contentStream.showText("Price");
            contentStream.newLineAtOffset(cellWidth, 0);
            contentStream.showText("Total");
            contentStream.endText();

            // Draw horizontal lines
            contentStream.moveTo(startX, startY - cellHeight);
            contentStream.lineTo(startX + cellWidth * 4, startY - cellHeight);
            contentStream.stroke();

            // Write bill items
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            float currentY = startY - cellHeight * 2;
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, currentY);
            contentStream.showText("Item 1");
            contentStream.newLineAtOffset(cellWidth, 0);
            contentStream.showText("2");
            contentStream.newLineAtOffset(cellWidth, 0);
            contentStream.showText("$10.00");
            contentStream.newLineAtOffset(cellWidth, 0);
            contentStream.showText("$20.00");
            contentStream.newLineAtOffset(-cellWidth * 3, -cellHeight);

            // Draw vertical lines
            contentStream.moveTo(startX + cellWidth, startY);
            contentStream.lineTo(startX + cellWidth, currentY - cellHeight * 3);
            contentStream.moveTo(startX + cellWidth * 2, startY);
            contentStream.lineTo(startX + cellWidth * 2, currentY - cellHeight * 3);
            contentStream.moveTo(startX + cellWidth * 3, startY);
            contentStream.lineTo(startX + cellWidth * 3, currentY - cellHeight * 3);
            contentStream.stroke();
            contentStream.endText();

            // Close the content stream
            contentStream.close();

            // Save the document to a file
            document.save("bill2.pdf");

            // Close the document
            document.close();

            System.out.println("Bill PDF generated successfully!");
        } catch (IOException e) {
        }  
        
    }
    
   
}
