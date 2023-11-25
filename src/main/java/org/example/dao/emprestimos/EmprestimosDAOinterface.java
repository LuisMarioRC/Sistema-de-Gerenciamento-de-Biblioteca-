package org.example.dao.emprestimos;

import org.example.dao.CRUD;
import org.example.excecoes.EmprestimosException;
import org.example.model.Emprestimos;
import org.example.model.Livro;
import org.example.model.Usuario;

import java.util.ArrayList;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see CRUD
 * @see EmprestimosException
 * @see Emprestimos
 * @see Livro
 * @see Usuario
 * @see java.util.ArrayList
 */
public interface EmprestimosDAOinterface extends CRUD<Emprestimos, Exception> {
    public Integer numLivrosEmprestados();
    public Integer numLivroAtrasado();
    public ArrayList<Emprestimos> historicoEmprestimosUsuario(Usuario usuario);
    public ArrayList<Livro> livroMaisPolular();
    public Boolean verificaAtrasoDeUsuario(Usuario usuario, String data);
    public boolean validaMulta(Usuario usuario,String dataHoje);
    public ArrayList<Emprestimos> econtraPorUsuario(Usuario usuario);
    public Emprestimos encontraPorIdDoLivro(int id) throws EmprestimosException;

}
