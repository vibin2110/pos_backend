package com.supermarket.pos.entity;

public class DashboardStats {

    private double todaySales;
    private int billsToday;
    private long totalProducts;
    private int lowStock;

    public DashboardStats() {}

    public DashboardStats(double todaySales, int billsToday, long totalProducts, int lowStock) {
        this.todaySales = todaySales;
        this.billsToday = billsToday;
        this.totalProducts = totalProducts;
        this.lowStock = lowStock;
    }

    public double getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(double todaySales) {
        this.todaySales = todaySales;
    }

    public int getBillsToday() {
        return billsToday;
    }

    public void setBillsToday(int billsToday) {
        this.billsToday = billsToday;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getLowStock() {
        return lowStock;
    }

    public void setLowStock(int lowStock) {
        this.lowStock = lowStock;
    }
}