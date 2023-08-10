package Database;

import java.sql.Connection;

public class CreateTables {

    // Fields
    Connection conn;

    // Constructor
    public CreateTables(Connection conn) {
        this.conn = conn;
    }

    // Methods to create individual tables
    /*
     * Create the `customers` table to manage customer information
     */
    public void createCustomersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS customers\n" +
                "(\n" +
                "    id                INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    first_name        TEXT    NOT NULL,\n" +
                "    last_name         TEXT    NOT NULL,\n" +
                "    username          TEXT    NOT NULL UNIQUE,\n" +
                "    email             TEXT    NOT NULL UNIQUE,\n" +
                "    password          TEXT    NOT NULL,\n" +
                "    approved          BOOLEAN NOT NULL DEFAULT 0,\n" +
                "    can_trade_options BOOLEAN NOT NULL DEFAULT 0,\n" +
                "    total_deposit     REAL    NOT NULL DEFAULT 0,\n" +
                "    total_cash        REAL    NOT NULL DEFAULT 0,\n" +
                "    total_invested    REAL    NOT NULL DEFAULT 0\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating customers table");
            conn.createStatement().execute(sql);
            System.out.println("Created customers table");
        } catch (Exception e) {
            System.out.println("Error creating customers table");
            System.out.println(e.getMessage());
        }
    }

    /*
     * Create the `stocks` table to manage stock information
     */
    public void createStocksTable() {
        String sql = "CREATE TABLE IF NOT EXISTS stocks\n" +
                "(\n" +
                "    id            INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    symbol        TEXT NOT NULL UNIQUE,\n" +
                "    name          TEXT NOT NULL,\n" +
                "    current_price REAL NOT NULL,\n" +
                "    tradeable    BOOLEAN NOT NULL DEFAULT 1\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating stocks table");
            conn.createStatement().execute(sql);
            System.out.println("Created stocks table");
        } catch (Exception e) {
            System.out.println("Error creating stocks table");
            System.out.println(e.getMessage());
        }
    }

    /*
     * Create the `trading_accounts` table to manage customer trading accounts and their balances
     */
    public void createTradingAccounts() {
        String sql = "CREATE TABLE IF NOT EXISTS trading_accounts\n" +
                "(\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    customer_id INTEGER NOT NULL,\n" +
                "    approved    BOOLEAN NOT NULL DEFAULT 1,\n" +
                "    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating trading_accounts table");
            conn.createStatement().execute(sql);
            System.out.println("Created trading_accounts table");
        } catch (Exception e) {
            System.out.println("Error creating trading_accounts table");
            System.out.println(e.getMessage());
        }
    }

    /*
     * Create the `trades` table to manage individual stock trades
     */
    public void createTradesTable(){
        String sql = "CREATE TABLE IF NOT EXISTS trades\n" +
                "(\n" +
                "    id               INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    customer_id      INTEGER NOT NULL,\n" +
                "    symbol           INTEGER NOT NULL,\n" +
                "    trade_type       TEXT    NOT NULL, -- 'buy' or 'sell'\n" +
                "    quantity         INTEGER NOT NULL,\n" +
                "    price            REAL    NOT NULL,\n" +
                "    timestamp        DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    total_trade_cost REAL    NOT NULL,\n" +
                "    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,\n" +
                "    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating trades table");
            conn.createStatement().execute(sql);
            System.out.println("Created trades table");
        } catch (Exception e) {
            System.out.println("Error creating trades table");
            System.out.println(e.getMessage());
        }
    }


    /**
     * Create the `unrealized_profits` table to manage customer unrealized profits
     */
    public void createTableUnRealizeProfits() {
        String sql = "CREATE TABLE IF NOT EXISTS unrealized_profits\n" +
                "(\n" +
                "    customer_id INTEGER NOT NULL,\n" +
                "    symbol      TEXT    NOT NULL,\n" +
                "    profit      REAL    NOT NULL,\n" +
                "    PRIMARY KEY (symbol),\n" +
                "    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,\n" +
                "    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating unrealized_profits table");
            conn.createStatement().execute(sql);
            System.out.println("Created unrealized_profits table");
        } catch (Exception e) {
            System.out.println("Error creating unrealized_profits table");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create the `realized_profits` table to manage customer realized profits
     */
    public void createTableRealizedProfits() {
        String sql = "CREATE TABLE IF NOT EXISTS realized_profits\n" +
                "(\n" +
                "    customer_id INTEGER,\n" +
                "    profit      REAL NOT NULL,\n" +
                "    symbol      TEXT NOT NULL,\n" +
                "    PRIMARY KEY (symbol),\n" +
                "    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,\n" +
                "    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating realized_profits table");
            conn.createStatement().execute(sql);
            System.out.println("Created realized_profits table");
        } catch (Exception e) {
            System.out.println("Error creating realized_profits table");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create the `admins` table to manage admin accounts
     */
    public void createTableAdmins() {
        String sql = "CREATE TABLE IF NOT EXISTS admins\n" +
                "(\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT NOT NULL UNIQUE,\n" +
                "    password TEXT NOT NULL\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating admins table");
            conn.createStatement().execute(sql);
            System.out.println("Created admins table");
        } catch (Exception e) {
            System.out.println("Error creating admins table");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create the `customers` table to manage customer accounts
     */
    public void createDefaultAdmin(){
        // create default admin user with username: admin, password: admin
        String sql = "INSERT INTO admins (username, password) VALUES ('admin', 'admin');";
        // try to execute the sql statement
        try {
            System.out.println("Creating default admin user");
            conn.createStatement().execute(sql);
            System.out.println("Created default admin user");
        } catch (Exception e) {
            System.out.println("Error creating default admin user");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create the `notifications` table to manage notifications
     */
    public void createNotificationsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS notifications (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    customer_id INTEGER NOT NULL,\n" +
                "    message TEXT NOT NULL,\n" +
                "    is_read BOOLEAN NOT NULL DEFAULT 0,\n" +
                "    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating notifications table");
            conn.createStatement().execute(sql);
            System.out.println("Created notifications table");
        } catch (Exception e) {
            System.out.println("Error creating notifications table");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create the `stock_updates` table to manage stock price updates
     */
    public void createStockUpdateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS stock_updates (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    symbol      TEXT NOT NULL,\n" +
                "    update_price            REAL    NOT NULL,\n" +
                "    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    FOREIGN KEY (symbol) REFERENCES stocks (symbol) ON DELETE CASCADE\n" +
                ");";
        // try to execute the sql statement
        try {
            System.out.println("Creating stock_update table");
            conn.createStatement().execute(sql);
            System.out.println("Created stock_update table");
        } catch (Exception e) {
            System.out.println("Error creating stock_update table");
            System.out.println(e.getMessage());
        }
    }


    /*
     * Aggregate Function to create all tables
     */
    public void createAllTables() {
        createCustomersTable();
        createStocksTable();
        createTradingAccounts();
        createTradesTable();
        createTableUnRealizeProfits();
        createTableRealizedProfits();
        createTableAdmins();
        createDefaultAdmin();
        createNotificationsTable();
        createStockUpdateTable();
    }

}
