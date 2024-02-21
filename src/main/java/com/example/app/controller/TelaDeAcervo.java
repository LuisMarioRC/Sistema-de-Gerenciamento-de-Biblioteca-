/**
 * Controller responsável pela tela de acervo.
 */
package com.example.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.MainLogin;
import com.example.dao.DAO;
import com.example.model.Livro;
import com.example.utils.AbrirProximaTela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TelaDeAcervo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnVoltar;

    @FXML
    private HBox hBoxMenu;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem menuItemAdcionar;

    @FXML
    private MenuItem menuItemEditar;

    @FXML
    private MenuItem radioItemRemover;

    @FXML
    private ListView<Livro> viewLivros;

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela de administrador.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaAdministrador.fxml" );

    }

    /**
     * Ação acionada quando o MenuButton é clicado.
     * @param event Evento de clique no MenuButton.
     */
    @FXML
    void menuButtonAction(ActionEvent event) {

    }

    /**
     * Ação acionada quando a opção 'Adicionar' do MenuButton é selecionada.
     * Abre a tela de registro de livros.
     * @param event Evento de seleção da opção 'Adicionar'.
     */
    @FXML
    void menuItemAdcionarAction(ActionEvent event) {
        abrirProximaTelaPeloMenu("resgistroLivros.fxml");

    }

    /**
     * Ação acionada quando a opção 'Editar' do MenuButton é selecionada.
     * Abre a tela de atualização de livro.
     * @param event Evento de seleção da opção 'Editar'.
     */
    @FXML
    void menuItemEditarAction(ActionEvent event) {
        abrirProximaTelaPeloMenu("telaAtualizacaoLivro.fxml");

    }

    /**
     * Ação acionada quando a opção 'Remover' do MenuButton é selecionada.
     * Abre a tela de remoção de livro.
     * @param event Evento de seleção da opção 'Remover'.
     */
    @FXML
    void menuItemRemoverAction(ActionEvent event) {
        abrirProximaTelaPeloMenu("telaDeRemocao.fxml");
    }

    /**
     * Abre a próxima tela de acordo com o nome fornecido.
     * @param nome Nome do arquivo FXML da próxima tela.
     */
    @FXML
    private void abrirProximaTelaPeloMenu(String nome) {
        try {
            // Carregar o arquivo FXML da próxima tela
            FXMLLoader loader = new FXMLLoader(MainLogin.class.getResource(nome));
            Parent root = loader.load();

            // Criar uma nova cena com a próxima tela
            Scene scene = new Scene(root);

            // Obter o palco (stage) do MenuItem usando o método getScene().getWindow()
            Stage stage = (Stage) menuButton.getScene().getWindow();

            // Definir a nova cena no palco (stage)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método de inicialização do controlador.
     * Mostra a lista de livros disponíveis.
     */
    @FXML
    void initialize() {
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";
        assert hBoxMenu != null : "fx:id=\"hBoxMenu\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";
        assert menuItemAdcionar != null : "fx:id=\"menuItemAdcionar\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";
        assert menuItemEditar != null : "fx:id=\"menuItemEditar\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";
        assert radioItemRemover != null : "fx:id=\"radioItemRemover\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";
        assert viewLivros != null : "fx:id=\"viewLivros\" was not injected: check your FXML file 'telaDeAcervo.fxml'.";

        mostraView();
    }

    /**
     * Exibe um alerta de informação.
     * @param title Título do alerta.
     * @param texto Texto do alerta.
     */
    private void informationAlert(String title,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    /**
     * Mostra a lista de livros disponíveis.
     */
    private void mostraView() {
        ArrayList<Livro> resultados = DAO.getLivroDAO().encontrarTodos();
        if (resultados != null) {
            // Verifique se a lista de resultados não está vazia
            if (!resultados.isEmpty()) {
                // Importe a classe javafx.collections.FXCollections para usar o método observableArrayList
                for (Livro livro : resultados) {
                    viewLivros.setItems(FXCollections.observableArrayList(resultados).sorted());
                }
            } else {
                // Se não houver resultados, limpe a lista
                viewLivros.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
            }
        } else {
            // Se a lista de resultados for nula, limpe a lista
            viewLivros.getItems().clear();
            informationAlert("ERROR", "Nenhum resultado encontrado.");
        }
    }

}
