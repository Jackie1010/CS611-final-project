package Models;

import Database.Services.StockMarketService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockRepository {

    private StockMarketService stockMarketService;

    /*
     * Constructor:
     */
    public StockRepository(StockMarketService stockMarketService) {
        this.stockMarketService = stockMarketService;
    }

    /*
     * Return the stock object if the stock exists in the database, queried by Stock Symbol.
     */
    // FIXME: Convert the return type to stock object
    public HashMap<String, String> findStockBySymbol(String symbol) {
        System.out.println("StockRepository: Finding stock by symbol");
        try {
            HashMap<String, String> stockMap = stockMarketService.getStockData(symbol);
            if (stockMap == null) {
                System.out.println("StockRepository: Stock is null");
                return null;
            }
            System.out.println("StockRepository: Found Stock");
            return stockMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Return stock price for a given stock symbol
     */
    public double getStockPrice(String symbol) {
        HashMap<String, String> stockMap = findStockBySymbol(symbol);
        if (stockMap == null) {
            System.out.println("StockRepository: Stock Price is null");
            return -1;
        }
        System.out.println("StockRepository: Stock Price: " + stockMap.get("current_price"));
        return Double.parseDouble(stockMap.get("current_price"));
    }

    /**
     * Method to get the stock tradeability
     * @param stockSymbol
     * @return
     */
    public boolean getStockTradeability(String stockSymbol) {
        HashMap<String, String> stockMap = findStockBySymbol(stockSymbol);
        if (stockMap == null) {
            System.out.println("StockRepository: Stock Tradeability is null");
            return false;
        }
        System.out.println("StockRepository: Stock Tradeability: " + stockMap.get("tradeable"));
        return stockMap.get("tradeable").equals("1");
    }

    /**
     * Method to get all the stocks
     * @return
     */
    public ArrayList<List<String>> getAllStocks() {
        ResultSet rs = stockMarketService.getAllStocks();
        if (rs == null) {
            System.out.println("StockRepository: ResultSet is null");
            return null;
        }
        ArrayList<List<String>> stocks = new ArrayList<>();
        try {
            while (rs.next()){
                String time_stamp = rs.getString("id");
                String stock_name = rs.getString("symbol");
                String stock_id = rs.getString("name");
                String at_price = rs.getString("current_price");
                String shares = rs.getString("tradeable");
                List<String> row = new ArrayList<>();
                row.add(time_stamp);
                row.add(stock_name);
                row.add(stock_id);
                row.add(at_price);
                row.add(shares);
                stocks.add(row);
            }
        } catch (SQLException e) {
            System.out.println("TradeRepository: Error getting customer trading history");
            return null;
        }
        System.out.println("TradeRepository: Returning customer trading history");
        return stocks;
    }



//    public void updateTradeability(String tradeability, String symbol) {
//        stockMarketService.updateTradeability(tradeability, symbol);
//    }

    /**
     * Method to get the stock update data
     * @param symbol
     * @return
     */
    public List<List<String>> getStockUpdateData(String symbol)  {
        return stockMarketService.getStockUpdateData(symbol);
    }
}
