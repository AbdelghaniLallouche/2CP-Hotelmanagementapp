module com.example.login_page {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.login_page to javafx.fxml;
    exports com.example.login_page;
}