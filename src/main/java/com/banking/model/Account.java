// Defines the package for model classes
package com.banking.model;

// Imports BigDecimal for high-precision arithmetic, suitable for financial calculations
import java.math.BigDecimal;

// Class representing a bank account entity
public class Account {
    // Unique identifier for the account
    private int accountNumber;
    // Name of the account holder
    private String accountHolderName;
    // Current balance of the account
    private BigDecimal balance;

    // Default constructor (no arguments)
    public Account() {
    }

    // Constructor to initialize an Account object with all fields (used when
    // retrieving from DB)
    public Account(int accountNumber, String accountHolderName, BigDecimal balance) {
        // Sets the account number
        this.accountNumber = accountNumber;
        // Sets the account holder's name
        this.accountHolderName = accountHolderName;
        // Sets the initial balance
        this.balance = balance;
    }

    // Constructor for creating a new Account (accountNumber is not yet assigned by
    // DB)
    public Account(String accountHolderName, BigDecimal balance) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Getter method to retrieve the account number
    public int getAccountNumber() {
        return accountNumber;
    }

    // Setter method to update the account number
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Getter method to retrieve the account holder's name
    public String getAccountHolderName() {
        return accountHolderName;
    }

    // Setter method to update the account holder's name
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    // Getter method to retrieve the current balance
    public BigDecimal getBalance() {
        return balance;
    }

    // Setter method to update the balance
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // Overrides the toString method to provide a string representation of the
    // Account object
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
