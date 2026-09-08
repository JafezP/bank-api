CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    mother_last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    second_name VARCHAR(100),
    dni VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(150) UNIQUE,
    phone_number VARCHAR(20) UNIQUE,
    address VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);