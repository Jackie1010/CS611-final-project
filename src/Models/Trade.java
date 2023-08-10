package Models;

import java.util.Date;

public class Trade {

    public enum TradeType {
        BUY, SELL
    }

    private String stockSymbol;
    private TradeType tradeType;
    private int quantity;
    private float price;
    private Date date;
    private float total_trade_cost;

    /**
     * Constructor:
     */
    public Trade(String stockSymbol, TradeType tradeType, int quantity, float price, Date date, float total_trade_cost) {
        this.stockSymbol = stockSymbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.total_trade_cost = total_trade_cost;
    }

    /**
     * Getters:
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Method to get trade type
     * @return
     */
    public TradeType getTradeType() {
        return tradeType;
    }

    /**
     * Method to get quantity
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Method to get price
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     * Method to get date
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Method to get total trade cost
     * @return
     */
    public float getTotalTradeCost() {
        return total_trade_cost;
    }

    /**
     * Setters:
     */

    /**
     * Method to set stock symbol
     * @param stockSymbol
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * Method to set trade type
     * @param tradeType
     */
    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Method to set quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Method to set trade cost
     * @param total_trade_cost
     */
    public void setTotalTradeCost(float total_trade_cost) {
        this.total_trade_cost = total_trade_cost;
    }




}
