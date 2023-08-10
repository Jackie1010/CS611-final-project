package Controllers;

import Database.Services.CustomerStockMarketService;
import Database.Services.StockMarketService;
import Models.*;
import Views.Admin.AdminMainPage;
import Views.Customer.CustomerMainPage;

import javax.swing.*;

public class ControllerFactory {

    private CustomerStockMarketService stockMarketService;

    private AdminStockRepository adminStockRepository;
    private UserRepository userRepository;
    private StockRepository stockRepository;
    private TradeRepository tradeRepository;
    private AdminRepository adminRepository;

    private CustomerController customerController;
    private CustomerHomeController customerHomeController;
    private CustomerStockAccountController customerStockAccountController;
    private CustomerStockMarketActionMenuController customerStockMarketActionMenuController;
    private CustomerStockMarketController customerStockMarketController;
    private CustomerTradingHistoryController customerTradingHistoryController;

    private AllUserTradingHistoryController allUserTradingHistoryController;

    private LoginController loginController;
    private SignupController signupController;

    private AdminController adminController;
    private AdminHomeController adminHomeController;
    private AdminMarketController adminMarketController;

    /**
     * Constructor
     */
    public ControllerFactory(UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository, AdminRepository adminRepository, CustomerStockMarketService stockMarketService, AdminStockRepository adminStockRepository) {
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.tradeRepository = tradeRepository;
        this.adminRepository = adminRepository;
        this.stockMarketService = stockMarketService;
        this.adminStockRepository = adminStockRepository;
    }

    /**
     * Getter for the customerHomeController
     */
    public CustomerHomeController getCustomerHomeController() {
        if (customerHomeController == null) {
            customerHomeController = new CustomerHomeController(stockMarketService, userRepository, stockRepository, tradeRepository, getCustomerTradingHistoryController());
        }
        return customerHomeController;
    }

    /**
     * Getter for the customerStockAccountController
     */
    public CustomerStockAccountController getCustomerStockAccountController() {
        if (customerStockAccountController == null) {
            customerStockAccountController = new CustomerStockAccountController(stockMarketService, userRepository, stockRepository, tradeRepository);
        }
        return customerStockAccountController;
    }

    /**
     * Getter for the customerStockMarketActionMenuController
     */
    public CustomerStockMarketActionMenuController getCustomerStockMarketActionMenuController() {
        if (customerStockMarketActionMenuController == null) {
            customerStockMarketActionMenuController = new CustomerStockMarketActionMenuController(stockMarketService, userRepository, stockRepository, tradeRepository);
        }
        return customerStockMarketActionMenuController;
    }

    /**
     * Getter for the customerStockMarketController
     */
    public CustomerStockMarketController getCustomerStockMarketController() {
        if (customerStockMarketController == null) {
            customerStockMarketController = new CustomerStockMarketController(stockMarketService, userRepository, stockRepository, tradeRepository);
        }
        return customerStockMarketController;
    }

    /**
     * Getter for the customerTradingHistoryController
     */
    public CustomerTradingHistoryController getCustomerTradingHistoryController() {
        if (customerTradingHistoryController == null) {
            customerTradingHistoryController = new CustomerTradingHistoryController(stockMarketService, userRepository, stockRepository, tradeRepository);
        }
        return customerTradingHistoryController;
    }

    /**
     * Getter for the loginController
     */
    public LoginController getLoginController() {
        if (loginController == null) {
            loginController = new LoginController(userRepository, this);
        }
        return loginController;
    }

    /**
     * Getter for the signupController
     */
    public SignupController getSignupController() {
        if (signupController == null) {
            signupController = new SignupController(userRepository);
        }
        return signupController;
    }

    /**
     * Getter for the adminController
     */
    public AdminController getAdminController() {
        if (adminController == null) {
            adminController = new AdminController(adminRepository, stockRepository, tradeRepository, userRepository);
        }
        return adminController;
    }

    /**
     * Getter for the adminHomeController
     */

    public AdminHomeController getAdminHomeController() {
        if (adminHomeController == null) {
            AllUserTradingHistoryController allUserTradingHistoryController = getAllUserTradingHistoryController();
            adminHomeController = new AdminHomeController(adminRepository, stockRepository, tradeRepository, userRepository, allUserTradingHistoryController);
        }
        return adminHomeController;
    }

    /**
     * Getter for the allUserTradingHistoryController
     */
    public AllUserTradingHistoryController getAllUserTradingHistoryController() {
        if (allUserTradingHistoryController == null) {
            allUserTradingHistoryController = new AllUserTradingHistoryController(adminRepository, stockRepository, tradeRepository, userRepository);
        }
        return allUserTradingHistoryController;
    }


    /**
     * Getter for the adminMarketController
     */
    public AdminMarketController getAdminMarketController() {
        if (adminMarketController == null) {
            adminMarketController = new AdminMarketController(adminRepository, stockRepository, tradeRepository, userRepository, adminStockRepository);
        }
        return adminMarketController;
    }

    /**
     * Show CustomerMainPage
     */
    public void showCustomerMainPage(Customer customer, int customerID, JFrame loginPage) {
        CustomerHomeController customerHomeController = getCustomerHomeController();
        CustomerStockMarketController customerStockMarketController = getCustomerStockMarketController();
        CustomerStockAccountController customerStockAccountController = getCustomerStockAccountController();
        CustomerTradingHistoryController customerTradingHistoryController = getCustomerTradingHistoryController();
        CustomerStockMarketActionMenuController customerStockMarketActionMenuController = getCustomerStockMarketActionMenuController();

        customerHomeController.setCustomer(customer);
        customerStockAccountController.setCustomer(customer);
        customerStockMarketController.setCustomer(customer);
        customerTradingHistoryController.setCustomer(customer);
        customerStockMarketActionMenuController.setCustomer(customer);

        CustomerMainPage customerMainPage = new CustomerMainPage(customerID, getLoginController(), getSignupController(), customerHomeController, customerStockMarketController, customerStockAccountController, customerTradingHistoryController, customerStockMarketActionMenuController);
        customerMainPage.setVisible(true);
        loginPage.setVisible(false);
    }

    /**
     * Show AdminMainPage
     */
    public void showAdminMainPage(Admin admin, int adminID, JFrame loginPage) {
        AdminHomeController adminHomeController = getAdminHomeController();
        AdminMarketController adminMarketController = getAdminMarketController();
        CustomerTradingHistoryController customerTradingHistoryController = getCustomerTradingHistoryController();
        AllUserTradingHistoryController allUserTradingHistoryController = getAllUserTradingHistoryController();

        adminHomeController.setAdmin(admin);
        adminMarketController.setAdmin(admin);

        AdminMainPage adminMainPage = new AdminMainPage(adminID, adminHomeController, getLoginController(), getSignupController(), customerTradingHistoryController, adminMarketController, allUserTradingHistoryController);
        adminMainPage.setVisible(true);
        loginPage.setVisible(false);
    }

}
