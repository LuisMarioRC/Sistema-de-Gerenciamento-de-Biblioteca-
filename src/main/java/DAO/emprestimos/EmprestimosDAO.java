package DAO.emprestimos;


import model.Emprestimos;
import model.Livro;
import model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimosDAO implements EmprestimosDAOinterface {
    private ArrayList<Emprestimos> listDeEmprestimos;


    public EmprestimosDAO(){
        this.listDeEmprestimos= new ArrayList<>();
    }

    public void addEmprestimos(Emprestimos emprestimos){
        this.listDeEmprestimos.add(emprestimos);
    }
    public ArrayList<Emprestimos> getListDeEmprestimos() {
        return listDeEmprestimos;
    }

    public void registrarEmprestimos(Livro livro, Usuario usuario){
        //Tem q verificar se o livro ta disponivel
        Emprestimos em = new Emprestimos();
        LocalDate dataDeEmprestimo= LocalDate.now();
        LocalDate dataDeDevolucao = dataDeEmprestimo.plusDays(7);
        livro.setDisponibilidade(false);
        em.setLivro(livro);
        em.setUsuario(usuario);
        em.setDataEmprestimos(dataDeEmprestimo);
        em.setDataDevolucao(dataDeDevolucao);
        this.addEmprestimos(em);
    }

    public void registraDevolucao(Livro livro, Usuario usuario){
        LocalDate dataAtual = LocalDate.now();


    }


    @Override
    public Emprestimos criar(Emprestimos obj) {
        return null;
    }

    @Override
    public void excluir(Emprestimos obj) {

    }

    @Override
    public void excluirTodos() {

    }

    @Override
    public Emprestimos atualizar(Emprestimos obj) {
        return null;
    }

    @Override
    public ArrayList<Emprestimos> encontrarTodos() {
        return null;
    }

    @Override
    public Emprestimos encontrarPorID(int id) {
        return null;
    }
}
