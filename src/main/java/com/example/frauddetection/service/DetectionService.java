package com.example.frauddetection.service;

import com.example.frauddetection.model.MLRequest;
import com.example.frauddetection.model.MLResponse;
import com.example.frauddetection.model.Transaction;
import com.example.frauddetection.repository.TransactionRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DetectionService {

    private final TransactionRepository repository;
    private final AlertService alertService;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public DetectionService(TransactionRepository repository,
                            AlertService alertService) {
        this.repository = repository;
        this.alertService = alertService;
    }

    private LocalDateTime parseTimestamp(String timestamp) {

        try {
            return LocalDateTime.parse(timestamp);
        } catch (Exception e) {
            return LocalDateTime.parse(timestamp, formatter);
        }
    }

    public int calculateRiskScore(Transaction transaction) {

        // ⭐ FIX 1: SAVE TRANSACTION FIRST so transaction_id is generated
        repository.save(transaction);

        int score = 0;
        StringBuilder reason = new StringBuilder();

        // RULE 1: High value
        if (transaction.getAmount() > 50000) {
            score += 50;
            reason.append("High value transaction; ");
        }

        // RULE 2: Suspicious merchant
        if ("Unknown".equalsIgnoreCase(transaction.getMerchantName())) {
            score += 30;
            reason.append("Suspicious merchant; ");
        }

        // RULE 3: Odd hours
        try {

            LocalDateTime time =
                    parseTimestamp(transaction.getTimestamp());

            int hour = time.getHour();

            if (hour >= 0 && hour <= 5) {
                score += 20;
                reason.append("Odd hour transaction; ");
            }

        } catch (Exception e) {
            System.out.println("Timestamp format issue");
        }

        List<Transaction> userTransactions =
                repository.findBySenderId(transaction.getSenderId());

        // RULE 4: Rapid transactions
        if (userTransactions.size() >= 3) {
            score += 20;
            reason.append("Rapid multiple transactions; ");
        }

        // RULE 5: Location mismatch
        if (!userTransactions.isEmpty()) {

            String lastLocation =
                    userTransactions.get(userTransactions.size() - 1).getLocation();

            if (!lastLocation.equals(transaction.getLocation())) {
                score += 25;
                reason.append("Location mismatch; ");
            }
        }

        // RULE 6: New location
        score += checkNewLocation(transaction, userTransactions, reason);

        long gap = 30;

        if (!userTransactions.isEmpty()) {

            Transaction lastTxn =
                    userTransactions.get(userTransactions.size() - 1);

            try {

                LocalDateTime lastTime =
                        parseTimestamp(lastTxn.getTimestamp());

                LocalDateTime currentTime =
                        parseTimestamp(transaction.getTimestamp());

                gap = Duration.between(lastTime, currentTime).getSeconds();

            } catch (Exception e) {
                gap = 30;
            }
        }

        transaction.setTxnGap(gap);
        transaction.setRuleScore(score);

        double mlProbability = getMLProbability(transaction);
        transaction.setMlProbability(mlProbability);

        transaction.setRiskScore(score);

        boolean fraudDetected = (score >= 70 || mlProbability >= 0.40);

        transaction.setFraudFlag(fraudDetected);

        if (fraudDetected) {

            alertService.createAlert(transaction, reason.toString());

            System.out.println("ALERT CREATED for transaction: "
                    + transaction.getTransactionId());
        }

        // ⭐ FIX 2: UPDATE TRANSACTION WITH NEW VALUES
        repository.save(transaction);

        return score;
    }

    private int checkNewLocation(Transaction transaction,
                                 List<Transaction> userTransactions,
                                 StringBuilder reason) {

        boolean knownLocation = userTransactions.stream()
                .anyMatch(tx -> tx.getLocation()
                        .equalsIgnoreCase(transaction.getLocation()));

        if (!knownLocation && !userTransactions.isEmpty()) {
            reason.append("New location detected; ");
            return 20;
        }

        return 0;
    }

    public double getMLProbability(Transaction transaction) {

        String url = "http://localhost:8000/predict";

        MLRequest request = new MLRequest();

        request.sender_id =
                Integer.parseInt(transaction.getSenderId().replace("USER", ""));

        request.amount = transaction.getAmount();

        request.device_id = 1;
        request.location = 1;
        request.transaction_type = 1;

        request.hour =
                parseTimestamp(transaction.getTimestamp()).getHour();

        request.txn_frequency = 5;
        request.user_avg_amount = 5000;

        request.amount_vs_avg =
                transaction.getAmount() / 5000;

        request.device_change = 0;
        request.location_change = 0;

        request.merchant_category = 1;

        request.txn_gap = transaction.getTxnGap();
        request.rule_score = transaction.getRuleScore();

        try {

            ResponseEntity<MLResponse> response =
                    restTemplate.postForEntity(url, request, MLResponse.class);

            return response.getBody().getFraud_probability();

        } catch (Exception e) {

            System.out.println("ML API error: " + e.getMessage());
            return 0.0;
        }
    }
}