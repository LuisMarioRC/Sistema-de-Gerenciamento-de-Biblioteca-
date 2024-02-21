/**
 * Controller responsável pela tela de remoção de livro.
 */
package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.model.Livro;
import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaDeRemocao {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;

    /**
     * Ação acionada quando o botão 'Remover' é clicado.
     * Remove o livro com o ID especificado.
     * @param event Evento de clique no botão 'Remover'.
     */
    @FXML
    void btnRemoverAction(ActionEvent event) throws LivroException {
        String idLivro = textIdLivro.getText();

        verificarCampoVazio(idLivro);
        verificarCampoInteger(idLivro);

        Livro livro = buscarLivro(Integer.parseInt(idLivro));

        remover(livro);

        informationAlert("SUCESSO", "O livro " + livro.getTitulo() + ", ID: " + livro.getId() +
                " \n foi removido com sucesso!");
        textIdLivro.clear();
    }

    /**
     * Remove o livro especificado.
     * @param livro Livro a ser removido.
     * @throws LivroException Se ocorrer um erro ao remover o livro.
     */
    private void remover(Livro livro) throws LivroException {
        try {
            DAO.getLivroDAO().excluir(livro);
        } catch (LivroException e) {
            informationAlert("ERRO", "Livro não encontrado");
            throw e;
        }
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
        } catch (Exception e) {
            informationAlert("ERROR", "Livro não encontrado");
            throw e;
        }
        return livro;
    }

    /**
     * Verifica se o campo de texto está vazio.
     * @param text Texto a ser verificado.
     */
    private void verificarCampoVazio(String text) {
        if (text.isEmpty()) {
            informationAlert("ERROR", "Por favor, insira o ID do livro!");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Verifica se o texto pode ser convertido para um número inteiro.
     * @param text Texto a ser verificado.
     */
    private void verificarCampoInteger(String text) {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            informationAlert("Erro", "Por favor, insira apenas números!");
            throw e;
        }
    }

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela do acervo.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeAcervo.fxml");
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
        assert btnRemover != null : "fx:id=\"btnRemover\" was not injected: check your FXML file 'telaDeRemocao.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeRemocao.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeRemocao.fxml'.";
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
