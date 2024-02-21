/**
 * Controller responsável pela tela de pesquisa de livros.
 */
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

    /**
     * Ação acionada quando o botão 'Voltar' é clicado.
     * Retorna à tela do usuário ou do bibliotecário, dependendo do tipo de usuário.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void bntVoltarAction(ActionEvent event) {
        Object login = SessionLogin.getUserInSession();
        if (login instanceof Usuario) {
            AbrirProximaTela.proximaTela(event, "telaUsuario.fxml");
        } else if (login instanceof Bibliotecario) {
            AbrirProximaTela.proximaTela(event, "telaBibliotecario.fxml");
        }
    }

    /**
     * Ação acionada quando o botão 'Enter' é clicado.
     * Realiza a pesquisa de livros com base no tipo de pesquisa selecionado.
     * @param event Evento de clique no botão 'Enter'.
     * @throws LivroException Exceção lançada se ocorrer um erro relacionado aos livros.
     */
    @FXML
    void btnEnterAction(ActionEvent event) throws LivroException {
        String tipoDePesquisa = pesquisaSelecionada();
        if (tipoDePesquisa == null) {
            informationAlert("ERROR", "Selecione o tipo da pesquisa");
            throw new IllegalArgumentException();
        }
        mostraView(tipoDePesquisa, textPesquisarLivro.getText());
    }

    /**
     * Exibe a lista de livros correspondente à pesquisa realizada.
     * @param tipoDePesquisa Tipo de pesquisa selecionada.
     * @param termoDePesquisa Termo de pesquisa inserido pelo usuário.
     */
    private void mostraView(String tipoDePesquisa, String termoDePesquisa) {
        try {
            ArrayList<Livro> resultados = returnDaPesquisa(tipoDePesquisa, termoDePesquisa);
            if (resultados != null) {
                if (!resultados.isEmpty()) {
                    livroListView.setItems(FXCollections.observableArrayList(resultados).sorted());
                } else {
                    livroListView.getItems().clear();
                    informationAlert("ERROR", "Nenhum resultado encontrado.");
                }
            } else {
                livroListView.getItems().clear();
                informationAlert("ERROR", "Nenhum resultado encontrado.");
            }
        } catch (LivroException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ação acionada quando um tipo de pesquisa é selecionado no MenuButton.
     * Desmarca os outros RadioMenuItems.
     * @param event Evento de seleção de um tipo de pesquisa.
     */
    @FXML
    void btnMenuPesquisaAction(ActionEvent event) {
        // Método vazio, a lógica está na marcação dos RadioMenuItems
    }

    /**
     * Ação acionada quando o RadioMenuItem 'Pesquisa por Autor' é selecionado.
     * Desmarca os outros RadioMenuItems.
     * @param event Evento de seleção do RadioMenuItem 'Pesquisa por Autor'.
     */
    @FXML
    void btnPequisaAutorAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPequisaAutor);
    }

    /**
     * Ação acionada quando o RadioMenuItem 'Pesquisa por Categoria' é selecionado.
     * Desmarca os outros RadioMenuItems.
     * @param event Evento de seleção do RadioMenuItem 'Pesquisa por Categoria'.
     */
    @FXML
    void btnPesquisaCategoriaAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPesquisaCategoria);
    }

    /**
     * Ação acionada quando o RadioMenuItem 'Pesquisa por ISBN' é selecionado.
     * Desmarca os outros RadioMenuItems.
     * @param event Evento de seleção do RadioMenuItem 'Pesquisa por ISBN'.
     */
    @FXML
    void btnPesquisaISBNAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPesquisaISBN);
    }

    /**
     * Ação acionada quando o RadioMenuItem 'Pesquisa por Título' é selecionado.
     * Desmarca os outros RadioMenuItems.
     * @param event Evento de seleção do RadioMenuItem 'Pesquisa por Título'.
     */
    @FXML
    void btnPesquisaTituloAction(ActionEvent event) {
        descelecionarOutrosRadios(btnPesquisaTitulo);
    }

    /**
     * Ação acionada quando o campo de pesquisa de livros é modificado.
     * @param event Evento de modificação do campo de pesquisa de livros.
     */
    @FXML
    void textPesquisarLivroAction(ActionEvent event) {
        // Método vazio, a lógica de pesquisa é acionada pelo botão 'Enter'
    }

    /**
     * Exibe um alerta de informação.
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

    /**
     * Desmarca todos os outros RadioMenuItems, exceto o selecionado.
     * @param selecionado RadioMenuItem selecionado.
     */
    private void descelecionarOutrosRadios(RadioMenuItem selecionado) {
        for (MenuItem item : btnMenuPesquisa.getItems()) {
            if (item instanceof RadioMenuItem && item != selecionado) {
                ((RadioMenuItem) item).setSelected(false);
            }
        }
    }

    /**
     * Retorna os resultados da pesquisa com base no tipo de pesquisa e no termo inserido.
     * @param selecaoDaPesquisa Tipo de pesquisa selecionada.
     * @param pesquisa Termo de pesquisa inserido pelo usuário.
     * @return Lista de livros correspondente à pesquisa.
     * @throws LivroException Exceção lançada se ocorrer um erro relacionado aos livros.
     */
    private ArrayList<Livro> returnDaPesquisa(String selecaoDaPesquisa, String pesquisa) throws LivroException {
        try {
            switch (selecaoDaPesquisa) {
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
        } catch (Exception e) {
            informationAlert("ERROR", "Busca não encontrada!");
            throw e;
        }
    }

    /**
     * Retorna o texto do RadioMenuItem selecionado.
     * @return Texto do RadioMenuItem selecionado.
     */
    private String pesquisaSelecionada() {
        for (MenuItem item : btnMenuPesquisa.getItems()) {
            if (item instanceof RadioMenuItem radioMenuItem) {
                if (radioMenuItem.isSelected()) {
                    return radioMenuItem.getText();
                }
            }
        }
        return null;
    }

    /**
     * Método executado ao inicializar a tela.
     */
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
                btnMenuPesquisa.setText(selectedRadioMenuItem.getText());
            }
        });
    }
}
