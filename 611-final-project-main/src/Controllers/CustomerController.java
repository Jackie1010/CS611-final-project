package Controllers;

import Database.Services.CustomerStockMarketService;
import Models.Customer;
import Models.StockRepository;
import Models.TradeRepository;
import Models.UserRepository;
import Views.Customer.CustomerHomeCard;
import Views.Customer.CustomerStockAccountCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {


    protected int customerID;

    protected UserRepository userRepository;
    protected StockRepository stockRepository;
    protected TradeRepository tradeRepository;
    protected Customer customer;

    /*
     * Constructor:
     */
    public CustomerController(CustomerStockMarketService stockMarketService, UserRepository userRepository, StockRepository stockRepository, TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
    }


    /**
     * Handles Buy Dialog
     * @param stockName
     * @param stockSymbol
     * @param LBLCashAvailable
     * @param customerID
     */
    public void toBuyDialog(String stockName, String stockSymbol, javax.swing.JLabel LBLCashAvailable, int customerID) {
        System.out.println("Show Dialog of Buying Stock");

        if (!userRepository.checkIfTradingAccountExists(customerID)) {
            System.out.println("CustomerController: Trading Account Does Not Exist");
            JOptionPane.showMessageDialog(null, "You do not have a trading account. Please create one first.");
            return;
        }

        if (!stockRepository.getStockTradeability(stockSymbol)) {
            System.out.println("CustomerController: Stock is not available to trade");
            JOptionPane.showMessageDialog(null, "This stock is not available to trade.");
            return;
        }

        // Show the buy dialog here
        JTextField sharesField = new JTextField();
        Object[] message = {
                "How many shares of " + stockName + " do you want to buy? (Auto rounded to 5 decimals)", sharesField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Buy Shares", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Parse the number of shares entered by the user
                double shares = Double.parseDouble(sharesField.getText().trim());
                if (shares <= 0) {
                    throw new NumberFormatException();
                }

                System.out.println("CustomerController: Calculating Stock Price ");
                double stockPrice = stockRepository.getStockPrice(stockSymbol);

                // Calculate the total buying cost
                System.out.println("CustomerController: Calculating Buying Cost ");
                double buyingCost = shares * stockPrice;

                // Get the current available cash
                System.out.println("CustomerController: Calculating Available Cash ");
                double availableCash = userRepository.getCashAvailable(customerID, customer);
                System.out.println("CustomerController: Available Cash: " + availableCash);

                // Check if the buying cost exceeds the available cash
                if (buyingCost > availableCash) {
                    throw new Exception("Transaction exceeds available cash.");
                }

                //TODO:: check from database to see if current stock available to trade, if not available, show a warning dialog
                if (!stockRepository.getStockTradeability(stockSymbol)) {
                    throw new Exception("This stock is not available to trade.");
                }
                //TODO:: update database the stocks he owns (add new stock to trading account, increment shares to existing stocks)
                System.out.println("CustomerController: Making Trade");
                tradeRepository.makeTrade(customerID, stockSymbol, shares, stockPrice, "buy");
                float amt_invested = (float) ((float) shares * stockPrice);
                float already_invested = userRepository.getTotalInvested(customerID, customer);
                amt_invested += already_invested;
                userRepository.updateTotalInvested(customerID, amt_invested, customer);

                //TODO:: synchronize other data with the database
                /**
                 * Once you update the trading account table in database, other data should be automatically updated
                 * in the trading account page (if I did it correctly, please double check).
                 */

                // Update the "ToBuy" column with the number of shares to buy
//                TBLStockMarket.setValueAt(String.format("%.2f", shares), row, 3);

                // Calculate and update the new available cash
                double newCashAvailable = availableCash - buyingCost;
                userRepository.updateCashAvailable(customerID, newCashAvailable, customer);
                if (LBLCashAvailable != null)
                    LBLCashAvailable.setText(String.format("%.2f", newCashAvailable));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid number of shares. Please enter a positive number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    /**
     * Handles Sell Dialog
     * @param row
     * @param stockName
     * @param stockSymbol
     * @param table
     * @param LBLCashAvailable
     * @param customerID
     */
    public void toSellDialog(int row, String stockName, String stockSymbol, JTable table, JLabel LBLCashAvailable, int customerID) {
        //TODO check from database to see if current stock available to trade, if not available, show a warning dialog
        //Realized Profit = (Current Price - Total Cost / Shares) * Shares Sold
        System.out.println("Show Dialog of Selling Stock");

        if (!userRepository.checkIfTradingAccountExists(customerID)) {
            System.out.println("CustomerController: Trading Account Does Not Exist");
            JOptionPane.showMessageDialog(null, "You do not have a trading account. Please create one first.");
            return;
        }

        // Get the stock name from the row
        // Get the number of shares owned by the user
        float currentShares = userRepository.getNumberOfShares(customerID, stockSymbol);

//        double currentShares = Double.parseDouble(TBLTradingStocks.getValueAt(row, 5).toString());
        // Show the sell dialog here
        JTextField sharesField = new JTextField();
        Object[] message = {
                "How many shares of " + stockName + " do you want to sell? (Auto rounded to 5 decimals)", sharesField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Sell Shares", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Parse the number of shares entered by the user
                double sharesToSold = Double.parseDouble(sharesField.getText().trim());
                if (sharesToSold <= 0) {
                    throw new NumberFormatException();
                }
                if (sharesToSold > currentShares) {
                    throw new Exception("You cannot sell more shares than you own.");
                }

                double currentCost = userRepository.getInvestedByStock(customerID, customer, stockSymbol);
                double currentPrice = Double.parseDouble(table.getValueAt(row, 3).toString());

                double newCost = (currentCost / currentShares) * (currentShares - sharesToSold);
                double newShares = currentShares - sharesToSold;


                table.setValueAt(String.format("%.2f", newShares), row, 5);
                table.setValueAt(String.format("%.2f", newCost), row, 4);
                tradeRepository.makeTrade(customerID, stockSymbol, sharesToSold, currentPrice, "sell");

                //Update the new Available Cash
                double soldCash = sharesToSold * currentPrice;
                float currentCash = userRepository.getCashAvailable(customerID, customer);
                float newCash = currentCash + (float) soldCash;
                userRepository.updateCashAvailable(customerID, newCash, customer);
                float currentTotalInvested = userRepository.getTotalInvested(customerID, customer);
                float newTotalInvested = currentTotalInvested - (float) soldCash;
                if (newTotalInvested < 0) {
                    newTotalInvested = 0;
                }
                // sum the 5th column of each row in the table
                double totalCost = 0;
                for (int i = 0; i < table.getRowCount(); i++) {
                    totalCost += Double.parseDouble(table.getValueAt(i, 4).toString());
                }
                userRepository.updateTotalInvested(customerID, totalCost, customer);
                LBLCashAvailable.setText(String.format("%.2f", newCash));

                double newUnrealizedProfit = (currentPrice * newShares) - newCost;
                double currentRealizedProfit = userRepository.getRealizedProfitByStock(customerID, customer, stockSymbol);
                double newRealizedProfit = (currentPrice - (currentCost / currentShares)) * sharesToSold;
                double newTotalRP = currentRealizedProfit + newRealizedProfit;
                userRepository.updateUnrealizedProfitByStock(customerID, newUnrealizedProfit, customer, stockSymbol);
                userRepository.updateRealizedProfitByStock(customerID, newTotalRP, customer, stockSymbol);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid number of shares. Please enter a positive number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    /**
     * Handles Getting the Realized Profit by Customer ID
     * @param customerID
     * @return
     */
    public double getRealizedProfitByCustomerID(int customerID) {
        return userRepository.getRealizedProfitByCustomerID(customerID);
    }

    /**
     * Handles Getting the Unrealized Profit by Customer ID
     * @param customerID
     * @return
     */
    public double getUnRealizedProfitByCustomerID(int customerID) {
        return userRepository.getUnRealizedProfitByCustomerID(customerID);
    }


    /**
     * Handles setting Customer Object
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Handles Getting the Cash Available by Customer ID
     * @param customerID
     * @return
     */
    public float getCashAvailable(int customerID) {
        return userRepository.getCashAvailable(customerID, customer);
    }

    /**
     * Handles Getting the Total Invested by Customer ID
     * @param customerID
     * @return
     */
    public float getTotalInvested(int customerID) {
        return userRepository.getTotalInvested(customerID, customer);
    }

    /**
     * Handles Setting the Customer ID
     * @param customerID
     */
    public void setCustomerId(int customerID) {
        this.customerID = customerID;
    }


}
