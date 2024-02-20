package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.utils.AbrirProximaTela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TelaDeRelatorio {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnHistoricoEmp;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button tbnLivroPopular;

    @FXML
    private ListView<?> viewNumLivrosAtrasados;

    @FXML
    private ListView<?> viewNumLivrosEmp;

    @FXML
    private ListView<?> viewNumLivrosReservados;

    @FXML
    void btnHistoricoEmpAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaHistoricoEmprestimo.fxml");

    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaAdministrador.fxml");

    }

    @FXML
    void tbnLivroPopularAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaLivroPopular.fxml");

    }

    @FXML
    void initialize() {
        assert btnHistoricoEmp != null : "fx:id=\"btnHistoricoEmp\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert tbnLivroPopular != null : "fx:id=\"tbnLivroPopular\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert viewNumLivrosAtrasados != null : "fx:id=\"viewNumLivrosAtrasados\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert viewNumLivrosEmp != null : "fx:id=\"viewNumLivrosEmp\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert viewNumLivrosReservados != null : "fx:id=\"viewNumLivrosReservados\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        mostraView((ListView<Integer>) viewNumLivrosAtrasados,DAO.getEmprestimosDAO().numLivroAtrasado());
        mostraView((ListView<Integer>) viewNumLivrosEmp, DAO.getEmprestimosDAO().numLivrosEmprestados());
        mostraView((ListView<Integer>) viewNumLivrosReservados, DAO.getReservaDAO().numLivrosReservados());
    }

    private void mostraView(ListView<Integer> localDeExibir, Integer exibicao){
        localDeExibir.setItems(FXCollections.observableArrayList(exibicao).sorted());
    }


}
