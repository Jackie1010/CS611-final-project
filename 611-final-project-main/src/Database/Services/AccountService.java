package Database.Services;

import Database.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class AccountService implements Service {

    private Connection conn;

    /*
     * Constructor:
     */
    public AccountService(Connection conn) {
        this.conn = conn;
    }


    /**
     * Method to create a customer account
     * @param username
     * @param type
     * @return
     */
    public int getUserID(String username, String type) {
        // Construct the sql statement to return the customer_id of the given username
        String sql;
        if (type.equals("customer")) {
            sql = "SELECT id FROM customers WHERE username = '" + username + "';";
        } else {
            sql = "SELECT id FROM admins WHERE username = '" + username + "';";
        }
        // try to execute the sql statement
        try {
            System.out.println("Getting customer_id");
            System.out.println(sql);
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                int user_id = rs.getInt("id");
                System.out.println("Got user_id");
                return user_id;
            }
        } catch (Exception e) {
            System.out.println("Error getting customer_id");
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }


    /**
     * Method to find customer by username
     * @param username
     * @return
     */
    public ResultSet findCustomerByUsername(String username) {
        // Given a username, return the result set when querying the customers table
        // and the admin table
        String sql = "SELECT * FROM customers WHERE username = '" + username + "';";
        ResultSet rs = executeService(sql, "select");
        return rs;
    }


    /**
     * Method to find admin by username
     * @param userID
     * @return
     */
    public float getCashRemaining(int userID) {
        // Given a userID, return the cash_remaining of the user
        String sql = "SELECT total_cash FROM customers WHERE id = " + userID + ";";
        // try to execute the sql statement
        try {
            System.out.println("Getting cash_remaining");
            System.out.println(sql);
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                float cash_remaining = rs.getFloat("total_cash");
                System.out.println("Got cash_remaining");
                return cash_remaining;
            }
        } catch (Exception e) {
            System.out.println("Error getting cash_remaining");
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    /**
     * Update the cash remaining of a given customer
     * @param customerID
     * @param newCashAvailable
     */
    public void updateCashAvailable(int customerID, double newCashAvailable) {
        // Given a customerID, update the total_cash of the user
        String sql = "UPDATE customers SET total_cash = " + newCashAvailable + " WHERE id = " + customerID + ";";
        // try to execute the sql statement
        try {
            System.out.println("Updating cash_available");
            System.out.println(sql);
            conn.createStatement().execute(sql);
            System.out.println("Updated cash_available");
        } catch (Exception e) {
            System.out.println("Error updating cash_available");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the total invested of a given customer
     * @param customerID
     * @return
     */
    public float getTotalInvested(int customerID) {
        // Given a customerID, return the total_invested of the user
        String sql = "SELECT total_invested FROM customers WHERE id = " + customerID + ";";
        // try to execute the sql statement
        try {
            System.out.println("Getting total_invested");
            System.out.println(sql);
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                float total_invested = rs.getFloat("total_invested");
                System.out.println("Got total_invested");
                return total_invested;
            }
        } catch (Exception e) {
            System.out.println("Error getting total_invested");
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    /**
     * Uopdate the total invested of a given customer
     * @param customerID
     * @param newTotalInvested
     */
    public void updateTotalInvested(int customerID, double newTotalInvested) {
        // Given a customerID, update the total_invested of the user
        String sql = "UPDATE customers SET total_invested = " + newTotalInvested + " WHERE id = " + customerID + ";";
        // try to execute the sql statement
        try {
            System.out.println("Updating total_invested");
            System.out.println(sql);
            conn.createStatement().execute(sql);
            System.out.println("Updated total_invested");
        } catch (Exception e) {
            System.out.println("Error updating total_invested");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the realized profit of a given stock for a given customer
     * @param customerID
     * @param newCash
     * @param stockSymbol
     */
    public void updateRealizedProfitByStock(int customerID, double newCash, String stockSymbol) {
        // sql should set the realized_profit of the given stock to the newCash for a given customer
        String sql = "INSERT OR REPLACE INTO realized_profits (customer_id, symbol, profit) VALUES (" + customerID + ", '" + stockSymbol + "', " + newCash + ");";
        executeService(sql, "update");
    }

    /**
     * Returns the unrealized profit of a given stock for a given customer
     * @param customerID
     * @param newUnrealizedProfit
     * @param stockSymbol
     */
    public void updateUnrealizedProfitByStock(int customerID, double newUnrealizedProfit, String stockSymbol) {
        // sql should set the unrealized_profit of the given stock to the newUnrealizedProfit for a given customer
        String sql = "INSERT OR REPLACE INTO unrealized_profits (customer_id, symbol, profit) VALUES (" + customerID + ", '" + stockSymbol + "', " + newUnrealizedProfit + ");";
        executeService(sql, "update");
    }

    /**
     * Returns the realized profit of a given stock for a given customer
     * @param customerID
     * @param stockSymbol
     * @return
     */
    public ResultSet getRealizedProfitByStock(int customerID, String stockSymbol) {
        // sql should return the realized_profit of the given stock for a given customer
        String sql = "SELECT profit FROM realized_profits WHERE customer_id = " + customerID + " AND symbol = '" + stockSymbol + "';";
        return executeService(sql, "select");
    }

    /**
     * Returns the unrealized profit of a given stock for a given customer
     * @param symbol
     * @param customerID
     * @return
     */
    public ResultSet getUnrealizedProfitByStock(String symbol, int customerID) {
        // sql should return the unrealized_profit of the given stock for a given customer
        String sql = "SELECT profit FROM unrealized_profits WHERE customer_id = " + customerID + " AND symbol = '" + symbol + "';";
        return executeService(sql, "select");
    }

    /**
     * Returns the average price of a given stock for a given customer
     * @param symbol
     * @param customerID
     * @return
     */
    public ResultSet getAveragePriceByStock(String symbol, int customerID) {
        // sql should return the average_price of the given stock for a given customer
        String sql = "WITH\n" +
                "    buy_trades AS (\n" +
                "        SELECT\n" +
                "            SUM(price * quantity) AS total_buy_cost,\n" +
                "            SUM(quantity) AS total_buy_shares\n" +
                "        FROM\n" +
                "            trades\n" +
                "        WHERE\n" +
                "            customer_id = " + customerID + "\n" +
                "            AND symbol = '" + symbol + "'\n" +
                "            AND trade_type = 'buy'\n" +
                "    ),\n" +
                "    sell_trades AS (\n" +
                "        SELECT\n" +
                "            SUM(price * quantity) AS total_sell_cost,\n" +
                "            SUM(quantity) AS total_sell_shares\n" +
                "        FROM\n" +
                "            trades\n" +
                "        WHERE\n" +
                "            customer_id = " + customerID + "\n" +
                "            AND symbol = '" + symbol + "'\n" +
                "            AND trade_type = 'sell'\n" +
                "    )\n" +
                "SELECT\n" +
                "    (total_buy_cost - COALESCE(total_sell_cost, 0)) / (total_buy_shares - COALESCE(total_sell_shares, 0)) AS average_cost_per_share\n" +
                "FROM\n" +
                "    buy_trades\n" +
                "LEFT JOIN\n" +
                "    sell_trades ON 1=1;\n";
        return executeService(sql, "select");
    }


    /**
     * This method is used to get the customer id for a given customer
     * @param customerID
     * @return
     */
    public ResultSet getRealizedProfitByCustomerID(int customerID) {
        String sql = "SELECT SUM(profit) as total_realized_profit FROM realized_profits WHERE customer_id = " + customerID + ";";
        return executeService(sql, "select");
    }

    /**
     * This method is used to get the customer id for a given customer
     * @param customerID
     * @return
     */
    public ResultSet getUnRealizedProfitByCustomerID(int customerID) {
        String sql = "SELECT SUM(profit) as total_unrealized_profit FROM unrealized_profits WHERE customer_id = " + customerID + ";";
        return executeService(sql, "select");
    }

    /**
     * This method is used to get the customer id for a given customer
     * @param customerID
     * @return
     */
    public ResultSet getInfo(int customerID) {
        // return customer id for a given customer
        String sql = "SELECT * FROM customers WHERE id = " + customerID + ";";
        return executeService(sql, "select");
    }

    /**
     * This method is used to get the customer id for a given customer
     * @param customerID
     * @return
     */
    public ResultSet checkIfTradingAccountExists(int customerID) {
        // return customer id for a given customer
        String sql = "SELECT * FROM trading_accounts  WHERE customer_id = " + customerID + ";";
        return executeService(sql, "select");
    }

    /**
     * This method is used to get the customer id for a given customer
     * @param username
     * @return
     */
    public ResultSet findAdminByUsername(String username) {
        // Given a username, return the result set when querying the customers table
        // and the admin table
        String sql = "SELECT * FROM admins WHERE username = '" + username + "';";
        return executeService(sql, "select");
    }

    /**
     * This method is used to get the customer id for a given customer
     * @param customerID
     * @return
     */
    public ResultSet getCustomerApproved(int customerID) {
        String sql = "SELECT approved FROM customers WHERE id = " + customerID + ";";
        return executeService(sql, "select");
    }


    /**
     * This method is used to update the total deposit of a customer
     * @param customerid
     * @param cashToAdd
     * @return
     */
    public ResultSet updateDeposit(int customerid, float cashToAdd) {
        // return customer id for a given customer
        String sql = "UPDATE customers SET total_deposit = total_deposit + " + cashToAdd + " WHERE customer_id = " + customerid + ";";
        return executeService(sql, "update");
    }

    /**
     * This method is used to get the total amount invsted by a customer in a given stock
     * @param customerID
     * @param stockSymbol
     * @return
     */
    public ResultSet getInvestedByStock(int customerID, String stockSymbol) {
        // return customer id for a given customer
        String sql = "WITH net_shares AS (\n" +
                "    SELECT\n" +
                "        c.id AS customer_id,\n" +
                "        s.id AS stock_id,\n" +
                "        s.symbol,\n" +
                "        s.name,\n" +
                "        SUM(CASE WHEN t.trade_type = 'buy' THEN t.quantity ELSE -t.quantity END) AS shares\n" +
                "    FROM\n" +
                "        customers c\n" +
                "    JOIN\n" +
                "        trades t ON c.id = t.customer_id\n" +
                "    JOIN\n" +
                "        stocks s ON t.symbol = s.symbol\n" +
                "    WHERE\n" +
                "        c.id = " + customerID + "\n" +
                "    AND\n" +
                "        s.symbol = '" + stockSymbol + "'\n" +
                "    GROUP BY\n" +
                "        c.id, s.id\n" +
                "    HAVING\n" +
                "        SUM(CASE WHEN t.trade_type = 'buy' THEN t.quantity ELSE -t.quantity END) > 0\n" +
                "),\n" +
                "weighted_purchase_data AS (\n" +
                "    SELECT\n" +
                "        c.id AS customer_id,\n" +
                "        s.id AS stock_id,\n" +
                "        s.symbol,\n" +
                "        s.name,\n" +
                "        SUM(CASE WHEN t.trade_type = 'buy' THEN t.quantity * t.price ELSE 0 END) AS weighted_purchase_cost,\n" +
                "        SUM(CASE WHEN t.trade_type = 'buy' THEN t.quantity ELSE 0 END) AS purchased_shares\n" +
                "    FROM\n" +
                "        customers c\n" +
                "    JOIN\n" +
                "        trades t ON c.id = t.customer_id\n" +
                "    JOIN\n" +
                "        stocks s ON t.symbol = s.symbol\n" +
                "    WHERE\n" +
                "        c.id = " + customerID + "\n" +
                "    AND\n" +
                "        s.symbol = '" + stockSymbol + "'\n" +
                "    GROUP BY\n" +
                "        c.id, s.id\n" +
                ")\n" +
                "SELECT\n" +
                "    ns.customer_id,\n" +
                "    ns.stock_id,\n" +
                "    ns.symbol,\n" +
                "    ns.name,\n" +
                "    (wpd.weighted_purchase_cost / wpd.purchased_shares) * ns.shares AS total_cost\n" +
                "FROM\n" +
                "    net_shares ns\n" +
                "JOIN\n" +
                "    weighted_purchase_data wpd ON ns.customer_id = wpd.customer_id AND ns.stock_id = wpd.stock_id;\n";
        return executeService(sql, "select");
    }

    /*
     * Check if the user exists in the database
     * @param email: the email of the user to check
     * @return: 1 if the user exists, 0 if the user does not exist, -1 if there was an error
     */
    public int checkIfUserExists(String username) {
        // Construct the sql statement to check if a row with the given email exists in customers table
        String sql = "SELECT * FROM customers WHERE username = '" + username + "';";
        // try to execute the sql statement
        try {
            System.out.println("Checking if user exists");
//            System.out.println(sql);
            // if the result set is not empty, then the user exists
            if (conn.createStatement().executeQuery(sql).next()) {
                System.out.println("User exists");
                return 1;
            }
            System.out.println("User does not exist");
        } catch (Exception e) {
            System.out.println("Error checking if user exists");
            System.out.println(e.getMessage());
            return -1;
        }
        return 0;
    }

    /**
     * Check if the email exists in the database
     * @param email
     * @return
     */
    public int checkIfEmailExists(String email) {
        // Construct the sql statement to check if a row with the given email exists in customers table
        String sql = "SELECT * FROM customers WHERE email = '" + email + "';";
        // try to execute the sql statement
        try {
            System.out.println("Checking if email exists");
            if (conn.createStatement().executeQuery(sql).next()) {
                System.out.println("Email exists");
                return 1;
            }
            System.out.println("Email does not exist");
        } catch (Exception e) {
            System.out.println("Error checking if email exists");
            System.out.println(e.getMessage());
            return -1;
        }
        return 0;
    }


    /*
     * Check if the user exists in the database
     * @return: boolean: true if the user created, false otherwise
     */
    public boolean createUser(String firstName, String lastName, String email, String password, String username) {
        // Construct the sql statement to insert a new row into the customers table with first_name, last_name, email, password, and username
        String sql = "INSERT INTO customers (first_name, last_name, email, password, username) VALUES ('" + firstName + "', '" + lastName + "', '" + email + "', '" + password + "', '" + username + "');";
        // try to execute the sql statement
        try {
            System.out.println("Creating user");
            System.out.println(sql);
            Connect.getConnection().createStatement().execute(sql);
            System.out.println("Created user");
        } catch (Exception e) {
            System.out.println("Error creating user");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /*
     * Check if the user enters a valid email.
     * A valid email format is as follows: <username>@<domain>.<extension>
     */
    public boolean emailSanityCheck(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        return pattern.matcher(email).matches();
    }


    /*
     * Check if the user exists in the database
     * @return: boolean: true if the user delete, false otherwise
     * Only takes email as parameter since email is the unique identifier
     */
    public boolean deleteUser(String username) {
        // Check if the user exists
        if (checkIfUserExists(username) != 1) {
            System.out.println("User does not exist");
            return false;
        }
        // Construct the sql statement to delete a row from the customers table
        String sql = "DELETE FROM customers WHERE username = '" + username + "';";
        // try to execute the sql statement
        try {
            System.out.println("Deleting user");
            System.out.println(sql);
            conn.createStatement().execute(sql);
            System.out.println("Deleted user");
        } catch (Exception e) {
            System.out.println("Error deleting user");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /*
     * Handles the account creation process
     * First checks if the email passes the sanity check
     * Then checks if the user exists
     * If the user does not exist, then create the user
     * @return: 1 if the user already exists, 0 if the user was created, -1 if there was an error
     */
    public int handleAccountCreation(String firstName, String lastName, String email, String password, String username) {
        // First check if the email passes the sanity check
        if (!emailSanityCheck(email)) {
            // Email does not pass sanity check
            System.out.println("Email does not pass sanity check");
            return -1;
        }
        // Check if the user exists
        int userExists = checkIfUserExists(username);
        switch (userExists) {
            case 1:
                // User exists
                System.out.println("User exists");
                return 1;
            case 0:
                // User does not exist
                System.out.println("User does not exist");
                // Create the user
                boolean userCreated = createUser(firstName, lastName, email, password, username);
                if (userCreated) {
                    // User created
                    System.out.println("User created");
                    return 0;
                } else {
                    // User not created
                    System.out.println("User not created");
                    return -1;
                }
            default:
                // Error checking if user exists
                System.out.println("Error checking if user exists");
                return -1;
        }
    }
}
