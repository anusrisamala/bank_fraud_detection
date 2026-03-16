package com.example.frauddetection.dto;

public class TransactionFilterRequest {

    private int page = 0;
    private int size = 10;

    private String search;          // transactionId / senderId / receiverId
    private Double minAmount;
    private Double maxAmount;
    private String location;
    private String fraudStatus = "ALL";   // ALL / NORMAL / FRAUD

    // ✅ Default Constructor
    public TransactionFilterRequest() {
    }

    // ✅ Getters and Setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    public void setFraudStatus(String fraudStatus) {
        this.fraudStatus = fraudStatus;
    }
}