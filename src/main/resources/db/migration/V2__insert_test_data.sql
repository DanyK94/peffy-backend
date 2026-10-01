-- ============================================================
-- USERS
-- ============================================================

INSERT INTO users (id, username, email, passw)
VALUES
    (1, 'Alpha', 'alpha@peffy.com', 'password123'),
    (2, 'Bravo', 'bravo@peffy.com', 'password123');


-- ============================================================
-- ACCOUNTS
-- User 1
-- ============================================================

INSERT INTO accounts (
    id,
    user_id,
    account_name,
    account_type,
    currency,
    initial_balance
)
VALUES
    (1, 1, 'Intesa', 'BANK', 'EUR', 2000.00),
    (2, 1, 'Revolut', 'BANK', 'EUR', 1000.00),
    (3, 1, 'Cash', 'CASH', 'EUR', 300.00),

-- User 2
    (4, 2, 'Test Bank', 'BANK', 'EUR', 1500.00);


-- ============================================================
-- CATEGORIES
-- User 1
-- ============================================================

INSERT INTO categories (
    id,
    user_id,
    category_name,
    category_type,
    parent_id
)
VALUES
    (1, 1, 'Food', 'EXPENSE', NULL),
    (2, 1, 'Groceries', 'EXPENSE', 1),
    (3, 1, 'Restaurant', 'EXPENSE', 1),
    (4, 1, 'Transport', 'EXPENSE', NULL),
    (5, 1, 'Fuel', 'EXPENSE', 4),
    (6, 1, 'Entertainment', 'EXPENSE', NULL),

-- User 2
    (7, 2, 'Food', 'EXPENSE', NULL),
    (8, 2, 'Transport', 'EXPENSE', NULL);


-- ============================================================
-- BUDGETS
-- User 1 - October 2026
-- ============================================================

INSERT INTO budgets (
    id,
    user_id,
    category_id,
    amount,
    b_month
)
VALUES
    (1, 1, 1, 500.00, '2026-10-01'),
    (2, 1, 4, 200.00, '2026-10-01'),
    (3, 1, 6, 150.00, '2026-10-01'),

-- User 2
    (4, 2, 7, 300.00, '2026-10-01');


-- ============================================================
-- TRANSACTIONS
-- User 1
-- ============================================================

INSERT INTO transactions (
    id,
    account_id,
    category_id,
    transaction_date,
    amount,
    t_description,
    notes
)
VALUES

-- Intesa
    (1, 1, NULL, '2026-10-01', 2500.00, 'Salary', 'October salary'),

    (2, 1, 2, '2026-10-02', -80.00, 'Groceries', 'Weekly groceries'),
    (3, 1, 3, '2026-10-03', -50.00, 'Restaurant', 'Dinner'),

-- Revolut
    (4, 2, 2, '2026-10-04', -120.00, 'Groceries', 'Supermarket'),

    (5, 2, 5, '2026-10-05', -60.00, 'Fuel', 'Car fuel'),

    (6, 2, 6, '2026-10-06', -40.00, 'Cinema', 'Movie'),

-- Cash
    (7, 3, 3, '2026-10-07', -30.00, 'Restaurant', 'Lunch'),

    (8, 3, 5, '2026-10-08', -20.00, 'Fuel', 'Parking/fuel');


-- ============================================================
-- TRANSACTIONS
-- User 2
-- ============================================================

INSERT INTO transactions (
    id,
    account_id,
    category_id,
    transaction_date,
    amount,
    t_description,
    notes
)
VALUES
    (9, 4, NULL, '2026-10-01', 2000.00, 'Salary', 'October salary'),
    (10, 4, 7, '2026-10-03', -100.00, 'Groceries', 'Weekly groceries');


-- ============================================================
-- RESET SEQUENCES
-- ============================================================

SELECT setval(
    pg_get_serial_sequence('users', 'id'),
    (SELECT MAX(id) FROM users)
);

SELECT setval(
    pg_get_serial_sequence('accounts', 'id'),
    (SELECT MAX(id) FROM accounts)
);

SELECT setval(
    pg_get_serial_sequence('categories', 'id'),
    (SELECT MAX(id) FROM categories)
);

SELECT setval(
    pg_get_serial_sequence('budgets', 'id'),
    (SELECT MAX(id) FROM budgets)
);

SELECT setval(
    pg_get_serial_sequence('transactions', 'id'),
    (SELECT MAX(id) FROM transactions)
);