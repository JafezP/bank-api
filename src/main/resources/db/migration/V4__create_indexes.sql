CREATE INDEX idx_transactions_account_id
    ON transactions(account_id);

CREATE INDEX idx_clients_dni
    ON clients(dni);

CREATE INDEX idx_accounts_client_id
    ON accounts(client_id);
