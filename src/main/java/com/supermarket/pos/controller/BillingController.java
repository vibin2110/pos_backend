package com.supermarket.pos.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Collection;

import com.supermarket.pos.entity.CartItem;
import com.supermarket.pos.entity.Invoice;
import com.supermarket.pos.service.BillingService;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/scan/{barcode}")
    public String scan(@PathVariable String barcode) {
        billingService.scanProduct(barcode);
        return "Product added to cart";
    }

    @GetMapping("/cart")
    public Collection<CartItem> getCart() {
        return billingService.getCartItems();
    }

    @PostMapping("/checkout")
    public Invoice checkout() {
        return billingService.checkout();
    }
}