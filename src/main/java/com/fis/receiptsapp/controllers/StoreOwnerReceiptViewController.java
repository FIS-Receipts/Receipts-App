package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.models.Customer;
import com.fis.receiptsapp.models.Product;
import com.fis.receiptsapp.models.Receipt;
import com.fis.receiptsapp.models.StoreOwner;
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

public class StoreOwnerReceiptViewController extends SceneEssentials implements Initializable {

    @FXML
    private TextField tf_filter;

    @FXML
    private Button button_sign_out;
    @FXML
    private Button button_new_receipt;
    @FXML
    private Button button_manage_store;

    @FXML
    private TableView table_receipts;
    @FXML
    private TableColumn col_receipt_id;
    @FXML
    private TableColumn col_date;
    @FXML
    private TableColumn col_customer;
    @FXML
    private TableColumn col_amount;

    @FXML
    private Label label_store_name;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        col_receipt_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        getReceipts();

        Connection connection = DatabaseController.getInstance().getConnection();

        StoreOwner storeOwner;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM store_owners WHERE id = " + getAccount().getId());
            if (!rs.next()) return;
            storeOwner = new StoreOwner(rs);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        label_store_name.setText(storeOwner.getName());

        FilteredList<Receipt> filteredData = new FilteredList<Receipt>(table_receipts.getItems(), b -> true);
        tf_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(productSearchModel -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    return true;
                String searchKeyword = newValue.toLowerCase();

                if (productSearchModel.getCustomerName().toLowerCase().indexOf(searchKeyword) > -1)
                    return true;
                else
                    return false;
            });
        });
        table_receipts.setItems(filteredData);
    }

    private void getReceipts() {
        table_receipts.getItems().clear();

        Connection connection = DatabaseController.getInstance().getConnection();

        // Query database for receipts and add to tableview
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM receipts WHERE store_owner_id = ?");
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

    public void newReceiptScene(ActionEvent event) throws IOException {
        changeSceneLogged(event, "create_new_receipt.fxml", new EmitReceiptController(), getAccount());
    }

    public void manageStoreScene(ActionEvent event) throws IOException {
        changeSceneLogged(event, "manage_product_database.fxml", new ManageProductDatabaseController(), getAccount());
    }
}
