package com.example.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class CadastroUsuario {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textEndereco;

    @FXML
    private TextField textNome;

    @FXML
    private TextField textRepetirSenha;

    @FXML
    private TextField textSenha;

    @FXML
    private TextField textTelefone;

    @FXML
    void btnCadastrarAction(ActionEvent event) {
        String nome = textNome.getText();
        String endereco = textEndereco.getText();
        String telefone = textTelefone.getText();
        String senha= textSenha.getText();
        String senhaRepetida= textRepetirSenha.getText();

        verificaCampos(nome,endereco,telefone,senha,senhaRepetida);
        verificaSenha(senha, senhaRepetida);

        Usuario usuarioCadastrado = DAO.getUsuarioDAO().criar(
                new Usuario(nome,endereco,telefone,Integer.parseInt(senha)));

        if (usuarioCadastrado != null){
            informationAlert("Cadastro Completo","O usuário foi " +
                    " cadastrado com sucesso com o ID: "+usuarioCadastrado.getNumeroDeIdentificacao());

        }else{
            informationAlert("ERROR","Ocorreu um erro ao o usuário.\n Por favor, tente novamente");
        }
        limparCampos(textNome,textEndereco,textTelefone,textSenha,textRepetirSenha);
    }

    private void limparCampos(TextField nome,TextField endereco,TextField telefone,
                                 TextField senha, TextField senhaRepetida) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1)); // Ajuste a duração conforme necessário
        delay.setOnFinished(event -> {
            nome.clear(); endereco.clear(); telefone.clear(); senha.clear(); senhaRepetida.clear();
        });
        delay.play();
    }



    private void verificaCampos(String nome,String endereco,
                                String telefone, String senha,
                                String senhaNovamente){
        if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty()
                || senha.isEmpty() || senhaNovamente.isEmpty()){
            informationAlert("ATENÇÂO", "Por favor, preencha todos os campos");
            throw new IllegalArgumentException("Por favor, preencha todos os campos");
        }

    }


    private void verificaSenha(String textSenha, String textSenhaRepetida){
        int senhaInt;
        int senhaNovamenteInt;
        try{
            senhaInt = Integer.parseInt(textSenha);
            senhaNovamenteInt= Integer.parseInt(textSenhaRepetida);
        }catch (NumberFormatException e ){
            informationAlert("ERROR", "Atenção, são aceita apenas senhas numéricas");
            throw e;
        }
        if (senhaInt != senhaNovamenteInt){
            informationAlert("ERROR", "As senhas devem ser iguais");
            throw new IllegalArgumentException("As senhas devem ser iguais");
        }

    }

    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event,"telaAdministrador.fxml");

    }

    @FXML
    void textEnderecoAction(ActionEvent event) {

    }

    @FXML
    void textNomeAction(ActionEvent event) {

    }

    @FXML
    void textRepetirSenhaAction(ActionEvent event) {

    }

    @FXML
    void textSenhaAction(ActionEvent event) {

    }

    @FXML
    void textTelefoneAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnCadastrar != null : "fx:id=\"btnCadastrar\" was not injected: check your FXML file 'Untitled'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'Untitled'.";
        assert textEndereco != null : "fx:id=\"textEndereco\" was not injected: check your FXML file 'Untitled'.";
        assert textNome != null : "fx:id=\"textNome\" was not injected: check your FXML file 'Untitled'.";
        assert textRepetirSenha != null : "fx:id=\"textRepetirSenha\" was not injected: check your FXML file 'Untitled'.";
        assert textSenha != null : "fx:id=\"textSenha\" was not injected: check your FXML file 'Untitled'.";
        assert textTelefone != null : "fx:id=\"textTelefone\" was not injected: check your FXML file 'Untitled'.";

    }
    private void informationAlert(String title, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

}
