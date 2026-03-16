package com.example.frauddetection.dto;

public class TransactionSummaryResponse {

    private int totalTransactions;
    private int todayTransactions;
    private int suspiciousTransactions;

    // ✅ Default Constructor
    public TransactionSummaryResponse() {
    }

    // ✅ All Args Constructor
    public TransactionSummaryResponse(int totalTransactions,
                                      int todayTransactions,
                                      int suspiciousTransactions) {
        this.totalTransactions = totalTransactions;
        this.todayTransactions = todayTransactions;
        this.suspiciousTransactions = suspiciousTransactions;
    }

    // ✅ Getters and Setters

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public int getTodayTransactions() {
        return todayTransactions;
    }

    public void setTodayTransactions(int todayTransactions) {
        this.todayTransactions = todayTransactions;
    }

    public int getSuspiciousTransactions() {
        return suspiciousTransactions;
    }

    public void setSuspiciousTransactions(int suspiciousTransactions) {
        this.suspiciousTransactions = suspiciousTransactions;
    }
}