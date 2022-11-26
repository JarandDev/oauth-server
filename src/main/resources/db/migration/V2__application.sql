CREATE TABLE application
(
    id                 UUID PRIMARY KEY,
    name               VARCHAR NOT NULL,
    description        VARCHAR NOT NULL,
    home_page_url      VARCHAR NOT NULL,
    privacy_policy_url VARCHAR NOT NULL,
    client_id          UUID    NOT NULL,
    client_secret      VARCHAR NOT NULL,
    owner_account_id   UUID    NOT NULL REFERENCES account (id),
    created            VARCHAR NOT NULL,
    updated            VARCHAR NOT NULL
);
