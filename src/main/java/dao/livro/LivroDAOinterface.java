package dao.livro;

import dao.CRUD;
import dao.excecoes.LivroException;
import model.Livro;

import java.util.ArrayList;

public interface LivroDAOinterface extends CRUD<Livro, Exception> {
    public ArrayList<Livro> pesquisaPorTitulo(String titulo) throws LivroException;
    public ArrayList<Livro> pesquisaPorisbn(Integer isbn) throws LivroException;
    public ArrayList<Livro> pesquisaPorCategoria(String categoria) throws LivroException;
    public ArrayList<Livro> pesquisaPorAutor(String autor) throws LivroException;

    }
