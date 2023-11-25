package dao.emprestimos;

import dao.CRUD;
import excecoes.EmprestimosException;
import model.Emprestimos;
import model.Livro;
import model.Usuario;

import java.util.ArrayList;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.CRUD
 * @see excecoes.EmprestimosException
 * @see model.Emprestimos
 * @see model.Livro
 * @see model.Usuario
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
