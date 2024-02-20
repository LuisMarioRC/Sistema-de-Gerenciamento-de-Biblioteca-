package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TelaDoBibliotecario {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox VboxFuncion;

    @FXML
    private Button btnDevolver;

    @FXML
    private Button btnEmprestar;

    @FXML
    private Button btnPesquisarLivro;

    @FXML
    private Button btnRegistrarNovoLivro;

    @FXML
    private Button btnSair;

    @FXML
    void btnSairAction(ActionEvent event){
        AbrirProximaTela.proximaTela(event,"login.fxml");

    }

    @FXML
    void btnDevolverAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeDevolucao.fxml");

    }

    @FXML
    void btnEmprestarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaDeEmprestimo.fxml");

    }

    @FXML
    void btnPesquisarLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDePesquisa.fxml");

    }

    @FXML
    void btnRegistrarNovoLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "resgistroLivros.fxml");

    }

    @FXML
    void initialize() {
        assert VboxFuncion != null : "fx:id=\"VboxFuncion\" was not injected: check your FXML file 'telaBibliotecario.fxml'.";
        assert btnDevolver != null : "fx:id=\"btnDevolver\" was not injected: check your FXML file 'telaBibliotecario.fxml'.";
        assert btnEmprestar != null : "fx:id=\"btnEmprestar\" was not injected: check your FXML file 'telaBibliotecario.fxml'.";
        assert btnPesquisarLivro != null : "fx:id=\"btnPesquisarLivro\" was not injected: check your FXML file 'telaBibliotecario.fxml'.";
        assert btnRegistrarNovoLivro != null : "fx:id=\"btnRegistrarNovoLivro\" was not injected: check your FXML file 'telaBibliotecario.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'telaBibliotecario.fxml'.";

    }

}
