package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmitReceiptController extends SceneEssentials implements Initializable {
    @FXML
    TextField tf_customer_id;
    @FXML
    TextField tf_product_id;
    @FXML
    TextField tf_quantity;

    @FXML
    Button button_remove;
    @FXML
    Button button_button_add;
    @FXML
    Button button_cancel;
    @FXML
    Button button_create_and_send;

    @FXML
    TableView table_receipt;
    @FXML
    TableColumn col_product_id;
    @FXML
    TableColumn col_product_name;
    @FXML
    TableColumn col_product_brand;
    @FXML
    TableColumn col_quantity;

    @FXML
    Label label_total;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        col_product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_product_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        label_total.setText(getStringTotalPrice());
    }

    private float getTotalPrice() {
        float totalPrice = 0;

        List<Product> products = (List<Product>) table_receipt.getItems();
        for (Product product : products) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        return totalPrice;
    }

    private String getStringTotalPrice() {
        return String.format("%.02f", getTotalPrice());
    }

    public void addProduct(ActionEvent event) {
        Product product = new Product();

        if (tf_product_id.getText().isEmpty()) return;
        try {
            product.setId(Integer.parseInt(tf_product_id.getText()));
        } catch (Exception e) {
            System.err.println(e);
            return;
        }

        if (tf_quantity.getText().isEmpty()) return;
        try {
            product.setQuantity(Integer.parseInt(tf_quantity.getText()));
        } catch (Exception e) {
            System.err.println(e);
            return;
        }

        Connection connection = DatabaseController.getInstance().getConnection();

        // Get product from database if exists
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from products WHERE product_id = " + product.getId());

            if (rs.next()) {
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setQuota(rs.getString("quota"));
                product.setPrice(rs.getFloat("price"));

                table_receipt.getItems().add(product);
            } else {
                return;
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        // Clear text fields
        tf_product_id.clear();
        tf_quantity.clear();

        label_total.setText(getStringTotalPrice());
    }

    public void removeProduct(ActionEvent event) {
        try {
            Product product = (Product) table_receipt.getSelectionModel().getSelectedItem();
            table_receipt.getItems().remove(product);
            label_total.setText(getStringTotalPrice());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
    }

    public void addReceiptToDatabase() {

    }


    // TODO: redirect cancel to store owner receipt view
}
