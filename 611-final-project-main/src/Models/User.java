package Models;

import Database.Services.AccountService;

import java.sql.Connection;

public class User {

    // Common properties across all users
    private String username;
    private String password;

    /*
     * Constructor:
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*
     * Getters and setters:
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Method to get password
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Method to set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to set password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Method to check if username is valid
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
