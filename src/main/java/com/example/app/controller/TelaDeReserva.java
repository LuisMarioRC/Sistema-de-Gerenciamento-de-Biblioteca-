/**
 * Controller responsável pela tela de reserva de livros.
 */
package com.example.app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Reserva;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import com.example.utils.SessionLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TelaDeReserva {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnReservar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;

    @FXML
    private VBox vBoxTexts;

    /**
     * Ação acionada quando o botão 'Reservar' é clicado.
     * Reserva o livro com o ID especificado.
     * @param event Evento de clique no botão 'Reservar'.
     * @throws UsuarioException Se ocorrer um erro relacionado ao usuário.
     * @throws LivroException Se ocorrer um erro relacionado ao livro.
     * @throws ReservaException Se ocorrer um erro relacionado à reserva.
     */
    @FXML
    void btnReservarAction(ActionEvent event) throws UsuarioException, LivroException, ReservaException {
        try {
            String idLivro = textIdLivro.getText();
            Object login = SessionLogin.getUserInSession();
            Usuario usuario = (Usuario) login;

            verificaCampos(idLivro);
            verificaCamposInt(idLivro);

            reservar(Integer.parseInt(idLivro), usuario);
        } catch (Exception e) {
            textIdLivro.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro foi reservado com sucesso!");
        textIdLivro.clear();
    }

    /**
     * Realiza a reserva do livro.
     * @param idLivro ID do livro a ser reservado.
     * @param usuario Usuário que está realizando a reserva.
     * @throws LivroException Se ocorrer um erro relacionado ao livro.
     * @throws UsuarioException Se ocorrer um erro relacionado ao usuário.
     * @throws ReservaException Se ocorrer um erro relacionado à reserva.
     */
    private void reservar(Integer idLivro, Usuario usuario) throws LivroException, UsuarioException, ReservaException {
        LocalDate dataHoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataHoje.format(formatter);
        try {
            Reserva reserva = DAO.getReservaDAO().criar(new Reserva(idLivro, usuario, dataFormatada));
        } catch (LivroException | UsuarioException | ReservaException e) {
            alertException("ERROR", e);
            throw e;
        }
    }

    /**
     * Verifica se o campo de texto contém um número inteiro.
     * @param idLivro Texto a ser verificado.
     */
    private void verificaCamposInt(String idLivro){
        try {
            int idLivroInt = Integer.parseInt(idLivro);
        } catch (NumberFormatException e) {
            informationAlert("ERROR", "Digite apenas números");
            throw e;
        }
    }

    /**
     * Verifica se o campo de texto está vazio.
     * @param idLivro Texto a ser verificado.
     */
    private void verificaCampos(String idLivro){
        if (idLivro.isEmpty() ) {
            informationAlert("ERROR", "Por favor, preencha todos os campos");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela do usuário.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaUsuario.fxml");
    }

    /**
     * Ação acionada quando o texto do campo de ID do livro é inserido.
     * Não possui funcionalidade neste momento.
     * @param event Evento de inserção de texto no campo de ID do livro.
     */
    @FXML
    void textIdLivroAction(ActionEvent event) {
        // Não possui funcionalidade neste momento
    }

    /**
     * Método executado ao inicializar a tela.
     * Verifica se os elementos do FXML foram injetados corretamente.
     */
    @FXML
    void initialize() {
        assert btnReservar != null : "fx:id=\"btnReservar\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
        assert vBoxTexts != null : "fx:id=\"vBoxTexts\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
    }

    /**
     * Exibe um alerta de exceção com o título especificado.
     * @param title Título do alerta.
     * @param e Exceção a ser exibida.
     */
    private void alertException(String title, Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Exibe um alerta de informação com o título e texto especificados.
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
