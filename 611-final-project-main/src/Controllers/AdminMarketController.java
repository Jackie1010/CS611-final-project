package Controllers;

import Models.*;
import Views.Admin.AdminStockMarketCard;
import Views.Admin.AdminStockMarketMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AdminMarketController extends AdminController implements ActionListener, Searchable {

    private String[] columnNames;
    private Object[][] data;

    private AdminStockMarketCard adminStockMarketCard;
    private AdminStockMarketMenu adminStockMarketMenu;
    private AdminStockRepository adminStockRepository;

    /**
     * Constructor:
     *
     * @param adminRepository
     * @param stockRepository
     * @param tradeRepository
     * @param userRepository
     */
    public AdminMarketController(AdminRepository adminRepository, StockRepository stockRepository, TradeRepository tradeRepository, UserRepository userRepository, AdminStockRepository adminStockRepository) {
        super(adminRepository, stockRepository, tradeRepository, userRepository);
        this.adminStockRepository = adminStockRepository;
        columnNames = new String[]{"Stock Name", "Stock ID", "Symbol", "Current Price", "Tradable"};
    }

    /**
     * Setter for AdminStockMarketCard
     */
    public void setAdminStockMarketCard(AdminStockMarketCard adminStockMarketCard) {
        this.adminStockMarketCard = adminStockMarketCard;
    }

    /**
     * Setter for AdminStockMarketMenu
     */
    public void setAdminStockMarketMenu(AdminStockMarketMenu adminStockMarketMenu) {
        this.adminStockMarketMenu = adminStockMarketMenu;
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
        return data;
    }

    /**
     * Private Setter method for data
     */
    public void setData() {
        ArrayList<List<String>> stockData = stockRepository.getAllStocks();
        // iterate through the stockData and store it in the data array
        Object[][] data = new Object[stockData.size()][5];
        for (int i = 0; i < stockData.size(); i++) {
            List<String> row = stockData.get(i);
            addDataRow(data, i, row);
        }
        this.data = data;
    }

    /**
     * Action Listener:
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (adminStockMarketCard != null) {
            if (e.getSource() == adminStockMarketCard.getBTNSearch()) {
                String search = adminStockMarketCard.getTXTSearchSymbol().getText();
                System.out.println("AdminMarketController::Search for Stock: " + search);
                if (search.equals("")) {
                    System.out.println("AdminMarketController::Search is empty");
                    setData();
                    adminStockMarketCard.updateTable();
                    return;
                }
                ArrayList<List<String>> stockData = stockRepository.getAllStocks();
                int columnToSearch = 1;
                Object[][] return_data = search(search, stockData, columnToSearch);
                if (return_data == null) {
                    JOptionPane.showMessageDialog(null, "Stock not found");
                    return;
                }
                // print return data
                System.out.println("AdminMarketController::Search::Return Data: " + Arrays.deepToString(return_data));
                Object[][] final_data = new Object[return_data.length][5];

                // check if the last element is 0, if it is then replace it with "F" else "T"
                if (return_data != null) {
                    for (int i = 0; i < return_data.length; i++) {
                        if (return_data[i][4].equals("0")) {
                            return_data[i][4] = "F";
                        } else {
                            return_data[i][4] = "T";
                        }
                    }
                }
                // final_data should be return_data but with the indices re-arranged like this:
                for (int i = 0; i < return_data.length; i++) {
                    final_data[i][0] = return_data[i][2];
                    final_data[i][1] = return_data[i][0];
                    final_data[i][2] = return_data[i][1];
                    final_data[i][3] = return_data[i][3];
                    final_data[i][4] = return_data[i][4];
                }
                this.data = final_data;
                adminStockMarketCard.updateTable();
            }
        }
        if (adminStockMarketMenu != null) {
            if (e.getSource() == adminStockMarketMenu.getCBBTradable()) {
                System.out.println("AdminMarketController::CBBTradable");
            }
            if (e.getSource() == adminStockMarketMenu.getBTNSave()) {
                // check if TXTCurrentPrice is empty
                String currentPrice = adminStockMarketMenu.getTXTCurrentPrice().getText();
                String selectedItem = (String) adminStockMarketMenu.getCBBTradable().getSelectedItem();
                // Tradeable must be either Yes (T) or No (F) but cannot be Select
                assert selectedItem != null;
                if (selectedItem.equals("Select")) {
                    JOptionPane.showMessageDialog(null, "Please select a value for Tradeable");
                    return;
                }
                if (currentPrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "Current Price cannot be empty");
                    return;
                }
                updatePrice(Double.parseDouble(currentPrice), adminStockMarketMenu.getSymbol());
                System.out.println("AdminMarketController::BTNSave::Tradeable: " + selectedItem);
                if (selectedItem.equals("No (F)")) {
                    updateTradeability("F", adminStockMarketMenu.getSymbol());
                } else {
                    updateTradeability("T", adminStockMarketMenu.getSymbol());
                }
                adminStockMarketMenu.setVisible(false);
                adminStockMarketMenu.dispose();
            }
        }
    }

    /**
     * Private method to add a row to the data array
     * @param data
     * @param index
     * @param row
     */
    private void addDataRow(Object[][] data, int index, List<String> row) {
        data[index][0] = row.get(2);
        data[index][1] = row.get(0);
        data[index][2] = row.get(1);
        data[index][3] = row.get(3);
        if (row.get(4).equals("1")) {
            data[index][4] = "T";
        } else {
            data[index][4] = "F";
        }
    }

    /**
     * Private method to update price for a stock
     * @param newPrice
     * @param symbol
     */
    public void updatePrice(double newPrice, String symbol) {
        adminStockRepository.updatePrice(newPrice, symbol);


        // Get all the customers who have this stock,
        // iterate through them and update their unrealized profit
        ArrayList<List<Integer>> customers = adminRepository.getCustomersByStock(symbol);
        for (List<Integer> customer : customers) {
            int customerID = customer.get(0);
            double newUnrealizedProfit = (newPrice - userRepository.getAveragePriceByStock(symbol, customerID)) * userRepository.getNumberOfShares(customerID, symbol);
            System.out.println("AdminMarketController::AveragePrice for customer " + customerID + " is " + userRepository.getAveragePriceByStock(symbol, customerID) + " for stock " + symbol + "");
            System.out.println("AdminMarketController::Number of shares for customer " + customerID + " is " + userRepository.getNumberOfShares(customerID, symbol) + " for stock " + symbol + "");
            System.out.println("AdminMarketController::Unrealized Profit for customer " + customerID + " is " + newUnrealizedProfit + " for stock " + symbol + "");
            userRepository.updateUnrealizedProfitByStock(customerID, newUnrealizedProfit, null, symbol);
        }
    }

    /**
     * Private method to update tradeability for a stock
     * @param tradeability
     * @param symbol
     */
    public void updateTradeability(String tradeability, String symbol) {
        adminStockRepository.updateTradeability(tradeability, symbol);
    }

    /**
     * Private method to keep track of stock price history
     * @param symbol
     * @return
     */
    public List<List<String>> getStockUpdateData(String symbol) {
        return stockRepository.getStockUpdateData(symbol);
    }
}
