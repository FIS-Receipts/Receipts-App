package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.MainApplication;
import com.fis.receiptsapp.models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerRegisterController extends SceneEssentials{

    private Customer customer;

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_first_name;
    @FXML
    private TextField tf_last_name;
    @FXML
    private TextField tf_telephone_number;

    @FXML
    private Button button_register;
    @FXML
    private Button button_sign_up_as_so;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void register(ActionEvent event) {
        customer = new Customer();

        if (tf_username.getText().isEmpty()) return;
        customer.setUsername(tf_username.getText());

        if (tf_password.getText().isEmpty()) return;
        customer.setPassword(Encrypt.sha256(tf_password.getText()));

        if (tf_first_name.getText().isEmpty()) return;
        customer.setFirst_name(tf_first_name.getText());

        if (tf_last_name.getText().isEmpty()) return;
        customer.setLast_name(tf_last_name.getText());

        if (tf_telephone_number.getText().isEmpty()) return;
        customer.setTelephone(tf_telephone_number.getText());

        Connection connection = DatabaseController.getInstance().getConnection();

        // Checking if account already exists with this username
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM accounts WHERE username = ?");
            ps.setString(1, customer.getUsername());
            ResultSet rs = ps.executeQuery();

            if ( rs.next() ) {
                System.out.println("Account with username " + customer.getUsername() + " already exists");
                return;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Inserting account into database
        try {
            CallableStatement cs = connection.prepareCall("{CALL insert_customer (?, ?, ?, ?, ?)}");

            cs.setString("username", customer.getUsername());
            cs.setString("password", customer.getPassword());
            cs.setString("first_name", customer.getFirst_name());
            cs.setString("last_name", customer.getLast_name());
            cs.setString("telephone", customer.getTelephone());

            cs.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Setting the id of the customer object
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM accounts WHERE username = ?");
            ps.setString(1, customer.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            changeScene(event, "client.fxml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void registerAsStoreOwner(ActionEvent event) throws IOException {
        changeScene(event, "store_owner-register.fxml");
    }

}
