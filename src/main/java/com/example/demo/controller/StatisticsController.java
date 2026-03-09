package com.example.demo.controller;

import com.example.demo.service.TransactionService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // For React
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final TransactionService service;

    public StatisticsController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public Map<String, BigDecimal> getFullStatistics() {
        Map<String, BigDecimal> stats = new HashMap<>();
        stats.put("total", service.getTotalBalance());
        stats.put("today", service.getTodayTotal());
        stats.put("lastDay", service.getLast24HoursTotal());
        stats.put("thisWeek", service.getThisWeekTotal());
        stats.put("thisMonth", service.getThisMonthTotal());
        return stats;
    }

    @GetMapping("/categories")
    public Map<String, BigDecimal> getCategoriesStats() {
        return service.getExpensesByCategoryThisMonth();
    }

    @GetMapping("/categories/unique")
    public List<String> getUniqueCategories() {
        return service.getUniqueCategories();
    }
}