module com.example.sistemadegenrenciamentodebiblioteca {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    /**
     * Permite o acesso aos pacotes
     */

    opens com.example to javafx.fxml, javafx.graphics, javafx.controls;
    exports com.example.app.controller;
    opens com.example.app.controller to javafx.controls, javafx.fxml, javafx.graphics ;
    exports com.example;

}