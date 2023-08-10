package Database.Services;

import java.sql.Connection;
import java.sql.ResultSet;

public class NotificationService implements Service {

    private Connection conn;

    /**
     * Constructor:
     */
    public NotificationService(Connection conn) {
        this.conn = conn;
    }


    /**
     * Method to put notification for customer in database
     */
    public void putNotificationForCustomer(int customerID, String message) {
        // Insert customerID and message into notifications table
        String sql = "INSERT INTO notifications (customer_id, message) VALUES (" + customerID + ", '" + message + "');";
        executeService(sql, "insert");
    }

    /**
     * Method to get notifications for customer
     * @param customerID
     * @return
     */
    public ResultSet getNotifications(int customerID) {
        // where customer_id = customerID and is_read = false;
        String sql = "SELECT * FROM notifications WHERE customer_id = " + customerID + " AND is_read = false;";
        return executeService(sql, "select");
    }

    /**
     * Method to mark notification as read
     * @param customerID
     * @param notification
     * @return
     */
    public ResultSet markNotificationAsRead(int customerID, String notification) {
        // where customer_id = customerID and message = notification;
        String sql = "UPDATE notifications SET is_read = true WHERE customer_id = " + customerID + " AND message = '" + notification + "';";
        return executeService(sql, "update");
    }
}
