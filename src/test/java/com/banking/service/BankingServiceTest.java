// Defines the package for tests
package com.banking.service;

// Import database config
import com.banking.config.DatabaseConfig;
// Import Account model
import com.banking.model.Account;
// Import JUnit annotation used to run a method before each test
import org.junit.jupiter.api.BeforeEach;
// Import JUnit annotation to mark a method as a test case
import org.junit.jupiter.api.Test;

// Import BigDecimal for comparisons
import java.math.BigDecimal;
// Import SQLException because service methods throw it
import java.sql.SQLException;

// Import static assertions from JUnit to verify results
import static org.junit.jupiter.api.Assertions.*;

// Test class for BankingService
class BankingServiceTest {

    // Instance of the service being tested
    private BankingService bankingService;

    // Method annotated with @BeforeEach runs before every single test method
    @BeforeEach
    void setUp() {
        // Initialize database (creates tables to ensure fresh state)
        DatabaseConfig.initializeDatabase();
        // Create a new instance of BankingService
        bankingService = new BankingService();
    }

    // Test case for account creation
    @Test
    void testCreateAccount() throws SQLException {
        // Call createAccount and capture the returned ID
        int accountId = bankingService.createAccount("Test User", new BigDecimal("100.00"), "1234");
        // Assert that the account ID is positive (meaning it was generated)
        assertTrue(accountId > 0, "Account ID should be positive");

        // Retrieve the created account
        Account account = bankingService.getAccount(accountId);
        // Assert that the account is not null
        assertNotNull(account, "Account should prevent null");
        // Assert that the name matches
        assertEquals("Test User", account.getAccountHolderName());
        // Assert that the balance matches
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    // Test case for authentication
    @Test
    void testAuthenticate() throws SQLException {
        // Create an account with PIN "5678"
        int accountId = bankingService.createAccount("Auth User", new BigDecimal("50.00"), "5678");

        // Assert that using the correct PIN returns true
        assertTrue(bankingService.authenticate(accountId, "5678"), "Should authenticate with correct PIN");
        // Assert that using the wrong PIN returns false
        assertFalse(bankingService.authenticate(accountId, "0000"), "Should not authenticate with wrong PIN");
        // Assert that using a non-existent account ID returns false
        assertFalse(bankingService.authenticate(-1, "5678"), "Should not authenticate non-existent account");
    }

    // Test case for depositing money
    @Test
    void testDeposit() throws SQLException {
        // Create an account
        int accountId = bankingService.createAccount("Deposit User", new BigDecimal("100.00"), "1111");

        // Perform a deposit of 50.00
        bankingService.deposit(accountId, new BigDecimal("50.00"));

        // Retrieve the account to check the new balance
        Account account = bankingService.getAccount(accountId);
        // Assert that the balance is now 150.00 (100 + 50)
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    // Test case for withdrawing money
    @Test
    void testWithdraw() throws SQLException {
        // Create an account
        int accountId = bankingService.createAccount("Withdraw User", new BigDecimal("200.00"), "2222");

        // Perform a withdrawal of 50.00
        bankingService.withdraw(accountId, new BigDecimal("50.00"));

        // Retrieve the account to check the new balance
        Account account = bankingService.getAccount(accountId);
        // Assert that the balance is now 150.00 (200 - 50)
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    // Test case for withdrawing more money than available
    @Test
    void testWithdrawInsufficientFunds() throws SQLException {
        // Create an account with a low balance
        int accountId = bankingService.createAccount("Broke User", new BigDecimal("10.00"), "3333");

        // Assert that calling withdraw with an amount larger than balance throws
        // IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            bankingService.withdraw(accountId, new BigDecimal("50.00"));
        });
    }
}
