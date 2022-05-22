package com.fis.receiptsapp.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Receipt {
    private int id;
    private int store_owner_id;
    private int customer_id;

    private Date date;
    private String customerName;
    private String storeName;
    private  StoreOwner.Store_type storeType;
    private List<Product> products;
    private int nrItems;
    private float amount;

    public Receipt() {}

    public Receipt(List<Product> products, StoreOwner storeOwner, Customer customer) {
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

    public String getJSONString() {
        JSONObject jo = new JSONObject();

        JSONArray ja = new JSONArray(products);

        jo.put("id", getId());
        jo.put("store_owner_id", getStore_owner_id());
        jo.put("customer_id", getCustomer_id());

        SimpleDateFormat ft = new SimpleDateFormat("dd.mm.yyyy");
        jo.put("date", ft.format(getDate()));
        jo.put("customerName", getCustomerName());
        jo.put("storeName", getStoreName());
        jo.put("storeType", getStoreType());
        jo.put("products", getProducts());

        jo.put("nrItems", getNrItems());
        jo.put("amount", getAmount());

        return jo.toString();
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
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
