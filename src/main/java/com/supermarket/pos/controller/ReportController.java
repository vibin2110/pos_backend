package com.supermarket.pos.controller;

import com.supermarket.pos.entity.SalesReport;
import com.supermarket.pos.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/daily-sales")
    public SalesReport getDailySales() {
        return reportService.getDailySalesReport();
    }
}