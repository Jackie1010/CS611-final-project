package Controllers;

import Database.Services.CustomerStockMarketService;
import Models.Customer;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;
import Views.Customer.CustomerHomeCard;
import Views.Customer.CustomerTradingAccountReport;
import Views.Customer.HomeAccountMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerHomeController extends CustomerController implements ActionListener {


    private CustomerHomeCard customerHomeCard;
    private HomeAccountMenu homeAccountMenu;

    private CustomerTradingHistoryController customerTradingHistoryController;

    private String[] columnNames;
    private Object[][] data;

    public CustomerHomeController(CustomerStockMarketService stockMarketService, UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository, CustomerTradingHistoryController customerTradingHistoryController) {
        super(stockMarketService, userRepository, stockRepository, tradeRepository);
        this.customerTradingHistoryController = customerTradingHistoryController;
        columnNames = new String[]{"Account ID", "Trading History"};
    }

    /**
     * Setter for the customerHomeCard
     */
    public void setCustomerHomeCard(CustomerHomeCard customerHomeCard) {
        this.customerHomeCard = customerHomeCard;
    }

    /**
     * Setter for the homeAccountMenu
     */
    public void setHomeAccountMenu(HomeAccountMenu homeAccountMenu) {
        this.homeAccountMenu = homeAccountMenu;
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
     * Setter for data
     */
    public void setData() {
        ArrayList<List<String>> return_data =  userRepository.getInfo(customerID);
        Object[][] final_data = new Object[return_data.size()][2];
        for (int i = 0; i < return_data.size(); i++) {
            List<String> row = return_data.get(i);
            final_data[i][0] = row.get(0);
            final_data[i][1] = "";
        }
        this.data = final_data;
    }


    /**
     * Action Listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (customerHomeCard != null)
        {
            if (e.getSource() == customerHomeCard.getBTNAddNewTradingAccount()) {
                addNewTradingAccount(customerID);
            }
            if (e.getSource() == customerHomeCard.getBTNAddCash()) {
                addCash(customerID);
            }
            if (e.getSource() == customerHomeCard.getBTNDeductCash()) {
                withdrawCash(customerID);
            }
        }
        if (homeAccountMenu != null)
        {
            if (e.getSource() == homeAccountMenu.getBTNGetReport()) {
                CustomerTradingAccountReport report = new CustomerTradingAccountReport(customerID, customerTradingHistoryController);
                report.setVisible(true);
                homeAccountMenu.setVisible(false);
                homeAccountMenu.dispose();
            }
        }

    }

    /**
     * Withdraw cash from the customer's account
     * @param customerID
     */
    private void withdrawCash(int customerID) {
        String input = JOptionPane.showInputDialog(customerHomeCard, "Enter the amount to deduct (max 2 decimal places):");
        float currentCash = userRepository.getCashAvailable(customerID, customer);
        if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
            float cashToDeduct = Float.parseFloat(input);
            if (cashToDeduct > currentCash) {
                JOptionPane.showMessageDialog(customerHomeCard, "You do not have enough cash to deduct.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            float newCashAvailable = currentCash - cashToDeduct;
            userRepository.updateCashAvailable(customerID, newCashAvailable, customer);
            JLabel cashAvail = customerHomeCard.getLBLCashAvailable();
            cashAvail.setText(String.format("%.2f", newCashAvailable));
        } else if (input != null) {
            JOptionPane.showMessageDialog(customerHomeCard, "Invalid input. Please enter a positive number with max 2 decimals.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    /**
     * Add cash to the customer's account
     * @param customerid
     */
    private void addCash(int customerid) {
        String input = JOptionPane.showInputDialog(customerHomeCard, "Enter the amount of money to add (max 2 decimals):");

        if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
            float cashAvailable = userRepository.getCashAvailable(customerid, customer);
            float cashToAdd = Float.parseFloat(input);
            float newCashAvailable = cashAvailable + cashToAdd;
            userRepository.updateCashAvailable(customerid, newCashAvailable, customer);
            userRepository.updateDeposit(customerid, cashToAdd, customer);
            JLabel cashAvail = customerHomeCard.getLBLCashAvailable();
            cashAvail.setText(String.format("%.2f", newCashAvailable));
        } else if (input != null) {
            JOptionPane.showMessageDialog(customerHomeCard, "Invalid input. Please enter a positive number with max 2 decimals.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    /**
     * Add new trading account for the customer
     * @param customerid
     */
    private void addNewTradingAccount(int customerid) {
        if (!super.userRepository.doesTradingAccountExist(customerid)) {
            userRepository.createNewTradingAccount(customerid);
            JOptionPane.showMessageDialog(customerHomeCard, "Trading Account created succesfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(customerHomeCard, "You are not eligible to apply new trading account.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Show notifications for the customer
     * @param customerID
     */
    public void showNotifications(int customerID) {
        System.out.println("CustomerHomeController::showNotifications");
        ArrayList<String> notifications = userRepository.getNotifications(customerID);
        System.out.println("CustomerHomeController::showNotifications::notifications returned: ");
        if (notifications.size() == 0) {
            return;
        } else {
            for (String notification : notifications) {
                System.out.println("Showing Notification");
                JOptionPane.showMessageDialog(customerHomeCard, notification, "Information", JOptionPane.INFORMATION_MESSAGE);
                userRepository.markNotificationAsRead(customerID, notification);
            }
        }
    }
}
