package com.example.app.controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.model.Livro;
import com.example.utils.AbrirProximaTela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TelaAtualizacaoLivro {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVoltar;

    @FXML
    private ListView<Livro> listViewLivro;

    @FXML
    private MenuButton menuBtnEdicao;

    @FXML
    private RadioMenuItem radioItemAnoPublicacao;

    @FXML
    private RadioMenuItem radioItemAutor;

    @FXML
    private RadioMenuItem radioItemCategoria;

    @FXML
    private RadioMenuItem radioItemEditora;

    @FXML
    private RadioMenuItem radioItemISBN;

    @FXML
    private RadioMenuItem radioItemNome;

    @FXML
    private TextField textIdLivro;

    @FXML
    private TextField textInformacao;

    @FXML
    void textInformacaoAction(ActionEvent event) {

    }

    @FXML
    void btnAtualizarAction(ActionEvent event) throws LivroException {
        String idLivro = textIdLivro.getText();
        String novainformacao =textInformacao.getText();
        verificarCampoVazio(idLivro);
        verificarCampoInteger(idLivro);
        verificarCampoVazio(novainformacao);

        Livro livro = buscarLivro(Integer.parseInt(idLivro));
        String atualizacao = atualizacaoSelecionada();

        verificaAtualizacao(atualizacao,novainformacao);

        atualizarInformcao(livro,atualizacao,novainformacao);

        listViewLivro.setItems(FXCollections.observableArrayList(livro).sorted());

    }

    private void verificaAtualizacao(String tipo, String texto){
        if (Objects.equals(tipo, "New ISBN")){
            verificarCampoInteger(texto);
        }else if (Objects.equals(tipo, "New Ano Publicado")){
            verificarCampoInteger(texto);
        }
    }


    private void atualizarInformcao(Livro livro, String tipo, String text) throws LivroException {
        switch (tipo) {
            case "New nome" ->{
                livro.setTitulo(text);
                DAO.getLivroDAO().atualizar(livro);
            } case "New autor" -> {
                livro.setAutor(text);
                DAO.getLivroDAO().atualizar(livro);
            } case "New ISBN" -> {
                livro.setIsbn(Integer.parseInt(text));
                DAO.getLivroDAO().atualizar(livro);
            } case "New editora" ->{
                livro.setEditora(text);
                DAO.getLivroDAO().atualizar(livro);
            }case "New Ano Publicado" -> {
                livro.setAnoDePublicacao(Integer.parseInt(text));
                DAO.getLivroDAO().atualizar(livro);
            }case "New Categoria" -> {
                livro.setCategoria(text);
                DAO.getLivroDAO().atualizar(livro);
            }
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




    @FXML
    void btnBuscarAction(ActionEvent event) throws LivroException {
        String idLivro = textIdLivro.getText();
        verificarCampoVazio(idLivro);
        verificarCampoInteger(idLivro);
        Livro livro = buscarLivro(Integer.parseInt(idLivro));
        try {
            listViewLivro.setItems(FXCollections.observableArrayList(livro).sorted());
        } catch (Exception ignored){
        }

    }

    private void verificarCampoVazio(String text){
        if (text.isEmpty()){
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
        AbrirProximaTela.proximaTela(event, "telaDeAcervo.fxml");

    }

    @FXML
    void menuBtnEdicaoAction(ActionEvent event) {

    }

    @FXML
    void radioItemAnoPublicacaoAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemAnoPublicacao);

    }

    @FXML
    void radioItemAutorAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemAutor);

    }

    @FXML
    void radioItemCategoriaAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemCategoria);

    }

    @FXML
    void radioItemEditoraAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemEditora);

    }

    @FXML
    void radioItemISBNAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemISBN);

    }

    @FXML
    void radioItemNomeAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemNome);

    }

    private void descelecionarOutrosRadios(RadioMenuItem selecionado) {
        for (javafx.scene.control.MenuItem item : menuBtnEdicao.getItems()) {
            if (item instanceof RadioMenuItem && item != selecionado) {
                ((RadioMenuItem) item).setSelected(false);
            }
        }
    }

    @FXML
    void textIdLivroAction(ActionEvent event) {

    }

    @FXML
    void initialize() throws LivroException {
        assert btnAtualizar != null : "fx:id=\"btnAtualizar\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert btnBuscar != null : "fx:id=\"btnBuscar\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert listViewLivro != null : "fx:id=\"listViewLivro\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert menuBtnEdicao != null : "fx:id=\"menuBtnEdicao\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert radioItemAnoPublicacao != null : "fx:id=\"radioItemAnoPublicacao\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert radioItemAutor != null : "fx:id=\"radioItemAutor\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert radioItemCategoria != null : "fx:id=\"radioItemCategoria\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert radioItemEditora != null : "fx:id=\"radioItemEditora\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert radioItemISBN != null : "fx:id=\"radioItemISBN\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert radioItemNome != null : "fx:id=\"radioItemNome\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaAtualizacaoLivro.fxml'.";
        // Cria um novo ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();

        // Adiciona os RadioMenuItems ao ToggleGroup
        radioItemNome.setToggleGroup(toggleGroup);
        radioItemAutor.setToggleGroup(toggleGroup);
        radioItemEditora.setToggleGroup(toggleGroup);
        radioItemISBN.setToggleGroup(toggleGroup);
        radioItemISBN.setToggleGroup(toggleGroup);
        radioItemAnoPublicacao.setToggleGroup(toggleGroup);
        radioItemCategoria.setToggleGroup(toggleGroup);

        // Adiciona um listener de propriedade ao selectedToggleProperty do ToggleGroup
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioMenuItem selectedRadioMenuItem = (RadioMenuItem) newToggle;
                menuBtnEdicao.setText(selectedRadioMenuItem.getText()); // Define o texto do MenuButton
            }
        });
    }

    private String atualizacaoSelecionada() {
        for (javafx.scene.control.MenuItem item : menuBtnEdicao.getItems()) {
            if (item instanceof RadioMenuItem radioMenuItem) {
                if (radioMenuItem.isSelected()) {
                    return radioMenuItem.getText(); // Retorna o texto do RadioMenuItem selecionado
                }
            }
        }
        return null; // Retorna null se nenhum RadioMenuItem estiver selecionado
    }

    private void informationAlert(String title,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

}
