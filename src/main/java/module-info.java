module com.fis.receiptsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.fis.receiptsapp to javafx.fxml;
    exports com.fis.receiptsapp;
    exports com.fis.receiptsapp.controllers;
    opens com.fis.receiptsapp.controllers to javafx.fxml;
}