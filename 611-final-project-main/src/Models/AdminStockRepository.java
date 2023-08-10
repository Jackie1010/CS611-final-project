package Models;

import Database.Services.AdminStockMarketService;
import Database.Services.StockMarketService;

public class AdminStockRepository extends StockRepository{

    private AdminStockMarketService adminStockMarketService;


    /**
     * Constructor:
     * @param stockMarketService
     * @param adminStockMarketService
     */
    public AdminStockRepository(StockMarketService stockMarketService, AdminStockMarketService adminStockMarketService) {
        super(stockMarketService);
        this.adminStockMarketService = adminStockMarketService;
    }

    /**
     * Method to update the price of a stock
     * @param newPrice
     * @param symbol
     */
    public void updatePrice(double newPrice, String symbol) {
        adminStockMarketService.updatePrice(newPrice, symbol);
        adminStockMarketService.addStockUpdate(newPrice,symbol);
    }

    /**
     * Method to update the tradeability of a stock
     * @param tradeability
     * @param symbol
     */
    public void updateTradeability(String tradeability, String symbol) {
//        if (tradeability.equals("true"))
        if (tradeability.equals("T")){
            adminStockMarketService.updateTradeability(1, symbol);
        }
        else {
            adminStockMarketService.updateTradeability(0, symbol);
        }

    }
}
