package Database.Testing;

import Database.Services.AccountService;

import java.sql.Connection;

public class AccountServiceTesting {

    private Connection conn;

    // Constructor
    public AccountServiceTesting(Connection conn) {
        this.conn = conn;
    }

    public void testAccountCreation() {
        //TESTING: User account creation
        String first_name_1 = "Dom";
        String last_name_1 = "Mag";
        String email_1 = "dombitch";
        String user_password_1 = "password";
        String username_1 = "dom";
        AccountService accountService = new AccountService(conn);
        System.out.println("-------------First Creating User--------------------------");
        accountService.handleAccountCreation(first_name_1, last_name_1, email_1, user_password_1, username_1);
        System.out.println("-------------Checking If User was Created-----------------");
        accountService.checkIfUserExists(email_1);
        System.out.println("-------------Attempting to create User Again--------------");
        accountService.handleAccountCreation(first_name_1, last_name_1, email_1, user_password_1, username_1);
        String first_name_2 = "Dani";
        String last_name_2 = "Melc";
        String email_2 = "danibitch@bu.edu";
        String user_password_2 = "password";
        String username_2 = "dani";
        System.out.println("-------------Creating Second User-------------------------");
        accountService.handleAccountCreation(first_name_2, last_name_2, email_2, user_password_2, username_2);
        System.out.println("-------------Checking If Second User was Created----------");
        accountService.checkIfUserExists(email_2);
        System.out.println("-------------Attempting to create User Again--------------");
        accountService.deleteUser(email_2);
        accountService.handleAccountCreation(first_name_2, last_name_2, email_2, user_password_2, username_2);
    }
}
