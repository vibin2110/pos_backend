package com.supermarket.pos.service;

import com.supermarket.pos.entity.Product;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LowStockEmailService {

    private final JavaMailSender mailSender;

    public LowStockEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendLowStockAlert(String adminEmail, Product product) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("Low Stock Alert - " + product.getName());
        message.setText(
                "Product: " + product.getName() + "\n" +
                        "Current stock: " + product.getQuantity() + "\n" +
                        "Please restock soon."
        );
        mailSender.send(message);
    }
}
