package com.example.frauddetection.controller;

import com.example.frauddetection.model.AnalyticsResponse;
import com.example.frauddetection.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    // Constructor Injection
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Main API for Analytics Page
     * Returns all analytics metrics needed by frontend
     */
    @GetMapping
    public AnalyticsResponse getAnalytics() {
        return analyticsService.getAnalyticsData();
    }

}