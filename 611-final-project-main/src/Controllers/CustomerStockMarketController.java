package Controllers;

import Database.Services.CustomerStockMarketService;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;
import Views.Customer.CustomerHomeCard;
import Views.Customer.CustomerStockMarketCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerStockMarketController extends CustomerController implements ActionListener, Searchable {
    private CustomerStockMarketService stockMarketService;
    private CustomerStockMarketCard customerStockMarketCard;

    private String[] columnNames;

    private Object[][] data;

    /**
     * COnstructor:
     * @param stockMarketService
     * @param userRepository
     * @param stockRepository
     * @param tradeRepository
     */
    public CustomerStockMarketController(CustomerStockMarketService stockMarketService, UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository) {
        super(stockMarketService, userRepository, stockRepository, tradeRepository);
        // TODO:: get rid of this, replace with StockRepository
        this.stockMarketService = stockMarketService;
        this.columnNames = new String[]{"Stock Name", "Symbol", "Current Price", "ToBuy", "Tradable"};

    }

    /**
     * Set Data for Customer Market Data Table
     */
    public void setData() {
        ArrayList<List<String>> stockData = readData();
        // iterate through the stockData and store it in the data array
        Object[][] data = new Object[stockData.size()][5];
        for (int i = 0; i < stockData.size(); i++) {
            List<String> row = stockData.get(i);
            addDataRow(data, i, row);
        }
        this.data = data;
    }

    /**
     * Getter Customer Market Data
     * @return
     */
    public Object[][] getCustomerMarketData() {
        // print the data array
        return data;
    }

    /**
     * Setter for CustomerStockMarketCard
     */
    public void setCustomerStockMarketCard(CustomerStockMarketCard customerStockMarketCard) {
        this.customerStockMarketCard = customerStockMarketCard;
    }


    /*
     * Read data from database and format it into the Object array
     */
    public ArrayList<List<String>> readData() {
        return stockMarketService.getAllStockData();
    }

    /*
     * Getter methods:
     */
    public String[] getStockMarketColumnNames() {
        return columnNames;
    }


    /**
     * Action Listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == customerStockMarketCard.getBTNSearch()) {
            // get text from search bar
            String search = customerStockMarketCard.getTXTSearchSymbol().getText();
            System.out.println("StockMarketCard: Search for Stock: " + search);
            if (search.equals("")) {
                System.out.println("StockMarketCard: Search is empty, so returning all stocks");
                setData();
                customerStockMarketCard.updateTable();
                return;
            }
            // if the symbol is found, only display that stock,
            // else have a pop up saying that the stock was not found
            ArrayList<List<String>> stockData = readData();
            int columnToSearch = 0;
            Object[][] return_data = search(search, stockData, columnToSearch);
            if (return_data == null) {
                JOptionPane.showMessageDialog(null, "Stock not found");
            } else {
                // print return_data
                System.out.println("StockMarketCard: Search found stock:: " + Arrays.deepToString(return_data));
                Object[][] final_data = new Object[return_data.length][5];
                for (int i = 0; i < return_data.length; i++) {
                    final_data[i][0] = return_data[i][1];
                    final_data[i][1] = return_data[i][0];
                    final_data[i][2] = return_data[i][2];
                    final_data[i][3] = "";
                    if (return_data[i][3].equals("0")) {
                        final_data[i][4] = "No";
                    } else {
                        final_data[i][4] = "Yes";
                    }
                }
                this.data = final_data;
                customerStockMarketCard.updateTable();
            }
        }
    }

    /**
     * private method that adds a row to a data array
     * @param data
     * @param index
     * @param row
     */
    private void addDataRow(Object[][] data, int index, List<String> row) {
        data[index][0] = row.get(1);
        data[index][1] = row.get(0);
        data[index][2] = row.get(2);
        data[index][3] = "";
        if (row.get(3).equals("1")) {
            data[index][4] = "Yes";
        } else {
            data[index][4] = "No";
        }
    }
}
