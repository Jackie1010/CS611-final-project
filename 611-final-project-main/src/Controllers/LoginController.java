package Controllers;

import Models.Admin;
import Models.Customer;
import Models.User;
import Models.UserRepository;
import Views.Admin.AdminMainPage;
import Views.Customer.CustomerMainPage;
import Views.LoginPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {


    private ControllerFactory controllerFactory;
    private LoginPage loginPage;
    private JPasswordField passwordField;
    private JTextField usernameField;

    private UserRepository userRepository;


    /**
     * Constructor
     * @param userRepository
     * @param controllerFactory
     */
    public LoginController(UserRepository userRepository, ControllerFactory controllerFactory){
        this.controllerFactory = controllerFactory;
        this.userRepository = userRepository;
    }

    // Setter methods for the LoginPage, password and username fields
    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    /**
     * Setter for the usernameField
     * @param usernameField
     */
    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    /**
     * Setter for the loginPage
     * @param loginPage
     */
    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    /**
     * Action Listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Login Button Clicked");

        //Fetch and match the data for validity
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username == null || username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(loginPage, "Please enter your username and password",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userRepository.authenticate(username, password);
        if (user != null) {
            if (user instanceof Customer) {
                System.out.println("Customer");
                int customerID = userRepository.getUserId(username, "customer");
                if (userRepository.getCustomerApproved(customerID, (Customer) user)) {
                    controllerFactory.showCustomerMainPage((Customer) user, customerID, loginPage);
                } else {
                    JOptionPane.showMessageDialog(loginPage, "Your account is not approved yet",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else if (user instanceof Admin) {
                int adminID = userRepository.getUserId(username, "admin");
                System.out.println("Admin");
                controllerFactory.showAdminMainPage((Admin) user, adminID, loginPage);
            }
        } else {
            JOptionPane.showMessageDialog(loginPage, "Your combination of username and password is incorrect",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
