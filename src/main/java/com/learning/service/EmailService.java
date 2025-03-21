package com.learning.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.learning.entity.Order;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private OrderService orderService;

    public void sendOrderReceipt(String orderId, String recipientEmail) throws MessagingException {
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found.");
        }

        Order order = orderOptional.get();
        byte[] pdfBytes = pdfService.generateReceipt(order);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipientEmail);
        helper.setSubject("Your Booking Receipt - Order " + orderId);
        helper.setText("Dear " + order.getUser().getName() + ",\n\nPlease find your order receipt attached.\n\nThank you!");

        helper.addAttachment("Order_" + orderId + ".pdf", new ByteArrayDataSource(pdfBytes, "application/pdf"));

        mailSender.send(message);
    }
}
