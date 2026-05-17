module com.example.tdpadronsilva {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tdpadronsilva to javafx.fxml;
    exports com.example.tdpadronsilva;

}