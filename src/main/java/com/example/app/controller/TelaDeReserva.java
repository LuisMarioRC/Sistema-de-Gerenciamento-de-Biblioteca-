package com.example.app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Reserva;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import com.example.utils.SessionLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TelaDeReserva {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnReservar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;


    @FXML
    private VBox vBoxTexts;


    @FXML
    void btnReservarAction(ActionEvent event) throws UsuarioException, LivroException, ReservaException {
        try {
            String idLivro = textIdLivro.getText();
            Object login = SessionLogin.getUserInSession();
            Usuario usuario =(Usuario) login;

            verificaCampos(idLivro);
            verificaCamposInt(idLivro);

            reservar(Integer.parseInt(idLivro), usuario);
        }catch(Exception e){
            textIdLivro.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro foi reservado com sucesso!");
        textIdLivro.clear();

    }

    private void reservar(Integer idLivro, Usuario usuario) throws LivroException, UsuarioException, ReservaException {
        LocalDate dataHoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataHoje.format(formatter);
        try{
            Reserva reserva= DAO.getReservaDAO().criar(
                    new Reserva(idLivro,usuario,dataFormatada));
        } catch (LivroException | UsuarioException | ReservaException e) {
            alertException("ERROR", e);
            throw e;
        }
    }

    private void verificaCamposInt(String idLivro){
        try{
            int idLivroInt= Integer.parseInt(idLivro);
        }catch (NumberFormatException e){
            informationAlert("ERROR", "Digite apenas números");
            throw e;
        }
    }

    private void verificaCampos(String idLivro){
        if (idLivro.isEmpty() ){
            informationAlert("ERROR", "Por favor, preecha todos os campos");
            throw new IllegalArgumentException();
        }
    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaUsuario.fxml");

    }

    @FXML
    void textIdLivroAction(ActionEvent event) {

    }


    @FXML
    void initialize() {
        assert btnReservar != null : "fx:id=\"btnReservar\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeReservar.fxml'.";
        assert vBoxTexts != null : "fx:id=\"vBoxTexts\" was not injected: check your FXML file 'telaDeReservar.fxml'.";

    }
    private void alertException(String title, Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    private void informationAlert(String title, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

}
