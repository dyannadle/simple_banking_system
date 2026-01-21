# Simple Banking System

A simple banking application built with Java, allowing users to manage accounts and perform basic banking transactions.

## Features

- **Account Management**: Create and manage bank accounts.
- **Transactions**: Perform deposits, withdrawals, and balance inquiries.
- **Transaction History**: View history of transactions (implied by TransactionDAO).
- **Data Persistence**: Uses H2 Database for storing account and transaction data.

## Technologies Used

- **Java 17**: Core programming language.
- **Maven**: Dependency management and build tool.
- **H2 Database**: Lightweight, embedded relational database.

## Prerequisites

- Java Development Kit (JDK) 17 or higher.
- Maven 3.6.0 or higher (optional if using an IDE with Maven support).

## Setup and Installation

1.  **Clone the repository** (if applicable):
    ```bash
    git clone <repository-url>
    cd simple-banking-system
    ```

2.  **Build the project**:
    ```bash
    mvn clean install
    ```

3.  **Run the application**:
    Navigate to the project root and run:
    ```bash
    mvn exec:java -Dexec.mainClass="com.banking.Main"
    ```
    *Note: Adjust the main class path if `com.banking.Main` is not the exact entry point.*

## Usage

Follow the on-screen prompts to navigate through the banking system options.

## Project Structure

```
simple_banking_system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── banking/
│   │   │           ├── dao/          # Data Access Objects
│   │   │           ├── model/        # Data Models (Account, Transaction)
│   │   │           ├── service/      # Business Logic
│   │   │           └── Main.java     # Entry Point
│   │   └── resources/
├── pom.xml                           # Maven Configuration
└── README.md
```
