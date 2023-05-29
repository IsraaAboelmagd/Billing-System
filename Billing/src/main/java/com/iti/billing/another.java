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
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class another {

    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            List<User> users = getUsersFromDB();
//             System.out.println("****"+users.get(0));
            for (User user : users) {
                System.out.println("-----------"+user.getName());
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                    float margin = 50;
                    float startY = page.getMediaBox().getHeight() - margin;
                    float endY = margin;

                    generateBillTable(contentStream, startY, endY, user);
                    generateUsageChart(document, user);
                }
            }
            document.save("bill4.pdf");
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
        String[] headers = {"Name", "Mobile Number", "Email", "Address", "Total Cost"};
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
        String[] data = {user.getName(), user.getMobileNumber(), user.getEmail(), user.getAddress(), String.valueOf(user.getTotalcost())};
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

    private static void generateUsageChart(PDDocument document, User user) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Voice Calls", 40);
        dataset.setValue("Data", 60);
        dataset.setValue("SMS", 10);

        JFreeChart chart = ChartFactory.createPieChart("Usage Chart for " + user.getName(), dataset);

        chart.setBackgroundPaint(new Color(255, 192, 203, 0));
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Voice Calls", Color.PINK);
        plot.setSectionPaint("Data", Color.CYAN);

        int width = 500;
        int height = 250;

        BufferedImage bufferedImage = chart.createBufferedImage(width, height);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, ChartUtils.encodeAsPNG(bufferedImage), "chart");
        PDPage page = document.getPage(document.getNumberOfPages() - 1);
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.drawImage(pdImage, 50, 300, width, height);
        contentStream.close();
    }

    private static List<User> getUsersFromDB() {
    List<User> users = new ArrayList<>();

    // JDBC connection parameters
    String url = "jdbc:postgresql://localhost:5432/newbill";
    String username = "postgres";
    String password = "123456";

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
        String query = "SELECT a.name, a.msisdn, a.email, a.address, a.accountid, l.accessfees " +
                       "FROM account a " +
                       "JOIN linktable l ON l.tmcode = a.contractid";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String mobileNumber = resultSet.getString("msisdn");
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");
                    String id = resultSet.getString("accountid");
                    double totalCost = resultSet.getDouble("accessfees");

                    User user = new User(name, mobileNumber, email, address, id);
                    user.setTotalcost(totalCost);
                    users.add(user);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return users;
}
}
