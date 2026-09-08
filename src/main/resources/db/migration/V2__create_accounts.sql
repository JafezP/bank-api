CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    client_id BIGINT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance NUMERIC(15,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_account_client FOREIGN KEY(client_id) REFERENCES clients(id)
);