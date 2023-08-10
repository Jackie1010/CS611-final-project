package Database.Services;

import Models.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TradesService implements Service {

    private Connection conn;

    /*
     * Constructor:
     */
    public TradesService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Method to make a trade
     * @param customerID
     * @param stockSymbol
     * @param shares
     * @param stockPrice
     * @param trade_type
     */
    public void makeTrade(int customerID, String stockSymbol, double shares, double stockPrice, String trade_type) {
        float total_trade_cost = (float) (shares * stockPrice);
        String sql = "INSERT INTO trades (customer_id, symbol, quantity, price, trade_type, total_trade_cost) VALUES (" + customerID + ", '" + stockSymbol + "', " + shares + ", " + stockPrice + ", '" + trade_type + "', " + total_trade_cost + ");";

        executeService(sql, "insert");
    }

    /**
     * Method to get the list of trades for a customer
     * @param customerID
     * @param customer
     * @return
     */
    public ResultSet getCustomerTrades(int customerID, Customer customer) {
        String sql = "SELECT * FROM trades WHERE customer_id = " + customerID + ";";
        return executeService(sql, "select");
    }

    /**
     * Method to get the list of stocks that a customer owns
     * @param customerId
     * @param customer
     * @return
     */
    public ResultSet getCustomerOwnedStock(int customerId, Customer customer) {
        // Query to get the list of stocks where the customer owns positive number of shares
        // I need the following columns: name, id, symbol, current_stock_price, total_cost, shares, unrealized_profits, tradeable

        // I need a list of all the stocks that the customer owns, then from that list I need to get the current stock price
        // for each of those stocks.
        // Then I need to calculate the total_cost of owning the shares they currently own, can compute this by summing the
        // total_trade_cost column in the trades table for all trades where the customer_id = customerId and trade_type = "buy"
        // and subtracting the total_trade_cost column in the trades table for all trades where the customer_id = customerId and trade_type = "sell" for the same stock
        // then I need to calculate the unrealized_profits for each stock, which I can get from the unrealized_profits table where the customer_id = customerId and symbol = stockSymbol
        // then I can just get tradeable from the stocks table where the symbol = stockSymbol
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
                "        c.id = " + customerId + "\n" +
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
                "        c.id = " + customerId + "\n" +
                "    GROUP BY\n" +
                "        c.id, s.id\n" +
                ")\n" +
                "SELECT\n" +
                "    ns.customer_id,\n" +
                "    ns.stock_id,\n" +
                "    ns.symbol,\n" +
                "    ns.name,\n" +
                "    s.current_price AS current_stock_price,\n" +
                "    ns.shares,\n" +
                "    (wpd.weighted_purchase_cost / wpd.purchased_shares) * ns.shares AS total_cost,\n" +
                "    s.tradeable\n" +
                "FROM\n" +
                "    net_shares ns\n" +
                "JOIN\n" +
                "    weighted_purchase_data wpd ON ns.customer_id = wpd.customer_id AND ns.stock_id = wpd.stock_id\n" +
                "JOIN\n" +
                "    stocks s ON ns.symbol = s.symbol\n" +
                "ORDER BY\n" +
                "    ns.customer_id, ns.stock_id;\n";

        return executeService(sql, "select");
    }

    /**
     * This method will sell a stock
     * @param customerID
     * @param stockSymbol
     * @param shares
     * @param stockPrice
     * @param total_trade_cost
     * @return
     */
    public ResultSet sellStock(int customerID, String stockSymbol, double shares, double stockPrice, double total_trade_cost) {
        // Query can just be an insert with a negative number of shares and trade_type = "sell"
        String sql = "INSERT INTO trades (customer_id, symbol, quantity, price, trade_type, total_trade_cost) VALUES (" + customerID + ", '" + stockSymbol + "', " + shares + ", " + stockPrice + ", 'sell', " + total_trade_cost + ");";
        return executeService(sql, "insert");
    }

    /**
     * This method will update the unrealized_profits table for a given customer and stock
     * @param customerID
     * @param symbol
     * @param unrealized_profits
     * @return
     */
    public ResultSet updateUnRealizedProfit(int customerID, String symbol, double unrealized_profits) {
        // Query can just be an insert with a negative number of shares and trade_type = "sell"
        String sql = "UPDATE unrealized_profits SET unrealized_profits = " + unrealized_profits + " WHERE customer_id = " + customerID + " AND symbol = '" + symbol + "';";
        return executeService(sql, "update");
    }

    /**
     * This method will return the number of shares a customer owns for a given stock
     * @param customerID
     * @param symbol
     * @return
     */
    public ResultSet getNumberOfShares(int customerID, String symbol) {
        String sql = "SELECT \n" +
                "    c.id AS customer_id, \n" +
                "    s.id AS stock_id,\n" +
                "    s.symbol, \n" +
                "    s.name,\n" +
                "    SUM(CASE WHEN t.trade_type = 'buy' THEN t.quantity ELSE -t.quantity END) AS shares\n" +
                "FROM \n" +
                "    customers c\n" +
                "INNER JOIN \n" +
                "    trades t ON c.id = t.customer_id\n" +
                "INNER JOIN \n" +
                "    stocks s ON t.symbol = s.symbol\n" +
                "WHERE\n" +
                "    c.id = " + customerID + "\n" +
                "AND\n" +
                "    s.symbol = '" + symbol + "'\n" +
                "GROUP BY \n" +
                "    c.id, s.id\n" +
                "HAVING \n" +
                "    SUM(CASE WHEN t.trade_type = 'buy' THEN t.quantity ELSE -t.quantity END) > 0;\n";
        return executeService(sql, "select");
    }

    /**
     * Get the trading history for a customer
     * @param customerID
     * @return
     */
    public ResultSet getCustomerTradingHistory(int customerID) {
        String sql = "SELECT \n" +
                "    t.timestamp AS Time_Stamp,\n" +
                "    s.name AS Stock_Name,\n" +
                "    s.id AS Stock_ID,\n" +
                "    t.price AS At_Price_Dollar,\n" +
                "    t.quantity AS Shares,\n" +
                "    t.trade_type AS Trade_Type,\n" +
                "    t.total_trade_cost AS Trade_Cost\n" +
                "FROM \n" +
                "    trades t\n" +
                "INNER JOIN \n" +
                "    customers c ON t.customer_id = c.id\n" +
                "INNER JOIN \n" +
                "    stocks s ON t.symbol = s.symbol\n" +
                "WHERE\n" +
                "    c.id = " + customerID + "\n" +
                "ORDER BY \n" +
                "    t.timestamp DESC;\n";
        return executeService(sql, "select");
    }

    /**
     * Get total amount invested in a stock by a customer
     * @param stockSymbol
     * @param customerID
     * @return
     */
    public ResultSet getInvestedByStock(String stockSymbol, int customerID) {
        // get total amount invested in a stock by a customer
        String sql = "SELECT MAX(SUM(CASE WHEN trade_type = 'buy' THEN total_trade_cost ELSE -total_trade_cost END), 0) as amount_invested\n" +
                "FROM trades\n" +
                "WHERE customer_id = " + customerID + " AND symbol = '" + stockSymbol + "'\n";
        return executeService(sql, "select");
    }

    /**
     * Get all customer trading history
     * @return
     */
    public ResultSet getAllCustomerTradingHistory() {
        String sql = "SELECT\n" +
                "    t.timestamp AS time_stamp,\n" +
                "    s.name AS stock_name,\n" +
                "    s.id AS stock_id,\n" +
                "    t.price AS at_price,\n" +
                "    t.quantity AS shares,\n" +
                "    t.trade_type,\n" +
                "    t.total_trade_cost AS trade_cost,\n" +
                "    c.first_name || ' ' || c.last_name AS customer_name\n" +
                "FROM\n" +
                "    trades t\n" +
                "JOIN\n" +
                "    customers c ON t.customer_id = c.id\n" +
                "JOIN\n" +
                "    stocks s ON t.symbol = s.symbol\n" +
                "ORDER BY\n" +
                "    t.timestamp;";
        return executeService(sql, "select");
    }
}
