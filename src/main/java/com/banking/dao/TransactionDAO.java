package com.banking.dao;

import com.banking.config.DatabaseConfig;
import com.banking.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void logTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transaction.getAccountNumber());
            pstmt.setString(2, transaction.getType());
            pstmt.setBigDecimal(3, transaction.getAmount());

            pstmt.executeUpdate();
        }
    }

    public List<Transaction> getTransactionHistory(int accountNumber) throws SQLException {
        List<Transaction> history = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    history.add(new Transaction(
                            rs.getInt("transaction_id"),
                            rs.getInt("account_number"),
                            rs.getString("type"),
                            rs.getBigDecimal("amount"),
                            rs.getTimestamp("timestamp")));
                }
            }
        }
        return history;
    }
}
