/**
 * Controller responsável pela tela de relatórios.
 */
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
    private ListView<Integer> viewNumLivrosAtrasados;

    @FXML
    private ListView<Integer> viewNumLivrosEmp;

    @FXML
    private ListView<Integer> viewNumLivrosReservados;

    /**
     * Ação acionada quando o botão 'Histórico de Empréstimos' é clicado.
     * Abre a tela de histórico de empréstimos.
     * @param event Evento de clique no botão 'Histórico de Empréstimos'.
     */
    @FXML
    void btnHistoricoEmpAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaHistoricoEmprestimo.fxml");
    }

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela do administrador.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaAdministrador.fxml");
    }

    /**
     * Ação acionada quando o botão 'Livro Popular' é clicado.
     * Abre a tela de livro popular.
     * @param event Evento de clique no botão 'Livro Popular'.
     */
    @FXML
    void tbnLivroPopularAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaLivroPopular.fxml");
    }

    /**
     * Método executado ao inicializar a tela.
     */
    @FXML
    void initialize() {
        // Verifica se os elementos do FXML foram injetados corretamente
        assert btnHistoricoEmp != null : "fx:id=\"btnHistoricoEmp\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert tbnLivroPopular != null : "fx:id=\"tbnLivroPopular\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert viewNumLivrosAtrasados != null : "fx:id=\"viewNumLivrosAtrasados\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert viewNumLivrosEmp != null : "fx:id=\"viewNumLivrosEmp\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";
        assert viewNumLivrosReservados != null : "fx:id=\"viewNumLivrosReservados\" was not injected: check your FXML file 'telaDeRelatorio.fxml'.";

        // Exibe o número de livros atrasados, emprestados e reservados nas respectivas ListViews
        mostraView(viewNumLivrosAtrasados, DAO.getEmprestimosDAO().numLivroAtrasado());
        mostraView(viewNumLivrosEmp, DAO.getEmprestimosDAO().numLivrosEmprestados());
        mostraView(viewNumLivrosReservados, DAO.getReservaDAO().numLivrosReservados());
    }

    /**
     * Exibe os dados na ListView especificada.
     * @param localDeExibir ListView onde os dados serão exibidos.
     * @param exibicao Dados a serem exibidos.
     */
    private void mostraView(ListView<Integer> localDeExibir, Integer exibicao) {
        localDeExibir.setItems(FXCollections.observableArrayList(exibicao).sorted());
    }
}
