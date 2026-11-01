-- V2__insert_test_data.sql
-- Dati di test (solo DEV). Nessun ID hardcoded: le FK vengono risolte per email/nome,
-- così IDENTITY e SERIAL restano coerenti.
-- Convenzione importi: entrate > 0, spese < 0.

-- =========================
-- USER
-- =========================
-- password_hash: placeholder in formato bcrypt, sostituiscilo con un hash generato dal tuo PasswordEncoder
INSERT INTO users (email, password_hash, first_name, last_name)
VALUES ('mario.rossi@example.com',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi5BEw5E3Z8a3c9Yc0u1x0vQ2xKQe6S',
        'Mario',
        'Rossi');

-- =========================
-- ACCOUNTS
-- =========================
INSERT INTO accounts (user_id, account_name, account_type, currency, initial_balance)
VALUES ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Conto Corrente', 'CHECKING', 'EUR', 1500.0000),
       ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Conto Risparmio', 'SAVINGS',  'EUR', 5000.0000);

-- =========================
-- CATEGORIES
-- =========================
-- Categorie radice
INSERT INTO categories (user_id, category_name, category_type, parent_id)
VALUES ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Stipendio',  'INCOME',  NULL),
       ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Interessi',  'INCOME',  NULL),
       ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Casa',       'EXPENSE', NULL),
       ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Alimentari', 'EXPENSE', NULL),
       ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Trasporti',  'EXPENSE', NULL),
       ((SELECT id FROM users WHERE email = 'mario.rossi@example.com'), 'Svago',      'EXPENSE', NULL);

-- Sottocategorie di "Casa"
INSERT INTO categories (user_id, category_name, category_type, parent_id)
SELECT p.user_id, v.name, 'EXPENSE', p.id
FROM categories p
         CROSS JOIN (VALUES ('Affitto'), ('Bollette')) AS v(name)
WHERE p.category_name = 'Casa'
  AND p.user_id = (SELECT id FROM users WHERE email = 'mario.rossi@example.com');

-- =========================
-- TRANSACTIONS (settembre - ottobre 2026)
-- =========================
INSERT INTO transactions (account_id, category_id, transaction_date, amount, t_description, notes)
SELECT a.id, c.id, v.tx_date, v.amount, v.descr, v.notes
FROM (VALUES
          -- Settembre 2026
          (DATE '2026-09-01', 'Conto Corrente', 'Affitto',     -750.00, 'Affitto settembre',        NULL),
          (DATE '2026-09-03', 'Conto Corrente', 'Alimentari',   -62.40, 'Spesa supermercato',       NULL),
          (DATE '2026-09-05', 'Conto Corrente', 'Trasporti',    -45.00, 'Abbonamento bus',          'Mensile'),
          (DATE '2026-09-10', 'Conto Corrente', 'Bollette',     -88.30, 'Bolletta luce',            NULL),
          (DATE '2026-09-12', 'Conto Corrente', 'Svago',        -28.00, 'Cinema e pizza',           'Con amici'),
          (DATE '2026-09-15', 'Conto Corrente', 'Alimentari',   -41.75, 'Spesa supermercato',       NULL),
          (DATE '2026-09-20', 'Conto Corrente', 'Trasporti',    -55.00, 'Rifornimento carburante',  NULL),
          (DATE '2026-09-27', 'Conto Corrente', 'Stipendio',   2200.00, 'Stipendio settembre',      NULL),
          (DATE '2026-09-28', 'Conto Corrente', NULL,          -500.00, 'Trasferimento a risparmio','Giroconto'),
          (DATE '2026-09-28', 'Conto Risparmio', NULL,          500.00, 'Trasferimento da conto corrente', 'Giroconto'),
          (DATE '2026-09-30', 'Conto Risparmio', 'Interessi',     3.20, 'Interessi trimestrali',    NULL),

          -- Ottobre 2026
          (DATE '2026-10-01', 'Conto Corrente', 'Affitto',     -750.00, 'Affitto ottobre',          NULL),
          (DATE '2026-10-04', 'Conto Corrente', 'Alimentari',   -78.90, 'Spesa supermercato',       NULL),
          (DATE '2026-10-05', 'Conto Corrente', 'Trasporti',    -45.00, 'Abbonamento bus',          'Mensile'),
          (DATE '2026-10-09', 'Conto Corrente', 'Bollette',     -54.10, 'Bolletta gas',             NULL),
          (DATE '2026-10-11', 'Conto Corrente', 'Svago',        -65.00, 'Concerto',                 'Biglietti'),
          (DATE '2026-10-16', 'Conto Corrente', 'Alimentari',   -36.20, 'Spesa supermercato',       NULL),
          (DATE '2026-10-18', 'Conto Corrente', 'Trasporti',    -60.00, 'Rifornimento carburante',  NULL),
          (DATE '2026-10-22', 'Conto Corrente', 'Svago',        -19.99, 'Abbonamento streaming',    NULL),
          (DATE '2026-10-27', 'Conto Corrente', 'Stipendio',   2200.00, 'Stipendio ottobre',        NULL),
          (DATE '2026-10-28', 'Conto Corrente', NULL,          -500.00, 'Trasferimento a risparmio','Giroconto'),
          (DATE '2026-10-28', 'Conto Risparmio', NULL,          500.00, 'Trasferimento da conto corrente', 'Giroconto')
     ) AS v(tx_date, account_name, category_name, amount, descr, notes)
         JOIN accounts a ON a.account_name = v.account_name
         LEFT JOIN categories c ON c.category_name = v.category_name;