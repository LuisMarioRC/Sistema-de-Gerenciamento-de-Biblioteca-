/**
 * Controller responsável para fazer o login
 */
package com.example.app.controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.AdministradorException;
import com.example.excecoes.BibliotecarioException;
import com.example.excecoes.UsuarioException;
import com.example.model.Administrador;
import com.example.model.Bibliotecario;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import com.example.utils.SessionLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TelaLogin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton buttonAdministrador;

    @FXML
    private RadioButton buttonBibliotecario;

    @FXML
    private Button buttonLogin;

    @FXML
    private RadioButton buttonUsuario;

    @FXML
    private TextField texId;

    @FXML
    private PasswordField textPassword;

    @FXML
    private VBox vBoxButtons;
    private String nomeDaProximaTela;

    /**
     * Ação acionada quando o botão de seleção do tipo de login 'Administrador' é clicado.
     * Desseleciona outros botões de seleção.
     * @param event Evento de clique no botão de seleção 'Administrador'.
     */
    @FXML
    void ButtonAdministradorAction(ActionEvent event) {
        deselecionarOutrosRadios(buttonAdministrador);
    }

    /**
     * Ação acionada quando o botão de seleção do tipo de login 'Bibliotecário' é clicado.
     * Desseleciona outros botões de seleção.
     * @param event Evento de clique no botão de seleção 'Bibliotecário'.
     */
    @FXML
    void buttonBbilbiotecarioAction(ActionEvent event) {
        deselecionarOutrosRadios(buttonBibliotecario);
    }

    /**
     * Ação acionada quando o botão de seleção do tipo de login 'Usuário' é clicado.
     * Desseleciona outros botões de seleção.
     * @param event Evento de clique no botão de seleção 'Usuário'.
     */
    @FXML
    void buttonUsuarioAction(ActionEvent event) {
        deselecionarOutrosRadios(buttonUsuario);
    }

    /**
     * Desseleciona outros botões de seleção, exceto o selecionado.
     * @param selecionado O botão de seleção atualmente selecionado.
     */
    private void deselecionarOutrosRadios(RadioButton selecionado) {
        for (javafx.scene.Node node : vBoxButtons.getChildren()) {
            if (node instanceof RadioButton && node != selecionado) {
                ((RadioButton) node).setSelected(false);
            }
        }
    }

    /**
     * Ação acionada quando o botão de login é clicado.
     * Realiza o processo de login, autenticando o usuário e redirecionando para a próxima tela.
     * @param event Evento de clique no botão de login.
     */
    @FXML
    void loginAction(ActionEvent event) throws BibliotecarioException, UsuarioException, AdministradorException {
        try {
            String userId = texId.getText();
            String password = textPassword.getText();
            Object objLogado =  login(userId, password);
            UsuarioBloqueado(userId, Objects.requireNonNull(cargoSelecionado()));

            SessionLogin.loginUser(objLogado);

            nomeDaProximaTela = proxPag(Objects.requireNonNull(cargoSelecionado()));
            AbrirProximaTela.proximaTela(event, nomeDaProximaTela);

        } catch (Exception e) {
            textPassword.clear(); texId.clear();
            buttonUsuario.setSelected(false); buttonBibliotecario.setSelected(false); buttonAdministrador.setSelected(false);
        }
    }

    /**
     * Verifica se o usuário está bloqueado antes de realizar o login.
     * @param id O ID do usuário.
     * @param cargo O cargo do usuário.
     * @throws UsuarioException Se o usuário estiver bloqueado.
     */
    private void UsuarioBloqueado(String id,String cargo) throws UsuarioException {
        if (cargo.equals("Usuário")) {
            Usuario usuario = DAO.getUsuarioDAO().encontrarPorID(Integer.parseInt(id));
            if (!usuario.getStatus()) {
                informationAlert("Usuário bloqueado");
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Verifica se os campos de ID e senha contêm apenas números.
     * @param id O ID fornecido pelo usuário.
     * @param senha A senha fornecida pelo usuário.
     */
    private void verificaIntCampos(String id, String senha){
        try{
            int idInt= Integer.parseInt(id);
            int senhaInt= Integer.parseInt(senha);
        } catch (NumberFormatException e) {
            informationAlert("Atenção, digite apenas números nos campos");
            throw e;
        }
    }

    /**
     * Realiza o processo de login, autenticando o usuário com base no ID, senha e cargo selecionado.
     * @param id O ID fornecido pelo usuário.
     * @param password A senha fornecida pelo usuário.
     * @return O objeto do usuário autenticado.
     */
    private Object login(String id, String password) throws BibliotecarioException, UsuarioException, AdministradorException {
        Object obj = null;
        String cargo = cargoSelecionado();

        if (id.length() == 0 || password.length() == 0 || cargo == null) {
            informationAlert("Por favor, preencha todos os campos");
            throw new IllegalArgumentException("Por favor, preencha todos os campos");
        }
        verificaIntCampos(id,password);
        obj = pessoaLogada(id,cargo);

        if (obj != null) {
            if (obj instanceof Usuario) {
                if (!((Usuario) obj).getSenha().equals(Integer.parseInt(password))) {
                    informationAlert("Senha Incorreta!");
                    throw new UsuarioException(UsuarioException.SENHA_INVALIDA);
                }
            } else if (obj instanceof Administrador) {
                if (!((Administrador) obj).getSenha().equals(Integer.parseInt(password))) {
                    informationAlert("Senha Incorreta!");
                    throw new AdministradorException(AdministradorException.SENHA_INVALIDA);
                }
            } else if (obj instanceof Bibliotecario ) {
                if (!((Bibliotecario) obj).getSenha().equals(Integer.parseInt(password))) {
                    informationAlert("Senha Incorreta!");
                    throw new BibliotecarioException(BibliotecarioException.SENHA_INVALIDA);
                }
            }
        }
        return obj;
    }

    /**
     * Retorna o tipo de cargo selecionado (Administrador, Bibliotecário ou Usuário).
     * @return O tipo de cargo selecionado.
     */
    private String cargoSelecionado() {
        for (javafx.scene.Node node : vBoxButtons.getChildren()) {
            if (node instanceof RadioButton radioButton) {
                if (radioButton.isSelected()) {
                    return radioButton.getText(); // Retorna o texto do RadioButton selecionado
                }
            }
        }
        return null; // Retorna null se nenhum RadioButton estiver selecionado
    }

    /**
     * Retorna o nome do arquivo FXML da próxima página com base no tipo de cargo.
     * @param cargo O tipo de cargo selecionado.
     * @return O nome do arquivo FXML da próxima página.
     */
    private String proxPag(String cargo) {
        switch (cargo) {
            case "Bibliotecário" -> {
                return "telaBibliotecario.fxml";
            }
            case "Administrador" -> {
                return "telaAdministrador.fxml";
            }
            case "Usuário" -> {
                return "telaUsuario.fxml";
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Retorna o objeto do usuário autenticado com base no ID e tipo de cargo.
     * @param id O ID fornecido pelo usuário.
     * @param cargo O tipo de cargo selecionado.
     * @return O objeto do usuário autenticado.
     * @throws UsuarioException Se ocorrer uma exceção ao procurar o usuário.
     * @throws AdministradorException Se ocorrer uma exceção ao procurar o administrador.
     * @throws BibliotecarioException Se ocorrer uma exceção ao procurar o bibliotecário.
     */
    private Object pessoaLogada(String id,String cargo) throws UsuarioException, AdministradorException, BibliotecarioException {
        try {
            switch (cargo) {
                case "Usuário" -> {
                    return DAO.getUsuarioDAO().encontrarPorID(Integer.parseInt(id));
                }
                case "Administrador" -> {
                    return DAO.getAdministradorDAO().encontrarPorID(Integer.parseInt(id));
                }
                case "Bibliotecário" -> {
                    return DAO.getBibliotecarioDAO().encontrarPorID(Integer.parseInt(id));
                }
                default -> {
                    return null;
                }
            }
        } catch (Exception e ){
            informationAlert("Esse ID não foi encontrado!");
            throw e;
        }
    }

    /**
     * Exibe um alerta de informação com o texto especificado.
     * @param texto O texto a ser exibido no alerta.
     */
    private void informationAlert(String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    /**
     * Método executado ao inicializar a tela.
     * Verifica se os elementos do FXML foram injetados corretamente.
     */
    @FXML
    void initialize() {
        assert buttonAdministrador != null : "fx:id=\"ButtonAdministrador\" was not injected: check your FXML file 'login.fxml'.";
        assert buttonBibliotecario != null : "fx:id=\"buttonBibliotecario\" was not injected: check your FXML file 'login.fxml'.";
        assert buttonLogin != null : "fx:id=\"buttonLogin\" was not injected: check your FXML file 'login.fxml'.";
        assert buttonUsuario != null : "fx:id=\"buttonUsuario\" was not injected: check your FXML file 'login.fxml'.";
        assert texId != null : "fx:id=\"texId\" was not injected: check your FXML file 'login.fxml'.";
        assert textPassword != null : "fx:id=\"textPassword\" was not injected: check your FXML file 'login.fxml'.";
        assert vBoxButtons != null : "fx:id=\"vBoxButtons\" was not injected: check your FXML file 'login.fxml'.";
    }
}
