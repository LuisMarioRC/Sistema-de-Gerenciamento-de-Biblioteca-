/**
 * Controller responsável pela exibição do histórico de empréstimos de um usuário.
 */
package com.example.app.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.UsuarioException;
import com.example.model.Emprestimos;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TelaHistoricoEmprestimo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEnter;

    @FXML
    private Button btnVoltar;

    @FXML
    private ListView<Emprestimos> listViewHistorico;

    @FXML
    private TextField textIdUsuario;

    /**
     * Ação acionada quando o botão 'Enter' é clicado.
     * Exibe o histórico de empréstimos do usuário com o ID especificado.
     * @param event Evento de clique no botão 'Enter'.
     * @throws UsuarioException Se ocorrer um erro relacionado ao usuário.
     */
    @FXML
    void btnEnterAction(ActionEvent event) throws UsuarioException {
        try {
            String idUsuario = textIdUsuario.getText();
            verificaCampos(idUsuario);
            verificaInt(idUsuario);
            Usuario usuario = buscaUsuario(Integer.parseInt(idUsuario));
            mostraView(usuario);
        } catch (Exception e) {
            listViewHistorico.getItems().clear();
            listViewHistorico.requestLayout();
        }
    }

    /**
     * Verifica se o campo de texto contém um valor válido.
     * @param idUsuario Texto a ser verificado.
     */
    private void verificaCampos(String idUsuario) {
        if (idUsuario.isEmpty()) {
            informationAlert("ATENÇÃO", "Por favor, preencha todos os campos");
            throw new IllegalArgumentException("Por favor, preencha todos os campos");
        }
    }

    /**
     * Verifica se o texto contém um valor numérico.
     * @param idUsuario Texto a ser verificado.
     */
    private void verificaInt(String idUsuario) {
        int idUsuarioInt;
        try {
            idUsuarioInt = Integer.parseInt(idUsuario);
        } catch (NumberFormatException e) {
            informationAlert("ERROR", "Atenção, são aceitos apenas IDs numéricos");
            throw e;
        }
    }

    /**
     * Exibe o histórico de empréstimos do usuário na interface gráfica.
     * @param usuario Usuário para o qual o histórico de empréstimos será exibido.
     */
    private void mostraView(Usuario usuario) {
        try {
            ArrayList<Emprestimos> listEmprestio = DAO.getEmprestimosDAO().historicoEmprestimosUsuario(usuario);
            if (listEmprestio != null) {
                // Verifica se a lista de resultados não está vazia
                if (!listEmprestio.isEmpty()) {
                    // Importe a classe javafx.collections.FXCollections para usar o método observableArrayList
                    for (Emprestimos emprestimos : listEmprestio) {
                        listViewHistorico.setItems(FXCollections.observableArrayList(listEmprestio).sorted());
                    }
                } else {
                    // Se não houver resultados, limpa a lista
                    listViewHistorico.getItems().clear();
                    informationAlert("ERROR", "Nenhum resultado encontrado.");
                    throw new IllegalArgumentException();
                }
            } else {
                // Se a lista de resultados for nula, limpa a lista
                listViewHistorico.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            // Trata adequadamente as exceções, se necessário
            e.printStackTrace();
        }
    }

    /**
     * Busca um usuário pelo ID.
     * @param idUsuario ID do usuário a ser buscado.
     * @return O usuário correspondente ao ID.
     * @throws UsuarioException Se ocorrer um erro ao buscar o usuário.
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
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela de relatórios.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRelatorio.fxml");
    }

    /**
     * Ação acionada quando o texto do campo de ID do usuário é inserido.
     * Não possui funcionalidade neste momento.
     * @param event Evento de inserção de texto no campo de ID do usuário.
     */
    @FXML
    void textIdUsuarioAction(ActionEvent event) {
        // Não possui funcionalidade neste momento
    }

    /**
     * Método executado ao inicializar a tela.
     * Verifica se os elementos do FXML foram injetados corretamente.
     */
    @FXML
    void initialize() {
        assert btnEnter != null : "fx:id=\"btnEnter\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
        assert listViewHistorico != null : "fx:id=\"listViewHistorico\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
        assert textIdUsuario != null : "fx:id=\"textIdUsuario\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
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
