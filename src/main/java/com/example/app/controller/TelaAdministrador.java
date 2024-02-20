package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TelaAdministrador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBloquearConta;

    @FXML
    private Button btnCadastrarUsuario;

    @FXML
    private Button btnGerarRelatório;

    @FXML
    private Button btnGerenciarAcervo;

    @FXML
    private Button btncadastrarOperador;

    @FXML
    private VBox vbox;

    @FXML
    private Button btnSair;

    @FXML
    void btnSairAction(ActionEvent event){
        AbrirProximaTela.proximaTela(event,"login.fxml");

    }

    @FXML
    void btnBloquearContaAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"TelaDeBloqueio.fxml");

    }

    @FXML
    void btnCadastrarUsuarioAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"cadastroUsuario.fxml");
    }

    @FXML
    void btnGerarRelatórioAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRelatorio.fxml");

    }

    @FXML
    void btnGerenciarAcervoAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeAcervo.fxml");

    }

    @FXML
    void btncadastrarOperadorAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"cadastroOperador.fxml");
    }

    @FXML
    void initialize() {
        assert btnBloquearConta != null : "fx:id=\"btnBloquearConta\" was not injected: check your FXML file 'telaAdministrador.fxml'.";
        assert btnCadastrarUsuario != null : "fx:id=\"btnCadastrarUsuario\" was not injected: check your FXML file 'telaAdministrador.fxml'.";
        assert btnGerarRelatório != null : "fx:id=\"btnGerarRelatório\" was not injected: check your FXML file 'telaAdministrador.fxml'.";
        assert btnGerenciarAcervo != null : "fx:id=\"btnGerenciarAcervo\" was not injected: check your FXML file 'telaAdministrador.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'telaAdministrador.fxml'.";
        assert btncadastrarOperador != null : "fx:id=\"btncadastrarOperador\" was not injected: check your FXML file 'telaAdministrador.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'telaAdministrador.fxml'.";

    }

}
