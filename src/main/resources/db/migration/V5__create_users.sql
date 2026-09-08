CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL,
    client_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user_client
    FOREIGN KEY(client_id)
    REFERENCES clients(id)
);