package com.fis.receiptsapp.models;

import java.sql.ResultSet;

public class StoreOwner extends Account{

    public enum Store_type {
        food,
        clothes,
        gas,
        other
    }

    private String cui;
    private String name;
    private Store_type store_type;

    public StoreOwner() {}

    public StoreOwner(int id, String username, String password, String cui, String name, Store_type store_type) {
        super(id, username, password, Account_type.store_owner);
        setCui(cui);
        setName(name);
        setStore_type(store_type);
    }

    public StoreOwner(ResultSet rs) {
        super(rs);

        try {
            setCui(rs.getString("cui"));
            setName(rs.getString("name"));
            setStore_type(Store_type.valueOf(rs.getString("store_type")));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store_type getStore_type() {
        return store_type;
    }

    public void setStore_type(Store_type store_type) {
        this.store_type = store_type;
    }
}
