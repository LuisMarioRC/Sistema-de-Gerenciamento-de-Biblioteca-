package com.example.app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Emprestimos;
import com.example.model.Livro;
import com.example.model.Usuario;
import com.example.utils.AbrirProximaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaDeEmprestimo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEmprestar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;

    @FXML
    private TextField textIdUsuario;

    @FXML
    void btnEmprestarAction(ActionEvent event) throws LivroException, UsuarioException, ReservaException {
        Livro livroEcontrado;
        try {
            String idLivro = textIdLivro.getText();
            String idUsuario = textIdUsuario.getText();

            verificaCampos(idLivro, idUsuario);
            verificaCamposInt(idLivro, idUsuario);

            livroEcontrado = buscarLivro(Integer.parseInt(idLivro));
            Usuario usuarioEncontrado = buscaUsuario(Integer.parseInt(idUsuario));
            emprestimos(livroEcontrado, usuarioEncontrado);
        } catch (Exception e) {
            textIdLivro.clear();
            textIdUsuario.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro " + livroEcontrado.getTitulo() + " foi emprestado o com sucesso");
        textIdLivro.clear();
        textIdUsuario.clear();
    }

    private void emprestimos(Livro livro, Usuario usuario) throws LivroException, UsuarioException, ReservaException {
        LocalDate dataHoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataHoje.format(formatter);
        try{
            Emprestimos emprestimo= DAO.getEmprestimosDAO().criar(new Emprestimos(livro,usuario,dataFormatada));
        }catch (UsuarioException | ReservaException | LivroException e){
            alertException("ERROR", e);
            throw e;
        }

    }

    private Usuario buscaUsuario(Integer idUsuario) throws UsuarioException {
        Usuario usuario;
        try{
            usuario = DAO.getUsuarioDAO().encontrarPorID(idUsuario);
        }catch (UsuarioException e){
            informationAlert("ERROR", "Usuário não encontrado");
            throw e;
        }
        return usuario;
    }

    private Livro buscarLivro(Integer idLivro) throws LivroException {
        Livro livro;
        try{
            livro= DAO.getLivroDAO().encontrarPorID(idLivro);
        }catch (LivroException e){
            informationAlert("ERROR", "Livro não encontrado");
            throw e;
        }
        return livro;
    }

    private void verificaCamposInt(String idLivro, String idUsuario){
        try{
            int idLivroInt= Integer.parseInt(idLivro);
            int idUsuarioInt= Integer.parseInt(idUsuario);
        }catch (NumberFormatException e){
            informationAlert("ERROR", "Digite apenas números");
            throw e;
        }
    }

    private void verificaCampos(String idLivro, String idUsuario){
        if (idLivro.isEmpty() || idUsuario.isEmpty()){
            informationAlert("ERROR", "Por favor, preecha todos os campos");
            throw new IllegalArgumentException();
        }
    }



    @FXML
    void btnVoltarAction(ActionEvent event) {
        AbrirProximaTela.proximaTela(event, "telaBibliotecario.fxml");
    }

    @FXML
    void textIdLivroAction(ActionEvent event) {

    }

    @FXML
    void textIdUsuarioAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnEmprestar != null : "fx:id=\"btnEmprestar\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";
        assert textIdUsuario != null : "fx:id=\"textIdUsuario\" was not injected: check your FXML file 'telaDeEmprestimo.fxml'.";

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
