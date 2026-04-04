CREATE TABLE exams
(
    id                  SERIAL PRIMARY KEY,
    title               VARCHAR(100) NOT NULL,
    date                DATE         NOT NULL,
    academic_semester   VARCHAR(2),
    goals               TEXT,
    service_unit        VARCHAR(100),
    procedure_performed VARCHAR(50),

    professor_id        UUID         NOT NULL REFERENCES users (id),
    specialism_id       INTEGER      NOT NULL REFERENCES specialisms (id),

    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by          INTEGER,

    updated_at          TIMESTAMP,
    updated_by          INTEGER,

    deleted_at          TIMESTAMP,
    deleted_by          INTEGER,
    deleted             BOOLEAN      NOT NULL DEFAULT FALSE
);