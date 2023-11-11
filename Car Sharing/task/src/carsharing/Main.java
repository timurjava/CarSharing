package carsharing;

import carsharing.services.MainMenu;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:C:\\Users\\tseu11cz\\IdeaProjects\\Account-Service-git\\Car Sharing\\Car Sharing\\task\\src\\carsharing\\db\\";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        String filename = Arrays.stream(args).reduce((name,arg) -> arg).isPresent() ? Arrays.stream(args).reduce((name,arg) -> arg).get(): "withoutname";
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL + filename);

            //STEP 3: Execute a query
            //System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   COMPANY  " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " NAME VARCHAR(255) UNIQUE  NOT NULL, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            //System.out.println("Created table in given database...");
            sql =  "CREATE TABLE   CAR  " +
                    "(id INTEGER AUTO_INCREMENT Primary Key, " +
                    " NAME VARCHAR(255) UNIQUE  NOT NULL, " +
                    " COMPANY_ID INTEGER NOT NULL , " +
                    " CONSTRAINT fk_comapny FOREIGN KEY (COMPANY_ID) " +
                    " REFERENCES COMPANY(ID)) ";
            // STEP 4: Clean-up environment
            stmt.executeUpdate(sql);

            sql =  "CREATE TABLE   CUSTOMER  " +
                    "(id INTEGER AUTO_INCREMENT Primary Key, " +
                    " NAME VARCHAR(255) UNIQUE  NOT NULL, " +
                    " RENTED_CAR_ID INTEGER , " +
                    " CONSTRAINT fk_customer FOREIGN KEY (RENTED_CAR_ID) " +
                    " REFERENCES CAR(ID)) ";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        //System.out.println("Goodbye!");

        MainMenu mainMenu = new MainMenu();
        mainMenu.start(DB_URL + filename);
    }
}
