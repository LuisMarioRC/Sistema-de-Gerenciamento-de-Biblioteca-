/**
 * Controller responsável pelo registro de livros no sistema.
 */
package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.model.Administrador;
import com.example.model.Bibliotecario;
import com.example.model.Livro;
import com.example.utils.AbrirProximaTela;
import com.example.utils.SessionLogin;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class RegistroLivros {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bntVoltar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField textAnoPublicacao;

    @FXML
    private TextField textAutor;

    @FXML
    private TextField textCategoria;

    @FXML
    private TextField textEditora;

    @FXML
    private TextField textISBN;

    @FXML
    private TextField textTitulo;

    @FXML
    private VBox vboxRegistro;

    /**
     * Método acionado ao clicar no botão 'Voltar'.
     * Retorna à tela anterior dependendo do tipo de usuário logado.
     * @param event Evento de clique no botão 'Voltar'.
     */
    @FXML
    void bntVoltarAction(ActionEvent event) {
        Object login = SessionLogin.getUserInSession();
        if (login instanceof Administrador) {
            AbrirProximaTela.proximaTela(event, "telaDeAcervo.fxml");
        } else if (login instanceof Bibliotecario) {
            AbrirProximaTela.proximaTela(event, "telaBibliotecario.fxml");
        }
    }

    /**
     * Método acionado ao clicar no botão 'Registrar'.
     * Realiza o registro de um novo livro no sistema.
     * @param event Evento de clique no botão 'Registrar'.
     */
    @FXML
    void btnRegistrarAction(ActionEvent event) {
        // Obtenção dos dados dos campos
        String isbnText = textISBN.getText();
        String anoPublicacaoText = textAnoPublicacao.getText();
        String titulo = textTitulo.getText();
        String autor = textAutor.getText();
        String categoria = textCategoria.getText();
        String editora = textEditora.getText();

        // Verificação de erros nos campos
        corDeError(isbnText,anoPublicacaoText);
        verificaCampos(titulo,autor,categoria,editora,isbnText,anoPublicacaoText);
        verificaInt(isbnText,anoPublicacaoText);

        // Registro do livro
        Livro livroRegistrado = DAO.getLivroDAO().criar(new Livro(titulo, autor, editora,
                Integer.parseInt(isbnText), Integer.parseInt(anoPublicacaoText), categoria));

        // Exibição do resultado do registro
        if (livroRegistrado != null) {
            informationAlert("Registro completo", "O livro foi registrado com sucesso, com ID: " + livroRegistrado.getId());
        } else {
            informationAlert("ERROR", "Ocorreu um erro ao registrar o livro. Por favor, tente novamente.");
        }

        // Limpeza dos campos
        limparTodosOsCampos(textTitulo,textAutor,textCategoria,textEditora,textISBN,textAnoPublicacao);
    }

    /**
     * Limpa todos os campos após o registro.
     * @param titulo Campo de título.
     * @param autor Campo de autor.
     * @param categoria Campo de categoria.
     * @param editora Campo de editora.
     * @param isbnText Campo de ISBN.
     * @param anoPublicacaoText Campo de ano de publicação.
     */
    private void limparTodosOsCampos(TextField titulo, TextField autor, TextField categoria,
                                     TextField editora, TextField isbnText, TextField anoPublicacaoText){
        PauseTransition delay = new PauseTransition(Duration.seconds(1)); // Ajuste a duração conforme necessário
        delay.setOnFinished(event -> {
            titulo.clear(); autor.clear(); categoria.clear();
            editora.clear(); isbnText.clear(); anoPublicacaoText.clear();
        });
        delay.play();
    }

    /**
     * Verifica se os campos de ISBN e ano de publicação contêm apenas números.
     * @param isbnText Texto do campo ISBN.
     * @param anoPublicacaoText Texto do campo ano de publicação.
     */
    private void verificaInt(String isbnText, String anoPublicacaoText){
        try {
            int isbn = Integer.parseInt(isbnText);
            int anoPublicacao = Integer.parseInt(anoPublicacaoText);
        } catch (NumberFormatException e) {
            informationAlert("ERROR", "Por favor, insira valores válidos para ISBN e Ano de Publicação.");
            throw e;
        }
    }

    /**
     * Verifica se todos os campos foram preenchidos.
     * @param titulo Título do livro.
     * @param autor Autor do livro.
     * @param categoria Categoria do livro.
     * @param editora Editora do livro.
     * @param isbnText ISBN do livro.
     * @param anoPublicacaoText Ano de publicação do livro.
     */
    private void verificaCampos(String titulo, String autor, String categoria, String editora, String isbnText, String anoPublicacaoText){
        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || editora.isEmpty()
                || isbnText.isEmpty() || anoPublicacaoText.isEmpty()) {
            informationAlert("ERROR", "Por favor, preencha todos os campos.");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Aplica estilo de erro aos campos de ISBN e ano de publicação que contêm letras.
     * @param isbnText Texto do campo ISBN.
     * @param anoPublicacaoText Texto do campo ano de publicação.
     */
    private void corDeError(String isbnText, String anoPublicacaoText){
        // Verificar se há letras nos campos de ISBN e Ano de Publicação
        if (isbnText.matches(".*[a-zA-Z].*")) {
            // Se houver letras, aplicamos a classe de estilo CSS de erro e limpamos após um curto intervalo
            textISBN.getStyleClass().add("text-field-error");
            limparAposDelay(textISBN);
        }
        if (anoPublicacaoText.matches(".*[a-zA-Z].*")) {
            // Se houver letras, aplicamos a classe de estilo CSS de erro e limpamos após um curto intervalo
            textAnoPublicacao.getStyleClass().add("text-field-error");
            limparAposDelay(textAnoPublicacao);
        }
    }

    /**
     * Limpa os campos após um breve intervalo quando ocorre um erro.
     * @param textField Campo de texto a ser limpo.
     */
    private void limparAposDelay(TextField textField) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            textField.getStyleClass().remove("text-field-error");
            textField.clear();
        });
        delay.play();
    }

    /**
     * Exibe um alerta de informação na interface.
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

    // Métodos para eventos de ação nos campos de texto podem ser adicionados aqui

    @FXML
    void initialize() {
        assert bntVoltar != null : "fx:id=\"bntVoltar\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert btnRegistrar != null : "fx:id=\"btnRegistrar\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert textAnoPublicacao != null : "fx:id=\"textAnoPublicacao\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert textAutor != null : "fx:id=\"textAutor\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert textCategoria != null : "fx:id=\"textCategoria\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert textEditora != null : "fx:id=\"textEditora\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert textISBN != null : "fx:id=\"textISBN\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert textTitulo != null : "fx:id=\"textTitulo\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
        assert vboxRegistro != null : "fx:id=\"vboxRegistro\" was not injected: check your FXML file 'resgistroLivros.fxml'.";
    }
}
