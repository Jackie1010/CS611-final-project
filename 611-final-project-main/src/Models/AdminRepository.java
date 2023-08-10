package Models;

import Database.Services.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {

    private AdminAccountService accountService;
    private TradingAccountService tradingAccountService;
    private TradesService tradesService;
    private NotificationService notificationService;


    /**
     * Constructor:
     * @param accountService
     * @param tradingAccountService
     * @param tradesService
     * @param notificationService
     */
    public AdminRepository(AdminAccountService accountService, TradingAccountService tradingAccountService, TradesService tradesService, NotificationService notificationService) {
        this.accountService = accountService;
        this.tradingAccountService = tradingAccountService;
        this.tradesService = tradesService;
        this.notificationService = notificationService;
    }

    /**
     * Method to get the unapproved customers
     * @return
     */
    public ArrayList<List<String>> getUnApprovedCustomers() {
        System.out.println("UserRepository: Getting unapproved customers");
        ResultSet rs = accountService.getUnApprovedCustomers();
        if (rs == null) {
            return null;
        }
        ArrayList<List<String>> unApprovedCustomers = new ArrayList<>();
        try {
            while (rs.next()) {
                List<String> customer = new ArrayList<>();
                customer.add(rs.getString("id"));
                customer.add(rs.getString("first_name"));
                customer.add(rs.getString("last_name"));
                unApprovedCustomers.add(customer);
            }
            System.out.println("UserRepository: Found " + unApprovedCustomers.size() + " unapproved customers");
            return unApprovedCustomers;
        } catch (SQLException e) {
            System.out.println("UserRepository: Error getting unapproved customers");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Approve customer
     * @param customerId
     */
    public void approveCustomer(String customerId) {
        System.out.println("UserRepository: Approving customer with id " + customerId);
        accountService.approveCustomer(customerId);
    }

    /**
     * Deny customer
     * @param customerId
     */
    public void denyCustomer(String customerId) {
        System.out.println("UserRepository: Denying customer with id " + customerId);
        accountService.denyCustomer(customerId);
    }

    /**
     * Get approved customers
     * @return
     */
    public ArrayList<List<String>> getApprovedCustomers() {
        ResultSet rs = accountService.getApprovedCustomers();
        if (rs == null) {
            return null;
        }
        ArrayList<List<String>> approvedCustomers = new ArrayList<>();
        try {
            while (rs.next()) {
                List<String> customer = new ArrayList<>();
                customer.add(rs.getString("customer_id"));
                customer.add(rs.getString("customer_name"));
                customer.add(rs.getString("trading_account_id"));
                customer.add(rs.getString("total_realized_profits"));
                customer.add(rs.getString("total_unrealized_profits"));
                approvedCustomers.add(customer);
            }
            System.out.println("UserRepository: Found " + approvedCustomers.size() + " approved customers");
            return approvedCustomers;
        } catch (SQLException e) {
            System.out.println("UserRepository: Error getting approved customers");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get customer by id
     * @param customerId
     * @param message
     */
    public void sendNotification(int customerId, String message) {
        System.out.println("UserRepository: Sending notification to customer with id " + customerId);
        notificationService.putNotificationForCustomer(customerId, message);
    }

    /**
     * Get customers by stock
     * @param symbol
     * @return
     */
    public ArrayList<List<Integer>> getCustomersByStock(String symbol) {
        ResultSet rs = accountService.getCustomersByStock(symbol);
        if (rs == null) {
            return null;
        }
        ArrayList<List<Integer>> customers = new ArrayList<>();
        try {
            while (rs.next()) {
                List<Integer> customer = new ArrayList<>();
                customer.add(rs.getInt("customer_id"));
                customers.add(customer);
            }
            System.out.println("UserRepository: Found " + customers.size() + " customers");
            return customers;
        } catch (SQLException e) {
            System.out.println("UserRepository: Error getting customers by stock");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get customers eligible for option accounts
     * @return
     */
    public ArrayList<Integer> getCustomersEligibleForOptionAccounts() {
        int cutOffForOptionAccounts = 10000;
        ResultSet rs = accountService.getCustomersEligibleForOptionAccounts(cutOffForOptionAccounts);
        if (rs == null) {
            return null;
        }
        ArrayList<Integer> customers = new ArrayList<>();
        try {
            while (rs.next()) {
                customers.add(rs.getInt("customer_id"));
            }
            System.out.println("UserRepository: Found " + customers.size() + " customers eligible for option accounts");
            return customers;
        } catch (SQLException e) {
            System.out.println("UserRepository: Error getting customers eligible for option accounts");
            e.printStackTrace();
            return null;
        }
    }


}
