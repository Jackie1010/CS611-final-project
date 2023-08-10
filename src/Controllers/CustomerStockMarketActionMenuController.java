package Controllers;

import Database.Services.CustomerStockMarketService;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;
import Views.Customer.StockAccountActionMenu;
import Views.Customer.StockMarketActionMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerStockMarketActionMenuController extends CustomerController implements ActionListener {

    private StockMarketActionMenu stockMarketActionMenu;
    private StockAccountActionMenu stockAccountActionMenu;

    /**
     * Constructor:
     * @param stockMarketService
     * @param userRepository
     * @param stockRepository
     * @param tradeRepository
     */
    public CustomerStockMarketActionMenuController(CustomerStockMarketService stockMarketService, UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository) {
        super(stockMarketService, userRepository, stockRepository, tradeRepository);
    }

    /**
     * Setter for the stockMarketActionMenu
     */
    public void setStockMarketActionMenu(StockMarketActionMenu stockMarketActionMenu) {
        this.stockMarketActionMenu = stockMarketActionMenu;
    }

    /**
     * Setter method for the stockAccountActionMenu
     */
    public void setStockAccountActionMenu(StockAccountActionMenu stockAccountActionMenu) {
        this.stockAccountActionMenu = stockAccountActionMenu;
    }

    /**
     * Action listener for the stock market action menu
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (stockMarketActionMenu != null){
            if (e.getSource() == stockMarketActionMenu.getBTNBuy()) {
                toBuyDialog(stockMarketActionMenu.getStockName(), stockMarketActionMenu.getStockSymbol(), null, stockMarketActionMenu.getCustomerID());
                stockMarketActionMenu.dispose();
            }
        }
        if (stockAccountActionMenu != null){
            if (e.getSource() == stockAccountActionMenu.getBTNBuy()){
                toBuyDialog(stockAccountActionMenu.getStockName(), stockAccountActionMenu.getStockSymbol(), null, stockAccountActionMenu.getCustomerID());
                stockAccountActionMenu.dispose();
            }
            if (e.getSource() == stockAccountActionMenu.getBTNSell()) {
                toSellDialog(stockAccountActionMenu.getRow(), stockAccountActionMenu.getStockName(), stockAccountActionMenu.getStockSymbol(), stockAccountActionMenu.getTable(), stockAccountActionMenu.getLBLCashAvailable(), stockAccountActionMenu.getCustomerID());
                stockAccountActionMenu.dispose();
            }
        }

    }

    /**
     * Method to get the stock price
     * @param stockSymbol
     * @return
     */
    public String getStockPrice(String stockSymbol) {
        double price = stockRepository.getStockPrice(stockSymbol);
        return String.format("%.2f", price);
    }

    /**
     * Method to get the total invested by a customer
     * @param stockSymbol
     * @param customerID
     * @return
     */
    public String getInvestedByStock(String stockSymbol, int customerID) {
        double invested = tradeRepository.getInvestedByStock(stockSymbol, customerID);
        return String.format("%.2f", invested);
    }

    /**
     * Method to get the tradability of a stock
     * @param stockSymbol
     * @return
     */
    public String getStockTradable(String stockSymbol) {
        boolean tradable = stockRepository.getStockTradeability(stockSymbol);
        if (tradable) {
            return "Yes";
        } else {
            return "No";
        }
    }

    /**
     * Method to get the number of shares a customer has for a stock
     * @param symbol
     * @param customerID
     * @return
     */
    public String getNumberOfShares(String symbol, int customerID) {
        float shares = userRepository.getNumberOfShares(customerID, symbol);
        return String.format("%.2f", shares);
    }

    /**
     * Method to get the stock data for the stock market
     * @param symbol
     * @return
     */
    public List<List<String>> getStockUpdateData(String symbol)  {
        return stockRepository.getStockUpdateData(symbol);
    }
}
