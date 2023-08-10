package Database.Services;

import Database.Connect;

import java.sql.Connection;
import java.util.regex.Pattern;

public class CustomerStockMarketService extends StockMarketService{


    /**
     * Constructor:
     * @param conn
     */
    public CustomerStockMarketService(Connection conn) {
        super(conn);
    }
}
