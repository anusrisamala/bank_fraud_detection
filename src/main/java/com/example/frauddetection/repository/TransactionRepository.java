//package com.example.frauddetection.repository;
//
//import com.example.frauddetection.model.Transaction;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
//
//@Repository
//public class TransactionRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public TransactionRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    // SAVE TRANSACTION (GENERATE transaction_id)
//    public void save(Transaction transaction) {
//
//        String sql = """
//                INSERT INTO transactions
//                (sender_id, receiver_id, amount, timestamp,
//                 location, device_id, merchant_name, transaction_type,
//                 status, risk_score, fraud_flag, ml_probability, rule_score, txn_gap)
//                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                """;
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//
//            PreparedStatement ps = connection.prepareStatement(
//                    sql,
//                    Statement.RETURN_GENERATED_KEYS
//            );
//
//            ps.setString(1, transaction.getSenderId());
//            ps.setString(2, transaction.getReceiverId());
//            ps.setDouble(3, transaction.getAmount());
//            ps.setString(4, transaction.getTimestamp());
//            ps.setString(5, transaction.getLocation());
//            ps.setString(6, transaction.getDeviceId());
//            ps.setString(7, transaction.getMerchantName());
//            ps.setString(8, transaction.getTransactionType());
//            ps.setString(9, transaction.getStatus());
//            ps.setInt(10, transaction.getRiskScore());
//            ps.setBoolean(11, transaction.isFraudFlag());
//            ps.setDouble(12, transaction.getMlProbability());
//            ps.setInt(13, transaction.getRuleScore());
//            ps.setLong(14, transaction.getTxnGap());
//
//            return ps;
//
//        }, keyHolder);
//
//        Number key = keyHolder.getKey();
//
//        if (key != null) {
//            transaction.setTransactionId(key.longValue());
//        }
//    }
//
//    // FIND USER TRANSACTIONS
//    public List<Transaction> findBySenderId(String senderId) {
//
//        String sql = """
//                SELECT *
//                FROM transactions
//                WHERE sender_id = ?
//                ORDER BY timestamp
//                """;
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//
//            Transaction t = new Transaction();
//
//            t.setTransactionId(rs.getLong("transaction_id"));
//            t.setSenderId(rs.getString("sender_id"));
//            t.setReceiverId(rs.getString("receiver_id"));
//            t.setAmount(rs.getDouble("amount"));
//            t.setTimestamp(rs.getString("timestamp"));
//            t.setLocation(rs.getString("location"));
//            t.setDeviceId(rs.getString("device_id"));
//            t.setMerchantName(rs.getString("merchant_name"));
//            t.setTransactionType(rs.getString("transaction_type"));
//            t.setStatus(rs.getString("status"));
//            t.setRiskScore(rs.getInt("risk_score"));
//            t.setFraudFlag(rs.getBoolean("fraud_flag"));
//            t.setMlProbability(rs.getDouble("ml_probability"));
//            t.setRuleScore(rs.getInt("rule_score"));
//            t.setTxnGap(rs.getLong("txn_gap"));
//
//            return t;
//
//        }, senderId);
//    }
//
//    // TOTAL TRANSACTIONS
//    public int getTotalTransactions() {
//
//        String sql = "SELECT COUNT(*) FROM transactions";
//
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
//
//        return count != null ? count : 0;
//    }
//
//    // FRAUD TRANSACTIONS COUNT
//    public int getFraudTransactions() {
//
//        String sql = "SELECT COUNT(*) FROM transactions WHERE fraud_flag = true";
//
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
//
//        return count != null ? count : 0;
//    }
//
//    // LIVE TRANSACTIONS (Dashboard)
//    public List<Map<String, Object>> getLiveTransactions() {
//
//        String sql = """
//                SELECT transaction_id, amount, location
//                FROM transactions
//                ORDER BY timestamp DESC
//                LIMIT 5
//                """;
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//
//            Map<String, Object> txn = new HashMap<>();
//
//            txn.put("id", rs.getLong("transaction_id"));
//            txn.put("amount", rs.getDouble("amount"));
//            txn.put("location", rs.getString("location"));
//
//            return txn;
//        });
//    }
//
//    // FRAUD TREND (Last 24 Hours)
//    public List<Map<String, Object>> getFraudTrend() {
//
//        String sql = """
//                SELECT
//                    DATE_FORMAT(timestamp, '%H:00') AS hour,
//                    COUNT(*) AS transactions,
//                    SUM(CASE WHEN fraud_flag = true THEN 1 ELSE 0 END) AS fraudAlerts
//                FROM transactions
//                WHERE timestamp >= NOW() - INTERVAL 1 DAY
//                GROUP BY hour
//                ORDER BY hour
//                """;
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//
//            Map<String, Object> row = new HashMap<>();
//
//            row.put("hour", rs.getString("hour"));
//            row.put("transactions", rs.getInt("transactions"));
//            row.put("fraudAlerts", rs.getInt("fraudAlerts"));
//
//            return row;
//        });
//    }
//}
package com.example.frauddetection.repository;

