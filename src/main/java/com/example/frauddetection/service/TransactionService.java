package com.example.frauddetection.service;

import com.example.frauddetection.dto.TransactionFilterRequest;
import com.example.frauddetection.dto.TransactionResponse;
import com.example.frauddetection.dto.TransactionSummaryResponse;
import com.example.frauddetection.model.Transaction;
import com.example.frauddetection.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    // =========================================================
    // 1️⃣ GET TRANSACTIONS (FILTER + PAGINATION)
    // =========================================================

    public List<TransactionResponse> getTransactions(TransactionFilterRequest filter) {

        int offset = filter.getPage() * filter.getSize();

        List<Transaction> transactions = repository.findTransactionsWithFilters(
                filter.getSearch(),
                filter.getMinAmount(),
                filter.getMaxAmount(),
                filter.getLocation(),
                filter.getFraudStatus(),
                filter.getSize(),
                offset
        );

        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // 2️⃣ GET TRANSACTION BY ID
    // =========================================================

    public TransactionResponse getTransactionById(Long id) {

        Transaction transaction = repository.findByTransactionId(id);

        return convertToResponse(transaction);
    }

    // =========================================================
    // 3️⃣ SUMMARY CARDS
    // =========================================================

    public TransactionSummaryResponse getSummary() {

        int total = repository.getTotalTransactions();
        int today = repository.countTodayTransactions();
        int suspicious = repository.getFraudTransactions();

        return new TransactionSummaryResponse(total, today, suspicious);
    }

    // =========================================================
    // 🔄 ENTITY → DTO CONVERSION
    // =========================================================

    private TransactionResponse convertToResponse(Transaction t) {

        TransactionResponse response = new TransactionResponse();

        response.setTransactionId(String.valueOf(t.getTransactionId()));
        response.setSenderId(t.getSenderId());
        response.setReceiverId(t.getReceiverId());
        response.setAmount(t.getAmount());
        response.setLocation(t.getLocation());
        response.setMerchantName(t.getMerchantName());
        response.setTimestamp(java.time.LocalDateTime.parse(t.getTimestamp()));

        response.setFraudFlag(t.isFraudFlag() ? 1 : 0);
        response.setRiskScore(t.getRiskScore());
        response.setMlProbability(t.getMlProbability());
        response.setRuleScore(t.getRuleScore());

        response.setStatus(t.getStatus());
        response.setDeviceId(t.getDeviceId());

        return response;
    }
}