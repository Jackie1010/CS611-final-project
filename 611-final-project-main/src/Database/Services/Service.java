package Database.Services;

import Database.Connect;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Service {
    /*
     * Execute the service
     * @param type: the type of service to execute (this can be Update, Create, Delete)
     * @return: true if the service was executed successfully, false otherwise
     */
    default ResultSet executeService(String sql, String type) {
        if (type.equals("select")) {
            try {
                System.out.println(sql);
                ResultSet rs =  Connect.getConnection().createStatement().executeQuery(sql);
                System.out.println("Query executed successfully");
                return rs;
            } catch (Exception e) {
                System.out.println("Error executing SELECT query");
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            try {
                System.out.println(sql);
                Connect.getConnection().createStatement().executeUpdate(sql);
                System.out.println("Query executed successfully");
                return null;
            } catch (Exception e) {
                System.out.println("Error executing insert/update query");
                System.out.println(e.getMessage());
                return null;
            }
        }
    }
}
