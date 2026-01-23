// Defines the package this class belongs to
package com.banking.config;

// Imports necessary JDBC classes for database connectivity
import java.sql.Connection;
// Imports DriverManager to manage database drivers
import java.sql.DriverManager;
// Imports SQLException to handle database access errors
import java.sql.SQLException;
// Imports Statement to execute simple SQL queries
import java.sql.Statement;
// Imports classes for file I/O operations
import java.io.IOException;
// Imports Files helper class for file operations
import java.nio.file.Files;
// Imports Paths helper class for handling file paths
import java.nio.file.Paths;

// Class responsible for database configuration and initialization
public class DatabaseConfig {
    // JDBC URL for the H2 database.
    // ./banking_db specifies the file path.
    // DB_CLOSE_DELAY=-1 keeps the database open even if the last connection closes
    private static final String URL = "jdbc:h2:./banking_db;DB_CLOSE_DELAY=-1";
    // Default username for H2 database
    private static final String USER = "sa";
    // Default password for H2 database (empty)
    private static final String PASSWORD = "";

    // Method to establish and return a database connection
    // Throws SQLException if connection fails
    public static Connection getConnection() throws SQLException {
        // Uses DriverManager to create a connection using the defined URL, User, and
        // Password
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to initialize the database schema (create tables)
    public static void initializeDatabase() {
        // Try-with-resources block ensures Connection and Statement are closed
        // automatically
        try (Connection conn = getConnection();
                // Create a Statement object for sending SQL statements to the database
                Statement stmt = conn.createStatement()) {

            // Reads all bytes from the schema.sql file and converts them to a String
            // This assumes the file is located at src/main/resources/schema.sql
            String schema = new String(Files.readAllBytes(Paths.get("src/main/resources/schema.sql")));

            // Executes the SQL commands read from the schema file
            stmt.execute(schema);
            // Prints a success message to the console
            System.out.println("Database initialized successfully.");

        } catch (SQLException | IOException e) {
            // Catches any SQL or Input/Output errors that occur during initialization
            // Prints an error message to the standard error stream
            System.err.println("Error initializing database: " + e.getMessage());
            // Prints the stack trace for debugging purposes
            e.printStackTrace();
        }
    }
}
