CREATE TABLE admin (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL,
    password_hash TEXT NOT NULL
);