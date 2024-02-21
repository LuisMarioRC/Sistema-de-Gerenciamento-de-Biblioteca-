/**
 * Controller responsável pela tela principal do Usuário.
 */
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

    /**
     * Ação acionada quando o botão 'Sair' é clicado.
     * Redireciona o usuário de volta para a tela de login.
     * @param event Evento de clique no botão 'Sair'.
     */
    @FXML
    void btnSairAction(ActionEvent event){
        AbrirProximaTela.proximaTela(event,"login.fxml");
    }

    /**
     * Ação acionada quando o botão 'Pesquisar Livro' é clicado.
     * Redireciona o usuário para a tela de pesquisa de livros.
     * @param event Evento de clique no botão 'Pesquisar Livro'.
     */
    @FXML
    void btnPesquisarLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaDePesquisa.fxml");
    }

    /**
     * Ação acionada quando o botão 'Renovar Empréstimo' é clicado.
     * Redireciona o usuário para a tela de renovação de empréstimo.
     * @param event Evento de clique no botão 'Renovar Empréstimo'.
     */
    @FXML
    void btnRenovarEmprestimoAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRenovacao.fxml");
    }

    /**
     * Ação acionada quando o botão 'Reservar Livro' é clicado.
     * Redireciona o usuário para a tela de reserva de livro.
     * @param event Evento de clique no botão 'Reservar Livro'.
     */
    @FXML
    void btnReservarLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeReservar.fxml");
    }

    /**
     * Método executado ao inicializar a tela.
     * Verifica se os elementos do FXML foram injetados corretamente.
     */
    @FXML
    void initialize() {
        assert btnPesquisarLivro != null : "fx:id=\"btnPesquisarLivro\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert btnRenovarEmprestimo != null : "fx:id=\"btnRenovarEmprestimo\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert btnReservarLivro != null : "fx:id=\"btnReservarLivro\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'telaUsuario.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'telaUsuario.fxml'.";
    }
}
