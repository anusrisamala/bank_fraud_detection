package com.example.frauddetection.service;

import com.example.frauddetection.model.AnalyticsResponse;
import com.example.frauddetection.repository.AlertRepository;
import com.example.frauddetection.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    private final AlertRepository alertRepository;
    private final TransactionRepository transactionRepository;

    public AnalyticsService(AlertRepository alertRepository,
                            TransactionRepository transactionRepository) {
        this.alertRepository = alertRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Main Method Called By Controller
     */
    public AnalyticsResponse getAnalyticsData() {

        AnalyticsResponse response = new AnalyticsResponse();

        // =========================
        // 1️⃣ Summary Cards
        // =========================

        long totalTransactions = transactionRepository.countTotalTransactions();
        long totalFraudAlerts = alertRepository.countTotalAlerts();
        long highRiskAlerts = alertRepository.countHighRiskAlerts();

        response.setTotalTransactions(totalTransactions);
        response.setTotalFraudAlerts(totalFraudAlerts);
        response.setHighRiskAlerts(highRiskAlerts);

        // Fraud Rate Calculation
        double fraudRate = 0.0;
        if (totalTransactions > 0) {
            fraudRate = ((double) totalFraudAlerts / totalTransactions) * 100;
        }
        response.setFraudRate(Math.round(fraudRate * 100.0) / 100.0); // 2 decimal precision


        // =========================
        // 2️⃣ Fraud Trend (Last 24 Hours)
        // =========================

        Map<Integer, Long> trendData = alertRepository.getFraudTrendLast24Hours();

        List<Integer> hours = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            hours.add(i);
            counts.add(trendData.getOrDefault(i, 0L));
        }

        response.setFraudTrendHours(hours);
        response.setFraudTrendCounts(counts);


        // =========================
        // 3️⃣ Alerts By Rule
        // =========================

        Map<String, Long> alertsByRule = alertRepository.countAlertsByRule();
        response.setAlertsByRule(alertsByRule);


        // =========================
        // 4️⃣ Fraud vs Normal Transactions
        // =========================

        long fraudTransactions = transactionRepository.countFraudTransactions();
        long normalTransactions = totalTransactions - fraudTransactions;

        response.setFraudTransactions(fraudTransactions);
        response.setNormalTransactions(normalTransactions);


        // =========================
        // 5️⃣ Transactions By Location
        // =========================

        Map<String, Long> locationData = transactionRepository.countTransactionsByLocation();
        response.setTransactionsByLocation(locationData);


        // =========================
        // 6️⃣ Risk Score Distribution
        // =========================

        Map<String, Long> riskDistribution = alertRepository.countByRiskLevel();
        response.setRiskDistribution(riskDistribution);


        return response;
    }
}