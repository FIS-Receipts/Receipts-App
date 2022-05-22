package com.fis.receiptsapp.models;

import java.util.ArrayList;
import java.util.Date;

public class Receipt {
    private int id;
    private int store_owner_id;
    private int customer_id;

    private ArrayList<Product> products;
    private Date date;
    private String customerName;
    private String storeName;
    private  StoreOwner.Store_type storeType;
    private int nrItems;
    private float amount;


    public Receipt() {}

    public Receipt(ArrayList<Product> products, StoreOwner storeOwner, Customer customer) {
        setProducts(products);
        setStore_owner_id(storeOwner.getId());
        setCustomer_id(customer.getId());

        date = new Date();

        setCustomerName(customer.getFirst_name() + " " + customer.getLast_name());
        setStoreName(storeOwner.getName());
        setStoreType(storeOwner.getStore_type());

        setNrItems(calculateNrItems());
        setAmount(calculateAmount());
    }

    private int calculateNrItems() {
        int cnt = 0;
        for (Product product : getProducts()) {
            cnt += product.getQuantity();
        }

        return cnt;
    }

    private float calculateAmount() {
        float total = 0;
        for (Product product : getProducts()) {
            total += product.getQuantity() * product.getPrice();
        }

        return total;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_owner_id() {
        return store_owner_id;
    }

    public void setStore_owner_id(int store_owner_id) {
        this.store_owner_id = store_owner_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public StoreOwner.Store_type getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreOwner.Store_type storeType) {
        this.storeType = storeType;
    }

    public int getNrItems() {
        return nrItems;
    }

    public void setNrItems(int nrItems) {
        this.nrItems = nrItems;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
