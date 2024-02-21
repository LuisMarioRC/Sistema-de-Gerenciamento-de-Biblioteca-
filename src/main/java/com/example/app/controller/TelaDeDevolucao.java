/**
 * Controller responsável pela tela de devolução de livros.
 */
package com.example.app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.EmprestimosException;
import com.example.excecoes.LivroException;
import com.example.excecoes.UsuarioException;
import com.example.model.Emprestimos;
import com.example.model.Livro;
import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaDeDevolucao {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDevolver;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;

    /**
     * Ação acionada quando o botão 'Devolver' é clicado.
     * Realiza a devolução do livro com o ID especificado.
     * @param event Evento de clique no botão 'Devolver'.
     * @throws LivroException Exceção lançada se ocorrer um erro ao buscar o livro.
     * @throws EmprestimosException Exceção lançada se ocorrer um erro ao buscar o empréstimo.
     * @throws UsuarioException Exceção lançada se ocorrer um erro ao buscar o usuário.
     */
    @FXML
    void btnDevolverAction(ActionEvent event) throws LivroException, EmprestimosException, UsuarioException {
        Livro livroEncontrado = null;
        try {
            String idLivro = textIdLivro.getText();

            verificaCampos(idLivro);
            verificaCamposInt(idLivro);

            livroEncontrado = buscarLivro(Integer.parseInt(idLivro));
            Emprestimos emprestimosEncontrado = buscaEmprestimo(livroEncontrado);

            devolver(livroEncontrado, emprestimosEncontrado);
        } catch (Exception e) {
            textIdLivro.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro " + livroEncontrado.getTitulo() + " devolvido com sucesso");
        textIdLivro.clear();
    }

    /**
     * Realiza a operação de devolução do livro.
     * @param livro Livro a ser devolvido.
     * @param emprestimos Empréstimo do livro.
     * @throws LivroException Exceção lançada se ocorrer um erro relacionado ao livro.
     * @throws EmprestimosException Exceção lançada se ocorrer um erro relacionado aos empréstimos.
     * @throws UsuarioException Exceção lançada se ocorrer um erro relacionado ao usuário.
     */
    private void devolver(Livro livro, Emprestimos emprestimos) throws LivroException, EmprestimosException, UsuarioException {
        LocalDate dataHoje= LocalDate.now();
        try {
            emprestimos.registraDevolucao(livro,emprestimos.getUsuario(),dataHoje);
        } catch (LivroException | EmprestimosException | UsuarioException e) {
            alertException("ERROR", e);
            throw e;
        }
    }

    /**
     * Busca o empréstimo do livro.
     * @param livro Livro para o qual será buscado o empréstimo.
     * @return Empréstimo encontrado.
     * @throws EmprestimosException Exceção lançada se não for possível encontrar o empréstimo.
     */
    private Emprestimos buscaEmprestimo(Livro livro) throws EmprestimosException {
        Emprestimos emprestimo;
        try {
            emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        } catch(Exception e) {
            informationAlert("ERROR", "Este livro não está emprestado!");
            throw e;
        }
        return emprestimo;
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
            informationAlert("ERROR", "Livro não encontrado!");
            throw e;
        }
        return livro;
    }

    /**
     * Verifica se os campos estão preenchidos.
     * @param idLivro ID do livro a ser verificado.
     */
    private void verificaCampos(String idLivro) {
        if (idLivro.isEmpty()) {
            informationAlert("ERROR", "Por favor, preencha todos os campos");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Verifica se o campo de ID contém apenas números.
     * @param idLivro ID do livro a ser verificado.
     */
    private void verificaCamposInt(String idLivro) {
        try {
            Integer.parseInt(idLivro);
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
     * Método de inicialização do controlador.
     */
    @FXML
    void initialize() {
        assert btnDevolver != null : "fx:id=\"btnDevolver\" was not injected: check your FXML file 'telaDeDevolucao.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeDevolucao.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeDevolucao.fxml'.";
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
