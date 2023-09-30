module com.example.projetopbl {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.junit.jupiter.api;
    requires javafx.graphics;


    opens com.example.projetopbl to javafx.fxml;
    exports com.example.projetopbl;
}