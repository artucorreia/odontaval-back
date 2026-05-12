CREATE TABLE IF NOT EXISTS evaluations
(
    id                   SERIAL PRIMARY KEY,

    title                VARCHAR(50),

    -- critérios avaliativos (range: -10 a 0)
    punctuality          NUMERIC(5, 2) NOT NULL CHECK (punctuality BETWEEN -10 AND 0),
    instrumental         NUMERIC(5, 2) NOT NULL CHECK (instrumental BETWEEN -10 AND 0),
    box_organization     NUMERIC(5, 2) NOT NULL CHECK (box_organization BETWEEN -10 AND 0),
    biosecurity          NUMERIC(5, 2) NOT NULL CHECK (biosecurity BETWEEN -10 AND 0),
    ethics               NUMERIC(5, 2) NOT NULL CHECK (ethics BETWEEN -10 AND 0),
    concept              NUMERIC(5, 2) NOT NULL CHECK (concept BETWEEN -10 AND 0),

    -- nota final calculada (range: 0 a 10)
    grade                NUMERIC(5, 2) NOT NULL CHECK (grade BETWEEN 0 AND 10),

    observations         TEXT,
    evaluation_number    VARCHAR(3)    CHECK (evaluation_number IN ('AV1', 'AV2', 'AV3')),

    date                 DATE          NOT NULL,
    academic_semester    VARCHAR(6),
    goals                TEXT,
    box                  VARCHAR(20),
    procedure_performed  VARCHAR(50),

    professor_id         UUID          NOT NULL REFERENCES users (id),
    student_id           UUID          NOT NULL REFERENCES users (id),
    specialism_id        INTEGER       NOT NULL REFERENCES specialisms (id),

    created_at           TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by           UUID,

    updated_at           TIMESTAMP,
    updated_by           UUID,

    deleted_at           TIMESTAMP,
    deleted_by           UUID,
    deleted              BOOLEAN       NOT NULL DEFAULT FALSE
);
