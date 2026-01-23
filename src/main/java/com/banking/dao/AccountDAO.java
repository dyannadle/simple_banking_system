// Defines the package for Data Access Objects (DAO)
package com.banking.dao;

// Import database configuration to get connections
import com.banking.config.DatabaseConfig;
// Import the Account model
import com.banking.model.Account;

// Import SQL classes for database interaction
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Import BigDecimal for financial arithmetic
import java.math.BigDecimal;

// Class responsible for database operations related to Accounts
public class AccountDAO {

    // Method to insert a new account into the database
    // Returns the auto-generated account ID
    public int createAccount(Account account) throws SQLException {
        // SQL query to insert account details. '?' are placeholders for values.
        String sql = "INSERT INTO accounts (account_holder_name, balance, pin) VALUES (?, ?, ?)";

        // Try-with-resources to manage database connection and statement
        // Statement.RETURN_GENERATED_KEYS allows us to retrieve the new ID
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the first parameter (name)
            pstmt.setString(1, account.getAccountHolderName());
            // Set the second parameter (initial balance)
            pstmt.setBigDecimal(2, account.getBalance());
            // Set the third parameter (PIN)
            pstmt.setString(3, account.getPin());

            // Execute the update. Returns the number of rows affected.
            int affectedRows = pstmt.executeUpdate();

            // If no rows were affected, the insertion failed
            if (affectedRows == 0) {
                // Throw exception indicating failure
                throw new SQLException("Creating account failed, no rows affected.");
            }

            // Retrieve the generated keys (the new account ID)
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                // Check if a key was returned
                if (generatedKeys.next()) {
                    // Return the first column of the result set (the ID)
                    return generatedKeys.getInt(1);
                } else {
                    // Throw error if no ID was returned
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
        }
    }

    // Method to retrieve an account by its unique Account Number
    public Account getAccount(int accountNumber) throws SQLException {
        // SQL query to select all columns for a specific account ID
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        // Open connection and prepare statement
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the account number parameter
            pstmt.setInt(1, accountNumber);

            // Execute the query and get the result set
            try (ResultSet rs = pstmt.executeQuery()) {
                // If a result is found (next() returns true)
                if (rs.next()) {
                    // Create and return a new Account object from the result set data
                    return new Account(
                            // Get account number from result set
                            rs.getInt("account_number"),
                            // Get account holder name from result set
                            rs.getString("account_holder_name"),
                            // Get balance from result set
                            rs.getBigDecimal("balance"),
                            // Get PIN from result set
                            rs.getString("pin"));
                }
            }
        }
        // Return null if no account was found with that ID
        return null;
    }

    // Method to update the balance of an existing account
    public void updateBalance(int accountNumber, BigDecimal newBalance) throws SQLException {
        // SQL query to update the balance column for a specific account
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        // Open connection and prepare statement
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the new balance parameter
            pstmt.setBigDecimal(1, newBalance);
            // Set the account number parameter to identify which row to update
            pstmt.setInt(2, accountNumber);

            // Execute the update
            pstmt.executeUpdate();
        }
    }
}
