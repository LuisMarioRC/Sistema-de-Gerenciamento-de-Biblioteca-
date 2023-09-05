package model;

import DAO.LivroDAO;

import java.util.ArrayList;

public class Bibliotecario extends Operador {

    public Bibliotecario(){

    }

    public Livro registraLivro(String titulo, String autor,String editora,Integer isbn,Integer anoDePublicacao,String categoria){
        Livro livro= new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setAnoDePublicacao(anoDePublicacao);
        livro.setCategoria(categoria);
        livro.setDisponibilidade(true);
        return livro;
    }

}
