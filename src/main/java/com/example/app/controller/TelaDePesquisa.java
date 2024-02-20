package com.example.app.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.model.Bibliotecario;
import com.example.model.Livro;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import com.example.utils.SessionLogin;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class TelaDePesquisa {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bntVoltar;

    @FXML
    private Button btnEnter;

    @FXML
    private MenuButton btnMenuPesquisa;

    @FXML
    private RadioMenuItem btnPequisaAutor;

    @FXML
    private RadioMenuItem btnPesquisaCategoria;

    @FXML
    private RadioMenuItem btnPesquisaISBN;

    @FXML
    private RadioMenuItem btnPesquisaTitulo;

    @FXML
    private TextField textPesquisarLivro;

    @FXML
    private HBox vboxPesquisa;

    @FXML
    private ListView<Livro> livroListView;


    @FXML
    void bntVoltarAction(ActionEvent event) {

        Object login = SessionLogin.getUserInSession();
        if (login instanceof Usuario){
            AbrirProximaTela.proximaTela(event, "telaUsuario.fxml");
        }
        else if (login instanceof Bibliotecario){
            AbrirProximaTela.proximaTela(event, "telaBibliotecario.fxml");
        }
    }


    @FXML
    void btnEnterAction(ActionEvent event) throws LivroException {
        String tipoDePesquisa = pesquisaSelecionada();
        if (tipoDePesquisa == null) {
            informationAlert("ERROR", "Selecione o tipo da pesquisa");
            throw new IllegalArgumentException();
        }
        mostraView(tipoDePesquisa,textPesquisarLivro.getText());
    }

    private void mostraView(String tipoDePesquisa, String termoDePesquisa){
        try {
            ArrayList<Livro> resultados = returnDaPesquisa(tipoDePesquisa, termoDePesquisa);
            if (resultados != null) {
                // Verifique se a lista de resultados não está vazia
                if (!resultados.isEmpty()) {
                    // Importe a classe javafx.collections.FXCollections para usar o método observableArrayList
                    for (Livro livro: resultados) {
                        livroListView.setItems(FXCollections.observableArrayList(resultados).sorted());
                    }
                } else {
                    // Se não houver resultados, limpe a lista
                    livroListView.getItems().clear();
                    informationAlert("ERROR", "Nenhum resultado encontrado.");
                }
            } else {
                // Se a lista de resultados for nula, limpe a lista
                livroListView.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
            }
        } catch (LivroException e) {
            // Trate adequadamente as exceções, se necessário
            e.printStackTrace(); // Ou qualquer tratamento específico de erro
        }

    }


    @FXML
    void btnMenuPesquisaAction(ActionEvent event) {

    }

    @FXML
    void btnPequisaAutorAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPequisaAutor);
    }

    @FXML
    void btnPesquisaCategoriaAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPesquisaCategoria);
    }

    @FXML
    void btnPesquisaISBNAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPesquisaISBN);
    }

    @FXML
    void btnPesquisaTituloAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPesquisaTitulo);
    }

    @FXML
    void textPesquisarLivroAction(ActionEvent event) {


    }

    private void informationAlert(String title,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
    /**
     * Método responsável para que apenas um radioMenuItem seja selecionado
     * @param selecionado
     */
    private void descelecionarOutrosRadios(RadioMenuItem selecionado) {
        for (javafx.scene.control.MenuItem item : btnMenuPesquisa.getItems()) {
            if (item instanceof RadioMenuItem && item != selecionado) {
                ((RadioMenuItem) item).setSelected(false);
            }
        }
    }

    /**
     * Método que retorna o resultado da pesquisa por livros.
     * @param selecaoDapesquisa
     * @param pesquisa
     * @return
     * @throws LivroException
     */
    private ArrayList<Livro> returnDaPesquisa(String selecaoDapesquisa, String pesquisa) throws LivroException {
        try {
            switch (selecaoDapesquisa) {
                case "Título" -> {
                    return DAO.getLivroDAO().pesquisaPorTitulo(pesquisa);
                }
                case "Autor" -> {
                    return DAO.getLivroDAO().pesquisaPorAutor(pesquisa);
                }
                case "ISBN" -> {
                    return DAO.getLivroDAO().pesquisaPorisbn(Integer.parseInt(pesquisa));
                }
                case "Categoria" -> {
                    return DAO.getLivroDAO().pesquisaPorCategoria(pesquisa);
                }
                default -> {
                    return null;
                }
            }
        }catch (Exception e){
            informationAlert("ERROR", "Busca não econtrada!");
            throw e;
        }
    }

    /**
     * Retorna o menuItem seleciona para pesquisa
     * @return
     */
    private String pesquisaSelecionada() {
        for (javafx.scene.control.MenuItem item : btnMenuPesquisa.getItems()) {
            if (item instanceof RadioMenuItem radioMenuItem) {
                if (radioMenuItem.isSelected()) {
                    return radioMenuItem.getText(); // Retorna o texto do RadioMenuItem selecionado
                }
            }
        }
        return null; // Retorna null se nenhum RadioMenuItem estiver selecionado
    }

    @FXML
    void initialize() {

        // Cria um novo ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();

        // Adiciona os RadioMenuItems ao ToggleGroup
        btnPesquisaTitulo.setToggleGroup(toggleGroup);
        btnPequisaAutor.setToggleGroup(toggleGroup);
        btnPesquisaISBN.setToggleGroup(toggleGroup);
        btnPesquisaCategoria.setToggleGroup(toggleGroup);

        // Adiciona um listener de propriedade ao selectedToggleProperty do ToggleGroup
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioMenuItem selectedRadioMenuItem = (RadioMenuItem) newToggle;
                btnMenuPesquisa.setText(selectedRadioMenuItem.getText()); // Define o texto do MenuButton
            }
        });

    }

}
