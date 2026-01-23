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
    // Security PIN for authentication
    private String pin;

    // Default constructor (no arguments)
    public Account() {
    }

    // Constructor to initialize an Account object with all fields (used when
    // retrieving from DB)
    public Account(int accountNumber, String accountHolderName, BigDecimal balance, String pin) {
        // Sets the account number
        this.accountNumber = accountNumber;
        // Sets the account holder's name
        this.accountHolderName = accountHolderName;
        // Sets the initial balance
        this.balance = balance;
        // Sets the PIN
        this.pin = pin;
    }

    // Constructor for creating a new Account (accountNumber is not yet assigned by
    // DB)
    public Account(String accountHolderName, BigDecimal balance, String pin) {
        // Sets the account holder's name
        this.accountHolderName = accountHolderName;
        // Sets the initial balance
        this.balance = balance;
        // Sets the PIN
        this.pin = pin;
    }

    // Getter method to retrieve the account number
    public int getAccountNumber() {
        // Return the account number
        return accountNumber;
    }

    // Setter method to update the account number
    public void setAccountNumber(int accountNumber) {
        // Assign the provided value to accountNumber
        this.accountNumber = accountNumber;
    }

    // Getter method to retrieve the account holder's name
    public String getAccountHolderName() {
        // Return the account holder's name
        return accountHolderName;
    }

    // Setter method to update the account holder's name
    public void setAccountHolderName(String accountHolderName) {
        // Assign the provided value to accountHolderName
        this.accountHolderName = accountHolderName;
    }

    // Getter method to retrieve the current balance
    public BigDecimal getBalance() {
        // Return the balance
        return balance;
    }

    // Setter method to update the balance
    public void setBalance(BigDecimal balance) {
        // Assign the provided value to balance
        this.balance = balance;
    }

    // Getter method to retrieve the PIN
    public String getPin() {
        // Return the PIN
        return pin;
    }

    // Setter method to update the PIN
    public void setPin(String pin) {
        // Assign the provided value to pin
        this.pin = pin;
    }

    // Overrides the toString method to provide a string representation of the
    // Account object
    @Override
    public String toString() {
        // Return a concatenated string with account details
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
