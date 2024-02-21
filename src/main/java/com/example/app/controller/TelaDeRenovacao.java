/**
 * Controller responsável pela tela de renovação de empréstimo de livros.
 */
package com.example.app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.EmprestimosException;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Emprestimos;
import com.example.model.Livro;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import com.example.utils.SessionLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TelaDeRenovacao {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRenovar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;

    @FXML
    private VBox vBoxTexts;

    /**
     * Ação acionada quando o botão 'Renovar' é clicado.
     * Renova o empréstimo do livro com o ID especificado.
     * @param event Evento de clique no botão 'Renovar'.
     * @throws UsuarioException Se ocorrer um erro relacionado ao usuário.
     * @throws LivroException Se ocorrer um erro relacionado ao livro.
     * @throws EmprestimosException Se ocorrer um erro relacionado ao empréstimo.
     * @throws ReservaException Se ocorrer um erro relacionado à reserva.
     */
    @FXML
    void btnRenovarAction(ActionEvent event) throws UsuarioException, LivroException, EmprestimosException, ReservaException {
        try {
            String idLivro = textIdLivro.getText();
            Object login = SessionLogin.getUserInSession();
            Usuario usuario =(Usuario) login;

            verificaCampos(idLivro);
            verificaCamposInt(idLivro);

            Livro livroEncontrado = buscarLivro(Integer.parseInt(idLivro));
            Emprestimos emprestimosEncontrado = buscaEmprestimo(livroEncontrado);

            renovar(livroEncontrado, usuario, emprestimosEncontrado);
        } catch (Exception e) {
            textIdLivro.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro foi renovado com sucesso!");
        textIdLivro.clear();
    }

    /**
     * Renova o empréstimo do livro.
     * @param livro Livro a ser renovado.
     * @param usuario Usuário que está realizando a renovação.
     * @param emprestimos Objeto de empréstimo relacionado ao livro.
     * @throws EmprestimosException Se ocorrer um erro relacionado ao empréstimo.
     * @throws UsuarioException Se ocorrer um erro relacionado ao usuário.
     * @throws ReservaException Se ocorrer um erro relacionado à reserva.
     */
    private void renovar(Livro livro, Usuario usuario, Emprestimos emprestimos ) throws EmprestimosException, UsuarioException, ReservaException {
        LocalDate dataHoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataHoje.format(formatter);
        try {
            emprestimos.renovar(livro,usuario,dataFormatada);
        } catch (EmprestimosException | UsuarioException | ReservaException e) {
            alertException("ERROR", e);
            throw e;
        }
    }

    /**
     * Busca o empréstimo relacionado ao livro.
     * @param livro Livro a ser buscado.
     * @return Objeto de empréstimo relacionado ao livro.
     * @throws EmprestimosException Se ocorrer um erro relacionado ao empréstimo.
     */
    private Emprestimos buscaEmprestimo(Livro livro) throws EmprestimosException {
        Emprestimos emprestimo;
        try {
            emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        } catch (Exception e) {
            informationAlert("ERROR", "Este livro não está emprestado!");
            throw e;
        }
        return emprestimo;
    }

    /**
     * Busca um livro pelo ID especificado.
     * @param idLivro ID do livro a ser buscado.
     * @return O livro encontrado.
     * @throws LivroException Se ocorrer um erro ao buscar o livro.
     */
    private Livro buscarLivro(Integer idLivro) throws LivroException {
        Livro livro;
        try {
            livro = DAO.getLivroDAO().encontrarPorID(idLivro);
        } catch (LivroException e) {
            informationAlert("ERROR", "Usuário não encontrado");
            throw e;
        }
        return livro;
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
        if (idLivro.isEmpty()) {
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
        assert btnRenovar != null : "fx:id=\"btnRenovar\" was not injected: check your FXML file 'telaDeRenovacao.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeRenovacao.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeRenovacao.fxml'.";
        assert vBoxTexts != null : "fx:id=\"vBoxTexts\" was not injected: check your FXML file 'telaDeRenovacao.fxml'.";
    }

    /**
     * Exibe uma caixa de diálogo de exceção.
     * @param title Título da caixa de diálogo.
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
     * Exibe uma caixa de diálogo de informações.
     * @param title Título da caixa de diálogo.
     * @param texto Conteúdo da caixa de diálogo.
     */
    private void informationAlert(String title, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}
