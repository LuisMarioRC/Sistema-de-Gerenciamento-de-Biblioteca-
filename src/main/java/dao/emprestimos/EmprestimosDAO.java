package dao.emprestimos;


import dao.excecoes.DAOException;
import model.Emprestimos;
import model.Livro;
import model.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

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
    public ArrayList<Emprestimos> getListDeEmprestimos() {
        return listDeEmprestimos;
    }

    public Boolean verificaAtraso(Usuario usuario){
        LocalDate dataHoje= LocalDate.now();
        return listDeEmprestimos.stream()
                .filter(emprestimo -> Objects.equals(emprestimo.getUsuario(), usuario))
                .anyMatch(emprestimo -> dataHoje.isAfter(emprestimo.getDataDevolucao()));
    }


    @Override
    public Emprestimos criar(Emprestimos obj) {
        obj.setId(this.getProximoID());
        this.listDeEmprestimos.add(obj);
        return obj;
    }

    @Override
    public void excluir(Emprestimos obj) throws DAOException{
        boolean remocao = this.listDeEmprestimos.remove(obj);
        if (!remocao){
            throw new DAOException(DAOException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeEmprestimos = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Emprestimos atualizar(Emprestimos obj) throws DAOException{
        int index = this.listDeEmprestimos.indexOf(obj);
        if (index == -1){
            throw new DAOException(DAOException.ATUALIZAR);
        }
        this.listDeEmprestimos.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Emprestimos> encontrarTodos() {
        return this.listDeEmprestimos;
    }

    @Override
    public Emprestimos encontrarPorID(int id) throws DAOException{
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getId(), id)){
                return emprestimo;
            }
        }
        throw new DAOException(DAOException.BUSCAR);
    }

    public Emprestimos encontraPorIdDoLivro(int id) {
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getLivro().getId(), id)){
                return emprestimo;
            }
        }
        return null;
    }

}
