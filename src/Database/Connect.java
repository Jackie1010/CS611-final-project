/**
 * Establishes a connection to the sqlite3 database
 * After the connection is established, the database is ready to be queried
 */

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static Connection conn = null;

    /**
     * Constructor:
     * Utilizes the singleton pattern to ensure only one database connection exists
     */
    private Connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:src/Database/db.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            System.out.println("Connection object value: " + conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        finally {
//            try {
//                if (conn != null) {
//                    System.out.println("Closing DB connection");
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
    }

    /**
     * Public facing method to allow one to access the database connection
     */
    public static Connection getConnection() {
        if (conn == null) {
            new Connect();
        }
        return conn;
    }
}
