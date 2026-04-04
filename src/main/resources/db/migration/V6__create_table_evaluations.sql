CREATE TABLE evaluations
(
    id                           SERIAL PRIMARY KEY,

    punctuality                  NUMERIC(4, 2) NOT NULL CHECK (punctuality BETWEEN 1 AND 10),
    instrumental                 NUMERIC(4, 2) NOT NULL CHECK (instrumental BETWEEN 1 AND 10),
    organization_of_service_unit NUMERIC(4, 2) NOT NULL CHECK (organization_of_service_unit BETWEEN 1 AND 10),
    biosecurity                  NUMERIC(4, 2) NOT NULL CHECK (biosecurity BETWEEN 1 AND 10),
    ethics                       NUMERIC(4, 2) NOT NULL CHECK (ethics BETWEEN 1 AND 10),
    concept                      NUMERIC(4, 2) NOT NULL CHECK (concept BETWEEN 1 AND 10),
    observations                 TEXT,

    student_id                   UUID REFERENCES users (id),
    exam_id                      INTEGER REFERENCES exams (id),


    created_at                   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by                   UUID,

    updated_at                   TIMESTAMP,
    updated_by                   UUID,

    deleted_at                   TIMESTAMP,
    deleted_by                   UUID,
    deleted                      BOOLEAN       NOT NULL DEFAULT FALSE
);