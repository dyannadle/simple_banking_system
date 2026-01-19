// Defines the package for Data Access Objects
package com.banking.dao;

// Import configuration and model classes
import com.banking.config.DatabaseConfig;
import com.banking.model.Transaction;

// Import SQL classes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Import List and ArrayList to handle collections of transactions
import java.util.ArrayList;
import java.util.List;

// Class responsible for database operations related to Transactions
public class TransactionDAO {

    // Method to record a new transaction in the database
    public void logTransaction(Transaction transaction) throws SQLException {
        // SQL query to insert transaction details
        String sql = "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)";

        // Open connection and prepare statement
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters from the transaction object
            pstmt.setInt(1, transaction.getAccountNumber()); // Account ID
            pstmt.setString(2, transaction.getType()); // Type (DEPOSIT/WITHDRAW)
            pstmt.setBigDecimal(3, transaction.getAmount()); // Amount involved

            // Execute the insertion
            pstmt.executeUpdate();
        }
    }

    // Method to retrieve the transaction history for a specific account
    public List<Transaction> getTransactionHistory(int accountNumber) throws SQLException {
        // Create an empty list to store the results
        List<Transaction> history = new ArrayList<>();
        // SQL query to select all transactions for an account, ordered by most recent
        // first
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";

        // Open connection and prepare statement
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the account number parameter
            pstmt.setInt(1, accountNumber);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // Iterate through the result set row by row
                while (rs.next()) {
                    // Create a Transaction object for each row and add it to the list
                    history.add(new Transaction(
                            rs.getInt("transaction_id"),
                            rs.getInt("account_number"),
                            rs.getString("type"),
                            rs.getBigDecimal("amount"),
                            rs.getTimestamp("timestamp")));
                }
            }
        }
        // Return the list of transactions
        return history;
    }
}
