package com.fis.receiptsapp.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseController {
    private static final DatabaseController controller = new DatabaseController();

    private static final String url = "jdbc:mysql://localhost:3306/receipts_app_db";
    private static final String username = "FIS";
    private static final String password = "FIS";

    private Connection connection;


    private DatabaseController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        startConnection();
    }

    public static DatabaseController getInstance() {
        return  controller;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void startConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void  resetConnection() {
        closeConnection();
        startConnection();
    }
}
