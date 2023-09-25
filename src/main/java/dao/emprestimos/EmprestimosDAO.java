package dao.emprestimos;

import dao.excecoes.EmprestimosException;
import model.Emprestimos;
import model.Livro;
import model.Usuario;
import java.time.LocalDate;
import java.util.*;

public class EmprestimosDAO implements EmprestimosDAOinterface {
    private ArrayList<Emprestimos> listDeEmprestimos;

    private int proximoID;
    private int getProximoID() {
        return this.proximoID++;
    }

    public EmprestimosDAO(){
        this.listDeEmprestimos= new ArrayList<>();
        this.proximoID = 0;
    }

    public Boolean verificaAtraso(Usuario usuario){
        LocalDate dataHoje= LocalDate.now();
        for (Emprestimos emprestimo: listDeEmprestimos){
            if (emprestimo.getUsuario() == usuario
                    && dataHoje.isAfter(emprestimo.getDataDevolucao())
                    && !emprestimo.getLivro().getDisponibilidade()){
                return true;
            }
        }
        return false;
    }

    public void reservarLivro(Livro livro, Usuario usuario){

    }

    @Override
    public Emprestimos criar(Emprestimos obj) {
        obj.setId(this.getProximoID());
        this.listDeEmprestimos.add(obj);
        return obj;
    }

    @Override
    public void excluir(Emprestimos obj) throws EmprestimosException{
        boolean remocao = this.listDeEmprestimos.remove(obj);
        if (!remocao){
            throw new EmprestimosException(EmprestimosException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeEmprestimos = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Emprestimos atualizar(Emprestimos obj) throws EmprestimosException{
        int index = this.listDeEmprestimos.indexOf(obj);
        if (index == -1){
            throw new EmprestimosException(EmprestimosException.ATUALIZAR);
        }
        this.listDeEmprestimos.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Emprestimos> encontrarTodos() {
        return this.listDeEmprestimos;
    }

    @Override
    public Emprestimos encontrarPorID(int id) throws EmprestimosException {
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getId(), id)){
                return emprestimo;
            }
        }
        throw new EmprestimosException(EmprestimosException.BUSCAR);
    }

    public Emprestimos encontraPorIdDoLivro(int id) throws EmprestimosException {
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getLivro().getId(), id)){
                // vai retornar o emprestimos onde o livro ta indisponível(emprestado)
                if (!emprestimo.getLivro().getDisponibilidade()) {
                    return emprestimo;
                }
            }
        }
        throw new EmprestimosException(EmprestimosException.BUSCAR);
    }

}
