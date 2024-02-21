/**
 * Controller responsável pela tela de bloqueio/desbloqueio de usuário.
 */
package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.UsuarioException;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TelaDeBloqueio {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox HboxBloqueios;

    @FXML
    private Button btnBloquear;

    @FXML
    private Button btnDesbloquear;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textId;

    @FXML
    private VBox vBoxText;

    /**
     * Ação acionada quando o botão 'Bloquear' é clicado.
     * Bloqueia o usuário com o ID especificado.
     * @param event Evento de clique no botão 'Bloquear'.
     * @throws UsuarioException Exceção lançada se ocorrer um erro ao bloquear o usuário.
     */
    @FXML
    void btnBloquearAction(ActionEvent event) throws UsuarioException {
        String id = textId.getText();

        verificaCampo(id);
        verificaIdInt(id);
        Usuario usuarioBloqueado = buscarPorId(id);

        usuarioBloqueado.bloquearConta(usuarioBloqueado);
        informationAlert("Bloqueio", "O usuário "+ usuarioBloqueado.getNome()+ " foi bloqueado com sucesso");
        textId.clear();
    }

    /**
     * Busca um usuário pelo ID especificado.
     * @param id ID do usuário a ser buscado.
     * @return O usuário encontrado.
     * @throws UsuarioException Exceção lançada se o usuário não for encontrado.
     */
    private Usuario buscarPorId(String id) throws UsuarioException {
        Usuario usuario;
        try {
            usuario = DAO.getUsuarioDAO().encontrarPorID(Integer.parseInt(id));
        } catch (Exception e ) {
            informationAlert("ERROR","Esse ID não foi encontrado!");
            throw e;
        }
        return usuario;
    }

    /**
     * Verifica se o ID especificado pode ser convertido para um número inteiro.
     * @param id ID a ser verificado.
     * @throws UsuarioException Exceção lançada se o ID não for um número inteiro.
     */
    private void verificaIdInt(String id) throws UsuarioException {
        try {
            Integer.parseInt(id);
        } catch(NumberFormatException e) {
            informationAlert("ERROR", "Por favor, digite apenas números");
            throw new UsuarioException("ID não é um número inteiro.");
        }
    }

    /**
     * Verifica se o campo de ID não está vazio.
     * @param id ID a ser verificado.
     * @throws UsuarioException Exceção lançada se o campo estiver vazio.
     */
    private void verificaCampo(String id) throws UsuarioException {
        if (id.isEmpty()) {
            informationAlert("ERROR","Por favor, informe o ID do usuário a ser bloqueado");
            throw new UsuarioException("Campo de ID vazio.");
        }
    }

    /**
     * Ação acionada quando o botão 'Desbloquear' é clicado.
     * Desbloqueia o usuário com o ID especificado.
     * @param event Evento de clique no botão 'Desbloquear'.
     * @throws UsuarioException Exceção lançada se ocorrer um erro ao desbloquear o usuário.
     */
    @FXML
    void btnDesbloquearAction(ActionEvent event) throws UsuarioException {
        String id = textId.getText();

        verificaCampo(id);
        verificaIdInt(id);
        Usuario usuarioBloqueado = buscarPorId(id);

        usuarioBloqueado.desbloqueiaConta(usuarioBloqueado);
        informationAlert("Desbloqueio", "O usuário "+ usuarioBloqueado.getNome() + " foi desbloqueado com sucesso!");
        textId.clear();
    }

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela de administrador.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaAdministrador.fxml");
    }

    /**
     * Ação acionada quando o campo de ID é modificado.
     * @param event Evento de modificação do campo de ID.
     */
    @FXML
    void textIdAction(ActionEvent event) {

    }

    /**
     * Método de inicialização do controlador.
     */
    @FXML
    void initialize() {
        assert HboxBloqueios != null : "fx:id=\"HboxBloqueios\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert btnBloquear != null : "fx:id=\"btnBloquear\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert btnDesbloquear != null : "fx:id=\"btnDesbloquear\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert textId != null : "fx:id=\"textId\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert vBoxText != null : "fx:id=\"vBoxText\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
    }

    /**
     * Exibe um alerta de informação.
     * @param title Título do alerta.
     * @param texto Texto do alerta.
     */
    private void informationAlert(String title, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}
