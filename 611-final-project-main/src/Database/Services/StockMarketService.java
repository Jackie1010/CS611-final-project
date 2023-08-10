package Database.Services;

import Parsers.CSVParser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class StockMarketService implements Service {

    private Connection conn;

    /*
     * Constructor:
     */
    public StockMarketService(Connection conn) {
        this.conn = conn;
    }


    /*
     * Read the stock market data from CSV Parser and store it in the database
     * @return: true if the data was read and stored successfully, false otherwise
     */
    public boolean readAndStoreStockMarketData() {
        CSVParser csvParser = new CSVParser("src/Data/snp_constituents.csv");
        csvParser.parse();
        ArrayList<List<String>> data = csvParser.getData();
        if (data == null) {
            System.out.println("Error reading stock market data");
            return false;
        }
        System.out.println("Stock market data read successfully");
//        System.out.println(data);

        // iterate through the data and store it in the database
        for (List<String> row : data) {
            String symbol = row.get(0);
            String name = row.get(1);
            float default_current_price = 100.00f;
            // Construct the sql statement to insert the row into the stock_market table
            // Symbol and Name are from the CSV file, current_price default is 100.00
            String sql = "INSERT INTO stocks (symbol, name, current_price) VALUES ('" + symbol + "', '" + name + "', " + default_current_price + ");";
            // try to execute the sql statement
            try {
//                System.out.println("Inserting row into stock_market table");
                System.out.println(sql);
                conn.createStatement().executeUpdate(sql);
//                System.out.println("Row inserted successfully");
            } catch (Exception e) {
                System.out.println("Error inserting row into stock_market table");
                System.out.println(e.getMessage());
                return false;
            }
        }
        System.out.println("All rows inserted successfully");
        return true;
    }

    /*
     * Read stock data from the DB and store it in a list
     * @return: list of stock data
     */
    public ArrayList<List<String>> getAllStockData() {
        // Construct the sql statement to read all rows from the stock_market table
        String sql = "SELECT * FROM stocks;";
        // try to execute the sql statement
        System.out.println("Reading stock_market table");
        System.out.println(sql);
        ArrayList<List<String>> data = new ArrayList<List<String>>();
        // execute the sql statement
        try {
            ResultSet result = conn.createStatement().executeQuery(sql);
            while (result.next()) {
                String symbol = result.getString("symbol");
                String name = result.getString("name");
                String current_price = result.getString("current_price");
                String tradeable = result.getString("tradeable");
                List<String> row = new ArrayList<String>();
                row.add(symbol);
                row.add(name);
                row.add(current_price);
                row.add(tradeable);
                data.add(row);
            }
            System.out.println("Stock market data read successfully");
            System.out.println(data);
            return data;
        } catch (Exception e) {
            System.out.println("Error reading stock_market table");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * Method to return data for one stock
     */
    public HashMap<String, String> getStockData(String symbol) {
        // Construct the sql statement to read all rows from the stock_market table
        String sql = "SELECT * FROM stocks WHERE symbol = '" + symbol + "';";
        // try to execute the sql statement
        System.out.println("Reading stock_market table");
        System.out.println(sql);
        HashMap<String, String> data = new HashMap<String, String>();
        // execute the sql statement
        try {
            ResultSet result = conn.createStatement().executeQuery(sql);
            while (result.next()) {
                String name = result.getString("name");
                String current_price = result.getString("current_price");
                String tradeable = result.getString("tradeable");
                data.put("symbol", symbol);
                data.put("name", name);
                data.put("current_price", current_price);
                data.put("tradeable", tradeable);
            }
            System.out.println("Stock market data read successfully");
            System.out.println(data);
            return data;
        } catch (Exception e) {
            System.out.println("Error reading stock_market table");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Method to get all the stocks
     * @return
     */
    public ResultSet getAllStocks() {
        String sql = "SELECT * FROM stocks;";
        return executeService(sql, "select");
    }


//    public void updateTradeability(String tradeability, String symbol) {
//        String sql = "UPDATE stocks SET tradeable = '" + tradeability + "' WHERE symbol = '" + symbol + "';";
//        executeService(sql, "update");
//    }

    /**
     * Method to update the current price of a stock
     * @param newPrice
     * @param symbol
     */
    public void addStockUpdate(double newPrice, String symbol) {
        String sql = "Insert into stock_updates (update_price,symbol) values (" + newPrice + ",'" + symbol + "');";
        executeService(sql, "insert");
    }

    /**
     * Method to get the stock updates for a given stock
     * @param symbol
     * @return
     */
    public List<List<String>> getStockUpdateData(String symbol) {
        String stockUpdateSQL = "SELECT * FROM stock_updates where symbol = '" + symbol + "';";
        List<List<String>> res = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = conn.createStatement().executeQuery(stockUpdateSQL);
            while(resultSet.next()){
                Double price = resultSet.getDouble("update_price");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                res.add(Arrays.asList(price.toString(),timestamp.toString()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

}
