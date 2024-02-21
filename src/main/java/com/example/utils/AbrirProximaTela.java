package com.example.utils;

import com.example.MainLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A classe AbrirProximaTela é uma classe utilitária para abrir a próxima tela da aplicação.
 * Ela fornece métodos para fechar a janela atual e abrir uma nova janela com a tela especificada.
 */
public abstract class AbrirProximaTela {

    /** A referência para o palco primário da aplicação. */
    private static Stage primaryStage;

    /**
     * Abre a próxima tela da aplicação.
     *
     * @param event O evento que acionou a abertura da próxima tela.
     * @param nomeDaProximaTela O nome do arquivo FXML da próxima tela a ser aberta.
     */
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
     * Obtém o palco atual a partir do evento fornecido.
     *
     * @param event O evento que contém a referência para o nó da cena atual.
     * @return O palco atual.
     */
    static Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    /**
     * Abre a próxima tela da aplicação carregando o arquivo FXML especificado.
     *
     * @param nomeDaProximaTela O nome do arquivo FXML da próxima tela a ser aberta.
     * @throws IOException Se ocorrer um erro ao carregar o arquivo FXML.
     */
    private static void abrirProximaTela(String nomeDaProximaTela) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainLogin.class.getResource(nomeDaProximaTela));
        primaryStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
