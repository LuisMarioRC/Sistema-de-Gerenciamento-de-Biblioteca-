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

    @FXML
    void btnEnterAction(ActionEvent event) throws UsuarioException {
        try {
            String idUsuario = textIdUsuario.getText();
            verificaCampos(idUsuario);
            verificaInt(idUsuario);
            Usuario usuario = buscaUsuario(Integer.parseInt(idUsuario));
            mostraView(usuario);
        }catch (Exception e){
            listViewHistorico.getItems().clear();
            listViewHistorico.requestLayout();
        }
    }

    private void verificaCampos(String idUsuario){
        if (idUsuario.isEmpty()){
            informationAlert("ATENÇÂO", "Por favor, preencha todos os campos");
            throw new IllegalArgumentException("Por favor, preencha todos os campos");
        }

    }


    private void verificaInt(String idUsuario){
        int idUsuarioInt;
        try{
            idUsuarioInt = Integer.parseInt(idUsuario);
        }catch (NumberFormatException e ){
            informationAlert("ERROR", "Atenção, são aceita apenas IDs numéricos");
            throw e;
        }

    }

    private void mostraView( Usuario usuario){
        try {
            ArrayList<Emprestimos> listEmprestio = DAO.getEmprestimosDAO().historicoEmprestimosUsuario(usuario);
            if (listEmprestio != null) {
                // Verifique se a lista de resultados não está vazia
                if (!listEmprestio.isEmpty()) {
                    // Importe a classe javafx.collections.FXCollections para usar o método observableArrayList
                    for (Emprestimos emprestimos : listEmprestio) {
                        listViewHistorico.setItems(FXCollections.observableArrayList(listEmprestio).sorted());
                    }
                } else {
                    // Se não houver resultados, limpe a lista
                    listViewHistorico.getItems().clear();
                    informationAlert("ERROR", "Nenhum resultado encontrado.");
                    throw new IllegalArgumentException();
                }
            } else {
                // Se a lista de resultados for nula, limpe a lista
                listViewHistorico.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            // Trate adequadamente as exceções, se necessário
            e.printStackTrace(); // Ou qualquer tratamento específico de erro
        }

    }
    private Usuario buscaUsuario(Integer idUsuario) throws UsuarioException {
        Usuario usuario;
        try{
            usuario = DAO.getUsuarioDAO().encontrarPorID(idUsuario);
        }catch (UsuarioException e){
            informationAlert("ERROR", "Usuário não encontrado");
            throw e;
        }
        return usuario;
    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeRelatorio.fxml");

    }

    @FXML
    void textIdUsuarioAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnEnter != null : "fx:id=\"btnEnter\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
        assert listViewHistorico != null : "fx:id=\"listViewHistorico\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";
        assert textIdUsuario != null : "fx:id=\"textIdUsuario\" was not injected: check your FXML file 'telaHistoricoEmprestimo.fxml'.";

    }
    private void informationAlert(String title,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }


}
