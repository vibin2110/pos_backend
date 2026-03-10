package com.supermarket.pos.controller;

import com.supermarket.pos.entity.DashboardStats;
import com.supermarket.pos.service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStats getStats() {
        return dashboardService.getDashboardStats();
    }
}