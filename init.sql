-- Am sters CREATE DATABASE pentru ca Docker face asta automat

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE login_data (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE account_data (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL REFERENCES login_data(id) ON DELETE CASCADE,
    iban VARCHAR(34) UNIQUE NOT NULL,
    balance_ron DECIMAL(15, 2) DEFAULT 0.00,
    balance_eur DECIMAL(15, 2) DEFAULT 0.00,
    balance_gbp DECIMAL(15, 2) DEFAULT 0.00,
    balance_usd DECIMAL(15, 2) DEFAULT 0.00,
    balance_pln DECIMAL(15, 2) DEFAULT 0.00,
    savings_usd DECIMAL(15, 2) DEFAULT 0.00,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela ta pentru istoricul de tranzactii
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    date VARCHAR(255),
    description VARCHAR(255),
    amount DECIMAL(15, 2),
    source_iban VARCHAR(34),
    destination_iban VARCHAR(34)
);

-- Inserarea datelor de test
INSERT INTO login_data (name, email, password) VALUES
('Alice Smith', 'aliceS@gmail.com', crypt('password123', gen_salt('bf'))),
('Bob Jones', 'boby220@yahoo.com', crypt('securePass456', gen_salt('bf'))),
('Charles Brown', 'charlie@gmail.com', crypt('admin789', gen_salt('bf')));

INSERT INTO account_data (user_id, iban, balance_ron, balance_eur, balance_usd, savings_usd) VALUES
(1, 'RO88EBNK2481632640623618', 5000.00, 120.50, 10.00, 78.00),
(2, 'RO45EBNK3927030729364416', 4300.00, 50.00, 250.00, 122.00),
(3, 'RO53EBNK4671257925429754', 3500.75, 0.00, 50.00, 0.00);