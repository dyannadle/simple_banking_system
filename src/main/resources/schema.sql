-- Create the 'accounts' table if it doesn't already exist
CREATE TABLE IF NOT EXISTS accounts (
    -- Unique identifier for the account, auto-increments for each new record
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    -- Name of the account holder, cannot be null
    account_holder_name VARCHAR(255) NOT NULL,
    -- Balance of the account, defaults to 0.00. DECIMAL(15, 2) stores up to 15 digits, 2 after decimal
    balance DECIMAL(15, 2) DEFAULT 0.00
);

-- Create the 'transactions' table if it doesn't already exist
CREATE TABLE IF NOT EXISTS transactions (
    -- Unique identifier for the transaction, auto-increments
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    -- References the account number from the 'accounts' table
    account_number INT,
    -- Type of transaction (e.g., 'DEPOSIT' or 'WITHDRAW')
    type VARCHAR(20),
    -- Amount involved in the transaction
    amount DECIMAL(15, 2),
    -- Timestamp of when the transaction occurred, defaults to current system time
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Enforces referential integrity: account_number must exist in specific 'accounts' table
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
