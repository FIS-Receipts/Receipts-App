package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.MainApplication;
import com.fis.receiptsapp.models.StoreOwner;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class StoreOwnerRegisterController extends SceneEssentials implements Initializable{

    private StoreOwner storeOwner;

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_store_name;
    @FXML
    private TextField tf_cui;

    @FXML
    private ComboBox cb_store_type;

    @FXML
    private Button button_register;
    @FXML
    private Button button_sign_up_as_cl;

    @FXML
    private Label label_error;

    private final String[] store_types = {"food", "clothes", "gas", "other"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cb_store_type.getItems().addAll(store_types);
    }

    public StoreOwner getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(StoreOwner storeOwner) {
        this.storeOwner = storeOwner;
    }

    public void register(ActionEvent event) {
        storeOwner = new StoreOwner();

        if (tf_username.getText().isEmpty()) return;
        storeOwner.setUsername(tf_username.getText());

        if (tf_password.getText().isEmpty()) return;
        storeOwner.setPassword(Encrypt.sha256(tf_password.getText()));

        if (tf_cui.getText().isEmpty()) return;
        storeOwner.setCui(tf_cui.getText());

        if (tf_store_name.getText().isEmpty()) return;
        storeOwner.setName(tf_store_name.getText());

        if (cb_store_type.getValue() == null) return;
        storeOwner.setStore_type(StoreOwner.Store_type.valueOf((String) cb_store_type.getValue()));

        Connection connection = DatabaseController.getInstance().getConnection();

        // Checking if account already exists with this username
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM accounts WHERE username = ?");
            ps.setString(1, storeOwner.getUsername());
            ResultSet rs = ps.executeQuery();

            if ( rs.next() ) {
                System.out.println(storeOwner.getUsername() + " already exists");
                label_error.setText(storeOwner.getUsername() + " already exists");
                return;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        // Inserting account into database
        try {
            CallableStatement cs = connection.prepareCall("{CALL insert_store_owner (?, ?, ?, ?, ?)}");

            cs.setString("username", storeOwner.getUsername());
            cs.setString("password", storeOwner.getPassword());
            cs.setString("cui", storeOwner.getCui());
            cs.setString("name", storeOwner.getName());
            cs.setString("store_type", storeOwner.getStore_type().name());

            cs.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        // Setting the id of the storeOwner object
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM accounts WHERE username = ?");
            ps.setString(1, storeOwner.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                storeOwner.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            changeScene(event, "login.fxml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void registerAsCustomer(ActionEvent event) throws IOException{
        changeScene(event, "client-register.fxml");
    }

}
