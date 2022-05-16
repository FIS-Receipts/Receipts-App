package com.fis.receiptsapp.models;
import  java.sql.*;

public class ConnectionTest {


    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/receipts_app_db", "FIS", "FIS");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from accounts");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("username") + " " + rs.getString("password") + " " + rs.getString("type"));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
