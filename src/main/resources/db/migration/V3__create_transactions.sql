CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    transaction_type VARCHAR(30) NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    balance_before NUMERIC(15,2) NOT NULL,
    balance_after NUMERIC(15,2) NOT NULL,
    transfer_reference VARCHAR(50),
    description VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_transaction_account FOREIGN KEY(account_id) REFERENCES accounts(id)
);