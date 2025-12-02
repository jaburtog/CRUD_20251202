-- PostgreSQL Database Schema for Users CRUD Application
-- This script creates the database and the users table

-- Create database (run this manually or via psql)
-- CREATE DATABASE usersdb;

-- Connect to the database
-- \c usersdb

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_active ON users(active);

-- Insert sample data (optional)
INSERT INTO users (name, email, phone, active) VALUES
    ('John Doe', 'john.doe@example.com', '+1234567890', true),
    ('Jane Smith', 'jane.smith@example.com', '+0987654321', true),
    ('Bob Johnson', 'bob.johnson@example.com', '+1122334455', true)
ON CONFLICT (email) DO NOTHING;

-- Grant permissions (adjust as needed for your environment)
-- GRANT ALL PRIVILEGES ON DATABASE usersdb TO postgres;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
-- GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO postgres;

COMMENT ON TABLE users IS 'Stores user information for the CRUD application';
COMMENT ON COLUMN users.id IS 'Unique identifier for each user';
COMMENT ON COLUMN users.name IS 'Full name of the user';
COMMENT ON COLUMN users.email IS 'Email address - must be unique';
COMMENT ON COLUMN users.phone IS 'Phone number (optional)';
COMMENT ON COLUMN users.created_at IS 'Timestamp when the user was created';
COMMENT ON COLUMN users.updated_at IS 'Timestamp when the user was last updated';
COMMENT ON COLUMN users.active IS 'Flag to indicate if the user is active';
