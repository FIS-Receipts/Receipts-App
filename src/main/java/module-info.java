module com.fis.receiptsapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fis.receiptsapp to javafx.fxml;
    exports com.fis.receiptsapp;
}