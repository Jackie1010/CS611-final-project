# 611-final-project

# Team Members

| Student Name | Student Email | Student BUID |
|---------|--------------|-----------|
| Zeqi Wang | zw100107@bu.edu | U91298632 |
| Vineet Raju | vineetr@bu.edu | U99140386 |
| Zhou Ye | yezhou@bu.edu | U22674694 |


# Instructions for running the program
1. Navigate to the root directory (the directory containing the src folder, not within the src folder itself)
2. Run this command to compile: `javac -cp . -d bin **/*.java`
3. Run this command to start the program: `java -cp bin Main`

## Adding External library to your program

In the folder "lib" there are manually added .jar files to support extra functions

To add them to your library, please follow the following instructions

### For different software IDE

Eclipse: right click the jar file, Build path -> Add to Build Path

IntelliJ: right click the jar file, Add as Library 


# Description of All Files
### File Structure
```shell
❯ tree .
.
├── Controllers 
│   ├── AdminController.java: Controller which defines functionalities common to other Admin Controllers.
│   ├── AdminHomeController.java: Controller for Admin Home Page. This controller extends AdminController.
│   ├── AdminMarketController.java: Controller for Admin Stock Market Page. This controller extends AdminController.
│   ├── AllUserTradingHistoryController.java: Controller which generates a report of all trades made by all customers for the admin.
│   ├── ControllerFactory.java: Controller Factory that manages the creation of all controllers and handles passing controller dependencies between them.
│   ├── CustomerController.java: Controller which defines common functionalities for all Customer Controllers.
│   ├── CustomerHomeController.java: Controller the Customer Home Page. This controller extends CustomerController.
│   ├── CustomerStockAccountController.java: Controller for the Customer Stock Account Page. This controller extends CustomerController.
│   ├── CustomerStockMarketActionMenuController.java: Controller for the Customer Stock Market Action Menu. This controller extends CustomerController.
│   ├── CustomerStockMarketController.java: Controller for the Customer Stock Market Page. This controller extends CustomerController.
│   ├── CustomerTradingHistoryController.java: Controller which generates a report of all trades made by a customer for the customer.
│   ├── LoginController.java: Controller for the Login Page.
│   ├── Searchable.java: Interface that any controller which implements search functionality must implement. Provides a base functionality for searching.
│   └── SignupController.java: Controller for the Signup Page.
├── Data
│   └── snp_constituents.csv: Data file with SNP 500 stocks. 
├── Database
│   ├── Connect.java: Class that implements the Singleton pattern to ensure that only one connection to the database is ever made.
│   ├── CreateTables.java: Class that creates the tables in the database and the default admin user.
│   ├── GetFromDB.java: 
│   ├── Services: This folder that defines services which actually excecute queries on the database.
│   │   ├── AccountService.java: Service that handles all queries related to the Customer account.
│   │   ├── AdminAccountService.java: Service that handles all queries related to the Admin account and Admin realted permissions for customer accounts that an individual customer should not have access to.
│   │   ├── AdminStockMarketService.java: Service that handles all queries related to the Admin Stock Market and Admin realted permissions for customer accounts that an individual customer should not have access to.
│   │   ├── CustomerStockMarketService.java: Service that handles all queries related to the Customer Stock Market.
│   │   ├── NotificationService.java: Service that handles all queries related to the Notification table.
│   │   ├── Service.java: Interface that all services must implement.
│   │   ├── StockMarketService.java: Service that handles all queries related to the Stock Market that are common to both the Admin and Customer.
│   │   ├── TradesService.java: Service that handles all queries related to the Trades table.
│   │   └── TradingAccountService.java: Service that handles all queries related to the Trading Account.
│   ├── Testing
│   │   ├── AccountServiceTesting.java: Tests for the AccountService.
│   │   └── StockMarketServiceTesting.java: Tests for the StockMarketService.
│   ├── db.db: The database file.
│   └── schema.sql: The schema for the database.
├── Main.java: Main class that starts the program.
├── Models: This folder contains all the models for the program.
│   ├── Admin.java: Model for the Admin.
│   ├── AdminRepository.java: Repository for the Admin model that provides high level abstractions that the controller can use to get requisite information related to the Admin.
│   ├── AdminStockRepository.java: Repository for the Admin Stock model that provides high level abstractions that the controller can use to get requisite information related to the Admin Stock.
│   ├── BuyStrategy.java: Interface that all buy strategies must implement.
│   ├── Customer.java: Model for the Customer.
│   ├── Observer.java: Interface that all observers must implement.
│   ├── SellStrategy.java: Interface that all sell strategies must implement.
│   ├── StockRepository.java: Repository for the Stock model that provides high level abstractions that the controller can use to get requisite information related to the Stock.
│   ├── Subject.java: Interface that all subjects must implement.
│   ├── Trade.java: Model for the Trade.
│   ├── TradeRepository.java: Repository for the Trade model that provides high level abstractions that the controller can use to get requisite information related to the Trade.
│   ├── User.java: Model for the User.
│   ├── UserRepository.java: Repository for the User model that provides high level abstractions that the controller can use to get requisite information related to the User.
│   └── UserTradeStrategy.java: Interface that all user trade strategies must implement.
├── Parsers
│   ├── CSVParser.java: Class that parses CSV files.
│   └── Parser.java: Interface that all parsers must implement.
└── Views
    ├── Admin
    │   ├── ALLCustomerReport.java: View for the Admin All Customer Report.
    │   ├── AdminHomeCard.java: View for the Admin Home Page.
    │   ├── AdminMainPage.java: View for the Admin Main Page.
    │   ├── AdminStockMarketCard.java: View for the Admin Stock Market Page.
    │   ├── AdminStockMarketMenu.java: View for the Admin Stock Market Menu.
    │   ├── CurrentCustomerMenu.java: View for the Admin Current Customer Menu.
    │   └── TableCellRenderers: This folder contains all the table cell renderers for the Admin.
    │       ├── CurrentCustomerTableCellRenderer.java: Table cell renderer for the Current Customer Menu.
    │       └── NewCustomerTableCellRenderer.java: Table cell renderer for the New Customer Menu.
    ├── Customer
    │   ├── CustomerHomeCard.java: View for the Customer Home Page.
    │   ├── CustomerMainPage.java: View for the Customer Main Page.
    │   ├── CustomerStockAccountCard.java: View for the Customer Stock Account Page.
    │   ├── CustomerStockMarketCard.java: View for the Customer Stock Market Page.
    │   ├── CustomerTradingAccountReport.java: View for the Customer Trading Account Report.
    │   ├── HomeAccountMenu.java: View for the Customer Home Account Menu.
    │   ├── StockAccountActionMenu.java: View for the Customer Stock Account Action Menu.
    │   ├── StockMarketActionMenu.java: View for the Customer Stock Market Action Menu.
    │   └── TableCellRenderers
    │       ├── CustomerHomeTableCellRenderer.java: Table cell renderer for the Customer Home Page.
    │       ├── CustomerReportCellRenderer.java: Table cell renderer for the Customer Trading Account Report.
    │       ├── StockMarketTableCellRenderer.java: Table cell renderer for the Customer Stock Market Page.
    │       └── TradeAccountTableCellRenderer.java: Table cell renderer for the Customer Stock Account Page.
    ├── LoginPage.java: View for the Login Page.
    └── SignUpPage.java: View for the Signup Page.
    └── StockGraphApplication.java: View for the Stock Graph Popup display.
    └── StockGraphPanel.java: View for the Stock Graph Panel.
```


### UML Diagrams

We have two UML diagrams: one with the just the classes and the high level relationships with them. But this does not fully show the architecture of the program since it does not show the relationships between the controllers and the views. So we have another UML diagram that shows the architecture of the program with the controllers and the views.


## Reset DB

To reset the DB, you can delete the `db.db` file and re-run the program. A fresh db will be created. 

# Bonus features
1. Extraordinary UI Design and User Experience. 
2. Ability to display stock price history for each stock. 
3. Customers can see their trading history. 
4. Search implementation for tables, which makes UI navigation more efficient.
5. Stock Price History graphing for each stock.
6. Model-View-Controller Deisgn Paradigm for better code organization, maintainability and separation of concerns.