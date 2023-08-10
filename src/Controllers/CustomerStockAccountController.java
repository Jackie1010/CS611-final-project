package Controllers;

import Database.Services.CustomerStockMarketService;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;
import Views.Customer.CustomerStockAccountCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerStockAccountController extends CustomerController implements ActionListener, Searchable {

    private CustomerStockAccountCard customerStockAccountCard;
    private String[] columnNames;

    private Object[][] data;

    /**
     * Constructor:
     * @param stockMarketService
     * @param userRepository
     * @param stockRepository
     * @param tradeRepository
     */
    public CustomerStockAccountController(CustomerStockMarketService stockMarketService, UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository) {
        super(stockMarketService, userRepository, stockRepository, tradeRepository);
        columnNames = new String[]{"Stock Name", "Stock ID", "Symbol", "Current Price($)", "Cost($)",
                "Shares", "ToBuy", "ToSell", "Tradable"};

    }

    /**
     * Setter for the customerStockAccountCard
     */
    public void setCustomerStockAccountCard(CustomerStockAccountCard customerStockAccountCard) {
        this.customerStockAccountCard = customerStockAccountCard;
    }

    /**
     * Action Listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == customerStockAccountCard.getBTNSearch()) {
            System.out.println("CustomerStockAccountController::actionPerformed()::BTNSearch");
            // Get the stock name from the search field
            String search = customerStockAccountCard.getTXTSearchSymbol().getText();
            System.out.println("CustomerStockAccountController::actionPerformed()::BTNSearch::search = " + search);
            if (search.equals("")) {
                System.out.println("CustomerStockAccountController::actionPerformed()::BTNSearch::search is empty");
                // if the search field is empty, show all stocks
                setData();
                customerStockAccountCard.updateTable();
                return;
            }
            // if the symbol is found, only display that stock,
            // else have a pop-up saying that the stock was not found
            ArrayList<List<String>> stockData = tradeRepository.getCustomerOwnedStock(customerID, customer);
            int columnToSearch = 2;
            Object[][] return_data = search(search, stockData, columnToSearch);
            // print return_data
            if (return_data != null) {
                System.out.println("CustomerStockAccountController::actionPerformed()::BTNSearch::search found stock::return_data = " + Arrays.deepToString(return_data));
                Object[][] final_data = new Object[return_data.length][9];
                for (int i = 0; i < return_data.length; i++) {
                    final_data[i][0] = return_data[i][0];
                    final_data[i][1] = return_data[i][1];
                    final_data[i][2] = return_data[i][2];
                    final_data[i][3] = return_data[i][3];
                    final_data[i][4] = return_data[i][4];
                    final_data[i][5] = return_data[i][5];
                    final_data[i][6] = "";
                    final_data[i][7] = "";
                    if (return_data[i][6].equals("0")){
                        final_data[i][8] = "No";
                    } else {
                        final_data[i][8] = "Yes";
                    }
                }
                this.data = final_data;
                customerStockAccountCard.updateTable();
            } else {
                JOptionPane.showMessageDialog(null, "Stock not found");
            }
        }
    }

    /**
     * method to add a row to the data array
     * @param data
     * @param index
     * @param row
     */
    private void addDataRow(Object[][] data, int index, List<String> row) {
        data[index][0] = row.get(0);
        data[index][1] = row.get(1);
        data[index][2] = row.get(2);
        data[index][3] = row.get(3);
        data[index][4] = row.get(4);
        data[index][5] = row.get(5);
        data[index][6] = "";
        data[index][7] = "";
    }

    public String[] getStockAccountColumns() {
        return columnNames;
    }

    /**
     * method to get data from the TradeRepository and return all trades for a given customer
     */
    public void setData() {
        // get the data from the TradeRepository
        ArrayList<List<String>> stockData = tradeRepository.getCustomerOwnedStock(customerID, customer);
        // iterate through the stockData and store it in the data array
        Object[][] data = new Object[stockData.size()][9];
        for (int i = 0; i < stockData.size(); i++) {
            List<String> row = stockData.get(i);
            addDataRow(data, i, row);
            if (row.get(6).equals("1")) {
                System.out.println("CustomerStockAccountController::Checking for Tradeability");
                data[i][8] = "Yes";
            } else {
                data[i][8] = "No";
            }
        }
        this.data = data;
    }

    /**
     * method to get the data from the TradeRepository and return all trades for a given customer
     * @return
     */
    public Object[][] getStockAccountData() {
        // print data
        System.out.println("CustomerStockAccountController::getStockAccountData()");
        return data;
    }
}
