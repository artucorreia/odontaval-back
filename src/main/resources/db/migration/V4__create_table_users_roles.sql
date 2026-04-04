CREATE TABLE users_roles
(
    user_id UUID NOT NULL REFERENCES users (id),
    role_id INTEGER NOT NULL REFERENCES roles (id),

    PRIMARY KEY (user_id, role_id)
);