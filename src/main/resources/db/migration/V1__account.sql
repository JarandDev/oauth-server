CREATE TABLE account
(
    id            UUID PRIMARY KEY,
    email         VARCHAR NOT NULL,
    password_hash VARCHAR NOT NULL,
    password_salt VARCHAR NOT NULL,
    created       VARCHAR NOT NULL,
    updated       VARCHAR NOT NULL
);
