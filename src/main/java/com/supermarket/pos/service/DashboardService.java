package com.supermarket.pos.service;

import com.supermarket.pos.entity.DashboardStats;
import com.supermarket.pos.repository.InvoiceRepository;
import com.supermarket.pos.repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    public DashboardService(InvoiceRepository invoiceRepository,
                            ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    public DashboardStats getDashboardStats() {

        double todaySales = invoiceRepository.getTodaySales();
        int billsToday = invoiceRepository.countTodayBills();
        long totalProducts = productRepository.count();
        int lowStock = productRepository.findLowStockProducts().size();

        return new DashboardStats(
                todaySales,
                billsToday,
                totalProducts,
                lowStock
        );
    }
}