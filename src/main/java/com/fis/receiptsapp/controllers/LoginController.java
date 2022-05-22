package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController extends SceneEssentials{
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @FXML
    private Button bt_login;
    @FXML
    private Button bt_sign_up_as_cs;


    public void login(ActionEvent event) {
        setAccount(new Account());

        Account account = getAccount();

        account.setUsername(tf_username.getText());
        account.setPassword(Encrypt.sha256(tf_password.getText()));

        Connection connection = DatabaseController.getInstance().getConnection();

        //Checking if account already exists in the database and match password
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM accounts WHERE username = ?");
            ps.setString(1, account.getUsername());
            ResultSet rs = ps.executeQuery();

            if ( rs.next() ) {
                account.setId(rs.getInt("id"));
                account.setAccount_type(Account.Account_type.valueOf(rs.getString("account_type")));

                if (rs.getString("password").equals(account.getPassword())) {
                    // TODO: set next scene and controller after logging in based on account type
                    if (account.getAccount_type() == Account.Account_type.customer)
                        changeSceneLogged(event, "customer_scene", new LoginController(), account);
                    else
                        changeSceneLogged(event, "store_owner_scene", new LoginController(), account);
                } else {
                    System.out.println("Password is not correct");
                }

            } else {
                System.out.println("Account with username " + account.getUsername() + " doesn't exists");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void registerAsCustomer(ActionEvent event) throws IOException {
        changeScene(event, "client-register.fxml");
    }

}
