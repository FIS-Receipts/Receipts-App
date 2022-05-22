package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.MainApplication;
import com.fis.receiptsapp.models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class SceneEssentials {
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    protected void changeScene(ActionEvent event, String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(sceneName));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void changeSceneLogged(ActionEvent event, String sceneName, SceneEssentials controller, Account account) throws IOException {
        controller.setAccount(account);

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(sceneName));
        loader.setController(controller.getClass().cast(controller));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
