package org.example;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Database {
    public static void createDatabase(){
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";

        /* SQL Query Statements */
        /*Creating Database*/
        String sql1 = "IF NOT EXISTS (SELECT NAME FROM master.dbo.sysdatabases WHERE NAME = N'GAME_ITEMS')\n" +
                "CREATE DATABASE GAME_ITEMS";

        /*Creating Tables*/
        //Users Table
        String dbUsers = "IF NOT EXISTS " +
                "(SELECT * FROM GAME_ITEMS.INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'Users') " +
                "CREATE TABLE GAME_ITEMS.dbo.Users(" +
                "UserID INTEGER NOT NULL IDENTITY (1, 1)," +
                "FirstName VARCHAR(30) NOT NULL, " +
                "Username VARCHAR(30) NOT NULL, " +
                "Password VARCHAR(30) NOT NULL, " +
                "ShippingAddress VARCHAR(100), " +
                "isAdmin BIT NOT NULL DEFAULT 0, " +
                "CONSTRAINT Users_PK PRIMARY KEY(UserID)" +
                ")";

        String seedAdmin = "INSERT INTO GAME_ITEMS.dbo.Users(FirstName, Username, Password, isAdmin)\n " +
                "VALUES('Ebube', 'oezeobi', 'theadmin', 1)";

        //Inventory Table
        String dbInventory = "IF NOT EXISTS " +
                "(SELECT * FROM GAME_ITEMS.INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'Inventory')" +
                "CREATE TABLE GAME_ITEMS.dbo.Inventory(" +
                "InventoryID INTEGER NOT NULL IDENTITY (1, 1), " +
                "ItemName VARCHAR(80) NOT NULL, " +
                "Description VARCHAR(300) NOT NULL, " +
                "Price NUMERIC(8, 2) NOT NULL, " +
                "CONSTRAINT Inventory_PK PRIMARY KEY(InventoryID)" +
                ")";

        //Shipping Type Table
        String dbShipping = "IF NOT EXISTS " +
                "(SELECT * FROM GAME_ITEMS.INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'ShippingType')" +
                "CREATE TABLE GAME_ITEMS.dbo.ShippingType(" +
                "ShippingTypeID INTEGER NOT NULL IDENTITY (1, 1), " +
                "ShippingName VARCHAR(60) NOT NULL, " +
                "Price NUMERIC(8, 2) NOT NULL, " +
                "CONSTRAINT ShippingType_PK PRIMARY KEY(ShippingTypeID)" +
                ")";

        //Sales Table
        String dbSales = "IF NOT EXISTS " +
                "(SELECT * FROM GAME_ITEMS.INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'Sales')" +
                "CREATE TABLE GAME_ITEMS.dbo.Sales(" +
                "UserID INTEGER NOT NULL, " +
                "InventoryID INTEGER NOT NULL, " +
                "ShippingTypeID INTEGER NOT NULL, " +
                "DateOfSale DATE NOT NULL, " +
                "Tax NUMERIC(8, 2) NOT NULL, " +
                "GrandTotal NUMERIC(8, 2) NOT NULL, " +
                "CONSTRAINT Sales_PK PRIMARY KEY(InventoryID), " +
                "CONSTRAINT Sales_FK FOREIGN KEY(UserID) REFERENCES USERS(UserID), " +
                "CONSTRAINT Sales_FK1 FOREIGN KEY(ShippingTypeID) REFERENCES ShippingType(ShippingTypeID) " +
                "ON UPDATE CASCADE" +
                ")";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
            System.out.println("Driver name: " + dm.getDriverName());
            System.out.println("Driver version: " + dm.getDriverVersion());
            System.out.println("Product name: " + dm.getDatabaseProductName());
            System.out.println("Product version: " + dm.getDatabaseProductVersion());
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(dbUsers);
            stmt.executeUpdate(seedAdmin);
            stmt.executeUpdate(dbInventory);
            stmt.executeUpdate(dbShipping);
            stmt.executeUpdate(dbSales);
        } catch (SQLException ex) {
            //noinspection CallToPrintStackTrace
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                //noinspection CallToPrintStackTrace
                ex.printStackTrace();
            }
        }
    }

    public static boolean signUp(String firstName, String userName, String password){
        boolean exists = false;
        String signUp = "IF NOT EXISTS ( SELECT Username FROM GAME_ITEMS.dbo.Users WHERE Username = '" + userName + "')\n" +
                "BEGIN\nINSERT INTO GAME_ITEMS.dbo.Users(FirstName, Username, Password)\n" +
                "VALUES('" + firstName + "', '" + userName + "', '" + password + "')\nEND";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            int rows = stmt.executeUpdate(signUp);
            String signUpExists = "IF EXISTS " +
                    "( SELECT 1 FROM GAME_ITEMS.dbo.Users WHERE Username = '" + userName + "')\n" +
                    "BEGIN\n" +
                    "    SELECT Username FROM GAME_ITEMS.dbo.Users\n" +
                    "    WHERE Username = '" + userName + "'" +
                    "END";
            exists = stmt.execute(signUpExists);
            if (exists && rows > 0){
                System.out.println("User Registered Successfully");
                System.out.println();
            }
            else if (exists){
                exists = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return exists;
    }

    public static boolean login(String userName, @NotNull String password){
        boolean loggedIn = false;
        String login = "IF EXISTS " +
                "( SELECT Username FROM GAME_ITEMS.dbo.Users WHERE Username = '" + userName + "')\n" +
                "BEGIN\n" +
                "    SELECT Password FROM GAME_ITEMS.dbo.Users\n" +
                "    WHERE Username = '" + userName + "'\n" +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            if(stmt.execute(login)){
                ResultSet rs = stmt.executeQuery(login);
                while (rs.next()){
                    if(password.equals(rs.getString("Password"))){
                        loggedIn = true;
                        System.out.println("Logged In Successfully");
                        System.out.println();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return loggedIn;
    }

    public static boolean addToInventory(String itemName, String description,double itemPrice){
        boolean present = false;
        String setItem = "IF NOT EXISTS " +
                "( SELECT ItemName FROM GAME_ITEMS.dbo.Inventory WHERE ItemName = '" + itemName + "')\n" +
                "BEGIN\n" +
                "    INSERT INTO GAME_ITEMS.dbo.Inventory(ItemName, Description, Price) " +
                "VALUES('" + itemName + "', '" + description + "', '" + itemPrice + "')\n" +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            int rows = stmt.executeUpdate(setItem);
            String setItemPresent = "IF EXISTS " +
                    "( SELECT 1 FROM GAME_ITEMS.dbo.Inventory WHERE ItemName = '" + itemName + "')\n" +
                    "BEGIN\n" +
                    "    SELECT ItemName FROM GAME_ITEMS.dbo.Inventory\n" +
                    "    WHERE ItemName = '" + itemName + "'\n" +
                    "END";
            present = stmt.execute(setItemPresent);
            if(present && rows > 0){
                System.out.println("Added Item to Inventory Successfully");
            }
            else if(present){
                present = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return present;
    }

    public static boolean addToShippingType(String shippingName, double shippingPrice){
        boolean present = false;
        String shipping = "IF NOT EXISTS " +
                "( SELECT 1 FROM GAME_ITEMS.dbo.ShippingType WHERE ShippingName = '" + shippingName + "')\n" +
                "BEGIN\n" +
                "    INSERT INTO GAME_ITEMS.dbo.ShippingType (ShippingName, Price) " +
                "VALUES('" + shippingName + "', '" + shippingPrice + "')\n" +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            int rows = stmt.executeUpdate(shipping);
            String shippingPresent = "IF EXISTS " +
                    "( SELECT 1 FROM GAME_ITEMS.dbo.ShippingType WHERE ShippingName = '" + shippingName + "')\n" +
                    "BEGIN\n" +
                    "    SELECT * FROM GAME_ITEMS.dbo.ShippingType\n" +
                    "    WHERE ShippingName = '" + shippingName + "' " +
                    "END";
            present = stmt.execute(shippingPresent);
            if(present && rows > 0){
                System.out.println("Added Shipping Type Successfully");
            }
            else if(present){
                present = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return present;
    }

    public static void addToSales(int userID, int inventoryID, int shippingID,
                                  String date, double tax, double grandTotal){
        String sales = "IF NOT EXISTS " +
                "( SELECT InventoryID FROM GAME_ITEMS.dbo.Sales WHERE InventoryID = " + inventoryID + " )\n" +
                "BEGIN\n" +
                "    INSERT INTO GAME_ITEMS.dbo.Sales " +
                "(UserID, InventoryID, ShippingTypeID, DateOfSale, Tax, GrandTotal) VALUES " +
                "(" + userID + ", " + inventoryID + ", " + shippingID + ", " +
                "'" + date + "', " + tax + ", " + grandTotal + ")\n" +
                "END";
        String removeItem = "IF EXISTS " +
                "( SELECT InventoryID FROM GAME_ITEMS.dbo.Inventory WHERE InventoryID = " + inventoryID + " )\n" +
                "BEGIN\n" +
                "    DELETE FROM GAME_ITEMS.dbo.Inventory\n" +
                "    WHERE InventoryID = " + inventoryID + " " +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            stmt.executeUpdate(sales);
            stmt.executeUpdate(removeItem);
            String salesExists = "IF EXISTS " +
                    "( SELECT 1 FROM GAME_ITEMS.dbo.Sales WHERE InventoryID = " + inventoryID + " )\n" +
                    "BEGIN\n" +
                    "    SELECT InventoryID FROM GAME_ITEMS.dbo.Sales\n" +
                    "    WHERE InventoryID = " + inventoryID + " " +
                    "END";
            String itemRemoved = "IF EXISTS " +
                    "( SELECT 1 FROM GAME_ITEMS.dbo.Inventory WHERE InventoryID = " + inventoryID + " )\n" +
                    "BEGIN\n" +
                    "    SELECT * FROM GAME_ITEMS.dbo.Inventory\n" +
                    "    WHERE InventoryID = " + inventoryID + " " +
                    "END";
            boolean exists = stmt.execute(salesExists);
            boolean notRemoved = stmt.execute(itemRemoved);
            if (exists && !notRemoved){
                System.out.println("Item added to Sales\nItem removed from Inventory");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void showInventory(){
        String inventory = "SELECT ItemName, Description, Price FROM GAME_ITEMS.dbo.Inventory";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            ResultSet rs = stmt.executeQuery(inventory);
            while (rs.next()){
                String itemName = rs.getString("ItemName");
                String description = rs.getString("Description");
                double itemPrice = rs.getDouble("Price");
                System.out.println("Name: "+ itemName +", Description: " + description + ", Item Price: " +  itemPrice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean searchInventory(String itemName){
        boolean present = false;
        String search = "IF EXISTS " +
                "( SELECT ItemName FROM GAME_ITEMS.dbo.Inventory WHERE ItemName = '" + itemName + "')\n" +
                "BEGIN\n" +
                "    SELECT ItemName, Description, Price FROM GAME_ITEMS.dbo.Inventory\n" +
                "    WHERE ItemName = '" + itemName + "' " +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            present = stmt.execute(search);
            if(present){
                ResultSet rs = stmt.executeQuery(search);
                while (rs.next()){
                    String description = rs.getString("Description");
                    double itemPrice = rs.getDouble("Price");
                    System.out.println("Name: " + itemName + ", Description:" +
                            " " + description + ", Item Price: $" +  itemPrice);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return present;
    }

    public static void showShipping(){
        String allShipping = "SELECT ShippingName, Price FROM GAME_ITEMS.dbo.ShippingType";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            ResultSet rs = stmt.executeQuery(allShipping);
            while (rs.next()){
                String shippingName = rs.getString("ShippingName");
                double shippingPrice = rs.getDouble("Price");
                System.out.println("Shipping Options\nName: " + shippingName + ", Price: " +  shippingPrice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static Users retrieveUser(String username){
        Users currentUser = new Users();
        String userInfo = "IF EXISTS " +
                "( SELECT 1 FROM GAME_ITEMS.dbo.Users WHERE Username = '" + username + "' )\n" +
                "BEGIN\n" +
                "    SELECT UserID, FirstName, Username, Password, ShippingAddress, isAdmin FROM GAME_ITEMS.dbo.Users\n" +
                "    WHERE Username = '" + username + "' " +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            ResultSet rs = stmt.executeQuery(userInfo);
            while(rs.next()){
                int id = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String password = rs.getString("Password");
                String shippingAddress = rs.getString("ShippingAddress");
                boolean isAdmin = rs.getBoolean("isAdmin");
                currentUser = new Users(id, firstName, username, password, shippingAddress, isAdmin);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return currentUser;
    }

    private static Inventory callItem(String itemName){
        Inventory currentItem = new Inventory();
        String itemInfo = "IF EXISTS " +
                "( SELECT 1 FROM GAME_ITEMS.dbo.Inventory WHERE ItemName = '" + itemName + "' )\n" +
                "BEGIN\n" +
                "    SELECT * FROM GAME_ITEMS.dbo.Inventory\n" +
                "    WHERE ItemName = '" + itemName + "' " +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            ResultSet rs = stmt.executeQuery(itemInfo);
            while (rs.next()){
                int id = rs.getInt("InventoryID");
                String description = rs.getString("Description");
                double itemPrice = rs.getDouble("Price");
                currentItem = new Inventory(id, itemName, description, itemPrice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return currentItem;
    }

    private static ShippingType chooseShipping(String shippingName){
        ShippingType currentShipping = new ShippingType();
        String shippingInfo = "IF EXISTS " +
                "( SELECT ShippingName FROM GAME_ITEMS.dbo.ShippingType WHERE ShippingName = '" + shippingName + "')\n" +
                "BEGIN\n" +
                "    SELECT * FROM GAME_ITEMS.dbo.ShippingType\n" +
                "    WHERE ShippingName = '" + shippingName + "'\n" +
                "END";
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://obie\\sqlexpress;portNumber=14337;" + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        String user = "sa";
        String pass = "password";
        try {
            conn = DriverManager.getConnection(dbURL, user, pass); Statement stmt = conn.createStatement();
            System.out.println("Connected");
            ResultSet rs = stmt.executeQuery(shippingInfo);
            while(rs.next()){
                int id = rs.getInt("ShippingTypeID");
                double shippingPrice = rs.getDouble("Price");
                currentShipping = new ShippingType(id, shippingName, shippingPrice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return currentShipping;
    }

    private static void addToCart(ArrayList<Inventory> present, String itemName){
        boolean available = searchInventory(itemName);
        boolean found = false;
        if (!available){
            System.out.println("Item not in Inventory");
        }
        else {
            Inventory currentItem = callItem(itemName);
            for (Inventory inventory: present) {
                if (inventory.getUniqueID() == currentItem.getUniqueID()){
                    System.out.println("Item already in cart");
                    found = true;
                    break;
                }
            }
            if (!found){
                present.add(currentItem);
                System.out.println("Item added to cart");
            }
        }
    }


    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        createDatabase();
        boolean loggedOut = true;
        boolean quitApplication = false;
        do{
            Users currentUser = new Users();
            while (loggedOut){
                System.out.print("Log In or Sign Up (L/S): ");
                char choice1 = myScan.next().toUpperCase().charAt(0);
                myScan.nextLine();
                while (choice1 != 'L' && choice1 != 'S'){
                    System.out.print("Log In or Sign Up (L/S): ");
                    choice1 = myScan.next().toUpperCase().charAt(0);
                    myScan.nextLine();
                }
                boolean exists = false;
                if(choice1 == 'S'){
                    System.out.print("First Name: ");
                    String firstName = myScan.nextLine();
                    System.out.print("Username: ");
                    String userName = myScan.nextLine();
                    System.out.print("Password: ");
                    String password = myScan.nextLine();
                    System.out.println(firstName + ", " + userName + ", " + password);
                    exists = signUp(firstName, userName, password);
                    if (!exists){
                        System.out.println("Username already exists");
                        break;
                    }
                    else {
                        loggedOut = false;
                        currentUser = retrieveUser(userName);
                    }
                }
                else{
                    System.out.print("Username: ");
                    String userName = myScan.nextLine();
                    System.out.print("Password: ");
                    String password = myScan.nextLine();
                    exists = login(userName, password);
                    if (!exists){
                        System.out.println("Login Failed! Wrong Username or Password!");
                        break;
                    }
                    else {
                        loggedOut = false;
                        currentUser = retrieveUser(userName);
                    }
                }
            }
            ArrayList<Inventory> stocked = new ArrayList<>();
            while (!loggedOut){
                int choice2;
                if (currentUser.isAdmin){
                    while (true){
                        try{
                            System.out.print("""
                        Options:
                        1) Show Inventory
                        2) Add To Cart
                        3) Show Cart
                        4) Buy/ Add to Sales
                        5) Log Out
                        6) Add to Inventory
                        7) Add Shipping Type
                        8) Promote User (Not Completed Yet)
                        
                        Choice: """);
                            choice2 = Integer.parseInt(myScan.nextLine());
                            if (choice2 > 0 && choice2 < 8){
                                break;
                            }

                        } catch (NumberFormatException e){
                            System.out.println("Invalid input. Please enter a valid number!");
                            System.out.println();
                        }
                    }
                }
                else {
                    while (true){
                        try{
                            System.out.print("""
                        Options:
                        1) Show Inventory
                        2) Add To Cart
                        3) Show Cart
                        4) Buy/ Add to Sales
                        5) Log Out
                        
                        Choice: """);
                            choice2 = Integer.parseInt(myScan.nextLine());
                            if (choice2 > 0 && choice2 < 6){
                                break;
                            }

                        } catch (NumberFormatException e){
                            System.out.println("Invalid input. Please enter a valid number!");
                            System.out.println();
                        }
                    }
                }
                if(choice2 == 1){
                    showInventory();
                    System.out.println();
                }
                else if(choice2 == 2){
                    System.out.print("Item Name: ");
                    String itemName = myScan.nextLine();
                    System.out.println(itemName);
                    addToCart(stocked, itemName);
                    System.out.println();
                }
                else if(choice2 == 3){
                    if(stocked.isEmpty()){
                        System.out.println("Cart is empty");
                        System.out.println();
                    }
                    else {
                        for (int i = 0; i < stocked.size(); i++){
                            System.out.println((i + 1) + ") Name: " + stocked.get(i).getItemName() + "," +
                                    " Description: " + stocked.get(i).getDescription() + "," +
                                    " Price: $" + stocked.get(i).getItemPrice());
                        }
                        System.out.println();
                    }
                }
                else if(choice2 == 4){
                    if(stocked.isEmpty()){
                        System.out.println("Cart is empty! Nothing to Buy");
                        System.out.println();
                    }
                    else {
                        showShipping();
                        System.out.print("Shipping Name: ");
                        String shippingName = myScan.nextLine();
                        ShippingType userShipping = chooseShipping(shippingName);
                        for (Inventory inventory : stocked) {
                            double tax = (0.06 * inventory.getItemPrice());
                            double grandTotal = (inventory.getItemPrice() + userShipping.getShippingPrice() + tax);
                            addToSales(currentUser.getUniqueID(), inventory.getUniqueID(),
                                    userShipping.getUniqueID(), time, tax, grandTotal);
                        }
                        stocked.clear();
                        System.out.println();
                    }
                }
                else if(choice2 == 5){
                    loggedOut = true;
                    System.out.print("Do you wish to quit (Y/N)? ");
                    char choice3 = myScan.next().toUpperCase().charAt(0);
                    myScan.nextLine();
                    while(choice3 != 'Y' && choice3 != 'N'){
                        System.out.print("Do you wish to quit (Y/N)? ");
                        choice3 = myScan.next().toUpperCase().charAt(0);
                        myScan.nextLine();
                    }
                    if(choice3 == 'N'){
                        quitApplication = true;
                    }
                }
                else if (choice2 == 6){
                    boolean added;
                    System.out.print("Item Name: ");
                    String itemName = myScan.nextLine();
                    System.out.print("Description: ");
                    String description = myScan.nextLine();
                    System.out.print("Item Price: $");
                    double price = myScan.nextDouble();
                    added = addToInventory(itemName, description, price);
                    myScan.nextLine();
                    if (!added){
                        System.out.println("Item already exists");
                        System.out.println();
                    }
                }
                else if(choice2 == 7){
                    boolean added;
                    System.out.print("Shipping Name: ");
                    String shippingName = myScan.nextLine();
                    System.out.print("Shipping Price: $");
                    double price = myScan.nextDouble();
                    added = addToShippingType(shippingName, price);
                    myScan.nextLine();
                    if (!added){
                        System.out.println("Shipping Type already exists");
                        System.out.println();
                    }
                }
            }
        }while (!quitApplication);
    }
}

class Users{
    public int uniqueID;
    public String firstName;
    public String userName;
    public String password;
    public String shippingAddress;
    public  boolean isAdmin;

    public Users(){
        uniqueID = 0;
        firstName = "";
        userName = "";
        password = "";
        shippingAddress = "";
        isAdmin = false;
    }

    public Users(int i, String f, String u, String p, String s, boolean a){
        uniqueID = i;
        firstName = f;
        userName = u;
        password = p;
        shippingAddress = s;
        isAdmin = a;
    }

    public int getUniqueID(){
        return uniqueID;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String f){
        firstName = f;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String u){
        userName = u;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String p){
        password = p;
    }

    public String getShippingAddress(){
        return shippingAddress;
    }

    public void setShippingAddress(String s){
        shippingAddress = s;
    }

    public boolean getAdmin(){
        return isAdmin;
    }

    public void setAdmin(boolean a){
        isAdmin = a;
    }
}

class Inventory{
    public int uniqueID;
    public String itemName;
    public String description;
    public double itemPrice;

    public Inventory(){
        uniqueID = 0;
        itemName = "";
        description = "";
        itemPrice = 0.00;
    }

    public Inventory(int u, String n, String d, double p){
        uniqueID = u;
        itemName = n;
        description = d;
        itemPrice = p;
    }

    public int getUniqueID(){
        return uniqueID;
    }
    public String getItemName(){
        return itemName;
    }

    public void setItemName(String i){
        itemName = i;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String d){
        description = d;
    }

    public double getItemPrice(){
        return itemPrice;
    }

    public void setItemPrice(double p) {
        itemPrice = p;
    }
}

class ShippingType{
    public int uniqueID;
    public String shippingName;
    public double shippingPrice;

    public ShippingType(){
        uniqueID = 0;
        shippingName = "";
        shippingPrice = 0.00;
    }

    public ShippingType(int u, String s, double p){
        uniqueID = u;
        shippingName = s;
        shippingPrice = p;
    }

    public int getUniqueID(){return uniqueID;}

    public String getShippingName(){
        return shippingName;
    }

    public void setShippingName(String n){
        shippingName = n;
    }

    public double getShippingPrice(){
        return shippingPrice;
    }

    public void setShippingPrice(double p) {
        shippingPrice = p;
    }
}

class Sales{
    public double tax;
    public double grandTotal;

    public Sales(double t, double g){
        tax =  t;
        grandTotal = g;
    }

    public double getTax() {
        return tax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }
}
