/**
 * Controller responsável pela tela de empréstimo de livros.
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
import com.example.model.Emprestimos;
import com.example.model.Livro;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaDeEmprestimo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEmprestar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;

    @FXML
    private TextField textIdUsuario;

    /**
     * Ação acionada quando o botão 'Emprestar' é clicado.
     * Realiza o empréstimo do livro com o ID especificado para o usuário com o ID especificado.
     * @param event Evento de clique no botão 'Emprestar'.
     * @throws LivroException Exceção lançada se ocorrer um erro relacionado ao livro.
     * @throws UsuarioException Exceção lançada se ocorrer um erro relacionado ao usuário.
     * @throws ReservaException Exceção lançada se ocorrer um erro relacionado à reserva.
     */
    @FXML
    void btnEmprestarAction(ActionEvent event) throws LivroException, UsuarioException, ReservaException {
        Livro livroEcontrado;
        try {
            String idLivro = textIdLivro.getText();
            String idUsuario = textIdUsuario.getText();

            verificaCampos(idLivro, idUsuario);
            verificaCamposInt(idLivro, idUsuario);

            livroEcontrado = buscarLivro(Integer.parseInt(idLivro));
            Usuario usuarioEncontrado = buscaUsuario(Integer.parseInt(idUsuario));
            emprestimos(livroEcontrado, usuarioEncontrado);
        } catch (Exception e) {
            textIdLivro.clear();
            textIdUsuario.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro " + livroEcontrado.getTitulo() + " foi emprestado com sucesso");
        textIdLivro.clear();
        textIdUsuario.clear();
    }

    /**
     * Realiza o registro do empréstimo do livro para o usuário.
     * @param livro Livro a ser emprestado.
     * @param usuario Usuário que receberá o empréstimo.
     * @throws LivroException Exceção lançada se ocorrer um erro relacionado ao livro.
     * @throws UsuarioException Exceção lançada se ocorrer um erro relacionado ao usuário.
     * @throws ReservaException Exceção lançada se ocorrer um erro relacionado à reserva.
     */
    private void emprestimos(Livro livro, Usuario usuario) throws LivroException, UsuarioException, ReservaException {
        LocalDate dataHoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataHoje.format(formatter);
        try {
            Emprestimos emprestimo = DAO.getEmprestimosDAO().criar(new Emprestimos(livro, usuario, dataFormatada));
        } catch (UsuarioException | ReservaException | LivroException e) {
            alertException("ERROR", e);
            throw e;
        }
    }

    /**
     * Busca o usuário pelo ID.
     * @param idUsuario ID do usuário a ser buscado.
     * @return Usuário encontrado.
     * @throws UsuarioException Exceção lançada se o usuário não for encontrado.
     */
    private Usuario buscaUsuario(Integer idUsuario) throws UsuarioException {
        Usuario usuario;
        try {
            usuario = DAO.getUsuarioDAO().encontrarPorID(idUsuario);
        } catch (UsuarioException e) {
            informationAlert("ERROR", "Usuário não encontrado");
            throw e;
        }
        return usuario;
    }

    /**
     * Busca o livro pelo ID.
     * @param idLivro ID do livro a ser buscado.
     * @return Livro encontrado.
     * @throws LivroException Exceção lançada se o livro não for encontrado.
     */
    private Livro buscarLivro(Integer idLivro) throws LivroException {
        Livro livro;
        try {
            livro = DAO.getLivroDAO().encontrarPorID(idLivro);
        } catch (LivroException e) {
            informationAlert("ERROR", "Livro não encontrado");
            throw e;
        }
        return livro;
    }

    /**
     * Verifica se os campos de ID estão preenchidos.
     * @param idLivro ID do livro.
     * @param idUsuario ID do usuário.
     */
    private void verificaCampos(String idLivro, String idUsuario) {
        if (idLivro.isEmpty() || idUsuario.isEmpty()) {
            informationAlert("ERROR", "Por favor, preencha todos os campos");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Verifica se os campos de ID contêm apenas números.
     * @param idLivro ID do livro.
     * @param idUsuario ID do usuário.
     */
    private void verificaCamposInt(String idLivro, String idUsuario) {
        try {
            int idLivroInt = Integer.parseInt(idLivro);
            int idUsuarioInt = Integer.parseInt(idUsuario);
        } catch (NumberFormatException e) {
            informationAlert("ERROR", "Digite apenas números");
            throw e;
        }
    }

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela de bibliotecário.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaBibliotecario.fxml");
    }

    /**
     * Ação acionada quando o campo de ID do livro é modificado.
     * @param event Evento de modificação do campo de ID do livro.
     */
    @FXML
    void textIdLivroAction(ActionEvent event) {

    }

    /**
     * Ação acionada quando o campo de ID do usuário é modificado.
     * @param event Evento de modificação do campo de ID do usuário.
     */
    @FXML
    void textIdUsuarioAction(ActionEvent event) {

    }

    /**
     * Método de inicialização do controlador.
     */
    @FXML
    void initialize() {
        assert btnEmprestar != null : "fx:id=\"btnEmprestar\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";
        assert textIdUsuario != null : "fx:id=\"textIdUsuario\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";

    }

    /**
     * Exibe um alerta de exceção.
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
