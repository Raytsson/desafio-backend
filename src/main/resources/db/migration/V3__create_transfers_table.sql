CREATE TABLE transfers (
    id binary(16) PRIMARY KEY,
    origin_user_id binary(16),
    destination_user_id binary(16),
    value DECIMAL(19,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    transfer_time TIMESTAMP NOT NULL,
    FOREIGN KEY (origin_user_id) REFERENCES users(id),
    FOREIGN KEY (destination_user_id) REFERENCES users(id)
);