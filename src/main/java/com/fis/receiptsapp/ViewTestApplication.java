package com.fis.receiptsapp;

import com.fis.receiptsapp.models.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewTestApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewTestApplication.class.getResource("store_owner-register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("View Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
