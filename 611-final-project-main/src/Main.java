import Controllers.*;
import Database.Connect;
import Database.CreateTables;
import Database.Services.*;
import Models.*;
import Views.LoginPage;

import javax.swing.*;
import java.sql.Connection;

public class Main {

    /**
     * Method to run the app
     * @param args
     */
    public static void main(String[] args) {


        RunApp runApp = new RunApp();
        runApp.runApp();

    }
}
