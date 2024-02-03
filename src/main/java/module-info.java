module com.example.sistemadegenrenciamentodebiblioteca {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    /**
     * Permite o acesso aos pacotes
     */

    opens com.example to javafx.fxml, javafx.graphics, javafx.controls;
    opens com.example.app to javafx.fxml, javafx.graphics, javafx.controls;

    exports com.example.app;
    exports com.example;

}