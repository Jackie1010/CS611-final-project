-- Create the `stocks` table to manage the available stocks and their current prices
CREATE TABLE IF NOT EXISTS stocks
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    symbol        TEXT    NOT NULL UNIQUE,
    name          TEXT    NOT NULL,
    current_price REAL    NOT NULL,
    tradeable     BOOLEAN NOT NULL DEFAULT 1
);

-- Create the `customers` table to manage customer information
CREATE TABLE IF NOT EXISTS customers
(
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name        TEXT    NOT NULL,
    last_name         TEXT    NOT NULL,
    username          TEXT    NOT NULL UNIQUE,
    email             TEXT    NOT NULL UNIQUE,
    password          TEXT    NOT NULL,
    approved          BOOLEAN NOT NULL DEFAULT 0,
    can_trade_options BOOLEAN NOT NULL DEFAULT 0,
    total_deposit     REAL    NOT NULL DEFAULT 100000,
    total_cash        REAL    NOT NULL DEFAULT 100000,
    total_invested    REAL    NOT NULL DEFAULT 0
);

-- Create the `trading_accounts` table to manage customer trading accounts and their balances
CREATE TABLE IF NOT EXISTS trading_accounts
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    approved    BOOLEAN NOT NULL DEFAULT 1,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);

-- Create the `trades` table to manage individual stock trades
CREATE TABLE IF NOT EXISTS trades
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id      INTEGER NOT NULL,
    symbol           INTEGER NOT NULL,
    trade_type       TEXT    NOT NULL, -- 'buy' or 'sell'
    quantity         INTEGER NOT NULL,
    price            REAL    NOT NULL,
    timestamp        DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_trade_cost REAL    NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE
);

-- Create the `unrealized_profits` table to store unrealized profits per stock for each customer
CREATE TABLE IF NOT EXISTS unrealized_profits
(
    customer_id INTEGER NOT NULL,
    symbol      TEXT    NOT NULL,
    profit      REAL    NOT NULL,
    PRIMARY KEY (symbol),
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE
);

-- Create the `realized_profits` table to store realized profits for each customer
CREATE TABLE IF NOT EXISTS realized_profits
(
    customer_id INTEGER PRIMARY KEY,
    profit      REAL NOT NULL,
    symbol      TEXT NOT NULL,
    PRIMARY KEY (symbol),
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE
);

-- Create the `admins` table to manage admin accounts
CREATE TABLE IF NOT EXISTS admins
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- Create the `notifications` table to manage notifications
CREATE TABLE IF NOT EXISTS notifications
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    message     TEXT    NOT NULL,
    is_read     BOOLEAN NOT NULL DEFAULT 0,
    timestamp   DATETIME         DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);