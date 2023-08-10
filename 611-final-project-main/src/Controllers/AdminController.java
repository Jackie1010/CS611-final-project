package Controllers;

import Models.*;

public class AdminController {

    protected int adminID;
    protected AdminRepository adminRepository;
    protected StockRepository stockRepository;
    protected TradeRepository tradeRepository;
    protected UserRepository userRepository;

    protected Admin admin;

    /**
     * Constructor:
     */
    public AdminController(AdminRepository adminRepository, StockRepository stockRepository, TradeRepository tradeRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.stockRepository = stockRepository;
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Setter and Getters
     */
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

}
