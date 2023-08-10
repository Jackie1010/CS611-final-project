package Controllers;

import Database.Services.CustomerStockMarketService;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerTradingHistoryController extends CustomerController{

    private String[] columnNames;
    private Object[][] data;


    /**
     * Constructor:
     * @param stockMarketService
     * @param userRepository
     * @param stockRepository
     * @param tradeRepository
     */
    public CustomerTradingHistoryController(CustomerStockMarketService stockMarketService, UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository) {
        super(stockMarketService, userRepository, stockRepository, tradeRepository);
        this.columnNames = new String[]{
                "Time Stamp", "Stock Name", "Stock ID", "At Price ($)", "Shares", "Trade Type", "Trade Cost/Profit ($)"
        };
    }

    /**
     * Setter for columnNames
     * @return
     */
    public String[] getTradingHistoryColumns() {
        return columnNames;
    }

    /**
     * Private Setter method for data
     */
    private void setData() {
        // get the data from the TradeRepository
        ArrayList<List<String>> rs = tradeRepository.getCustomerTradingHistory(customerID);
        // iterate through the stockData and store it in the data array
        Object[][] data = new Object[rs.size()][7];
        for (int i = 0; i < rs.size(); i++) {
            List<String> row = rs.get(i);
            data[i][0] = row.get(0);
            data[i][1] = row.get(1);
            data[i][2] = row.get(2);
            data[i][3] = row.get(3);
            data[i][4] = row.get(4);
            data[i][5] = row.get(5);
            data[i][6] = row.get(6);
        }
        this.data = data;
    }

    /**
     * Getter for data
     * @return
     */
    public Object[][] getTradingHistoryData() {
        setData();
        return data;
    }
}
