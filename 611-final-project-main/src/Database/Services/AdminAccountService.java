package Database.Services;

import java.sql.Connection;
import java.sql.ResultSet;

public class AdminAccountService implements Service {

    private Connection conn;


    /**
     * Constructor:
     */
    public AdminAccountService(Connection conn) {
        this.conn = conn;
    }


    /**
     * Method to deny customer
     * @param customerId
     */
    public void denyCustomer(String customerId) {
        String sql = "DELETE FROM customers WHERE id = " + customerId + ";";
        executeService(sql, "delete");
    }

    /**
     * Method to approve customer
     * @param customerId
     */
    public void approveCustomer(String customerId) {
        String sql = "UPDATE customers SET approved = 1 WHERE id = " + customerId + ";";
        executeService(sql, "update");
    }

    /**
     * Method to get all unapproved customers
     * @return
     */
    public ResultSet getUnApprovedCustomers() {
        String sql = "SELECT * FROM customers WHERE approved = 0;";
        return executeService(sql, "select");
    }


    /**
     * Method to get all approved customers
     * @return
     */
    public ResultSet getApprovedCustomers() {
        String sql = "SELECT \n" +
                "    c.id AS customer_id,\n" +
                "    c.first_name || ' ' || c.last_name AS customer_name,\n" +
                "    ta.id AS trading_account_id,\n" +
                "    COALESCE((SELECT SUM(profit) FROM unrealized_profits WHERE customer_id = c.id), 0) AS total_unrealized_profits,\n" +
                "    COALESCE((SELECT SUM(profit) FROM realized_profits WHERE customer_id = c.id), 0) AS total_realized_profits\n" +
                "FROM \n" +
                "    customers c\n" +
                "INNER JOIN \n" +
                "    trading_accounts ta ON c.id = ta.customer_id\n" +
                "WHERE\n" +
                "    c.approved = 1\n" +
                "ORDER BY \n" +
                "    c.id;\n";
        return executeService(sql, "select");
    }

    /**
     * Method to get all customers who have bought a stock
     * @param symbol
     * @return
     */
    public ResultSet getCustomersByStock(String symbol) {
        String sql = "WITH\n" +
                "    buy_trades AS (\n" +
                "        SELECT\n" +
                "            customer_id,\n" +
                "            SUM(quantity) AS total_buy_shares\n" +
                "        FROM\n" +
                "            trades\n" +
                "        WHERE\n" +
                "            symbol = '" + symbol + "'\n" +
                "            AND trade_type = 'buy'\n" +
                "        GROUP BY\n" +
                "            customer_id\n" +
                "    ),\n" +
                "    sell_trades AS (\n" +
                "        SELECT\n" +
                "            customer_id,\n" +
                "            SUM(quantity) AS total_sell_shares\n" +
                "        FROM\n" +
                "            trades\n" +
                "        WHERE\n" +
                "            symbol = '" + symbol + "'\n" +
                "            AND trade_type = 'sell'\n" +
                "        GROUP BY\n" +
                "            customer_id\n" +
                "    ),\n" +
                "    net_shares AS (\n" +
                "        SELECT\n" +
                "            buy_trades.customer_id,\n" +
                "            total_buy_shares - COALESCE(total_sell_shares, 0) AS net_shares\n" +
                "        FROM\n" +
                "            buy_trades\n" +
                "        LEFT JOIN\n" +
                "            sell_trades ON buy_trades.customer_id = sell_trades.customer_id\n" +
                "    )\n" +
                "SELECT\n" +
                "    customer_id\n" +
                "FROM\n" +
                "    net_shares\n" +
                "WHERE\n" +
                "    net_shares > 0;\n";
        return executeService(sql, "select");
    }

    /**
     * Method to get customers who are eligible for an option account
     * @param eligibility
     * @return
     */
    public ResultSet getCustomersEligibleForOptionAccounts(int eligibility) {
        // return customer id for a given customer
        // Customers who have more than 10K in realized profits are eligible for an option account
        String sql = "SELECT \n" +
                "    customer_id\n" +
                "FROM \n" +
                "    realized_profits\n" +
                "GROUP BY \n" +
                "    customer_id\n" +
                "HAVING \n" +
                "    SUM(profit) > " + eligibility + ";";
        return executeService(sql, "select");
    }


}
