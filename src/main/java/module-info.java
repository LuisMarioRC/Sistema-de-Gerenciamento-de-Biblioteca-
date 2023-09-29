module com.example.projetopbl {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.projetopbl to javafx.fxml;
    exports com.example.projetopbl;
}