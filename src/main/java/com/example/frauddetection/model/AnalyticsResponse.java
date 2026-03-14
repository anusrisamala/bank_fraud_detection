package com.example.frauddetection.model;

import java.util.List;
import java.util.Map;

public class AnalyticsResponse {

    // Top Summary Cards
    private long totalTransactions;
    private long totalFraudAlerts;
    private double fraudRate;
    private long highRiskAlerts;

    // Fraud Trend (Last 24 Hours)
    private List<Integer> fraudTrendHours;      // e.g. [1,2,3...23]
    private List<Long> fraudTrendCounts;        // e.g. [5,8,3...]

    // Alerts Grouped by Rule
    private Map<String, Long> alertsByRule;

    // Fraud vs Normal
    private long fraudTransactions;
    private long normalTransactions;

    // Transactions by Location
    private Map<String, Long> transactionsByLocation;

    // Risk Score Distribution
    private Map<String, Long> riskDistribution;

    // Constructors
    public AnalyticsResponse() {
    }

    // =========================
    // Getters and Setters
    // =========================

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public long getTotalFraudAlerts() {
        return totalFraudAlerts;
    }

    public void setTotalFraudAlerts(long totalFraudAlerts) {
        this.totalFraudAlerts = totalFraudAlerts;
    }

    public double getFraudRate() {
        return fraudRate;
    }

    public void setFraudRate(double fraudRate) {
        this.fraudRate = fraudRate;
    }

    public long getHighRiskAlerts() {
        return highRiskAlerts;
    }

    public void setHighRiskAlerts(long highRiskAlerts) {
        this.highRiskAlerts = highRiskAlerts;
    }

    public List<Integer> getFraudTrendHours() {
        return fraudTrendHours;
    }

    public void setFraudTrendHours(List<Integer> fraudTrendHours) {
        this.fraudTrendHours = fraudTrendHours;
    }

    public List<Long> getFraudTrendCounts() {
        return fraudTrendCounts;
    }

    public void setFraudTrendCounts(List<Long> fraudTrendCounts) {
        this.fraudTrendCounts = fraudTrendCounts;
    }

    public Map<String, Long> getAlertsByRule() {
        return alertsByRule;
    }

    public void setAlertsByRule(Map<String, Long> alertsByRule) {
        this.alertsByRule = alertsByRule;
    }

    public long getFraudTransactions() {
        return fraudTransactions;
    }

    public void setFraudTransactions(long fraudTransactions) {
        this.fraudTransactions = fraudTransactions;
    }

    public long getNormalTransactions() {
        return normalTransactions;
    }

    public void setNormalTransactions(long normalTransactions) {
        this.normalTransactions = normalTransactions;
    }

    public Map<String, Long> getTransactionsByLocation() {
        return transactionsByLocation;
    }

    public void setTransactionsByLocation(Map<String, Long> transactionsByLocation) {
        this.transactionsByLocation = transactionsByLocation;
    }

    public Map<String, Long> getRiskDistribution() {
        return riskDistribution;
    }

    public void setRiskDistribution(Map<String, Long> riskDistribution) {
        this.riskDistribution = riskDistribution;
    }
}