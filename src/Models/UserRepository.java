package Models;

import Database.Services.AccountService;
import Database.Services.NotificationService;
import Database.Services.TradesService;
import Database.Services.TradingAccountService;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private AccountService accountService;
    private TradingAccountService tradingAccountService;
    private TradesService tradesService;

    private NotificationService notificationService;

    /*
     * Constructor:
     */
    public UserRepository(AccountService accountService, TradingAccountService tradingAccountService, TradesService tradesService, NotificationService notificationService) {
        this.accountService = accountService;
        this.tradingAccountService = tradingAccountService;
        this.tradesService = tradesService;
        this.notificationService = notificationService;
    }

    /*
     * Return the user object if the username exists in the database.
     */
    public User findUserByUsername(String username) {
        System.out.println("UserRepository: Finding user by username");
        try {
            ResultSet customerRS = accountService.findCustomerByUsername(username);
            boolean customerExists = customerRS.next();
            if (customerExists) {
                String password = customerRS.getString("password");
                String email = customerRS.getString("email");
                System.out.println("UserRepository: Found Customer");
                return new Customer(username, password, email);
            } else {
                System.out.println("UserRepository: Customer ResultSet is null");
                ResultSet adminRS = accountService.findAdminByUsername(username);
                if (adminRS == null) {
                    System.out.println("UserRepository: Admin ResultSet is null");
                    return null;
                }
                if (adminRS.next()) {
                    System.out.println("UserRepository: Found Admin");
                    String password = adminRS.getString("password");
                    return new Admin(username, password);
                }
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException finding the user by username");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * method to get the user type
     * @param username
     * @param password
     * @return
     */
    public User authenticate(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * method to get the user id
     * @param username
     * @param type
     * @return
     */
    public int getUserId(String username, String type) {
        return accountService.getUserID(username, type);
    }

//    public boolean emailSanityCheck(String email) {
//        return accountService.emailSanityCheck(email);
//    }

    /**
     * method to check if username exists
     * @param username
     * @return
     */
    public int checkIfUserExists(String username) {
        return accountService.checkIfUserExists(username);
    }

    /**
     * method to check if email exists
     * @param email
     * @return
     */
    public int checkIfEmailExists(String email) {
        return accountService.checkIfEmailExists(email);
    }

    /**
     * method to handle account creation
     * @param first_name
     * @param last_name
     * @param email
     * @param username
     * @param password
     * @return
     */
    public int handleAccountCreation(String first_name, String last_name, String email, String username, String password) {
        return accountService.handleAccountCreation(first_name, last_name, email, username, password);
    }

    /**
     * method to get the cash available for a customer
     * @param userID
     * @param customer
     * @return
     */
    public float getCashAvailable(int userID, Customer customer) {
        customer.setTotalCash(accountService.getCashRemaining(userID));
        System.out.println("UserRepository: Cash available: " + customer.getTotalCash());
        return customer.getTotalCash();
    }

    /**
     * method to update the cash available for a customer
     * @param customerID
     * @param newCashAvailable
     * @param customer
     */
    public void updateCashAvailable(int customerID, double newCashAvailable, Customer customer) {
        accountService.updateCashAvailable(customerID, newCashAvailable);
        customer.setTotalCash((float) newCashAvailable);
    }

    /**
     * method to get the total amount invested by a customer
     */
    public float getTotalInvested(int customerID, Customer customer) {
        if (customer != null) {
            customer.setTotalInvested(accountService.getTotalInvested(customerID));
            return customer.getTotalInvested();
        }
        return accountService.getTotalInvested(customerID);
    }

    /**
     * method to update the total amount invested by a customer
     */
    public void updateTotalInvested(int customerID, double newTotalInvested, Customer customer) {
        accountService.updateTotalInvested(customerID, newTotalInvested);
        customer.setTotalInvested((float) newTotalInvested);
    }

    /**
     * method to get the total realized profit for a customer
     * @param customerID
     * @return
     */
    public boolean doesTradingAccountExist(int customerID) {
        System.out.println("UserRepository: Checking if trading account exists");
        ResultSet rs = tradingAccountService.findTradingAccountByID(customerID);
        System.out.println("UserRepository: Result set is " + rs);
        if (rs == null) {
            System.out.println("UserRepository: Trading account with id " + customerID + " does not exist");
            return false;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Trading account with id " + customerID + " exists");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Should not execute this");
        return false;
    }


    /**
     * method to get the total realized profit for a customer
     * @param customerID
     */
    public void createNewTradingAccount(int customerID) {
        System.out.println("UserRepository: Applying for trading account");
        tradingAccountService.createTradingAccount(customerID);
    }

    /**
     * method to get the total realized profit for a customer
     * @param customerID
     * @param stockName
     * @return
     */
    public float getNumberOfShares(int customerID, String stockName) {
        System.out.println("UserRepository: Getting number of shares for " + stockName);
        ResultSet rs = tradesService.getNumberOfShares(customerID, stockName);
        if (rs == null) {
            System.out.println("UserRepository: ResultSet is null");
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found number of shares");
                return rs.getFloat("shares");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting number of shares");
            e.printStackTrace();
            return 0;
        }
        System.out.println("UserRepository: Should not execute this");
        return 0;
    }


    /**
     * method to get the total realized profit for a customer
     * @param customerID
     * @param customer
     * @return
     */
    public boolean getCustomerApproved(int customerID, Customer customer) {
        ResultSet rs = accountService.getCustomerApproved(customerID);
        if (rs == null) {
            return false;
        }
        try {
            if (rs.next()) {
                if (rs.getBoolean("approved")) {
                    customer.setApproved(true);
                    return true;
                }
                return rs.getBoolean("approved");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting customer approved");
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * method to get the total realized profit for a customer
     * @param customerID
     * @param newCash
     * @param customer
     * @param stockSymbol
     */
    public void updateRealizedProfitByStock(int customerID, double newCash, Customer customer, String stockSymbol) {
        System.out.println("UserRepository: Updating realized profit for customer with id " + customerID);
        accountService.updateRealizedProfitByStock(customerID, newCash, stockSymbol);
    }

    /**
     * Update unrealized profit for a stock
     * @param customerID
     * @param newUnrealizedProfit
     * @param customer
     * @param stockSymbol
     */
    public void updateUnrealizedProfitByStock(int customerID, double newUnrealizedProfit, Customer customer, String stockSymbol) {
        System.out.println("UserRepository: Updating unrealized profit for customer with id " + customerID);
        accountService.updateUnrealizedProfitByStock(customerID, newUnrealizedProfit, stockSymbol);
    }

    /**
     * Get realized profit for a stock
     * @param customerID
     * @param customer
     * @param stockSymbol
     * @return
     */
    public double getRealizedProfitByStock(int customerID, Customer customer, String stockSymbol) {
        ResultSet rs = accountService.getRealizedProfitByStock(customerID, stockSymbol);
        if (rs == null) {
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found realized profit");
                return rs.getDouble("profit");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting realized profit by stock");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * Get unrealized profit for a stock
     * @param symbol
     * @param customerID
     * @return
     */
    public double getUnrealizedProfitByStock(String symbol, int customerID) {
        ResultSet rs = accountService.getUnrealizedProfitByStock(symbol, customerID);
        if (rs == null) {
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found unrealized profit");
                return rs.getDouble("profit");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting unrealized profit by stock");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * Get average price for a stock
     * @param symbol
     * @param customerID
     * @return
     */
    public double getAveragePriceByStock(String symbol, int customerID) {
        ResultSet rs = accountService.getAveragePriceByStock(symbol, customerID);
        if (rs == null) {
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found average price");
                return rs.getDouble("average_cost_per_share");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting average price by stock");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }


    /**
     * Get realized profit for a customer
     * @param customerID
     * @return
     */
    public double getRealizedProfitByCustomerID(int customerID) {
        ResultSet rs = accountService.getRealizedProfitByCustomerID(customerID);
        if (rs == null) {
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found realized profit");
                return rs.getDouble("total_realized_profit");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting realized profit by customer id");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * Get unrealized profit for a customer
     * @param customerID
     * @return
     */
    public double getUnRealizedProfitByCustomerID(int customerID) {
        ResultSet rs = accountService.getUnRealizedProfitByCustomerID(customerID);
        if (rs == null) {
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found unrealized profit");
                return rs.getDouble("total_unrealized_profit");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting unrealized profit by customer id");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * Get all notifications for a customer
     * @param customerID
     * @return
     */
    public ArrayList<String> getNotifications(int customerID) {
        ResultSet rs = notificationService.getNotifications(customerID);
        if (rs == null) {
            return null;
        }
        ArrayList<String> notifications = new ArrayList<>();
        try {
            while (rs.next()) {
                notifications.add(rs.getString("message"));
            }
            System.out.println("UserRepository: Found " + notifications.size() + " notifications");
            return notifications;
        } catch (SQLException e) {
            System.out.println("UserRepository: Error getting notifications");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Mark a notification as read
     * @param customerID
     * @param notification
     */
    public void markNotificationAsRead(int customerID, String notification) {
        ResultSet rs = notificationService.markNotificationAsRead(customerID, notification);
    }

    /**
     * Get all the info for a customer
     * @param customerID
     * @return
     */
    public ArrayList<List<String>> getInfo(int customerID) {
        ResultSet rs = accountService.getInfo(customerID);
        if (rs == null) {
            return null;
        }
        ArrayList<List<String>> info = new ArrayList<>();
        try {
            while (rs.next()) {
                List<String> customer = new ArrayList<>();
                customer.add(rs.getString("id"));
                info.add(customer);
            }
            System.out.println("UserRepository: Found " + info.size() + " info");
            return info;
        } catch (SQLException e) {
            System.out.println("UserRepository: Error getting info");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a trading account exists for a customer
     * @param customerID
     * @return
     */
    public boolean checkIfTradingAccountExists(int customerID) {
        ResultSet rs = accountService.checkIfTradingAccountExists(customerID);
        if (rs == null) {
            return false;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found trading account");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException checking if trading account exists");
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * Update the deposit of a customer
     * @param customerid
     * @param cashToAdd
     * @param customer
     */
    public void updateDeposit(int customerid, float cashToAdd, Customer customer) {
        ResultSet rs = accountService.updateDeposit(customerid, cashToAdd);
    }

    /**
     * Get the total amount of money invested by a stock
     * @param customerID
     * @param customer
     * @param stockSymbol
     * @return
     */
    public double getInvestedByStock(int customerID, Customer customer, String stockSymbol) {
        ResultSet rs = accountService.getInvestedByStock(customerID, stockSymbol);
        if (rs == null) {
            return 0;
        }
        try {
            if (rs.next()) {
                System.out.println("UserRepository: Found invested by stock");
                return rs.getDouble("total_cost");
            }
        } catch (SQLException e) {
            System.out.println("UserRepository: SQLException getting invested by stock");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
}