package com.fis.receiptsapp.models;

public class Product {

    private int id;
    private int store_owners_id;
    private String name;
    private String brand;
    private String quota;
    private float price;
    private int quantity;

    public Product() {}

    public Product(int id, int store_owners_id, String name, String brand, String quota, float price) {
        setId(id);
        setStore_owners_id(store_owners_id);
        setName(name);
        setBrand(brand);
        setQuota(quota);
        setPrice(price);
    }

    public Product(int id, int store_owners_id, String name, String brand, String quota, float price, int quantity) {
        setId(id);
        setStore_owners_id(store_owners_id);
        setName(name);
        setBrand(brand);
        setQuota(quota);
        setPrice(price);
        setQuantity(quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_owners_id() {
        return store_owners_id;
    }

    public void setStore_owners_id(int store_owners_id) {
        this.store_owners_id = store_owners_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
