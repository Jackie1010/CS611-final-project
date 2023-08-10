### File Descriptions:
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

### Object Oriented Design: 

### Design Patterns:
1. Singleton Pattern: Connect.java implements the Singleton pattern, which ensures that only one instance of the database connection is alive at any given point in time. 
   This design pattern is used to manage database connections efficiently and reduce the overhead associated with opening and closing multiple connections.
2. Observer Pattern: 

    a. Java Swing: Java Swing components are used for creating the graphical user interface. Swing components follow the Observer pattern, in which components register as listeners for events (e.g., button clicks) and are notified when events occur. This allows for a clean separation of concerns between the user interface and the underlying logic.

    b. Notification System: The Observer pattern is also employed for the notification system, where the admin can send notifications to customers. The admin acts as the subject, while customers are observers. When the admin sends a notification, all subscribed customers are notified.

3. Strategy Pattern: The Strategy pattern is used for implementing buy and sell actions in the application. BuyStrategy.java and SellStrategy.java encapsulate the specific buying and selling algorithms. 
   This allows the application to switch between different algorithms for buying and selling stocks without modifying the code that uses these strategies. 
   It also promotes code reusability, modularity and abstracts away the details of the algorithms from the rest of the application.
4. Model-View-Controller: MVC allows for appropriate separation of concerns.

    a. Model: The Models folder contains classes representing the application's domain objects, such as Customer, Admin, and Trade. These classes are responsible for managing the application's data and business logic.

    b. View: The Views folder contains classes responsible for displaying the user interface. These classes are organized into subfolders for Admin and Customer views. Each view class renders a specific part of the application's UI, such as LoginPage.java or SignUpPage.java.

    c. Controller: The Controllers folder contains classes responsible for handling user input and coordinating the Model and View components. Each controller class is responsible for a specific part of the application, such as LoginController.java or SignupController.java.


### Extensibility, Resuability and Scalability:
(i). Extensibility: The application's architecture facilitates easy extension through modular folder structure, design patterns, service layer, database abstraction, and customizable UI components. These factors allow developers to quickly and easily add new features, modify existing functionality, or adapt the application to new requirements without causing disruptions or requiring significant refactoring.

(ii). Reusability: The application promotes code reusability through its modular design, the use of well-established design patterns, and abstraction layers. By organizing code into distinct, purpose-driven directories and employing patterns like Singleton, Observer, Strategy, and Model-View-Controller (MVC), the application encourages the development of reusable components that can be shared across different parts of the application or even across multiple projects. For example, the Strategy pattern enables the reuse of buying and selling algorithms, while the Observer pattern facilitates the reuse of event listeners and notification systems.

(iii). Scalability:

    (1). Database Abstraction: The database-related classes (e.g., Connect.java and GetFromDB.java) provide an abstraction layer for interacting with the underlying database. This abstraction makes it easier to switch to a different database management system (DBMS) or implement features like connection pooling, caching, or load balancing, which can help scale the application to handle more users or increased loads.
    (2). Service Layer: The Service layer (found in the Database/Services folder) centralizes the application's core business logic, allowing for the efficient management of resources and making it easier to optimize or scale the application as needed. By abstracting the core logic into a separate layer, developers can focus on performance improvements or scaling strategies without affecting the Controllers or Views directly.
    (3). Modular Design: The modular design of the application, with clear separation of concerns, allows for individual components to be scaled or optimized as needed. This makes it easier to identify and address performance bottlenecks, ensuring that the application can handle increased loads or user base without sacrificing performance or usability.
        One such example, searchable interface, abstracts away search logic so any component can easily use the search functionality. This allows for easy extensibility and reusability of the search functionality.
    

### Explanation of GUI, backend relationship:
As described earlier, The Model-View-Controller (MVC) pattern provides a clear separation of concerns between the graphical user interface (GUI) and backend services. This separation allows the application to maintain a clean and efficient structure, where the GUI focuses on presenting data and capturing user input, while backend services handle data processing and storage.

For example, consider the interaction between the CustomerMainPage.java view and the underlying database when a customer logs in. When the user enters their credentials in the LoginPage.java view and clicks the login button, the LoginController.java captures the input and validates the user's identity. If the credentials are correct, the LoginController.java retrieves the user's information from the database through the UserRepository.java and CustomerRepository.java models. The UserRepository.java and CustomerRepository.java models interact with the database via the Service layer, specifically the AccountService.java class, which executes queries to the DB.

Once the customer's data is retrieved, the LoginController.java initializes the CustomerMainPage.java view, which displays the customer's home page with relevant information, such as their portfolio and recent trades. This end-to-end example demonstrates how the GUI and backend services interact in the MVC pattern, ensuring a clean separation of concerns and a streamlined user experience.

### Object Model:
a. User: The User.java class serves as the base class for both Admin and Customer classes. It encapsulates common properties, such as username, password, and email. This class provides a clean abstraction for the application's users, from specific types of customers can be further implemented. We currently have two types of users: Admin and Customer.

b. Admin: The Admin.java class extends the User class. Admins have elevated privileges and can manage and edit stocks, view customer reports, and send notifications.

c. Customer: The Customer.java class extends the User class and represents a customer who can trade stocks, view their portfolio, and receive notifications. This class contains additional properties, such as balance and trading history.

d. Trade: The Trade.java class represents a single trade made by a customer, including details like the stock symbol, quantity, price, and trade type (buy or sell). This class is used to store and manage the trading history of customers.

e. BuyStrategy and SellStrategy: These classes encapsulate the algorithms for buying and selling stocks. They implement a strategy pattern, enabling the application to switch between different trading algorithms easily.

f. Observer and Subject: These interfaces enable any pub-sub system, which can be added by implementing these interfaces. We have implemented this for the notification system. Admin classes act as subjects, while Customer classes are observers, receiving notifications when the admin sends them.

g. Repositories: The UserRepository.java, AdminRepository.java, CustomerRepository.java, StockRepository.java, and TradeRepository.java classes serve as repositories for their respective domain objects. These classes manage the data access and storage for the objects, abstracting away the underlying database operations by interacting with the Database service classes.

These models enable the application to maintain a clean separation of concerns between the GUI and backend services. The GUI focuses on presenting data and capturing user input, while backend services handle data processing and storage.
This structure also allows for easy extensibility, reusability, and scalability.