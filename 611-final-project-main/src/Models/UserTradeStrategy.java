package Models;

import Database.Services.TradesService;

public interface UserTradeStrategy {

    /**
     * interface method to trade
     * @param customerID
     * @param stockSymbol
     * @param shares
     * @param stockPrice
     * @param tradesService
     */
    public void trade(int customerID, String stockSymbol, double shares, double stockPrice, TradesService tradesService);
}
