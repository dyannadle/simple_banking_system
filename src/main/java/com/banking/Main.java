package com.banking;

import com.banking.config.DatabaseConfig;
import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.service.BankingService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final BankingService bankingService = new BankingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConfig.initializeDatabase();
        System.out.println("Welcome to Simple Banking System");

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        checkBalance();
                        break;
                    case 5:
                        viewHistory();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void createAccount() throws SQLException {
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        BigDecimal balance = new BigDecimal(scanner.nextLine());

        int id = bankingService.createAccount(name, balance);
        System.out.println("Account created successfully! Account Number: " + id);
    }

    private static void deposit() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        bankingService.deposit(id, amount);
        System.out.println("Deposit successful.");
    }

    private static void withdraw() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        bankingService.withdraw(id, amount);
        System.out.println("Withdrawal successful.");
    }

    private static void checkBalance() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());

        Account account = bankingService.getAccount(id);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.println("Current Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewHistory() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Transaction> transactions = bankingService.getTransactionHistory(id);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }
}
