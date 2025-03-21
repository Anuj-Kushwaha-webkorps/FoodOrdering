package com.learning.service;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.learning.entity.Order;
import com.learning.entity.OrderItem;
import com.itextpdf.layout.element.Cell;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateReceipt(Order order) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Booking Receipt").setBold().setFontSize(18));

            document.add(new Paragraph("Order ID: " + order.getOrderId()));
            document.add(new Paragraph("User: " + order.getUser().getName()));
            document.add(new Paragraph("Total Price: ₹" + order.getTotalPrice()));
            document.add(new Paragraph("Order Date: " + order.getOrderTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            document.add(new Paragraph("Delivery Address: " + order.getDeliveryAddress()));

            Table table = new Table(new float[]{3, 1, 1, 1});
            table.addHeaderCell(new Cell().add(new Paragraph("Dish Name")));
            table.addHeaderCell(new Cell().add(new Paragraph("Size")));
            table.addHeaderCell(new Cell().add(new Paragraph("Quantity")));
            table.addHeaderCell(new Cell().add(new Paragraph("Price")));

            for (OrderItem item : order.getItems()) {
                table.addCell(new Cell().add(new Paragraph(item.getDishName())));
                table.addCell(new Cell().add(new Paragraph(item.getDish().getDishSize().toString())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
                table.addCell(new Cell().add(new Paragraph("₹" + item.getPricePerItem())));
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}

