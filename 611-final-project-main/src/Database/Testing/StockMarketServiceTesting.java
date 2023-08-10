package Database.Testing;

import Database.Connect;
import Database.Services.CustomerStockMarketService;
import Database.Services.StockMarketService;

import java.sql.Connection;

public class StockMarketServiceTesting {

    private Connection conn;

    /*
     * Constructor:
     */
    public StockMarketServiceTesting(Connection conn) {
        this.conn = conn;
    }

    public void testStockMarketService(String[] args) {
        StockMarketService stockMarketService = new CustomerStockMarketService(conn);
        stockMarketService.readAndStoreStockMarketData();
    }


    public static void main(String[] args) {
        Connection conn = Connect.getConnection();
        StockMarketService stockMarketService = new CustomerStockMarketService(conn);
        stockMarketService.readAndStoreStockMarketData();
        stockMarketService.getAllStockData();
    }
}
