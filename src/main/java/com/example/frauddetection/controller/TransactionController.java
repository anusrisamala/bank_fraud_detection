package com.example.frauddetection.controller;

import com.example.frauddetection.model.Transaction;
import com.example.frauddetection.repository.TransactionRepository;
import com.example.frauddetection.service.DetectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    private final TransactionRepository repository;
    private final DetectionService detectionService;

    public TransactionController(TransactionRepository repository,
                                 DetectionService detectionService) {

        this.repository = repository;
        this.detectionService = detectionService;
    }

    // CHECK FRAUD (without saving)
    @PostMapping("/check")
    public Transaction checkFraud(@RequestBody Transaction transaction) {

        int riskScore = detectionService.calculateRiskScore(transaction);

        transaction.setFraudFlag(riskScore >= 70);

        return transaction;
    }

    // SAVE TRANSACTION
    @PostMapping
    public String saveTransaction(@RequestBody Transaction transaction) {

        transaction.setStatus("SUCCESS");

        detectionService.calculateRiskScore(transaction);

        repository.save(transaction);

        return "Transaction saved. RiskScore=" +
                transaction.getRiskScore() +
                " Fraud=" +
                transaction.isFraudFlag();
    }

    // 🔹 LIVE TRANSACTIONS (Dashboard feed)
    @GetMapping("/live")
    public List<Map<String, Object>> getLiveTransactions() {

        return repository.getLiveTransactions();
    }
}