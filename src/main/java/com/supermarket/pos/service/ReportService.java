package com.supermarket.pos.service;

import com.supermarket.pos.entity.SalesReport;
import com.supermarket.pos.entity.DailySalesReport;
import com.supermarket.pos.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private InvoiceRepository invoiceRepository;


    // Existing method (kept exactly as before)
    public List<SalesReport> getProductSalesReport() {

        List<Object[]> results = invoiceRepository.getDailySales();

        List<SalesReport> reports = new ArrayList<>();

        for (Object[] row : results) {

            SalesReport report = new SalesReport();

            if (row[0] != null) {
                report.setDate(row[0].toString());
            }

            if (row[1] != null) {
                report.setTotalSales(((Number) row[1]).doubleValue());
            }

            reports.add(report);
        }

        return reports;
    }


    // ⭐ NEW method for daily sales analytics
    public List<DailySalesReport> getDailySalesReport() {

        List<Object[]> results = invoiceRepository.getDailySales();

        List<DailySalesReport> reports = new ArrayList<>();

        for (Object[] row : results) {

            DailySalesReport report = new DailySalesReport();

            if (row[0] != null) {
                report.setDate(row[0].toString());
            }

            if (row[1] != null) {
                report.setTotalSales(((Number) row[1]).doubleValue());
            }

            reports.add(report);
        }

        return reports;
    }
}