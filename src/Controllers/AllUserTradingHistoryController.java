package Controllers;

import Models.AdminRepository;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class AllUserTradingHistoryController extends AdminController{

    private String[] columnNames;
    private Object[][] data;


    /**
     * Constructor:
     *
     * @param adminRepository
     * @param stockRepository
     * @param tradeRepository
     * @param userRepository
     */
    public AllUserTradingHistoryController(AdminRepository adminRepository, StockRepository stockRepository, TradeRepository tradeRepository, UserRepository userRepository) {
        super(adminRepository, stockRepository, tradeRepository, userRepository);
        columnNames = new String [] {
                "Time Stamp", "Stock Name", "Stock ID", "At Price ($)", "Shares", "Trade Type", "Trade Cost/Profit ($)", "Customer Name"
        };
    }

    /**
     * Getter for columnNames
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * Getter for data
     */
    public Object[][] getData() {
        setData();
        return data;
    }

    /**
     * Private Setter method for data
     */
    private void setData(){
        tradeRepository.getAllUserTradingHistory();
        ArrayList<List<String>> rs = tradeRepository.getAllUserTradingHistory();
        // after every customer, add an empty row
        Object[][] data = new Object[rs.size()][8];
        for (int i = 0; i < rs.size(); i++) {
            List<String> row = rs.get(i);
            data[i][0] = row.get(0);
            data[i][1] = row.get(1);
            data[i][2] = row.get(2);
            data[i][3] = row.get(3);
            data[i][4] = row.get(4);
            data[i][5] = row.get(5);
            data[i][6] = row.get(6);
            data[i][7] = row.get(7);
        }
        this.data = data;
    }


}
