package com.example.frauddetection.repository;

import com.example.frauddetection.model.Alert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class AlertRepository {

    private final JdbcTemplate jdbcTemplate;

    public AlertRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SAVE ALERT
    public void save(Alert alert) {

        String sql = """
                INSERT INTO alerts
                (transaction_id, sender_id, risk_score, fraud_flag, reason, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                alert.getTransactionId(),
                alert.getSenderId(),
                alert.getRiskScore(),
                alert.isFraudFlag(),
                alert.getReason(),
                alert.getCreatedAt()
        );
    }

    // GET ALL ALERTS
    public List<Alert> findAll() {

        String sql = "SELECT * FROM alerts";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Alert alert = new Alert();
            alert.setAlertId(rs.getInt("alert_id"));
            alert.setTransactionId(rs.getLong("transaction_id"));
            alert.setSenderId(rs.getString("sender_id"));
            alert.setRiskScore(rs.getInt("risk_score"));
            alert.setFraudFlag(rs.getBoolean("fraud_flag"));
            alert.setReason(rs.getString("reason"));
            alert.setCreatedAt(rs.getString("created_at"));
            return alert;
        });
    }

    // HIGH RISK ALERTS
    public List<Alert> findHighRisk() {

        String sql = "SELECT * FROM alerts WHERE risk_score >= 70";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Alert alert = new Alert();
            alert.setAlertId(rs.getInt("alert_id"));
            alert.setTransactionId(rs.getLong("transaction_id"));
            alert.setSenderId(rs.getString("sender_id"));
            alert.setRiskScore(rs.getInt("risk_score"));
            alert.setFraudFlag(rs.getBoolean("fraud_flag"));
            alert.setReason(rs.getString("reason"));
            alert.setCreatedAt(rs.getString("created_at"));
            return alert;
        });
    }

    // FILTER ALERTS BY RULE
    public List<Alert> findByRule(String rule) {

        String sql = "SELECT * FROM alerts WHERE reason LIKE ?";

        return jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, "%" + rule + "%"),
                (rs, rowNum) -> {
                    Alert alert = new Alert();
                    alert.setAlertId(rs.getInt("alert_id"));
                    alert.setTransactionId(rs.getLong("transaction_id"));
                    alert.setSenderId(rs.getString("sender_id"));
                    alert.setRiskScore(rs.getInt("risk_score"));
                    alert.setFraudFlag(rs.getBoolean("fraud_flag"));
                    alert.setReason(rs.getString("reason"));
                    alert.setCreatedAt(rs.getString("created_at"));
                    return alert;
                }
        );
    }

    // COUNT HIGH RISK ALERTS (Dashboard metric)
    public int getHighRiskCount() {

        String sql = "SELECT COUNT(*) FROM alerts WHERE risk_score >= 80";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        return count != null ? count : 0;
    }

    // ALERTS BY RULE (Bar Chart)
    public List<Map<String, Object>> getAlertsByRule() {

        String sql = """
                SELECT reason AS rule, COUNT(*) AS count
                FROM alerts
                GROUP BY reason
                ORDER BY count DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Map<String, Object> row = new HashMap<>();

            row.put("rule", rs.getString("rule"));
            row.put("count", rs.getInt("count"));

            return row;
        });
    }

    // RECENT ALERTS (Dashboard Table)
    // RECENT ALERTS (Dashboard Table)
    public List<Map<String, Object>> getRecentAlerts() {

        String sql = """
            SELECT 
                a.alert_id,
                a.transaction_id,
                a.reason,
                a.risk_score,
                a.created_at,
                t.amount
            FROM alerts a
            LEFT JOIN transactions t
            ON a.transaction_id = t.transaction_id
            ORDER BY a.created_at DESC
            LIMIT 10
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Map<String, Object> alert = new HashMap<>();

            alert.put("alertId", rs.getInt("alert_id"));
            alert.put("transactionId", rs.getLong("transaction_id"));
            alert.put("rule", rs.getString("reason"));
            alert.put("riskScore", rs.getInt("risk_score"));
            alert.put("amount", rs.getObject("amount")); // can be null now
            alert.put("time", rs.getString("created_at"));

            return alert;
        });
    }
}