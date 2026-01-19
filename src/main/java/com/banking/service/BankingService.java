package com.banking.service;

import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BankingService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public BankingService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public int createAccount(String name, BigDecimal initialBalance) throws SQLException {
        Account newAccount = new Account(name, initialBalance);
        return accountDAO.createAccount(newAccount);
    }

    public Account getAccount(int accountNumber) throws SQLException {
        return accountDAO.getAccount(accountNumber);
    }

    public void deposit(int accountNumber, BigDecimal amount) throws SQLException {
        Account account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        accountDAO.updateBalance(accountNumber, newBalance);

        Transaction transaction = new Transaction(accountNumber, "DEPOSIT", amount);
        transactionDAO.logTransaction(transaction);
    }

    public void withdraw(int accountNumber, BigDecimal amount) throws SQLException {
        Account account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        accountDAO.updateBalance(accountNumber, newBalance);

        Transaction transaction = new Transaction(accountNumber, "WITHDRAW", amount);
        transactionDAO.logTransaction(transaction);
    }

    public List<Transaction> getTransactionHistory(int accountNumber) throws SQLException {
        return transactionDAO.getTransactionHistory(accountNumber);
    }
}
