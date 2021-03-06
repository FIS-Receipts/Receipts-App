package com.fis.receiptsapp.models;

import java.sql.ResultSet;

public class Customer extends Account{

    private String first_name;
    private String last_name;
    private String telephone;

    public Customer() {}

    public Customer(int id, String username, String password, String first_name, String last_name, String telephone) {
        super(id, username, password, Account_type.customer);
        setFirst_name(first_name);
        setLast_name(last_name);
        setTelephone(telephone);
    }

    public Customer(ResultSet rs) {
        super(rs);
        try {
            setFirst_name(rs.getString("first_name"));
            setLast_name(rs.getString("last_name"));
            setTelephone(rs.getString("telephone"));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
