package Database.Services;

import java.sql.Connection;
import java.sql.ResultSet;

public class AdminStockMarketService extends StockMarketService{

    /**
     * Constructor:
     */
    public AdminStockMarketService(Connection conn) {
        super(conn);
    }


    /**
     * Method to update the price of a stock
     * @param newPrice
     * @param symbol
     */
    public void updatePrice(double newPrice, String symbol) {
        String sql = "UPDATE stocks SET current_price = " + newPrice + " WHERE symbol = '" + symbol + "';";
        executeService(sql, "update");
    }


    /**
     * Method to update the tradeability of a stock
     * @param tradeability
     * @param symbol
     */
    public void updateTradeability(int tradeability, String symbol) {
        String sql = "UPDATE stocks SET tradeable = " + tradeability + " WHERE symbol = '" + symbol + "';";
        executeService(sql, "update");
    }


}
