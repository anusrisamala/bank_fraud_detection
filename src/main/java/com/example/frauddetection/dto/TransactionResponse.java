package com.example.frauddetection.dto;

import java.time.LocalDateTime;

public class TransactionResponse {

    private String transactionId;
    private String senderId;
    private String receiverId;
    private double amount;
    private String location;
    private String merchantName;
    private LocalDateTime timestamp;

    private int fraudFlag;          // 0 or 1 (from DB)
    private double riskScore;
    private double mlProbability;
    private double ruleScore;

    private String status;          // SUCCESS / FAILED etc.
    private String deviceId;

    // ✅ Default Constructor
    public TransactionResponse() {
    }

    // ✅ All Args Constructor
    public TransactionResponse(String transactionId,
                               String senderId,
                               String receiverId,
                               double amount,
                               String location,
                               String merchantName,
                               LocalDateTime timestamp,
                               int fraudFlag,
                               double riskScore,
                               double mlProbability,
                               double ruleScore,
                               String status,
                               String deviceId) {

        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.location = location;
        this.merchantName = merchantName;
        this.timestamp = timestamp;
        this.fraudFlag = fraudFlag;
        this.riskScore = riskScore;
        this.mlProbability = mlProbability;
        this.ruleScore = ruleScore;
        this.status = status;
        this.deviceId = deviceId;
    }

    // ✅ Getters and Setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getFraudFlag() {
        return fraudFlag;
    }

    public void setFraudFlag(int fraudFlag) {
        this.fraudFlag = fraudFlag;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public double getMlProbability() {
        return mlProbability;
    }

    public void setMlProbability(double mlProbability) {
        this.mlProbability = mlProbability;
    }

    public double getRuleScore() {
        return ruleScore;
    }

    public void setRuleScore(double ruleScore) {
        this.ruleScore = ruleScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}