package dao.emprestimos;

import dao.CRUD;
import dao.excecoes.EmprestimosException;
import model.Emprestimos;
import model.Livro;
import model.Usuario;

import java.util.ArrayList;

public interface EmprestimosDAOinterface extends CRUD<Emprestimos, Exception> {
    public void renovar(Livro livro, Usuario usuario, String dataHoje) throws EmprestimosException;
    public Integer numLivrosEmprestados();
    public Integer numLivroAtrasado();
    public ArrayList<Emprestimos> historicoEmprestimosUsuario(Usuario usuario);
    public void livroMaisPolular();
    public Boolean verificaAtrasoDeUsuario(Usuario usuario);
    public boolean validaMulta(Usuario usuario,String dataHoje);
    public ArrayList<Emprestimos> econtraPorUsuario(Usuario usuario);
    public Emprestimos encontraPorIdDoLivro(int id) throws EmprestimosException;

}
