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

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRelatorio.fxml");
    }

    @FXML
    void initialize() {
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaLivroPopular.fxml'.";
        assert listViewLivroPupular != null : "fx:id=\"listViewLivroPupular\" was not injected: check your FXML file 'telaLivroPopular.fxml'.";
        mostraView();

    }

    private void mostraView() {
        try {
            ArrayList<Livro> listPopular = DAO.getEmprestimosDAO().livroMaisPolular();
            if (listPopular != null) {
                // Verifique se a lista de resultados não está vazia
                if (!listPopular.isEmpty()) {
                    // Importe a classe javafx.collections.FXCollections para usar o método observableArrayList
                    for (Livro livro : listPopular) {
                        listViewLivroPupular.setItems(FXCollections.observableArrayList(listPopular).sorted());
                    }
                } else {
                    // Se não houver resultados, limpe a lista
                    listViewLivroPupular.getItems().clear();
                    informationAlert("ERROR", "Nenhum resultado encontrado.");
                    throw new IllegalArgumentException();
                }
            } else {
                // Se a lista de resultados for nula, limpe a lista
                listViewLivroPupular.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            // Trate adequadamente as exceções, se necessário
            e.printStackTrace(); // Ou qualquer tratamento específico de erro
        }
    }


        private void informationAlert (String title, String texto){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(texto);
            alert.showAndWait();
        }



}
