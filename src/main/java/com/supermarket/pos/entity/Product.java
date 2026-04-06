package com.supermarket.pos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String barcode;
    private double price;
    private int quantity;

    @Column(name = "min_stock")
    private int minStock;

    @Column(name = "low_stock_alert_sent")
    private boolean lowStockAlertSent = false;

    public Product() {}

    public Product(String name, String barcode, double price, int quantity, int minStock) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.minStock = minStock;
        this.lowStockAlertSent = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinStock() {
        return minStock;
    }

    public boolean isLowStockAlertSent() {
        return lowStockAlertSent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public void setLowStockAlertSent(boolean lowStockAlertSent) {
        this.lowStockAlertSent = lowStockAlertSent;
    }
}
