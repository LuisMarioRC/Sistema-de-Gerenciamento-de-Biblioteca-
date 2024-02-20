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


    private void remover(Livro livro) throws LivroException {
        try{
            DAO.getLivroDAO().excluir(livro);
        } catch (LivroException e) {
            informationAlert("ERROE", "Livro não encontrado");
            throw e;
        }
    }


    private Livro buscarLivro (Integer idLivro) throws LivroException {
        Livro livro;
        try{
            livro = DAO.getLivroDAO().encontrarPorID(idLivro);
        }catch (Exception e){
            informationAlert("ERROR", "Livro não encontrado");
            throw e;
        }
        return livro;
    }



    private void verificarCampoVazio(String text) {
        if (text.isEmpty()) {
            informationAlert("ERROR", "Por favor, insira o texto!");
            throw new IllegalArgumentException();
        }
    }

    private void verificarCampoInteger(String text){
        int campoInt;
        try{
            campoInt= Integer.parseInt(text);
        }catch (NumberFormatException e){
            informationAlert("Erro", "Por favor, insira apenas números!");
            throw e;
        }
    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaDeAcervo.fxml");
    }

    @FXML
    void textIdLivroAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnRemover != null : "fx:id=\"btnRemover\" was not injected: check your FXML file 'telaDeRemocao.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeRemocao.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeRemocao.fxml'.";

    }

    private void informationAlert(String title,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }


}
