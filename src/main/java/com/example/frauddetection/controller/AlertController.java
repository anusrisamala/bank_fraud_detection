package com.example.frauddetection.controller;

import com.example.frauddetection.model.Alert;
import com.example.frauddetection.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "http://localhost:5173")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // 1️⃣ GET ALL ALERTS (used by alerts page)
    @GetMapping
    public List<Map<String, Object>> getAlerts() {
        return alertService.getAlerts();
    }

    // 2️⃣ SEARCH ALERTS
    // Example: /api/alerts/search?query=amazon
    @GetMapping("/search")
    public List<Map<String, Object>> searchAlerts(@RequestParam String query) {
        return alertService.searchAlerts(query);
    }

    // 3️⃣ FILTER BY RULE
    // Example: /api/alerts/rule?rule=location
    @GetMapping("/rule")
    public List<Alert> filterByRule(@RequestParam String rule) {
        return alertService.getAlertsByRule(rule);
    }

    // 4️⃣ FILTER BY RISK
    // Example: /api/alerts/risk?score=80
    @GetMapping("/risk")
    public List<Map<String, Object>> filterByRisk(@RequestParam int score) {
        return alertService.filterByRisk(score);
    }

    // 5️⃣ PAGINATION
    // Example: /api/alerts/page?page=0&size=10
    @GetMapping("/page")
    public List<Map<String, Object>> getAlertsWithPagination(
            @RequestParam int page,
            @RequestParam int size) {
        return alertService.getAlertsWithPagination(page, size);
    }

    // 6️⃣ HIGH RISK ALERTS
    @GetMapping("/high-risk")
    public List<Alert> getHighRiskAlerts() {
        return alertService.getHighRiskAlerts();
    }

    // 7️⃣ TOTAL ALERTS COUNT (Dashboard card)
    @GetMapping("/count")
    public int getTotalAlertsCount() {
        return alertService.getTotalAlertsCount();
    }

    // 8️⃣ HIGH RISK COUNT (Dashboard card)
    @GetMapping("/high-risk/count")
    public int getHighRiskAlertsCount() {
        return alertService.getHighRiskAlertsCount();
    }

    // 9️⃣ RECENT ALERTS (Dashboard table)
    @GetMapping("/recent")
    public List<Map<String, Object>> getRecentAlerts() {
        return alertService.getRecentAlerts();
    }

    // 🔟 GET ALERT BY ID (keep this LAST to avoid path conflicts)
    @GetMapping("/{id}")
    public Map<String, Object> getAlertById(@PathVariable int id) {
        return alertService.getAlertById(id);
    }
}