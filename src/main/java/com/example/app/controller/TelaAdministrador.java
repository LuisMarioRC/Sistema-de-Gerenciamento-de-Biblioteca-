/**
 * Controller responsável pela tela do administrador, onde são exibidas opções de gerenciamento do sistema.
 */
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
     * Ação acionada quando o botão 'Bloquear Conta' é clicado.
     * Abre a tela de bloqueio de conta.
     * @param event Evento de clique no botão 'Bloquear Conta'.
     */
    @FXML
    void btnBloquearContaAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"TelaDeBloqueio.fxml");
    }

    /**
     * Ação acionada quando o botão 'Cadastrar Usuário' é clicado.
     * Abre a tela de cadastro de usuário.
     * @param event Evento de clique no botão 'Cadastrar Usuário'.
     */
    @FXML
    void btnCadastrarUsuarioAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"cadastroUsuario.fxml");
    }

    /**
     * Ação acionada quando o botão 'Gerar Relatório' é clicado.
     * Abre a tela de geração de relatório.
     * @param event Evento de clique no botão 'Gerar Relatório'.
     */
    @FXML
    void btnGerarRelatórioAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRelatorio.fxml");
    }

    /**
     * Ação acionada quando o botão 'Gerenciar Acervo' é clicado.
     * Abre a tela de gerenciamento de acervo.
     * @param event Evento de clique no botão 'Gerenciar Acervo'.
     */
    @FXML
    void btnGerenciarAcervoAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeAcervo.fxml");
    }

    /**
     * Ação acionada quando o botão 'Cadastrar Operador' é clicado.
     * Abre a tela de cadastro de operador.
     * @param event Evento de clique no botão 'Cadastrar Operador'.
     */
    @FXML
    void btncadastrarOperadorAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"cadastroOperador.fxml");
    }

    /**
     * Método de inicialização do controlador.
     * Verifica a injeção dos elementos da interface.
     */
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
