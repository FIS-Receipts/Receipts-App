package com.fis.receiptsapp.controllers;

import com.fis.receiptsapp.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class SceneEssentials {

    protected void changeScene(ActionEvent event, String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(sceneName));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
