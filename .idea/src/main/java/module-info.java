module com.example.projetopbl {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.projetopbl to javafx.fxml;
    exports com.example.projetopbl;
}