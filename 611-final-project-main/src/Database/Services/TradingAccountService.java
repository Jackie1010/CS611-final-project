package Database.Services;

import java.sql.Connection;
import java.sql.ResultSet;

public class TradingAccountService implements Service {

    private Connection conn;

    /**
     * Constructor:
     */
    public TradingAccountService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Check if a TradingAccount for a id exists in the trading_accounts table
     */
    public ResultSet findTradingAccountByID(int id) {
        String query = "SELECT * FROM trading_accounts WHERE customer_id = " + id;
        return executeService(query, "select" );
    }

    /**
     * If a account doesn't exist, create an "application for it"
     * Called by the UserRepository
     */
    public ResultSet createTradingAccount(int id) {
        String query = "INSERT INTO trading_accounts (customer_id) VALUES (" + id + ");";
        return executeService(query, "insert" );
    }

    /**
     * Get unrealized profit for a customer
     */
    public ResultSet getUnrealizedProfit(int id) {
        String query = "SELECT profit FROM unrealized_profits WHERE customer_id = " + id;
        return executeService(query, "select");
    }

    /**
     * Get realized profit for a customer
     */
    public ResultSet getRealizedProfit(int id) {
        String query = "SELECT profit FROM realized_profits WHERE customer_id = " + id;
        return executeService(query, "select");
    }
}
