package com.supermarket.pos.entity;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // NEW: setter for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Increase by 1 (used for barcode scan)
    public void increaseQuantity(int qty) {
        this.quantity += qty;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}