import com.example.frauddetection.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SAVE TRANSACTION (GENERATE transaction_id)
    public void save(Transaction transaction) {

        String sql = """
                INSERT INTO transactions
                (sender_id, receiver_id, amount, timestamp,
                 location, device_id, merchant_name, transaction_type,
                 status, risk_score, fraud_flag, ml_probability, rule_score, txn_gap)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, transaction.getSenderId());
            ps.setString(2, transaction.getReceiverId());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getTimestamp());
            ps.setString(5, transaction.getLocation());
            ps.setString(6, transaction.getDeviceId());
            ps.setString(7, transaction.getMerchantName());
            ps.setString(8, transaction.getTransactionType());
            ps.setString(9, transaction.getStatus());
            ps.setInt(10, transaction.getRiskScore());
            ps.setBoolean(11, transaction.isFraudFlag());
            ps.setDouble(12, transaction.getMlProbability());
            ps.setInt(13, transaction.getRuleScore());
            ps.setLong(14, transaction.getTxnGap());

            return ps;

        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key != null) {
            transaction.setTransactionId(key.longValue());
        }
    }

    // FIND USER TRANSACTIONS
    public List<Transaction> findBySenderId(String senderId) {

        String sql = """
                SELECT *
                FROM transactions
                WHERE sender_id = ?
                ORDER BY timestamp
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Transaction t = new Transaction();

            t.setTransactionId(rs.getLong("transaction_id"));
            t.setSenderId(rs.getString("sender_id"));
            t.setReceiverId(rs.getString("receiver_id"));
            t.setAmount(rs.getDouble("amount"));
            t.setTimestamp(rs.getString("timestamp"));
            t.setLocation(rs.getString("location"));
            t.setDeviceId(rs.getString("device_id"));
            t.setMerchantName(rs.getString("merchant_name"));
            t.setTransactionType(rs.getString("transaction_type"));
            t.setStatus(rs.getString("status"));
            t.setRiskScore(rs.getInt("risk_score"));
            t.setFraudFlag(rs.getBoolean("fraud_flag"));
            t.setMlProbability(rs.getDouble("ml_probability"));
            t.setRuleScore(rs.getInt("rule_score"));
            t.setTxnGap(rs.getLong("txn_gap"));

            return t;

        }, senderId);
    }

    // TOTAL TRANSACTIONS
    public int getTotalTransactions() {

        String sql = "SELECT COUNT(*) FROM transactions";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        return count != null ? count : 0;
    }

    // FRAUD TRANSACTIONS COUNT
    public int getFraudTransactions() {

        String sql = "SELECT COUNT(*) FROM transactions WHERE fraud_flag = true";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        return count != null ? count : 0;
    }

    // LIVE TRANSACTIONS (Dashboard)
    public List<Map<String, Object>> getLiveTransactions() {

        String sql = """
                SELECT transaction_id, amount, location
                FROM transactions
                ORDER BY timestamp DESC
                LIMIT 5
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Map<String, Object> txn = new HashMap<>();

            txn.put("id", rs.getLong("transaction_id"));
            txn.put("amount", rs.getDouble("amount"));
            txn.put("location", rs.getString("location"));

            return txn;
        });
    }

    // FRAUD TREND (Last 24 Hours)
    public List<Map<String, Object>> getFraudTrend() {

        String sql = """
                SELECT 
                    DATE_FORMAT(timestamp, '%H:00') AS hour,
                    COUNT(*) AS transactions,
                    SUM(CASE WHEN fraud_flag = true THEN 1 ELSE 0 END) AS fraudAlerts
                FROM transactions
                WHERE timestamp >= NOW() - INTERVAL 1 DAY
                GROUP BY hour
                ORDER BY hour
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Map<String, Object> row = new HashMap<>();

            row.put("hour", rs.getString("hour"));
            row.put("transactions", rs.getInt("transactions"));
            row.put("fraudAlerts", rs.getInt("fraudAlerts"));

            return row;
        });
    }
    public long countTotalTransactions() {

        String sql = "SELECT COUNT(*) FROM transactions";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count != null ? count : 0;
    }
    public long countFraudTransactions() {

        String sql = "SELECT COUNT(*) FROM transactions WHERE fraud_flag = true";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count != null ? count : 0;
    }
    public Map<String, Long> countTransactionsByLocation() {

        String sql = """
            SELECT location, COUNT(*) as count
            FROM transactions
            GROUP BY location
            ORDER BY count DESC
            """;

        Map<String, Long> result = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            result.put(rs.getString("location"), rs.getLong("count"));
        });

        return result;
    }
    // TRANSACTIONS PAGE - FILTER + PAGINATION
    public List<Transaction> findTransactionsWithFilters(
            String search,
            Double minAmount,
            Double maxAmount,
            String location,
            String fraudStatus,
            int limit,
            int offset
    ) {

        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE 1=1 ");
        new Object() {}; // just to separate visually

        List<Object> params = new java.util.ArrayList<>();

        if (search != null && !search.isBlank()) {
            sql.append(" AND (transaction_id LIKE ? OR sender_id LIKE ? OR receiver_id LIKE ?)");
            String searchValue = "%" + search + "%";
            params.add(searchValue);
            params.add(searchValue);
            params.add(searchValue);
        }

        if (minAmount != null) {
            sql.append(" AND amount >= ?");
            params.add(minAmount);
        }

        if (maxAmount != null) {
            sql.append(" AND amount <= ?");
            params.add(maxAmount);
        }

        if (location != null && !location.isBlank()) {
            sql.append(" AND location = ?");
            params.add(location);
        }

        if (fraudStatus != null) {
            if (fraudStatus.equalsIgnoreCase("NORMAL")) {
                sql.append(" AND fraud_flag = false");
            } else if (fraudStatus.equalsIgnoreCase("FRAUD")) {
                sql.append(" AND fraud_flag = true");
            }
        }

        sql.append(" ORDER BY timestamp DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {

            Transaction t = new Transaction();

            t.setTransactionId(rs.getLong("transaction_id"));
            t.setSenderId(rs.getString("sender_id"));
            t.setReceiverId(rs.getString("receiver_id"));
            t.setAmount(rs.getDouble("amount"));
            t.setTimestamp(rs.getString("timestamp"));
            t.setLocation(rs.getString("location"));
            t.setDeviceId(rs.getString("device_id"));
            t.setMerchantName(rs.getString("merchant_name"));
            t.setTransactionType(rs.getString("transaction_type"));
            t.setStatus(rs.getString("status"));
            t.setRiskScore(rs.getInt("risk_score"));
            t.setFraudFlag(rs.getBoolean("fraud_flag"));
            t.setMlProbability(rs.getDouble("ml_probability"));
            t.setRuleScore(rs.getInt("rule_score"));
            t.setTxnGap(rs.getLong("txn_gap"));

            return t;

        }, params.toArray());
    }
    // TRANSACTION DETAIL BY ID
    public Transaction findByTransactionId(Long transactionId) {

        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            Transaction t = new Transaction();

            t.setTransactionId(rs.getLong("transaction_id"));
            t.setSenderId(rs.getString("sender_id"));
            t.setReceiverId(rs.getString("receiver_id"));
            t.setAmount(rs.getDouble("amount"));
            t.setTimestamp(rs.getString("timestamp"));
            t.setLocation(rs.getString("location"));
            t.setDeviceId(rs.getString("device_id"));
            t.setMerchantName(rs.getString("merchant_name"));
            t.setTransactionType(rs.getString("transaction_type"));
            t.setStatus(rs.getString("status"));
            t.setRiskScore(rs.getInt("risk_score"));
            t.setFraudFlag(rs.getBoolean("fraud_flag"));
            t.setMlProbability(rs.getDouble("ml_probability"));
            t.setRuleScore(rs.getInt("rule_score"));
            t.setTxnGap(rs.getLong("txn_gap"));

            return t;

        }, transactionId);
    }
    // TODAY TRANSACTIONS COUNT
    public int countTodayTransactions() {

        String sql = """
            SELECT COUNT(*)
            FROM transactions
            WHERE DATE(timestamp) = CURDATE()
            """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        return count != null ? count : 0;
    }
}