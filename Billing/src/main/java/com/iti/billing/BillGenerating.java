/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.billing;

/**
 *
 * @author Aisha
 */


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class BillGenerating {

    public static void main(String[] args) {
        try { 
            List<User> users = getUsersFromDB(); 

            PDDocument document = new PDDocument();
            for (User user : users) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                float margin = 50;
                float startY = page.getMediaBox().getHeight() - margin;
                float endY = margin;

                generateBillTable(contentStream, startY, endY, user);
   generateUsageChart(document);
                contentStream.close();
            }
            document.save("bills.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateBillTable(PDPageContentStream contentStream, float startY, float endY, User user) throws IOException {
        // Define the table parameters
        float tableWidth = 500;
        float yPosition = startY;
        float tableMargin = 50;
        float cellMargin = 10;

        // Define the number of rows and columns
        int rows = 1;
        int cols = 5;
        float[] colWidths = {100, 100, 100, 100, 100};

        // Set the font and font size
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        // Set the initial position
        float currentPosition = yPosition;

        // Draw the table headers
        drawTableHeader(contentStream, tableWidth, currentPosition, tableMargin, cellMargin, colWidths);
        currentPosition -= 20; // Move down after drawing the headers

        drawTableRow(contentStream, tableWidth, currentPosition, tableMargin, cellMargin, colWidths, user);

        drawTableBorders(contentStream, tableWidth, currentPosition, tableMargin, cellMargin, colWidths);
    }

    private static void drawTableHeader(PDPageContentStream contentStream, float tableWidth, float currentPosition, float tableMargin, float cellMargin, float[] colWidths) throws IOException {
        contentStream.setNonStrokingColor(Color.BLACK);

      
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        float cellHeight = 20;

        float currentXPosition = tableMargin;
        float currentYPosition = currentPosition;

        // Draw the headers
        String[] headers = {"Name", "Mobile Number", "Email", "Total Cost", "Rate Plan"};
        for (int i = 0; i < colWidths.length; i++) {
            contentStream.addRect(currentXPosition, currentYPosition, colWidths[i], cellHeight);
            contentStream.fill();


           
            contentStream.beginText();
             contentStream.setNonStrokingColor(Color.PINK);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(currentXPosition + cellMargin, currentYPosition + cellHeight / 2 - 4);
            contentStream.showText(headers[i]);
             contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.endText();

            // Move to the next cell position
            currentXPosition += colWidths[i];
        }
    }

    private static void drawTableRow(PDPageContentStream contentStream, float tableWidth, float currentPosition, float tableMargin, float cellMargin, float[] columnWidths, User user) throws IOException {
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        float cellHeight = 20;
        float currentXPosition = tableMargin;
        float currentYPosition = currentPosition;
        String[] data = {"user.getName()", "user.getMobileNumber()", "user.getEmail()", "String.valueOf(user.getTotalCost())", "user.getRatePlan()"};
        for (int i = 0; i < columnWidths.length; i++) {
            contentStream.addRect(currentXPosition, currentYPosition, columnWidths[i], cellHeight);
            contentStream.stroke();
            contentStream.beginText();
            contentStream.newLineAtOffset(currentXPosition + cellMargin, currentYPosition + cellHeight / 2 - 4);
            contentStream.showText(data[i]);
            contentStream.endText();
            currentXPosition += columnWidths[i];
        }
    }

    private static void drawTableBorders(PDPageContentStream contentStream, float tableWidth, float currentPosition, float tableMargin, float cellMargin, float[] columnWidths) throws IOException {
        contentStream.setLineWidth(1f);

        contentStream.addRect(tableMargin, currentPosition - 20, tableWidth, 20 + (columnWidths.length * 20));
        contentStream.stroke();
    }


     private static void generateUsageChart(PDDocument document) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Voice Calls", 40);
        dataset.setValue("Data", 60);
        dataset.setValue("SMS", 10);
 

        JFreeChart chart = ChartFactory.createPieChart("Usage Chart for this month", dataset);
        

        chart.setBackgroundPaint(new Color(255, 192, 203, 0));
         PiePlot plot =(PiePlot) chart.getPlot();
         plot.setSectionPaint("Voice Calls", Color.pink);
         plot.setSectionPaint("Data", Color.cyan);

        int width = 500;
        int height = 250;

        java.awt.Image awtImage = chart.createBufferedImage(width, height);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, ChartUtils.encodeAsPNG((BufferedImage) awtImage), "chart");
        PDPage page = document.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.drawImage(pdImage, 50, 300, width, height);
        contentStream.close();
    }


    private static List<User> getUsersFromDB() {

        List<User> users = new ArrayList<>();
        users.add(new User("John Doe", "1234567890", "john@example.com", 50.0, "RedPlan A"));
        users.add(new User("Jane Smith", "9876543210", "jane@example.com", 60.0, "REDPlan B"));
        users.add(new User("Mike Johnson", "5555555555", "mike@example.com", 70.0, "ratePlan C"));
        return users;
    }
}
