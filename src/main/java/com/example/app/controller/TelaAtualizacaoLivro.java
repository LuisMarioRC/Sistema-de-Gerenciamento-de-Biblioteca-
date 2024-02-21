/**
 * Controller responsável pela tela de atualização de informações de livros.
 */
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

    /**
     * Ação acionada quando o texto de informação é modificado.
     * @param event Evento de modificação do texto de informação.
     */
    @FXML
    void textInformacaoAction(ActionEvent event) {

    }

    /**
     * Ação acionada quando o botão 'Atualizar' é clicado.
     * Atualiza as informações do livro selecionado.
     * @param event Evento de clique no botão 'Atualizar'.
     * @throws LivroException Exceção lançada se ocorrer um erro durante a atualização do livro.
     */
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

        atualizarInformacao(livro,atualizacao,novainformacao);

        listViewLivro.setItems(FXCollections.observableArrayList(livro).sorted());

    }

    /**
     * Verifica o tipo de atualização selecionado e valida a nova informação.
     * @param tipo Tipo de atualização selecionado.
     * @param texto Nova informação a ser atualizada.
     */
    private void verificaAtualizacao(String tipo, String texto){
        if (Objects.equals(tipo, "New ISBN")){
            verificarCampoInteger(texto);
        }else if (Objects.equals(tipo, "New Ano Publicado")){
            verificarCampoInteger(texto);
        }
    }

    /**
     * Atualiza as informações do livro de acordo com o tipo de atualização selecionado.
     * @param livro Livro a ser atualizado.
     * @param tipo Tipo de atualização.
     * @param text Nova informação.
     * @throws LivroException Exceção lançada se ocorrer um erro durante a atualização do livro.
     */
    private void atualizarInformacao(Livro livro, String tipo, String text) throws LivroException {
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

    /**
     * Busca um livro pelo ID.
     * @param idLivro ID do livro a ser buscado.
     * @return O livro encontrado.
     * @throws LivroException Exceção lançada se o livro não for encontrado.
     */
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

    /**
     * Ação acionada quando o botão 'Buscar' é clicado.
     * Busca um livro pelo ID.
     * @param event Evento de clique no botão 'Buscar'.
     * @throws LivroException Exceção lançada se o livro não for encontrado.
     */
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

    /**
     * Verifica se o campo de texto está vazio.
     * @param text Texto a ser verificado.
     */
    private void verificarCampoVazio(String text){
        if (text.isEmpty()){
            informationAlert("ERROR", "Por favor, insira o texto!");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Verifica se o campo de texto contém apenas números.
     * @param text Texto a ser verificado.
     */
    private void verificarCampoInteger(String text){
        int campoInt;
        try{
            campoInt= Integer.parseInt(text);
        }catch (NumberFormatException e){
            informationAlert("Erro", "Por favor, insira apenas números!");
            throw e;
        }
    }

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela de acervo.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaDeAcervo.fxml");

    }

    /**
     * Ação acionada quando uma opção do MenuButton de edição é selecionada.
     * @param event Evento de seleção de uma opção do MenuButton de edição.
     */
    @FXML
    void menuBtnEdicaoAction(ActionEvent event) {

    }

    /**
     * Ação acionada quando o RadioMenuItem de ano de publicação é selecionado.
     * Desseleciona os outros RadioMenuItems.
     * @param event Evento de seleção do RadioMenuItem de ano de publicação.
     */
    @FXML
    void radioItemAnoPublicacaoAction(ActionEvent event) {
        descelecionarOutrosRadios(radioItemAnoPublicacao);

    }

    // Métodos similares para os outros RadioMenuItems...

    /**
     * Desseleciona os outros RadioMenuItems.
     * @param selecionado RadioMenuItem selecionado.
     */
    private void descelecionarOutrosRadios(RadioMenuItem selecionado) {
        for (MenuItem item : menuBtnEdicao.getItems()) {
            if (item instanceof RadioMenuItem && item != selecionado) {
                ((RadioMenuItem) item).setSelected(false);
            }
        }
    }

    @FXML
    void textIdLivroAction(ActionEvent event) {

    }

    /**
     * Método de inicialização do controlador.
     * Inicializa o ToggleGroup e adiciona um listener para o evento de seleção de opção do MenuButton de edição.
     * @throws LivroException Exceção lançada se ocorrer um erro durante a inicialização do controlador.
     */
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

    /**
     * Retorna o tipo de atualização selecionado.
     * @return Tipo de atualização selecionado.
     */
    private String atualizacaoSelecionada() {
        for (MenuItem item : menuBtnEdicao.getItems()) {
            if (item instanceof RadioMenuItem radioMenuItem) {
                if (radioMenuItem.isSelected()) {
                    return radioMenuItem.getText(); // Retorna o texto do RadioMenuItem selecionado
                }
            }
        }
        return null; // Retorna null se nenhum RadioMenuItem estiver selecionado
    }

    /**
     * Exibe um alerta de informação.
     * @param title Título do alerta.
     * @param texto Texto do alerta.
     */
    private void informationAlert(String title,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

}
