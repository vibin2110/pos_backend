package com.supermarket.pos.service;

import com.supermarket.pos.entity.DashboardStats;
import com.supermarket.pos.entity.Invoice;
import com.supermarket.pos.repository.ProductRepository;
import com.supermarket.pos.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductService productService;

    public DashboardStats getDashboardStats() {

        DashboardStats stats = new DashboardStats();

        // Total products
        stats.setTotalProducts(productRepository.count());

        // Low stock products
        stats.setLowStockProducts(productService.getLowStockProducts().size());

        // Total invoices
        stats.setTotalInvoices(invoiceRepository.count());

        // Calculate today's revenue
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Invoice> todayInvoices =
                invoiceRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        double revenue = todayInvoices
                .stream()
                .mapToDouble(Invoice::getTotalAmount)
                .sum();

        stats.setTodayRevenue(revenue);

        return stats;
    }
}