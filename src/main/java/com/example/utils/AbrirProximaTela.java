package com.example.utils;

import com.example.MainLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbrirProximaTela {

    private static Stage primaryStage;
    public static void proximaTela(ActionEvent event, String nomeDaProximaTela) {
        try {
            Stage currentStage = getCurrentStage(event);
            currentStage.close();

            abrirProximaTela(nomeDaProximaTela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Esse método fecha a janela atual para abrir a proxima
     * @param event
     * @return
     */
    static Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    private static void abrirProximaTela(String nomeDaProximaTela) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainLogin.class.getResource(nomeDaProximaTela));
        primaryStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
