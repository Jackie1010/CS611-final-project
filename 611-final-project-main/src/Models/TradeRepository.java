package Models;

import Database.Services.TradesService;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeRepository {
    private TradesService tradesService;


    /**
     * Constructor:
     */
    public TradeRepository(TradesService tradesService) {
        this.tradesService = tradesService;
    }


    /**
     * Method to make a trade
     * @param customerID
     * @param stockSymbol
     * @param shares
     * @param stockPrice
     * @param buy
     */
    public void makeTrade(int customerID, String stockSymbol, double shares, double stockPrice, String buy) {
        System.out.println("TradeRepository: Making trade");
        UserTradeStrategy strategy;
        if (buy.equals("buy")) {
            strategy = new BuyStrategy();
        } else {
            strategy = new SellStrategy();
        }
        strategy.trade(customerID, stockSymbol, shares, stockPrice, tradesService);
    }

    /**
     * Method to get the customer trades
     * @param customerID
     * @param customer
     * @return
     */
    public ArrayList<List<String>> getCustomerTrades(int customerID, Customer customer) {
        System.out.println("TradeRepository: Getting customer trades");
        ResultSet rs = tradesService.getCustomerTrades(customerID, customer);
        Object[][] final_data = null;
        ArrayList<List<String>> data = new ArrayList<>();
        try {
            while (rs.next()) {
                String symbol = rs.getString("symbol");
                String type = rs.getString("trade_type");
                String quantity = rs.getString("quantity");
                String price = rs.getString("price");
                String time = rs.getString("time");
                String total_trade_cost = rs.getString("total_trade_cost");
                List<String> row = new ArrayList<>();
                row.add(symbol);
                row.add(type);
                row.add(quantity);
                row.add(price);
                row.add(time);
                row.add(total_trade_cost);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println("TradeRepository: Error getting customer trades");
            return null;
        }
        System.out.println("TradeRepository: Returning customer trades");
        return data;
    }

    /**
     * Method to get the customer owned stock
     * @param customerId
     * @param customer
     * @return
     */
    public ArrayList<List<String>> getCustomerOwnedStock(int customerId, Customer customer) {
        System.out.println("TradeRepository: Getting customer owned stock");
        ResultSet rs = tradesService.getCustomerOwnedStock(customerId, customer);
        ArrayList<List<String>> data = new ArrayList<>();
        try {
            while (rs.next()) {
                String stockname = rs.getString("name");
                String id = rs.getString("stock_id");
                String symbol = rs.getString("symbol");
                String current_stock_price = rs.getString("current_stock_price");
                String total_cost = rs.getString("total_cost");
                String shares = rs.getString("shares");
                String tradeable = rs.getString("tradeable");
                List<String> row = new ArrayList<>();
                row.add(stockname);
                row.add(id);
                row.add(symbol);
                row.add(current_stock_price);
                row.add(total_cost);
                row.add(shares);
                row.add(tradeable);
                data.add(row);
            }
            System.out.println("TradeRepository: Got customer owned stock: " + data);
        } catch (SQLException e) {
            System.out.println("TradeRepository: Error getting customer owned stock");
            return null;
        }
        return data;

    }

    /**
     * Method to get the customer trading history
     * @param customerID
     * @return
     */
    public ArrayList<List<String>> getCustomerTradingHistory(int customerID) {
        System.out.println("TradeRepository: Getting customer trading history");
        ResultSet rs = tradesService.getCustomerTradingHistory(customerID);
        ArrayList<List<String>> data = new ArrayList<>();
        try {
            while (rs.next()){
                String time_stamp = rs.getString("Time_Stamp");
                String stock_name = rs.getString("Stock_Name");
                String stock_id = rs.getString("Stock_ID");
                String at_price = rs.getString("At_Price_Dollar");
                String shares = rs.getString("Shares");
                String trade_type = rs.getString("Trade_Type");
                String trade_cost_profit = rs.getString("Trade_Cost");
                List<String> row = new ArrayList<>();
                row.add(time_stamp);
                row.add(stock_name);
                row.add(stock_id);
                row.add(at_price);
                row.add(shares);
                row.add(trade_type);
                row.add(trade_cost_profit);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println("TradeRepository: Error getting customer trading history");
            return null;
        }
        System.out.println("TradeRepository: Returning customer trading history");
        return data;
    }

    /**
     * Gets the total amount invested by a customer in a stock
     * @param stockSymbol
     * @param customerID
     * @return
     */
    public double getInvestedByStock(String stockSymbol, int customerID) {
        ResultSet rs = tradesService.getInvestedByStock(stockSymbol, customerID);
        double totalInvested = 0;
        try {
            while (rs.next()) {
                totalInvested += rs.getDouble("amount_invested");
            }
        } catch (SQLException e) {
            System.out.println("TradeRepository: Error getting invested by stock");
            return 0;
        }
        return totalInvested;
    }

    /**
     * Gets all user trading history
     * @return
     */
    public ArrayList<List<String>> getAllUserTradingHistory() {
        ResultSet rs = tradesService.getAllCustomerTradingHistory();
        ArrayList<List<String>> data = new ArrayList<>();
        try {
            while (rs.next()){
                String time_stamp = rs.getString("time_stamp");
                String stock_name = rs.getString("stock_name");
                String stock_id = rs.getString("stock_id");
                String at_price = rs.getString("at_price");
                String shares = rs.getString("shares");
                String trade_type = rs.getString("trade_type");
                String trade_cost_profit = rs.getString("trade_cost");
                String customerName = rs.getString("customer_name");
                List<String> row = new ArrayList<>();
                row.add(time_stamp);
                row.add(stock_name);
                row.add(stock_id);
                row.add(at_price);
                row.add(shares);
                row.add(trade_type);
                row.add(trade_cost_profit);
                row.add(customerName);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println("TradeRepository: Error getting customer trading history");
            return null;
        }
        System.out.println("TradeRepository: Returning customer trading history");
        return data;
    }

}
