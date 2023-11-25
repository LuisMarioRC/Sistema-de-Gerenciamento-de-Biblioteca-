package dao.livro;

import dao.CRUD;
import excecoes.LivroException;
import model.Livro;

import java.util.ArrayList;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.CRUD
 * @see excecoes.LivroException
 * @see model.Livro
 * @see java.util.ArrayList
 */
public interface LivroDAOinterface extends CRUD<Livro, Exception> {
    public ArrayList<Livro> pesquisaPorTitulo(String titulo) throws LivroException;
    public ArrayList<Livro> pesquisaPorisbn(Integer isbn) throws LivroException;
    public ArrayList<Livro> pesquisaPorCategoria(String categoria) throws LivroException;
    public ArrayList<Livro> pesquisaPorAutor(String autor) throws LivroException;

    }
