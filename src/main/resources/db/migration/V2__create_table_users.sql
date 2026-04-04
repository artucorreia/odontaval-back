CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    specialism_id INTEGER REFERENCES specialisms (id),

    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,

    updated_at TIMESTAMP,
    updated_by INTEGER,

    deleted_at TIMESTAMP,
    deleted_by INTEGER,
    deleted    BOOLEAN      NOT NULL DEFAULT FALSE
);