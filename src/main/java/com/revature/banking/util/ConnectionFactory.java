package com.revature.banking.util;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//est. attributes
public class ConnectionFactory {
    private static final ConnectionFactory connectionFactory = new ConnectionFactory(); // instead Eager Singleton
    //eager just means instead of waiting for the constructor to be called we can just say new connection factory.
    private Properties prop = new Properties();
    private ConnectionFactory(){
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            prop.load(loader.getResourceAsStream("db.properties"));// t's looking on top of our thread and searching down the line for wherever this db properties file pops up.
        } catch (IOException e) {
            throw new RuntimeException(e);// TODO why did i get runtime? e.printstacktrace();
        }
    }


    static {
        // Reflections are just viewing a class
        try {
            Class.forName("org.postgresql.Driver");// class loading is making sure that we have driver we are looking for
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//method
    //it is static method because we can't make an object anywhere else in memory Except for within this own class,
    public static ConnectionFactory getInstance(){


        return connectionFactory;
    }

public Connection getConnection() {
    Connection conn = null;
    String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank"; // default url will connect you to public
    String user = "postgres";
    String password = "Tukulii12345#";
    try {
        conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return conn;

}

}
// This connection variable is inside this try catch and this is known or the tribe block in particular that tribe block means that this.
// Variable is scoped it's in the block scope of the try.
//So remember variables have scopes and these scopes can be contained within these blocks,
// much like this variable is actually because it's static it's part of the classes scope.
//If I had another variable in here, which we will shortly that isn't.
//static it's part of the instance scope.
// Then anytime we make any sort of method anything within this method within these parentheses of the method
// method scope anytime you see any curly braces block scope.
//So realize about how the scopes are working and keep in mind, because that's how we can set up variables,