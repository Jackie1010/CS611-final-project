package Controllers;

import Models.*;
import Views.Admin.ALLCustomerReport;
import Views.Admin.AdminHomeCard;
import Views.Admin.CurrentCustomerMenu;
import Views.Customer.CustomerTradingAccountReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminHomeController extends AdminController implements ActionListener, Subject {

    private Map<Observer, List<String>> observerMessageMap;

    private String[] columnNamesTableNewCustomers;
    private Object[][] dataTableNewCustomers;

    private String[] columnNamesTableCurrentCustomers;
    private Object[][] dataTableCurrentCustomers;

    private CurrentCustomerMenu currentCustomerMenu;
    private AdminHomeCard adminHomeCard;
    private AllUserTradingHistoryController allUserTradingHistoryController;

    /**
     * Constructor:
     *
     * @param adminRepository
     * @param stockRepository
     * @param tradeRepository
     * @param userRepository
     */
    public AdminHomeController(AdminRepository adminRepository, StockRepository stockRepository, TradeRepository tradeRepository, UserRepository userRepository, AllUserTradingHistoryController allUserTradingHistoryController) {
        super(adminRepository, stockRepository, tradeRepository, userRepository);
        this.allUserTradingHistoryController = allUserTradingHistoryController;
        columnNamesTableNewCustomers = new String[]{"Customer ID", "Customer Name", "To Approve", "To Deny"};

        columnNamesTableCurrentCustomers = new String[]{
                "Customer ID", "Customer Name", "Trading Account ID", "Realized Profit", "Unrealized Profit", "Account Report", "Send Notification"
        };
        observerMessageMap = new HashMap<>();
    }

    /**
     * Setter for CurrentCustomerMenu
     */
    public void setCurrentCustomerMenu(CurrentCustomerMenu currentCustomerMenu) {
        this.currentCustomerMenu = currentCustomerMenu;
    }

    /**
     * Setter for AdminHomeCard
     */
    public void setAdminHomeCard(AdminHomeCard adminHomeCard) {
        this.adminHomeCard = adminHomeCard;
    }

    /**
     * Method to setData for NewCustomer table in AdminHomeCard
     */
    private void setDataTableNewCustomers() {
        ArrayList<List<String>> unApprovedCustomers = adminRepository.getUnApprovedCustomers();
        dataTableNewCustomers = new Object[unApprovedCustomers.size()][4];
        for (int i = 0; i < unApprovedCustomers.size(); i++) {
            dataTableNewCustomers[i][0] = unApprovedCustomers.get(i).get(0);
            dataTableNewCustomers[i][1] = unApprovedCustomers.get(i).get(1) + " " + unApprovedCustomers.get(i).get(2);
            dataTableNewCustomers[i][2] = "Approve";
            dataTableNewCustomers[i][3] = "Deny";
        }
    }

    /**
     * Method to setData for CurrentCustomer table in AdminHomeCard
     */
    private void setDataTableCurrentCustomers() {
        ArrayList<List<String>> approvedCustomers = adminRepository.getApprovedCustomers();
        dataTableCurrentCustomers = new Object[approvedCustomers.size()][7];
        for (int i = 0; i < approvedCustomers.size(); i++) {
            dataTableCurrentCustomers[i][0] = approvedCustomers.get(i).get(0);
            dataTableCurrentCustomers[i][1] = approvedCustomers.get(i).get(1);
            dataTableCurrentCustomers[i][2] = approvedCustomers.get(i).get(2);
            dataTableCurrentCustomers[i][3] = approvedCustomers.get(i).get(3);
            dataTableCurrentCustomers[i][4] = approvedCustomers.get(i).get(4);
            dataTableCurrentCustomers[i][5] = "";
            dataTableCurrentCustomers[i][6] = "";
        }
    }


    /**
     * Getters for Column Names
     */
    public String[] getColumnNamesTableNewCustomers() {
        return columnNamesTableNewCustomers;
    }

    /*
    ** Getters for Column Names
     */
    public String[] getColumnNamesTableCurrentCustomers() {
        return columnNamesTableCurrentCustomers;
    }

    /**
     * Getters for Data Tables
     */
    public Object[][] getDataTableNewCustomers() {
        setDataTableNewCustomers();
        return dataTableNewCustomers;
    }

    /**
     * Getters for Data Tables
     */
    public Object[][] getDataTableCurrentCustomers() {
        setDataTableCurrentCustomers();
        return dataTableCurrentCustomers;
    }


    /**
     * handles approve action by admin
     */
    public void toApprove(int row, JTable table) {
        String customerId = (String) table.getValueAt(row, 0);
        adminRepository.approveCustomer(customerId);

        // remove from the table
        setDataTableNewCustomers();
        // Remove the selected row from the table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.removeRow(row);
    }

    /**
     * handles deny action by admin
     * @param row
     * @param tblNewCustomers
     */
    public void toDeny(int row, JTable tblNewCustomers) {
        String customerId = (String) tblNewCustomers.getValueAt(row, 0);
        adminRepository.denyCustomer(customerId);
        // remove from the table
        setDataTableNewCustomers();
        // Remove the selected row from the table
        DefaultTableModel model = (DefaultTableModel) tblNewCustomers.getModel();
        model.removeRow(row);
    }

    /**
     * handles account report action by admin
     * @param customerId
     * @param customerTradingHistoryController
     */
    public void getReport(int customerId, CustomerTradingHistoryController customerTradingHistoryController) {
        System.out.println("Getting report for trading ID: " + customerId);
        customerTradingHistoryController.setCustomerId(customerId);
        CustomerTradingAccountReport report = new CustomerTradingAccountReport(customerId, customerTradingHistoryController);
        report.setVisible(true);
    }

    /**
     * ActionListerer for AdminHomeCard
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentCustomerMenu != null){
            if (e.getSource() == currentCustomerMenu.getBTNGetReport()) {
                getReport(currentCustomerMenu.getCustomerID(), currentCustomerMenu.getTradingHistoryController());
                currentCustomerMenu.setVisible(false);
                currentCustomerMenu.dispose();
            }
            if (e.getSource() == currentCustomerMenu.getBTNSendNotification()){
                String message = JOptionPane.showInputDialog("Enter your message: ", "");
                if (message != null) {
                    // user clicked "Send"
                    System.out.println("Message: " + message);
                    sendNotification(currentCustomerMenu.getCustomerID(), message);
                } else {
                    // user clicked "Cancel" or closed the dialog box
                    System.out.println("Dialog closed without sending a message.");
                }
                currentCustomerMenu.setVisible(false);
                currentCustomerMenu.dispose();
            }
        }
        if (adminHomeCard != null){
            if (e.getSource() == adminHomeCard.getBTNGetAllReport()){
                ALLCustomerReport allCustomerReport = new ALLCustomerReport(allUserTradingHistoryController);
                allCustomerReport.setVisible(true);
            }
            if (e.getSource() == adminHomeCard.getBTNNotifyAll()) {
                ArrayList<Integer> customerIds = adminRepository.getCustomersEligibleForOptionAccounts();
                if (customerIds.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No customers are eligible for an option account!");
                } else {
                    JOptionPane.showMessageDialog(null, "Notifying " + customerIds.size() + " customers!");
                }
                for (Integer customerId : customerIds) {
                    sendNotification(customerId, "You are eligible for an option account!");
                }
            }
        }
    }

    /**
     * handles getting amount invested by customer
     * @param customerID
     * @return
     */
    public String getTotalInvestedAmount(int customerID) {
        return String.format("%.2f", userRepository.getTotalInvested(customerID, null));
    }

    /**
     * handles adding observer
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        observerMessageMap.put(observer, new ArrayList<>());
    }

    /**
     * handles removing observer
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        observerMessageMap.remove(observer);
    }

    /**
     * handles notifying observers
     * @param message
     */
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observerMessageMap.keySet()) {
            List<String> messages = observerMessageMap.get(observer);
            if (messages != null && !messages.isEmpty()){
                for (String msg : messages) {
                    observer.update(msg);
                }
                messages.clear();
            }
        }
    }

    /**
     * handles adding message for observer
     * @param observer
     * @param message
     */
    public void addMessageForObserver(Observer observer, String message){
        List<String> messages = observerMessageMap.get(observer);
        if (messages != null){
            messages.add(message);
        }
    }

    /**
     * handles sending notification to customer
     * @param customerId
     * @param message
     */
    public void sendNotification(int customerId, String message) {
        System.out.println("AdminHomeController::sendNotification");
        adminRepository.sendNotification(customerId, message);
    }

    /**
     * handles getting realized profit for a customer
     * @param customerID
     * @return
     */
    public double getRealizedProfit(int customerID) {
        return userRepository.getUnRealizedProfitByCustomerID(customerID);
    }

    /**
     * handles getting unrealized profit for a customer
     * @param customerID
     * @return
     */
    public double getUnrealizedProfit(int customerID) {
        return userRepository.getRealizedProfitByCustomerID(customerID);
    }
}
