package com.supermarket.pos.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Map;

import com.supermarket.pos.entity.CartItem;
import com.supermarket.pos.entity.Invoice;
import com.supermarket.pos.service.BillingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    // Barcode scanning (adds 1 item)
    @PostMapping("/scan")
    public void scan(@RequestBody Map<String, Object> data) {

        System.out.println("SCAN API HIT");

        String barcode = (String) data.get("barcode");
        int quantity = ((Number) data.get("quantity")).intValue();

        System.out.println(" barcode=" + barcode + " qty=" + quantity);

        billingService.scan(barcode, quantity);
    }
    @PostMapping("/add")
    public void add(@RequestBody Map<String, Object> data) {

        String barcode = (String) data.get("barcode");
        int qty = ((Number) data.get("qty")).intValue();

        System.out.println("RECEIVED QTY: " + qty);

        billingService.addProduct(barcode, qty);
    }

    // View cart
    @GetMapping("/cart")
    public Collection<CartItem> getCart() {
        return billingService.getCartItems();
    }

    // Checkout
    @PostMapping("/checkout")
    public Invoice checkout() {
        return billingService.checkout();
    }
    @GetMapping("/top-products")
    public Map<String, Integer> topProducts() {
        return billingService.getTopProducts();
    }
}