package com.example.frauddetection.controller;

import com.example.frauddetection.repository.AlertRepository;
import com.example.frauddetection.repository.TransactionRepository;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    private final TransactionRepository transactionRepository;
    private final AlertRepository alertRepository;

    public DashboardController(TransactionRepository transactionRepository,
                               AlertRepository alertRepository) {
        this.transactionRepository = transactionRepository;
        this.alertRepository = alertRepository;
    }

    // 1️⃣ Dashboard Metrics
    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {

        Map<String, Object> data = new HashMap<>();

        int totalTransactions = transactionRepository.getTotalTransactions();
        int fraudTransactions = transactionRepository.getFraudTransactions();
        int highRiskAlerts = alertRepository.getHighRiskCount();

        double fraudRate = 0;
        if (totalTransactions > 0) {
            fraudRate = (fraudTransactions * 100.0) / totalTransactions;
        }

        data.put("totalTransactions", totalTransactions);
        data.put("fraudAlerts", fraudTransactions);
        data.put("highRiskAlerts", highRiskAlerts);
        data.put("fraudRate", fraudRate);

        return data;
    }

    // 2️⃣ Fraud Trend Chart
    @GetMapping("/fraud-trend")
    public List<Map<String, Object>> getFraudTrend() {
        return transactionRepository.getFraudTrend();
    }

    // 3️⃣ Alerts By Rule (Bar Chart)
    @GetMapping("/alerts-by-rule")
    public List<Map<String, Object>> getAlertsByRule() {
        return alertRepository.getAlertsByRule();
    }

}