/**
 * Controller responsável pela tela principal do bibliotecário.
 */
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

    /**
     * Ação acionada quando o botão 'Sair' é clicado.
     * Retorna à tela de login.
     * @param event Evento de clique no botão 'Sair'.
     */
    @FXML
    void btnSairAction(ActionEvent event){
        AbrirProximaTela.proximaTela(event,"login.fxml");
    }

    /**
     * Ação acionada quando o botão 'Devolver' é clicado.
     * Abre a tela de devolução de livros.
     * @param event Evento de clique no botão 'Devolver'.
     */
    @FXML
    void btnDevolverAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeDevolucao.fxml");
    }

    /**
     * Ação acionada quando o botão 'Emprestar' é clicado.
     * Abre a tela de empréstimo de livros.
     * @param event Evento de clique no botão 'Emprestar'.
     */
    @FXML
    void btnEmprestarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaDeEmprestimo.fxml");
    }

    /**
     * Ação acionada quando o botão 'Pesquisar Livro' é clicado.
     * Abre a tela de pesquisa de livros.
     * @param event Evento de clique no botão 'Pesquisar Livro'.
     */
    @FXML
    void btnPesquisarLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDePesquisa.fxml");
    }

    /**
     * Ação acionada quando o botão 'Registrar Novo Livro' é clicado.
     * Abre a tela de registro de novos livros.
     * @param event Evento de clique no botão 'Registrar Novo Livro'.
     */
    @FXML
    void btnRegistrarNovoLivroAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "resgistroLivros.fxml");
    }

    /**
     * Método executado ao inicializar a tela.
     * Verifica se os elementos do FXML foram injetados corretamente.
     */
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
