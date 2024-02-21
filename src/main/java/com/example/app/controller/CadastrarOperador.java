/**
 * Controller responsável pelo cadastro de operadores (Administrador ou Bibliotecário).
 */
package com.example.app.controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.model.Administrador;
import com.example.model.Bibliotecario;
import com.example.utils.AbrirProximaTela;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CadastrarOperador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private HBox hboxOperador;

    @FXML
    private RadioButton radioButtonAdministrador;

    @FXML
    private RadioButton radioButtonBibliotecario;

    @FXML
    private VBox vboxEntradas;

    @FXML
    private TextField textNome;

    @FXML
    private TextField textSenha;

    @FXML
    private TextField textSenhaRepetida;

    /**
     * Método acionado ao clicar no botão 'Cadastrar'.
     * Realiza o cadastro do operador.
     * @param event Evento de clique no botão 'Cadastrar'.
     */
    @FXML
    void btnCadastrarAction(ActionEvent event) {
        // Obtenção dos dados dos campos
        String nome = textNome.getText();
        String senha = textSenha.getText();
        String senhaRepetida = textSenhaRepetida.getText();

        // Determinação do cargo selecionado
        String cargo = cargoSelecionado();

        // Verificação dos campos
        verificarCampos(nome, senha, senhaRepetida, cargo);

        // Verificação das senhas
        verificaSenha(senha, senhaRepetida);

        // Cadastro do operador
        cadastro(nome, senha, cargo);

        // Limpeza dos campos
        limparCampos();
    }

    /**
     * Método para limpar os campos após o cadastro.
     */
    private void limparCampos() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1)); // Ajuste a duração conforme necessário
        delay.setOnFinished(event -> {
            textNome.clear();
            textSenha.clear();
            textSenhaRepetida.clear();
            radioButtonBibliotecario.setSelected(false);
            radioButtonAdministrador.setSelected(false);
        });
        delay.play();
    }

    /**
     * Realiza o cadastro do operador com base no cargo especificado.
     * @param nome Nome do operador.
     * @param senha Senha do operador.
     * @param cargo Cargo do operador.
     */
    private void cadastro(String nome, String senha, String cargo) {
        if (Objects.equals(cargo, "Bibliotecario")) {
            Bibliotecario bibliotecarioCadastrado = DAO.getBibliotecarioDAO().criar(new Bibliotecario(nome, Integer.parseInt(senha)));
            verificaCadastrado(bibliotecarioCadastrado);
        } else {
            Administrador administradorCadastrado = DAO.getAdministradorDAO().criar(new Administrador(nome, Integer.parseInt(senha)));
            verificaCadastrado(administradorCadastrado);
        }
    }

    /**
     * Verifica se o operador foi cadastrado com sucesso.
     * @param cadastrado Objeto do operador cadastrado.
     */
    private void verificaCadastrado(Object cadastrado) {
        if (cadastrado != null) {
            informationAlert("Cadastro Completo", "O operador foi cadastrado com sucesso");
        } else {
            informationAlert("ERROR", "Ocorreu um erro ao cadastrar o operador.\n Por favor, tente novamente");
        }
    }

    /**
     * Verifica se as senhas informadas são iguais.
     * @param senha Senha informada.
     * @param senhaRepetida Senha repetida.
     */
    private void verificaSenha(String senha, String senhaRepetida) {
        int senhaInt;
        int senhaRepetidaInt;
        try {
            senhaInt = Integer.parseInt(senha);
            senhaRepetidaInt = Integer.parseInt(senhaRepetida);
        } catch (NumberFormatException e) {
            informationAlert("ERROR", "Atenção, são aceita apenas senhas numéricas");
            throw e;
        }
        if (senhaInt != senhaRepetidaInt) {
            informationAlert("ERROR", "As senhas devem ser iguais");
            throw new IllegalArgumentException("As senhas devem ser iguais");
        }
    }

    /**
     * Verifica se todos os campos foram preenchidos.
     * @param nome Nome do operador.
     * @param senha Senha do operador.
     * @param senhaRepetida Senha repetida do operador.
     * @param cargo Cargo do operador.
     */
    private void verificarCampos(String nome, String senha, String senhaRepetida, String cargo) {
        if (nome.isEmpty() || senha.isEmpty() || senhaRepetida.isEmpty() || cargo == null) {
            informationAlert("ERROR", "Por favor, preencha todos os campos!");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Exibe um Alerta de informação.
     * @param title Título do Alerta.
     * @param texto Texto do Alerta.
     */
    private void informationAlert(String title, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaAdministrador.fxml");
    }

    @FXML
    void textNomeAction(ActionEvent event) {
    }

    @FXML
    void textSenhaAction(ActionEvent event) {
    }

    @FXML
    void textSenhaRepetidaAction(ActionEvent event) {
    }

    @FXML
    void radioButtonAdministradorAction(ActionEvent event) {
        deselecionarOutrosRadios(radioButtonAdministrador);
    }

    @FXML
    void radioButtonBibliotecarioAction(ActionEvent event) {
        deselecionarOutrosRadios(radioButtonBibliotecario);
    }

    /**
     * Desmarca os outros RadioButtones quando um é selecionado.
     * @param selecionado RadioButton selecionado.
     */
    private void deselecionarOutrosRadios(RadioButton selecionado) {
        for (javafx.scene.Node node : hboxOperador.getChildren()) {
            if (node instanceof RadioButton && node != selecionado) {
                ((RadioButton) node).setSelected(false);
            }
        }
    }

    /**
     * Retorna o cargo selecionado.
     * @return Cargo selecionado.
     */
    private String cargoSelecionado() {
        for (javafx.scene.Node node : hboxOperador.getChildren()) {
            if (node instanceof RadioButton radioButton) {
                if (radioButton.isSelected()) {
                    return radioButton.getText(); // Retorna o texto do RadioButton selecionado
                }
            }
        }
        return null; // Retorna null se nenhum RadioButton estiver selecionado
    }

    @FXML
    void initialize() {
        assert btnCadastrar != null : "fx:id=\"btnCadastrar\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert hboxOperador != null : "fx:id=\"hboxOperador\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert radioButtonAdministrador != null : "fx:id=\"radioButtonAdministrador\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert radioButtonBibliotecario != null : "fx:id=\"radioButtonBibliotecario\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert textNome != null : "fx:id=\"textNome\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert textSenha != null : "fx:id=\"textSenha\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert textSenhaRepetida != null : "fx:id=\"textSenhaRepetida\" was not injected: check your FXML file 'cadastroOperador.fxml'.";
        assert vboxEntradas != null : "fx:id=\"vboxEntradas\" was not injected: check your FXML file 'cadastroOperador.fxml'.";

    }

}
