//package com.example.frauddetection.controller;
//
//import com.example.frauddetection.model.Transaction;
//import com.example.frauddetection.repository.TransactionRepository;
//import com.example.frauddetection.service.DetectionService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/transactions")
//@CrossOrigin
//public class TransactionController {
//
//    private final TransactionRepository repository;
//    private final DetectionService detectionService;
//
//    public TransactionController(TransactionRepository repository,
//                                 DetectionService detectionService) {
//
//        this.repository = repository;
//        this.detectionService = detectionService;
//    }
//
//    // CHECK FRAUD (without saving)
//    @PostMapping("/check")
//    public Transaction checkFraud(@RequestBody Transaction transaction) {
//
//        int riskScore = detectionService.calculateRiskScore(transaction);
//
//        transaction.setFraudFlag(riskScore >= 70);
//
//        return transaction;
//    }
//
//    // SAVE TRANSACTION
//    @PostMapping
//    public String saveTransaction(@RequestBody Transaction transaction) {
//
//        transaction.setStatus("SUCCESS");
//
//        detectionService.calculateRiskScore(transaction);
//
//        repository.save(transaction);
//
//        return "Transaction saved. RiskScore=" +
//                transaction.getRiskScore() +
//                " Fraud=" +
//                transaction.isFraudFlag();
//    }
//
//    // 🔹 LIVE TRANSACTIONS (Dashboard feed)
//    @GetMapping("/live")
//    public List<Map<String, Object>> getLiveTransactions() {
//
//        return repository.getLiveTransactions();
//    }
//}
package com.example.frauddetection.controller;

import com.example.frauddetection.dto.TransactionFilterRequest;
import com.example.frauddetection.dto.TransactionResponse;
import com.example.frauddetection.dto.TransactionSummaryResponse;
import com.example.frauddetection.model.Transaction;
import com.example.frauddetection.repository.TransactionRepository;
import com.example.frauddetection.service.DetectionService;
import com.example.frauddetection.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    private final TransactionRepository repository;
    private final DetectionService detectionService;
    private final TransactionService transactionService;

    public TransactionController(TransactionRepository repository,
                                 DetectionService detectionService,
                                 TransactionService transactionService) {

        this.repository = repository;
        this.detectionService = detectionService;
        this.transactionService = transactionService;
    }

    // =========================================================
    // EXISTING ENDPOINTS (DO NOT TOUCH - USED BY OTHER PAGES)
    // =========================================================

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

    // LIVE TRANSACTIONS (Dashboard feed)
    @GetMapping("/live")
    public List<Map<String, Object>> getLiveTransactions() {
        return repository.getLiveTransactions();
    }

    // =========================================================
    // NEW ENDPOINTS (TRANSACTIONS PAGE)
    // =========================================================

    // 1️⃣ GET ALL TRANSACTIONS (FILTER + PAGINATION)
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "ALL") String fraudStatus
    ) {

        TransactionFilterRequest filter = new TransactionFilterRequest();
        filter.setPage(page);
        filter.setSize(size);
        filter.setSearch(search);
        filter.setMinAmount(minAmount);
        filter.setMaxAmount(maxAmount);
        filter.setLocation(location);
        filter.setFraudStatus(fraudStatus);

        List<TransactionResponse> transactions =
                transactionService.getTransactions(filter);

        return ResponseEntity.ok(transactions);
    }

    // 2️⃣ GET TRANSACTION BY ID (MODAL VIEW)
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable Long id
    ) {

        TransactionResponse response =
                transactionService.getTransactionById(id);

        return ResponseEntity.ok(response);
    }

    // 3️⃣ SUMMARY CARDS
    @GetMapping("/summary")
    public ResponseEntity<TransactionSummaryResponse> getSummary() {

        TransactionSummaryResponse summary =
                transactionService.getSummary();

        return ResponseEntity.ok(summary);
    }
}