package Models;

public class Customer extends User{

    private String email;
    private boolean approved;
    private boolean can_trade_options;
    private float total_deposit;
    private float total_cash;
    private float total_invested;

    /**
     * Constructor:
     * @param username
     * @param password
     * @param email
     */
    public Customer(String username, String password, String email) {
        super(username, password);
        this.email = email;
        this.approved = false;
        this.can_trade_options = false;
        this.total_deposit = 100_000.0f;
        this.total_cash = 100_000.0f;
        this.total_invested = 0.0f;
    }

    /**
     * Method to get email
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Method to get total invested
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to set approved
     * @param approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Method to get approved
     * @return
     */
    public boolean getApproved() {
        return this.approved;
    }

    /**
     * Method to set can trade options
     * @param can_trade_options
     */
    public void setCanTradeOptions(boolean can_trade_options) {
        this.can_trade_options = can_trade_options;
    }

    /**
     * Method to get can trade options
     * @return
     */
    public boolean getCanTradeOptions() {
        return this.can_trade_options;
    }

    /**
     * Method to set total deposit
     * @param total_deposit
     */
    public void setTotalDeposit(float total_deposit) {
        this.total_deposit = total_deposit;
    }

    /**
     * Method to get total deposit
     * @return
     */
    public float getTotalDeposit() {
        return this.total_deposit;
    }

    /**
     * Method to set total cash
     *
     * @param total_cash
     */
    public void setTotalCash(float total_cash) {
        this.total_cash = total_cash;
    }

    /**
     * Method to get total cash
     * @return
     */
    public float getTotalCash() {
        return this.total_cash;
    }

    /**
     * Method to set total invested
     * @param total_invested
     */
    public void setTotalInvested(float total_invested) {
        this.total_invested = total_invested;
    }

    /**
     * Method to get total invested
     * @return
     */
    public float getTotalInvested() {
        return this.total_invested;
    }
}
