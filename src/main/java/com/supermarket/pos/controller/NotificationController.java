package com.supermarket.pos.controller;

import com.supermarket.pos.entity.Product;
import com.supermarket.pos.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notifications")
public class NotificationController {

    private final ProductRepository productRepository;

    public NotificationController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<String> getNotifications() {

        List<String> notifications = new ArrayList<>();

        List<Product> lowStockProducts = productRepository.findLowStockProducts();

        for (Product p : lowStockProducts) {
            notifications.add(p.getName() + " stock is low");
        }

        return notifications;
    }

}