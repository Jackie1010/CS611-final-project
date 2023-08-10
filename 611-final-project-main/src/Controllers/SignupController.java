package Controllers;

import Database.Services.AccountService;
import Models.User;
import Models.UserRepository;
import Views.SignUpPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupController implements ActionListener {


    private SignUpPage signUpPage;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;

    private UserRepository userRepository;


    /*
     * Constructor
     */
    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Setter methods for the SignUpPage, password and username fields
     */
    public void setSignUpPage(SignUpPage signUpPage) {
        this.signUpPage = signUpPage;
    }

    /**
     * Setter for the usernameField
     * @param usernameField
     */
    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    /**
     * Setter for the passwordField
     * @param passwordField
     */
    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    /**
     * Setter for the confirmPasswordField
     * @param confirmPasswordField
     */
    public void setConfirmPasswordField(JPasswordField confirmPasswordField) {
        this.confirmPasswordField = confirmPasswordField;
    }

    /**
     * Setter for the firstNameField
     * @param firstNameField
     */
    public void setFirstNameField(JTextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    /**
     * Setter for the lastNameField
     * @param lastNameField
     */
    public void setLastNameField(JTextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    /**
     * Setter for the emailField
     * @param emailField
     */
    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    /**
     * Action Listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // All values:
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String username = usernameField.getText();
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        // Make sure none of these fields cannot be empty
        if (password.equals("") || confirmPassword.equals("") || username.equals("") ||
                email.equals("") || firstName.equals("") || lastName.equals("")) {
            JOptionPane.showMessageDialog(signUpPage, "Please fill all the fields",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // first check if the password and the confirm password fields match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(signUpPage, "Passwords do not match",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // check if the username is already taken
        if (userRepository.checkIfUserExists(username) == 1) {
            JOptionPane.showMessageDialog(signUpPage, "Username already taken",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // check if the email is already taken
        if (userRepository.checkIfEmailExists(email) == 1) {
            JOptionPane.showMessageDialog(signUpPage, "Email already taken",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Password entered correctly, username and email available, try to create the account
        int res = userRepository.handleAccountCreation(firstName, lastName, email, password, username);
        switch (res) {
            case -1:
                JOptionPane.showMessageDialog(signUpPage, "Error creating account",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                break;
            case 0:
                JOptionPane.showMessageDialog(signUpPage, "Account Created. Waiting for admin approval!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                signUpPage.dispose();
                break;
            case 1:
                JOptionPane.showMessageDialog(signUpPage, "Account already exists",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                signUpPage.dispose();
                break;
        }
        return;
    }
}
