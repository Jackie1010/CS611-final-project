import Controllers.ControllerFactory;
import Controllers.LoginController;
import Controllers.SignupController;
import Database.Connect;
import Database.CreateTables;
import Database.Services.*;
import Models.*;
import Views.LoginPage;

import javax.swing.*;
import java.sql.Connection;

/**
 * Class that runs the application
 */
public class RunApp {



    /**
     * Constructor:
     */
    public RunApp() {
    }

    /**
     * Method to run the app
     */
    public void runApp(){
        Connection conn = Connect.getConnection();
        CreateTables createTables = new CreateTables(conn);
        createTables.createAllTables();


        // Services
        CustomerStockMarketService customerStockMarketService = new CustomerStockMarketService(conn);
        customerStockMarketService.readAndStoreStockMarketData();
        AccountService as = new AccountService(conn);
        TradesService ts = new TradesService(conn);
        TradingAccountService tas = new TradingAccountService(conn);
        NotificationService ns = new NotificationService(conn);
        AdminAccountService aas = new AdminAccountService(conn);
        AdminStockMarketService adminStockMarketService = new AdminStockMarketService(conn);


        // Models
        UserRepository userRepository = new UserRepository(as, tas, ts, ns);
        StockRepository stockRepository = new StockRepository(customerStockMarketService);
        TradeRepository tradeRepository = new TradeRepository(ts);
        AdminRepository adminRepository = new AdminRepository(aas, tas, ts, ns);
        AdminStockRepository adminStockRepository = new AdminStockRepository(customerStockMarketService, adminStockMarketService);


        // Controllers
        ControllerFactory controllerFactory = new ControllerFactory(userRepository, stockRepository, tradeRepository, adminRepository, customerStockMarketService, adminStockRepository);
        LoginController loginController = controllerFactory.getLoginController();
        SignupController signupController = controllerFactory.getSignupController();



        // Log-In View
        JFrame jframe = new LoginPage(loginController, signupController);
        jframe.setVisible(true);
    }
}
