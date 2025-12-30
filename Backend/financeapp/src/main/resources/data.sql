-- data.sql
-- Données initiales pour l'application H2

-- Supprimer les tables si elles existent (pour un démarrage propre)
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- Créer la table users si elle n'existe pas
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

-- Insérer des utilisateurs de test (mot de passe: password123)
-- Note: Le mot de passe hashé est pour 'password123'
MERGE INTO users (id, email, password, first_name, last_name, created_at, updated_at, created_by, updated_by) 
KEY(email) 
VALUES 
(1, 'admin@financeapp.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV6UiC', 'Admin', 'User', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
(2, 'user@financeapp.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV6UiC', 'John', 'Doe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');

-- Créer la table accounts si elle n'existe pas
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_name VARCHAR(255) NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insérer des comptes
MERGE INTO accounts (id, account_number, account_name, balance, user_id, created_at, updated_at, created_by, updated_by) 
KEY(account_number) 
VALUES
(1, 'ACC001', 'Compte Courant Principal', 10000.00, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
(2, 'ACC002', 'Compte Épargne', 5000.00, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
(3, 'ACC003', 'Compte Personnel', 3000.00, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');

-- Créer la table transactions si elle n'existe pas
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(15, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- Insérer des transactions
MERGE INTO transactions (id, amount, type, description, transaction_date, account_id, created_at, updated_at, created_by, updated_by) 
KEY(id) 
VALUES
(1, 1000.00, 'DEPOSIT', 'Dépôt initial', DATEADD('DAY', -30, CURRENT_TIMESTAMP), 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
(2, 250.50, 'WITHDRAWAL', 'Retrait ATM', DATEADD('DAY', -15, CURRENT_TIMESTAMP), 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
(3, 500.00, 'TRANSFER', 'Transfert vers épargne', DATEADD('DAY', -7, CURRENT_TIMESTAMP), 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');