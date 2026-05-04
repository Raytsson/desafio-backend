CREATE TABLE transfers (
    id CHAR(36) PRIMARY KEY,
    origin_user_id CHAR(36),
    destination_user_id CHAR(36),
    value DECIMAL(19,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    transfer_time TIMESTAMP NOT NULL,
    FOREIGN KEY (origin_user_id) REFERENCES users(id),
    FOREIGN KEY (destination_user_id) REFERENCES users(id)
);