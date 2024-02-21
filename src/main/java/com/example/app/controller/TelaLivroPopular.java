/**
 * Controller responsável pela exibição dos livros mais populares.
 */
package com.example.app.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.model.Livro;
import com.example.utils.AbrirProximaTela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TelaLivroPopular {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnVoltar;

    @FXML
    private ListView<Livro> listViewLivroPupular;

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela de relatórios.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRelatorio.fxml");
    }

    /**
     * Método executado ao inicializar a tela.
     * Verifica se os elementos do FXML foram injetados corretamente e exibe os livros mais populares.
     */
    @FXML
    void initialize() {
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaLivroPopular.fxml'.";
        assert listViewLivroPupular != null : "fx:id=\"listViewLivroPupular\" was not injected: check your FXML file 'telaLivroPopular.fxml'.";
        mostraView();
    }

    /**
     * Exibe os livros mais populares na interface gráfica.
     */
    private void mostraView() {
        try {
            ArrayList<Livro> listPopular = DAO.getEmprestimosDAO().livroMaisPolular();
            if (listPopular != null) {
                // Verifica se a lista de resultados não está vazia
                if (!listPopular.isEmpty()) {
                    // Importa a classe javafx.collections.FXCollections para usar o método observableArrayList
                    for (Livro livro : listPopular) {
                        listViewLivroPupular.setItems(FXCollections.observableArrayList(listPopular).sorted());
                    }
                } else {
                    // Se não houver resultados, limpa a lista
                    listViewLivroPupular.getItems().clear();
                    informationAlert("ERROR", "Nenhum resultado encontrado.");
                    throw new IllegalArgumentException();
                }
            } else {
                // Se a lista de resultados for nula, limpa a lista
                listViewLivroPupular.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            // Trata adequadamente as exceções, se necessário
            e.printStackTrace();
        }
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
