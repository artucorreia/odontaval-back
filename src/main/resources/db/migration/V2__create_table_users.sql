CREATE TABLE IF NOT EXISTS users
(
    id                UUID PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    email             VARCHAR(150) NOT NULL UNIQUE,
    password          VARCHAR(255) NOT NULL,

    verified_email    BOOLEAN      NOT NULL,
    verified_email_at TIMESTAMP,
    verified_email_by UUID,

    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by        UUID,

    updated_at        TIMESTAMP,
    updated_by        UUID,

    deleted_at        TIMESTAMP,
    deleted_by        UUID,
    deleted           BOOLEAN      NOT NULL DEFAULT FALSE
);