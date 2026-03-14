//package com.example.frauddetection.service;
//
//import com.example.frauddetection.model.Alert;
//import com.example.frauddetection.model.Transaction;
//import com.example.frauddetection.repository.AlertRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class AlertService {
//
//    private final AlertRepository alertRepository;
//
//    public AlertService(AlertRepository alertRepository) {
//        this.alertRepository = alertRepository;
//    }
//
//    // 1️⃣ All Alerts
//    public List<Alert> getAllAlerts() {
//        return alertRepository.findAll();
//    }
//
//    // 2️⃣ High Risk Alerts
//    public List<Alert> getHighRiskAlerts() {
//        return alertRepository.findHighRisk();
//    }
//
//    // 3️⃣ Alerts By Rule
//    public List<Alert> getAlertsByRule(String rule) {
//        return alertRepository.findByRule(rule);
//    }
//
//    // 4️⃣ Recent Alerts (Dashboard Table)
//    public List<Map<String, Object>> getRecentAlerts() {
//        return alertRepository.getRecentAlerts();
//    }
//
//    // 5️⃣ Create Alert (DetectionService)
//    public void createAlert(Transaction transaction, String reason) {
//
//        Alert alert = new Alert();
//
//        alert.setTransactionId(transaction.getTransactionId());
//        alert.setSenderId(transaction.getSenderId());
//        alert.setRiskScore(transaction.getRiskScore());
//        alert.setFraudFlag(transaction.isFraudFlag());
//        alert.setReason(reason);
//        alert.setCreatedAt(java.time.LocalDateTime.now().toString());
//
//        alertRepository.save(alert);
//    }
//}

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

    // 1️⃣ Get all alerts (Alerts page table)
    public List<Map<String, Object>> getAlerts() {
        return alertRepository.getAlertsWithTransactionDetails();
    }

    // 2️⃣ Get all alerts basic
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // 3️⃣ Get alert by ID
    public Map<String, Object> getAlertById(int id) {
        return alertRepository.getAlertById(id);
    }

    // 4️⃣ High risk alerts
    public List<Alert> getHighRiskAlerts() {
        return alertRepository.findHighRisk();
    }

    // 5️⃣ Filter alerts by rule
    public List<Alert> getAlertsByRule(String rule) {
        return alertRepository.findByRule(rule);
    }

    // 6️⃣ Filter alerts by risk score
    public List<Map<String, Object>> filterByRisk(int riskScore) {
        return alertRepository.filterByRisk(riskScore);
    }

    // 7️⃣ Search alerts (ID / merchant / transaction)
    public List<Map<String, Object>> searchAlerts(String keyword) {
        return alertRepository.searchAlerts(keyword);
    }

    // 8️⃣ Pagination for alerts
    public List<Map<String, Object>> getAlertsWithPagination(int page, int size) {
        return alertRepository.getAlertsWithPagination(page, size);
    }

    // 9️⃣ Recent alerts (Dashboard table)
    public List<Map<String, Object>> getRecentAlerts() {
        return alertRepository.getRecentAlerts();
    }

    // 🔟 Total alerts count (Dashboard card)
    public int getTotalAlertsCount() {
        return alertRepository.getTotalAlertsCount();
    }

    // 1️⃣1️⃣ High risk alerts count (Dashboard card)
    public int getHighRiskAlertsCount() {
        return alertRepository.getHighRiskCount();
    }

    // 1️⃣2️⃣ Alerts grouped by rule (Dashboard chart)
    public List<Map<String, Object>> getAlertsByRuleChart() {
        return alertRepository.getAlertsByRule();
    }

    // 1️⃣3️⃣ Create alert (called by DetectionService)
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

