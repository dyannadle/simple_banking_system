// Defines the main package
package com.banking;

// Import configuration to initialize the database
import com.banking.config.DatabaseConfig;
// Import models
import com.banking.model.Account;
import com.banking.model.Transaction;
// Import the service layer which handles logic
import com.banking.service.BankingService;

// Import BigDecimal for money handling
import java.math.BigDecimal;
// Import SQLException for database error handling
import java.sql.SQLException;
// Import List for collections
import java.util.List;
// Import Scanner for reading user input
import java.util.Scanner;

// Main class acts as the user interface (Console Application)
public class Main {
    // Instantiate the banking service to handle operations
    private static final BankingService bankingService = new BankingService();
    // Create a Scanner object to read input from the standard input (keyboard)
    private static final Scanner scanner = new Scanner(System.in);

    // Main method: Entry point of the Java application
    public static void main(String[] args) {
        // Initialize the database (tables) at startup
        DatabaseConfig.initializeDatabase();
        System.out.println("Welcome to Simple Banking System");

        // Infinite loop to keep the application running until user chooses to exit
        while (true) {
            // Display valid menu options
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            // Variable to store user choice
            int choice = -1;
            try {
                // Read input and parse it as an integer
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Handle cases where input is not a number
                System.out.println("Invalid input. Please enter a number.");
                continue; // Skip the rest of the loop and show menu again
            }

            // Execute code based on the user's numeric choice
            try {
                switch (choice) {
                    case 1:
                        // Call helper method to create an account
                        createAccount();
                        break;
                    case 2:
                        // Call helper method to process a deposit
                        deposit();
                        break;
                    case 3:
                        // Call helper method to process a withdrawal
                        withdraw();
                        break;
                    case 4:
                        // Call helper method to check balance
                        checkBalance();
                        break;
                    case 5:
                        // Call helper method to view history
                        viewHistory();
                        break;
                    case 6:
                        // Exit the application
                        System.out.println("Exiting...");
                        return; // Ends the main method, stopping the program
                    default:
                        // Handle invalid numbers (not 1-6)
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                // Catch any unexpected errors (like database issues) and print the message
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Helper method to handle UI for creating an account
    private static void createAccount() throws SQLException {
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        // Read balance as String first to avoid precision issues, then convert to
        // BigDecimal
        BigDecimal balance = new BigDecimal(scanner.nextLine());

        // Call service to create account and get the new ID
        int id = bankingService.createAccount(name, balance);
        System.out.println("Account created successfully! Account Number: " + id);
    }

    // Helper method to handle UI for deposits
    private static void deposit() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        // Call service to perform deposit
        bankingService.deposit(id, amount);
        System.out.println("Deposit successful.");
    }

    // Helper method to handle UI for withdrawals
    private static void withdraw() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        // Call service to perform withdrawal
        bankingService.withdraw(id, amount);
        System.out.println("Withdrawal successful.");
    }

    // Helper method to handle UI for checking balance
    private static void checkBalance() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());

        // Retrieve account details
        Account account = bankingService.getAccount(id);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.println("Current Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    // Helper method to handle UI for viewing transaction history
    private static void viewHistory() throws SQLException {
        System.out.print("Enter Account Number: ");
        int id = Integer.parseInt(scanner.nextLine());

        // Retrieve list of transactions
        List<Transaction> transactions = bankingService.getTransactionHistory(id);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            // Iterate through the list and print each transaction
            for (Transaction t : transactions) {
                // This calls the toString() method of the Transaction class
                System.out.println(t);
            }
        }
    }
}
