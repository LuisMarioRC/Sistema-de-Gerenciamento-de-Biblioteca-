package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.UsuarioException;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TelaDeBloqueio {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox HboxBloqueios;

    @FXML
    private Button btnBloquear;

    @FXML
    private Button btnDesbloquear;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textId;

    @FXML
    private VBox vBoxText;

    @FXML
    void btnBloquearAction(ActionEvent event) throws UsuarioException {
        String id = textId.getText();

        verificaCampo(id);
        verificaIdInt(id);
        Usuario usuarioBloqeuado = buscarPorId(id);

        usuarioBloqeuado.bloquearConta(usuarioBloqeuado);
        informationAlert("Bloqueio", "O usuário "+ usuarioBloqeuado.getNome()+ "foi bloqueado com sucesso");
        textId.clear();
    }

    private Usuario buscarPorId(String id) throws UsuarioException {
        Usuario usuario;
        try {
            usuario = DAO.getUsuarioDAO().encontrarPorID(Integer.parseInt(id));
        }catch (Exception e ){
            informationAlert("ERROR","Esse ID não foi encontrado!");
            throw e;
        }
        return usuario;
    }

    private void verificaIdInt(String id){
        int idInt;
        try{
            idInt= Integer.parseInt(id);
        }catch(NumberFormatException e){
            informationAlert("ERROR", "Por favor, digite apenas números");
            throw e;
        }

    }

    private void verificaCampo(String id){
        if (id.isEmpty()){
            informationAlert("ERROR","Por favor, informe o id do usuário a ser bloqueado");
            throw new IllegalArgumentException();
        }
    }

    @FXML
    void btnDesbloquearAction(ActionEvent event) throws UsuarioException {
        String id = textId.getText();

        verificaCampo(id);
        verificaIdInt(id);
        Usuario usuarioBloqeuado = buscarPorId(id);

        usuarioBloqeuado.desbloqueiaConta(usuarioBloqeuado);
        informationAlert("Desbloqueio", "O usuário "+ usuarioBloqeuado.getNome() + " foi desbloqueado com sucesso!");
        textId.clear();
    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaAdministrador.fxml");

    }

    @FXML
    void textIdAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert HboxBloqueios != null : "fx:id=\"HboxBloqueios\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert btnBloquear != null : "fx:id=\"btnBloquear\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert btnDesbloquear != null : "fx:id=\"btnDesbloquear\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert textId != null : "fx:id=\"textId\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";
        assert vBoxText != null : "fx:id=\"vBoxText\" was not injected: check your FXML file 'TelaDeBloqueio.fxml'.";

    }

    private void informationAlert(String title, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }


}
