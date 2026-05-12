CREATE TABLE IF NOT EXISTS mail_tokens
(
    id         SERIAL PRIMARY KEY,
    user_id    UUID REFERENCES users (id) NOT NULL,
    token      VARCHAR(36)                NOT NULL,
    expires_at TIMESTAMP                  NOT NULL,
    type       varchar(40)
)