package com.fis.receiptsapp.models;

import java.sql.ResultSet;

public class Account {
    public enum Account_type {
        customer,
        store_owner
    }

    private int id;
    private String username;
    private String password;
    private Account_type account_type;

    public Account() {}

    public Account(int id, String username, String password, Account_type account_type) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setAccount_type(account_type);
    }

    public Account(ResultSet rs) {
        try {
            setId(rs.getInt("id"));
            setUsername(rs.getString("username"));
            setPassword(rs.getString("password"));
            setAccount_type(Account_type.valueOf(rs.getString("account_type")));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount_type(Account_type account_type) {
        this.account_type = account_type;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account_type getAccount_type() {
        return account_type;
    }
}
