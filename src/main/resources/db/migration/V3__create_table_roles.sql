CREATE TABLE roles
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL UNIQUE,

    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,

    updated_at TIMESTAMP,
    updated_by INTEGER,

    deleted_at TIMESTAMP,
    deleted_by INTEGER,
    deleted    BOOLEAN     NOT NULL DEFAULT FALSE
);