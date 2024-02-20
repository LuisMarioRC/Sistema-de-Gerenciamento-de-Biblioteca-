package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TelaUsuario {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnPesquisarLivro;

    @FXML
    private Button btnRenovarEmprestimo;

    @FXML
    private Button btnReservarLivro;

    @FXML
    private VBox vbox;

    @FXML
    private Button btnSair;



    @FXML
    void btnSairAction(ActionEvent event){
        AbrirProximaTela.proximaTela(event,"login.fxml");

    }

    @FXML
    void btnPesquisarLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaDePesquisa.fxml");
    }

    @FXML
    void btnRenovarEmprestimoAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRenovacao.fxml");

    }

    @FXML
    void btnReservarLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeReservar.fxml");
    }

    @FXML
    void initialize() {
        assert btnPesquisarLivro != null : "fx:id=\"btnPesquisarLivro\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert btnRenovarEmprestimo != null : "fx:id=\"btnRenovarEmprestimo\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert btnReservarLivro != null : "fx:id=\"btnReservarLivro\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'telaUsuario.fxml'.";

    }

}
