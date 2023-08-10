package Models;

import Database.Services.TradesService;

public class BuyStrategy implements UserTradeStrategy{

    /**
     * Method to trade
     * @param customerID
     * @param stockSymbol
     * @param shares
     * @param stockPrice
     * @param tradesService
     */
    @Override
    public void trade(int customerID, String stockSymbol, double shares, double stockPrice, TradesService tradesService) {
        System.out.println("BuyStrategy: Making trade");
        tradesService.makeTrade(customerID, stockSymbol, shares, stockPrice, "buy");
    }
}
