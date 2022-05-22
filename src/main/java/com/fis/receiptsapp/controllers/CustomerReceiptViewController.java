package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.models.Customer;
import com.fis.receiptsapp.models.Product;
import com.fis.receiptsapp.models.Receipt;
import javafx.beans.Observable;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerReceiptViewController extends SceneEssentials implements Initializable {

    @FXML
    private TextField tf_filter;

    @FXML
    private Button button_sign_out;

    @FXML
    private TableView table_receipts;
    @FXML
    private TableColumn col_number_of_items;
    @FXML
    private TableColumn col_date;
    @FXML
    private TableColumn col_store_name;
    @FXML
    private TableColumn col_store_type;
    @FXML
    private TableColumn col_amount;

    @FXML
    private Label label_client_name;
    @FXML
    private Label label_total;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        col_number_of_items.setCellValueFactory(new PropertyValueFactory<>("nrItems"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_store_name.setCellValueFactory(new PropertyValueFactory<>("storeName"));
        col_store_type.setCellValueFactory(new PropertyValueFactory<>("storeType"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        getReceipts();

        Connection connection = DatabaseController.getInstance().getConnection();

        Customer customer;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE id = " + getAccount().getId());
            if (!rs.next()) return;
            customer = new Customer(rs);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        label_client_name.setText(customer.getFirst_name() + " " + customer.getLast_name());
        label_total.setText(getStringTotalPrice());

        FilteredList<Receipt> filteredData = new FilteredList<Receipt>(table_receipts.getItems(), b -> true);
        tf_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(productSearchModel -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    return true;
                String searchKeyword = newValue.toLowerCase();

                if (productSearchModel.getStoreName().toLowerCase().indexOf(searchKeyword) > -1)
                    return true;
                else if (String.valueOf(productSearchModel.getStoreType()).toLowerCase().indexOf(searchKeyword) > -1)
                    return true;
                else
                    return false;
            });
            label_total.setText(getStringTotalPrice());
        });
        table_receipts.setItems(filteredData);
    }

    private float getTotalPrice() {
        float totalPrice = 0;

        List<Receipt> receipts = (List<Receipt>) table_receipts.getItems();
        for (Receipt receipt : receipts) {
            totalPrice += receipt.getAmount();
        }

        return totalPrice;
    }

    private String getStringTotalPrice() {
        return String.format("%.02f", getTotalPrice());
    }

    private void getReceipts() {
        table_receipts.getItems().clear();

        Connection connection = DatabaseController.getInstance().getConnection();

        // Query database for receipts and add to tableview
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM receipts WHERE customer_id = ?");
            ps.setInt(1, getAccount().getId());
            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {
                Receipt receipt = new Receipt(rs);

                table_receipts.getItems().add(receipt);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void signOut(ActionEvent event) throws IOException {
        changeScene(event, "login.fxml");
    }
}
