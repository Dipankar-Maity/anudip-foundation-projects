package com.ghs.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    // Database URL for ghs_db
    private static final String URL = "jdbc:mysql://localhost:3306/ghs_db";
    private static final String USER = "root"; 
    private static final String PASS = "Your_Password";

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Loading the Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed! See details below:");
            e.printStackTrace();
        }
        return con;
    }

    // Quick test to verify the link
    public static void main(String[] args) {
        if (getConnection() != null) {
            System.out.println("Successfully connected to ghs_db!");
        }
    }
}