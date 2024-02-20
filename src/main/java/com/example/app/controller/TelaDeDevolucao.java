package com.example.app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.dao.DAO;
import com.example.excecoes.EmprestimosException;
import com.example.excecoes.LivroException;
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

public class TelaDeDevolucao {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDevolver;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField textIdLivro;


    @FXML
    void btnDevolverAction(ActionEvent event) throws LivroException, EmprestimosException, UsuarioException {
        Livro livroEncontrado = null;
        try {
            String idLivro = textIdLivro.getText();

            verificaCampos(idLivro);
            verificaCamposInt(idLivro);

            livroEncontrado = buscarLivro(Integer.parseInt(idLivro));
            Emprestimos emprestimosEncontrado = buscaEmprestimo(livroEncontrado);

            devolver(livroEncontrado, emprestimosEncontrado);
        } catch (Exception e) {
            textIdLivro.clear();
            throw e;
        }
        informationAlert("SUCESSO", "O Livro " + livroEncontrado.getTitulo() + " devolvido com sucesso");
        textIdLivro.clear();

    }

    private void devolver(Livro livro, Emprestimos emprestimos) throws LivroException, EmprestimosException, UsuarioException {
        LocalDate dataHoje= LocalDate.now();
        try{
            emprestimos.registraDevolucao(livro,emprestimos.getUsuario(),dataHoje);
        } catch (LivroException | EmprestimosException | UsuarioException e) {
            alertException("ERROR", e);
            throw e;
        }
    }

    private Emprestimos buscaEmprestimo(Livro livro) throws EmprestimosException {
        Emprestimos emprestimo;
        try {
            emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        }catch(Exception e){
            informationAlert("ERROR", "Este livro não está emprestado!");
            throw e;
        }
        return emprestimo;
    }

    private Livro buscarLivro(Integer idLivro) throws LivroException {
        Livro livro;
        try{
            livro= DAO.getLivroDAO().encontrarPorID(idLivro);
        }catch (LivroException e){
            informationAlert("ERROR", "Livro não encontrado!");
            throw e;
        }
        return livro;
    }

    private void verificaCampos(String idLivro){
        if (idLivro.isEmpty()){
            informationAlert("ERROR", "Por favor, preecha todos os campos");
            throw new IllegalArgumentException();
        }
    }


    private void verificaCamposInt(String idLivro){
        try{
            int idLivroInt= Integer.parseInt(idLivro);
        }catch (NumberFormatException e){
            informationAlert("ERROR", "Digite apenas números");
            throw e;
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
    void initialize() {
        assert btnDevolver != null : "fx:id=\"btnDevolver\" was not injected: check your FXML file 'telaDeDevolucao.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'telaDeDevolucao.fxml'.";
        assert textIdLivro != null : "fx:id=\"textIdLivro\" was not injected: check your FXML file 'telaDeDevolucao.fxml'.";

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
