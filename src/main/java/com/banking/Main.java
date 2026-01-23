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
    // Instantiate the banking service to handle operations (static so it can be
    // used in main method)
    private static final BankingService bankingService = new BankingService();
    // Create a Scanner object to read input from the standard input (keyboard)
    private static final Scanner scanner = new Scanner(System.in);

    // Main method: Entry point of the Java application
    public static void main(String[] args) {
        // Initialize the database (create tables if they don't exist) at startup
        DatabaseConfig.initializeDatabase();
        // Print welcome message
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
            // Prompt user for input
            System.out.print("Enter choice: ");

            // Variable to store user choice
            int choice = -1;
            try {
                // Read input and parse it as an integer
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Handle cases where input is not a number
                System.out.println("Invalid input. Please enter a number.");
                // Skip the rest of the loop and show menu again
                continue;
            }

            // Execute code based on the user's numeric choice
            try {
                // Switch statement to handle different menu options
                switch (choice) {
                    case 1:
                        // Call helper method to create an account
                        createAccount();
                        // Break out of the switch statement
                        break;
                    case 2:
                        // Call helper method to process a deposit
                        deposit();
                        // Break out of the switch statement
                        break;
                    case 3:
                        // Call helper method to process a withdrawal
                        withdraw();
                        // Break out of the switch statement
                        break;
                    case 4:
                        // Call helper method to check balance
                        checkBalance();
                        // Break out of the switch statement
                        break;
                    case 5:
                        // Call helper method to view history
                        viewHistory();
                        // Break out of the switch statement
                        break;
                    case 6:
                        // Exit the application
                        System.out.println("Exiting...");
                        // Ends the main method, stopping the program
                        return;
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
        // Prompt for account holder name
        System.out.print("Enter Account Holder Name: ");
        // Read name input
        String name = scanner.nextLine();
        // Prompt for initial balance
        System.out.print("Enter Initial Balance: ");
        // Read balance as String first to avoid precision issues, then convert to
        // BigDecimal
        BigDecimal balance = new BigDecimal(scanner.nextLine());

        // Prompt for PIN creation
        System.out.println("Set a 4-digit PIN: ");
        // Read PIN input
        String pin = scanner.nextLine();

        // Call service to create account and get the new ID
        int id = bankingService.createAccount(name, balance, pin);
        // Print success message with the new account number
        System.out.println("Account created successfully! Account Number: " + id);
    }

    // Helper method to handle UI for deposits
    private static void deposit() throws SQLException {
        // Prompt for account number
        System.out.print("Enter Account Number: ");
        // Read and parse account number
        int id = Integer.parseInt(scanner.nextLine());
        // Prompt for PIN
        System.out.print("Enter PIN: ");
        // Read PIN
        String pin = scanner.nextLine();

        // Authenticate the user
        if (!bankingService.authenticate(id, pin)) {
            // Print error if authentication fails
            System.out.println("Invalid PIN or Account Number.");
            // Return to main menu
            return;
        }

        // Prompt for deposit amount
        System.out.print("Enter Amount: ");
        // Read amount
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        // Call service to perform deposit
        bankingService.deposit(id, amount);
        // Print success message
        System.out.println("Deposit successful.");
    }

    // Helper method to handle UI for withdrawals
    private static void withdraw() throws SQLException {
        // Prompt for account number
        System.out.print("Enter Account Number: ");
        // Read and parse account number
        int id = Integer.parseInt(scanner.nextLine());
        // Prompt for PIN
        System.out.print("Enter PIN: ");
        // Read PIN
        String pin = scanner.nextLine();

        // Authenticate the user
        if (!bankingService.authenticate(id, pin)) {
            // Print error if authentication fails
            System.out.println("Invalid PIN or Account Number.");
            // Return to main menu
            return;
        }

        // Prompt for withdrawal amount
        System.out.print("Enter Amount: ");
        // Read amount
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        // Call service to perform withdrawal
        bankingService.withdraw(id, amount);
        // Print success message
        System.out.println("Withdrawal successful.");
    }

    // Helper method to handle UI for checking balance
    private static void checkBalance() throws SQLException {
        // Prompt for account number
        System.out.print("Enter Account Number: ");
        // Read and parse account number
        int id = Integer.parseInt(scanner.nextLine());
        // Prompt for PIN
        System.out.print("Enter PIN: ");
        // Read PIN
        String pin = scanner.nextLine();

        // Authenticate the user
        if (!bankingService.authenticate(id, pin)) {
            // Print error if authentication fails
            System.out.println("Invalid PIN or Account Number.");
            // Return to main menu
            return;
        }

        // Retrieve account details from service
        Account account = bankingService.getAccount(id);
        // Check if account exists
        if (account != null) {
            // Print account holder name
            System.out.println("Account Holder: " + account.getAccountHolderName());
            // Print current balance
            System.out.println("Current Balance: " + account.getBalance());
        } else {
            // Print error if account not found
            System.out.println("Account not found.");
        }
    }

    // Helper method to handle UI for viewing transaction history
    private static void viewHistory() throws SQLException {
        // Prompt for account number
        System.out.print("Enter Account Number: ");
        // Read and parse account number
        int id = Integer.parseInt(scanner.nextLine());
        // Prompt for PIN
        System.out.print("Enter PIN: ");
        // Read PIN
        String pin = scanner.nextLine();

        // Authenticate the user
        if (!bankingService.authenticate(id, pin)) {
            // Print error if authentication fails
            System.out.println("Invalid PIN or Account Number.");
            // Return to main menu
            return;
        }

        // Retrieve list of transactions from service
        List<Transaction> transactions = bankingService.getTransactionHistory(id);
        // Check if history is empty
        if (transactions.isEmpty()) {
            // Print message if no transactions
            System.out.println("No transactions found.");
        } else {
            // Print header
            System.out.println("Transaction History:");
            // Iterate through the list and print each transaction
            for (Transaction t : transactions) {
                // This calls the toString() method of the Transaction class
                System.out.println(t);
            }
        }
    }
}
