CREATE TABLE password_reset_request
(
    id         UUID PRIMARY KEY,
    email      VARCHAR NOT NULL,
    token_hash VARCHAR NOT NULL,
    token_salt VARCHAR NOT NULL,
    created    VARCHAR NOT NULL
);
