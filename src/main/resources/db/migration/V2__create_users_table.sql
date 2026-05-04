CREATE TABLE users (
    id CHAR(36) PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    cpf_cnpj VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    wallet_id CHAR(36),
    FOREIGN KEY (wallet_id) REFERENCES wallets(id)
);