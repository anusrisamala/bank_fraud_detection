package com.example.frauddetection.service;

import com.example.frauddetection.model.Alert;
import com.example.frauddetection.model.Transaction;
import com.example.frauddetection.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    // 1️⃣ All Alerts
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // 2️⃣ High Risk Alerts
    public List<Alert> getHighRiskAlerts() {
        return alertRepository.findHighRisk();
    }

    // 3️⃣ Alerts By Rule
    public List<Alert> getAlertsByRule(String rule) {
        return alertRepository.findByRule(rule);
    }

    // 4️⃣ Recent Alerts (Dashboard Table)
    public List<Map<String, Object>> getRecentAlerts() {
        return alertRepository.getRecentAlerts();
    }

    // 5️⃣ Create Alert (DetectionService)
    public void createAlert(Transaction transaction, String reason) {

        Alert alert = new Alert();

        alert.setTransactionId(transaction.getTransactionId());
        alert.setSenderId(transaction.getSenderId());
        alert.setRiskScore(transaction.getRiskScore());
        alert.setFraudFlag(transaction.isFraudFlag());
        alert.setReason(reason);
        alert.setCreatedAt(java.time.LocalDateTime.now().toString());

        alertRepository.save(alert);
    }
}