// Defines the package for the Service layer
package com.banking.service;

// Import DAO classes to interact with the database
import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
// Import Model classes
import com.banking.model.Account;
import com.banking.model.Transaction;

// Import BigDecimal for precise monetary calculations
import java.math.BigDecimal;
// Import SQLException to handle database errors
import java.sql.SQLException;
// Import List for handling collections of transactions
import java.util.List;

// Service class that contains the business logic of the application
public class BankingService {
    // DAO instances to handle data access
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    // Constructor initializes the DAO objects
    public BankingService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Business method to create a new account
    // Takes name and initial balance, returns the new Account ID
    public int createAccount(String name, BigDecimal initialBalance) throws SQLException {
        // Create a new Account object with the provided data
        Account newAccount = new Account(name, initialBalance);
        // Delegate to AccountDAO to save it to the database
        return accountDAO.createAccount(newAccount);
    }

    // Business method to retrieve an account by ID
    public Account getAccount(int accountNumber) throws SQLException {
        // Delegate to AccountDAO
        return accountDAO.getAccount(accountNumber);
    }

    // Business method to deposit money into an account
    public void deposit(int accountNumber, BigDecimal amount) throws SQLException {
        // First retrieve the account to check if it exists
        Account account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            // Throw exception if account not found
            throw new IllegalArgumentException("Account not found.");
        }
        // Validate that the deposit amount is positive
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        // Calculate the new balance (current balance + deposit amount)
        BigDecimal newBalance = account.getBalance().add(amount);
        // Update the account's balance in the database
        accountDAO.updateBalance(accountNumber, newBalance);

        // Record the transaction details (DEPOSIT)
        Transaction transaction = new Transaction(accountNumber, "DEPOSIT", amount);
        // Save the transaction log to the database
        transactionDAO.logTransaction(transaction);
    }

    // Business method to withdraw money from an account
    public void withdraw(int accountNumber, BigDecimal amount) throws SQLException {
        // Retrieve the account
        Account account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            // Throw exception if account not found
            throw new IllegalArgumentException("Account not found.");
        }
        // Validate that the withdrawal amount is positive
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        // Check if the account has sufficient funds
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        // Calculate the new balance (current balance - withdrawal amount)
        BigDecimal newBalance = account.getBalance().subtract(amount);
        // Update the account's balance in the database
        accountDAO.updateBalance(accountNumber, newBalance);

        // Record the transaction details (WITHDRAW)
        Transaction transaction = new Transaction(accountNumber, "WITHDRAW", amount);
        // Save the transaction log to the database
        transactionDAO.logTransaction(transaction);
    }

    // Business method to retrieve transaction history for an account
    public List<Transaction> getTransactionHistory(int accountNumber) throws SQLException {
        // Delegate to TransactionDAO
        return transactionDAO.getTransactionHistory(accountNumber);
    }
}
