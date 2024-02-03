package com.example.app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("example/MainWindow.fxml"));
        //URL xmlURL = getClass().getResource("MainWindow.fxml");
        //loader.setLocation(xmlURL);

        //Parent parent= loader.load();

        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
