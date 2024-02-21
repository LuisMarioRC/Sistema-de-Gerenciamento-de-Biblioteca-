/**
 * Classe responável pela inicialização da aplicação.
 */
package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta classe é responsável por iniciar a aplicação e carregar a tela de login.
 */
public class MainLogin extends Application {

    /**
     * O método start é chamado quando a aplicação é iniciada.
     * Ele configura a cena e exibe a interface do usuário definida em login.fxml.
     *
     * @param stage O palco principal onde a cena é exibida.
     * @throws IOException se houver um erro de E/S ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainLogin.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tela de Login!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * O método main é o ponto de entrada da aplicação.
     *
     * @param args Argumentos da linha de comando (não são usados neste caso).
     */
    public static void main(String[] args) {
        launch();
    }
}
