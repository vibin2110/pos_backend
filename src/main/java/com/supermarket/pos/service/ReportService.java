package com.supermarket.pos.service;

import com.supermarket.pos.entity.Invoice;
import com.supermarket.pos.entity.SalesReport;
import com.supermarket.pos.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public SalesReport getDailySalesReport() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        List<Invoice> invoices =
                invoiceRepository.findByCreatedAtBetween(start, end);

        double revenue = invoices.stream()
                .mapToDouble(Invoice::getTotalAmount)
                .sum();

        SalesReport report = new SalesReport();
        report.setDate(today);
        report.setTotalInvoices(invoices.size());
        report.setTotalRevenue(revenue);

        return report;
    }
}