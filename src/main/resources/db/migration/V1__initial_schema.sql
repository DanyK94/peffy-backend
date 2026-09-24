CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    passw VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    account_name VARCHAR(100) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    initial_balance NUMERIC(19, 4) NOT NULL DEFAULT 0,

    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    category_type VARCHAR(50) NOT NULL,
    parent_id INT,

    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_categories_parent FOREIGN KEY (parent_id) REFERENCES categories(id)
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    category_id INT,
    transaction_date DATE NOT NULL DEFAULT CURRENT_DATE,
    amount NUMERIC(19, 4) NOT NULL,
    t_description VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id)
);


CREATE TABLE budgets (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    b_month DATE NOT NULL,

    CONSTRAINT fk_budget_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_budget_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT uq_budget_user_category_month UNIQUE (user_id, category_id, b_month)
);