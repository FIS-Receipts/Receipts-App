package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.models.Product;
import com.fis.receiptsapp.models.StoreOwner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ManageProductDatabaseController extends SceneEssentials implements Initializable {

    @FXML
    private TextField tf_product_name;
    @FXML
    private TextField tf_brand;
    @FXML
    private TextField tf_quota;
    @FXML
    private TextField tf_price;

    @FXML
    private Button button_remove;
    @FXML
    private Button button_add;
    @FXML
    private Button button_return;

    @FXML
    private TableView table_products;
    @FXML
    private TableColumn col_product_id;
    @FXML
    private TableColumn col_product_name;
    @FXML
    private TableColumn col_product_brand;
    @FXML
    private TableColumn col_quota;
    @FXML
    private TableColumn col_price;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        col_product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_product_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        col_quota.setCellValueFactory(new PropertyValueFactory<>("quota"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        getProducts();
    }

    private void getProducts() {
        table_products.getItems().clear();

        Connection connection = DatabaseController.getInstance().getConnection();

        // Query database for product list and add to tableview
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE store_owners_id = ?");
            ps.setInt(1, getAccount().getId());
            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {
                Product product = new Product();

                product.setId(rs.getInt("product_id"));
                product.setStore_owners_id(rs.getInt("store_owners_id"));
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setQuota(rs.getString("quota"));
                product.setPrice(rs.getFloat("price"));

                table_products.getItems().add(product);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void addProduct(ActionEvent event) {
        Product product = new Product();

        if (tf_product_name.getText().isEmpty()) return;
        product.setName(tf_product_name.getText());

        if (tf_brand.getText().isEmpty()) return;
        product.setBrand(tf_brand.getText());

        if (tf_quota.getText().isEmpty()) return;
        product.setQuota(tf_quota.getText());

        if (tf_price.getText().isEmpty()) return;
        try {
            product.setPrice(Float.parseFloat(tf_price.getText()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        Connection connection = DatabaseController.getInstance().getConnection();

        // Inserting product into database
        try {
            CallableStatement cs = connection.prepareCall("{CALL insert_product (?, ?, ?, ?, ?)}");

            cs.setInt("store_owners_id", getAccount().getId());
            cs.setString("name", product.getName());
            cs.setString("brand", product.getBrand());
            cs.setString("quota", product.getQuota());
            cs.setFloat("price", product.getPrice());

            cs.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        // Get products from database after addition
        getProducts();

        // Clear text fields
        tf_product_name.clear();
        tf_brand.clear();
        tf_quota.clear();
        tf_price.clear();
    }

    public void removeProduct(ActionEvent event) {
        Product product;

        try {
            product = (Product) table_products.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        Connection connection = DatabaseController.getInstance().getConnection();

        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE from products WHERE product_id = " + product.getId());

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        // Get products from database after removal
        getProducts();

        // Clear text fields
        tf_product_name.clear();
        tf_brand.clear();
        tf_quota.clear();
        tf_price.clear();
    }

    public void returnToReceiptView(ActionEvent event) throws IOException {
        changeSceneLogged(event, "store_owner-receipt_view.fxml", new StoreOwnerReceiptViewController(), getAccount());
    }
}
