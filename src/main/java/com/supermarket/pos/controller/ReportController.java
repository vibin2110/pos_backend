package com.supermarket.pos.controller;

import com.supermarket.pos.entity.DailySalesReport;
import com.supermarket.pos.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Get daily sales analytics
    @GetMapping("/daily-sales")
    public List<DailySalesReport> getDailySales() {
        return reportService.getDailySalesReport();
    }
}