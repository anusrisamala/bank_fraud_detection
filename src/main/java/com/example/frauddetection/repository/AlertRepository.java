//package com.example.frauddetection.repository;
//
//import com.example.frauddetection.model.Alert;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
//
//@Repository
//public class AlertRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public AlertRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    // SAVE ALERT
//    public void save(Alert alert) {
//
//        String sql = """
//                INSERT INTO alerts
//                (transaction_id, sender_id, risk_score, fraud_flag, reason, created_at)
//                VALUES (?, ?, ?, ?, ?, ?)
//                """;
//
//        jdbcTemplate.update(
//                sql,
//                alert.getTransactionId(),
//                alert.getSenderId(),
//                alert.getRiskScore(),
//                alert.isFraudFlag(),
//                alert.getReason(),
//                alert.getCreatedAt()
//        );
//    }
//
//    // GET ALL ALERTS
//    public List<Alert> findAll() {
//
//        String sql = "SELECT * FROM alerts";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            Alert alert = new Alert();
//            alert.setAlertId(rs.getInt("alert_id"));
//            alert.setTransactionId(rs.getLong("transaction_id"));
//            alert.setSenderId(rs.getString("sender_id"));
//            alert.setRiskScore(rs.getInt("risk_score"));
//            alert.setFraudFlag(rs.getBoolean("fraud_flag"));
//            alert.setReason(rs.getString("reason"));
//            alert.setCreatedAt(rs.getString("created_at"));
//            return alert;
//        });
//    }
//
//    // HIGH RISK ALERTS
//    public List<Alert> findHighRisk() {
//
//        String sql = "SELECT * FROM alerts WHERE risk_score >= 70";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            Alert alert = new Alert();
//            alert.setAlertId(rs.getInt("alert_id"));
//            alert.setTransactionId(rs.getLong("transaction_id"));
//            alert.setSenderId(rs.getString("sender_id"));
//            alert.setRiskScore(rs.getInt("risk_score"));
//            alert.setFraudFlag(rs.getBoolean("fraud_flag"));
//            alert.setReason(rs.getString("reason"));
//            alert.setCreatedAt(rs.getString("created_at"));
//            return alert;
//        });
//    }
//
//    // FILTER ALERTS BY RULE
//    public List<Alert> findByRule(String rule) {
//
//        String sql = "SELECT * FROM alerts WHERE reason LIKE ?";
//
//        return jdbcTemplate.query(
//                sql,
//                ps -> ps.setString(1, "%" + rule + "%"),
//                (rs, rowNum) -> {
//                    Alert alert = new Alert();
//                    alert.setAlertId(rs.getInt("alert_id"));
//                    alert.setTransactionId(rs.getLong("transaction_id"));
//                    alert.setSenderId(rs.getString("sender_id"));
//                    alert.setRiskScore(rs.getInt("risk_score"));
//                    alert.setFraudFlag(rs.getBoolean("fraud_flag"));
//                    alert.setReason(rs.getString("reason"));
//                    alert.setCreatedAt(rs.getString("created_at"));
//                    return alert;
//                }
//        );
//    }
//
//    // COUNT HIGH RISK ALERTS (Dashboard metric)
//    public int getHighRiskCount() {
//
//        String sql = "SELECT COUNT(*) FROM alerts WHERE risk_score >= 80";
//
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
//
//        return count != null ? count : 0;
//    }
//
//    // ALERTS BY RULE (Bar Chart)
//    public List<Map<String, Object>> getAlertsByRule() {
//
//        String sql = """
//                SELECT reason AS rule, COUNT(*) AS count
//                FROM alerts
//                GROUP BY reason
//                ORDER BY count DESC
//                """;
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//
//            Map<String, Object> row = new HashMap<>();
//
//            row.put("rule", rs.getString("rule"));
//            row.put("count", rs.getInt("count"));
//
//            return row;
//        });
//    }
//
//    // RECENT ALERTS (Dashboard Table)
//    // RECENT ALERTS (Dashboard Table)
//    public List<Map<String, Object>> getRecentAlerts() {
//
//        String sql = """
//            SELECT
//                a.alert_id,
//                a.transaction_id,
//                a.reason,
//                a.risk_score,
//                a.created_at,
//                t.amount
//            FROM alerts a
//            LEFT JOIN transactions t
//            ON a.transaction_id = t.transaction_id
//            ORDER BY a.created_at DESC
//            LIMIT 10
//            """;
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//
//            Map<String, Object> alert = new HashMap<>();
//
//            alert.put("alertId", rs.getInt("alert_id"));
//            alert.put("transactionId", rs.getLong("transaction_id"));
//            alert.put("rule", rs.getString("reason"));
//            alert.put("riskScore", rs.getInt("risk_score"));
//            alert.put("amount", rs.getObject("amount")); // can be null now
//            alert.put("time", rs.getString("created_at"));
//
//            return alert;
//        });
//    }
//}

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

        String sql = "SELECT * FROM alerts ORDER BY created_at DESC";

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

        String sql = "SELECT * FROM alerts WHERE risk_score >= 70 ORDER BY created_at DESC";

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

        String sql = "SELECT * FROM alerts WHERE reason LIKE ? ORDER BY created_at DESC";

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

    // TOTAL ALERTS COUNT
    public int getTotalAlertsCount() {

        String sql = "SELECT COUNT(*) FROM alerts";

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
            alert.put("amount", rs.getObject("amount"));
            alert.put("time", rs.getString("created_at"));

            return alert;
        });
    }

    // FULL ALERT LIST WITH TRANSACTION DETAILS (Alerts Page)
    public List<Map<String, Object>> getAlertsWithTransactionDetails() {

        String sql = """
            SELECT 
                a.alert_id,
                a.transaction_id,
                a.reason,
                a.risk_score,
                a.created_at,
                a.sender_id,
                t.amount,
                t.location,
                t.merchant_name,
                t.receiver_id,
                t.device_id
            FROM alerts a
            JOIN transactions t 
            ON a.transaction_id = t.transaction_id
            ORDER BY a.created_at DESC
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Map<String, Object> alert = new HashMap<>();

            alert.put("alertId", rs.getInt("alert_id"));
            alert.put("transactionId", rs.getLong("transaction_id"));
            alert.put("ruleTriggered", rs.getString("reason"));
            alert.put("riskScore", rs.getInt("risk_score"));
            alert.put("createdAt", rs.getString("created_at"));
            alert.put("senderId", rs.getString("sender_id"));

            alert.put("amount", rs.getObject("amount"));
            alert.put("location", rs.getString("location"));
            alert.put("merchantName", rs.getString("merchant_name"));
            alert.put("receiverId", rs.getString("receiver_id"));
            alert.put("deviceId", rs.getString("device_id"));

            return alert;
        });
    }

    // SEARCH ALERTS (ID / TRANSACTION / MERCHANT)
    public List<Map<String, Object>> searchAlerts(String keyword) {

        String sql = """
            SELECT 
                a.alert_id,
                a.transaction_id,
                a.reason,
                a.risk_score,
                a.created_at,
                a.sender_id,
                t.amount,
                t.location,
                t.merchant_name,
                t.receiver_id,
                t.device_id
            FROM alerts a
            JOIN transactions t
            ON a.transaction_id = t.transaction_id
            WHERE 
                a.alert_id LIKE ?
                OR a.transaction_id LIKE ?
                OR t.merchant_name LIKE ?
            ORDER BY a.created_at DESC
            """;

        String search = "%" + keyword + "%";

        return jdbcTemplate.query(
                sql,
                ps -> {
                    ps.setString(1, search);
                    ps.setString(2, search);
                    ps.setString(3, search);
                },
                (rs, rowNum) -> {

                    Map<String, Object> alert = new HashMap<>();

                    alert.put("alertId", rs.getInt("alert_id"));
                    alert.put("transactionId", rs.getLong("transaction_id"));
                    alert.put("ruleTriggered", rs.getString("reason"));
                    alert.put("riskScore", rs.getInt("risk_score"));
                    alert.put("createdAt", rs.getString("created_at"));
                    alert.put("senderId", rs.getString("sender_id"));

                    alert.put("amount", rs.getObject("amount"));
                    alert.put("location", rs.getString("location"));
                    alert.put("merchantName", rs.getString("merchant_name"));
                    alert.put("receiverId", rs.getString("receiver_id"));
                    alert.put("deviceId", rs.getString("device_id"));

                    return alert;
                }
        );
    }

    // GET ALERT BY ID
    public Map<String, Object> getAlertById(int id) {

        String sql = """
        SELECT 
            a.alert_id,
            a.transaction_id,
            a.reason,
            a.risk_score,
            a.created_at,
            a.sender_id,
            t.amount,
            t.location,
            t.merchant_name,
            t.receiver_id,
            t.device_id
        FROM alerts a
        JOIN transactions t
        ON a.transaction_id = t.transaction_id
        WHERE a.alert_id = ?
        """;

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {

            Map<String, Object> alert = new HashMap<>();

            alert.put("alertId", rs.getInt("alert_id"));
            alert.put("transactionId", rs.getLong("transaction_id"));
            alert.put("ruleTriggered", rs.getString("reason"));
            alert.put("riskScore", rs.getInt("risk_score"));
            alert.put("createdAt", rs.getString("created_at"));
            alert.put("senderId", rs.getString("sender_id"));

            alert.put("amount", rs.getObject("amount"));
            alert.put("location", rs.getString("location"));
            alert.put("merchantName", rs.getString("merchant_name"));
            alert.put("receiverId", rs.getString("receiver_id"));
            alert.put("deviceId", rs.getString("device_id"));

            return alert;
        });
    }


    // FILTER ALERTS BY RISK
    public List<Map<String, Object>> filterByRisk(int riskScore) {

        String sql = """
        SELECT 
            a.alert_id,
            a.transaction_id,
            a.reason,
            a.risk_score,
            a.created_at,
            a.sender_id,
            t.amount,
            t.location,
            t.merchant_name,
            t.receiver_id,
            t.device_id
        FROM alerts a
        JOIN transactions t
        ON a.transaction_id = t.transaction_id
        WHERE a.risk_score >= ?
        ORDER BY a.created_at DESC
        """;

        return jdbcTemplate.query(sql, new Object[]{riskScore}, (rs, rowNum) -> {

            Map<String, Object> alert = new HashMap<>();

            alert.put("alertId", rs.getInt("alert_id"));
            alert.put("transactionId", rs.getLong("transaction_id"));
            alert.put("ruleTriggered", rs.getString("reason"));
            alert.put("riskScore", rs.getInt("risk_score"));
            alert.put("createdAt", rs.getString("created_at"));
            alert.put("senderId", rs.getString("sender_id"));

            alert.put("amount", rs.getObject("amount"));
            alert.put("location", rs.getString("location"));
            alert.put("merchantName", rs.getString("merchant_name"));
            alert.put("receiverId", rs.getString("receiver_id"));
            alert.put("deviceId", rs.getString("device_id"));

            return alert;
        });
    }


    // PAGINATION FOR ALERTS
    public List<Map<String, Object>> getAlertsWithPagination(int page, int size) {

        int offset = page * size;

        String sql = """
        SELECT 
            a.alert_id,
            a.transaction_id,
            a.reason,
            a.risk_score,
            a.created_at,
            a.sender_id,
            t.amount,
            t.location,
            t.merchant_name,
            t.receiver_id,
            t.device_id
        FROM alerts a
        JOIN transactions t
        ON a.transaction_id = t.transaction_id
        ORDER BY a.created_at DESC
        LIMIT ? OFFSET ?
        """;

        return jdbcTemplate.query(sql, new Object[]{size, offset}, (rs, rowNum) -> {

            Map<String, Object> alert = new HashMap<>();

            alert.put("alertId", rs.getInt("alert_id"));
            alert.put("transactionId", rs.getLong("transaction_id"));
            alert.put("ruleTriggered", rs.getString("reason"));
            alert.put("riskScore", rs.getInt("risk_score"));
            alert.put("createdAt", rs.getString("created_at"));
            alert.put("senderId", rs.getString("sender_id"));

            alert.put("amount", rs.getObject("amount"));
            alert.put("location", rs.getString("location"));
            alert.put("merchantName", rs.getString("merchant_name"));
            alert.put("receiverId", rs.getString("receiver_id"));
            alert.put("deviceId", rs.getString("device_id"));

            return alert;
        });
    }

    public long countTotalAlerts() {

        String sql = "SELECT COUNT(*) FROM alerts";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count != null ? count : 0;
    }
    public long countHighRiskAlerts() {

        String sql = "SELECT COUNT(*) FROM alerts WHERE risk_score >= 80";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count != null ? count : 0;
    }
    public Map<String, Long> countAlertsByRule() {

        String sql = """
            SELECT reason, COUNT(*) as count
            FROM alerts
            GROUP BY reason
            """;

        Map<String, Long> result = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            result.put(rs.getString("reason"), rs.getLong("count"));
        });

        return result;
    }
    public Map<String, Long> countByRiskLevel() {

        String sql = """
        SELECT
            CASE
                WHEN risk_score >= 80 THEN 'High Risk'
                WHEN risk_score >= 50 THEN 'Medium Risk'
                ELSE 'Low Risk'
            END AS risk_level,
            COUNT(*) as count
        FROM alerts
        GROUP BY risk_level
        """;

        Map<String, Long> result = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            result.put(rs.getString("risk_level"), rs.getLong("count"));
        });

        return result;
    }
    public Map<Integer, Long> getFraudTrendLast24Hours() {

        String sql = """
        SELECT HOUR(created_at) as hour, COUNT(*) as count
        FROM alerts
        WHERE created_at >= NOW() - INTERVAL 24 HOUR
        GROUP BY HOUR(created_at)
        """;

        Map<Integer, Long> result = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            result.put(rs.getInt("hour"), rs.getLong("count"));
        });

        return result;
    }
}