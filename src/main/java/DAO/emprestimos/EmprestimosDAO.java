package DAO.emprestimos;


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

    public Boolean verificaAtraso(Usuario usuario){
        LocalDate dataHoje= LocalDate.now();
        return listDeEmprestimos.stream()
                .filter(emprestimo -> Objects.equals(emprestimo.getUsuario(), usuario))
                .anyMatch(emprestimo -> dataHoje.isAfter(emprestimo.getDataDevolucao()));
    }


    public ArrayList<Emprestimos> getListDeEmprestimos() {
        return listDeEmprestimos;
    }


    public void registrarEmprestimos(Livro livro, Usuario usuario){
        //Tem q verificar se o livro ta disponivel
        Emprestimos emprestimo = new Emprestimos();
        LocalDate dataDeEmprestimo= LocalDate.now();
        LocalDate dataDeDevolucao = dataDeEmprestimo.plusDays(7);
        livro.setDisponibilidade(false);
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimos(dataDeEmprestimo);
        emprestimo.setDataDevolucao(dataDeDevolucao);
        this.criar(emprestimo);
    }


    private void multa (Livro livro, Usuario usuario){
        LocalDate datahoje = LocalDate.now();
        int idLivro = livro.getId();
        Emprestimos emprestimo = encontraPorIdDoLivro(idLivro);
        long diferencaEntreDias = ChronoUnit.DAYS.between(datahoje, emprestimo.getDataDevolucao());
        Integer diasDeMulta = Math.toIntExact(diferencaEntreDias * 2);
        usuario.setMulta(diasDeMulta);
        usuario.setStatus(false);
    }


    public void registraDevolucao(Livro livro, Usuario usuario){
        this.multa(livro,usuario);
        int idLivro = livro.getId();
        Emprestimos emprestimo = encontraPorIdDoLivro(idLivro);
        livro.setDisponibilidade(true);
        this.excluir(emprestimo);
        livro.setDisponibilidade(true);

    }

    @Override
    public Emprestimos criar(Emprestimos obj) {
        obj.setId(this.getProximoID());
        this.listDeEmprestimos.add(obj);
        return obj;
    }

    @Override
    public void excluir(Emprestimos obj) {
        this.listDeEmprestimos.remove(obj);
    }

    @Override
    public void excluirTodos() {
        this.listDeEmprestimos = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Emprestimos atualizar(Emprestimos obj) {
        int index = this.listDeEmprestimos.indexOf(obj);
        this.listDeEmprestimos.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Emprestimos> encontrarTodos() {
        return this.listDeEmprestimos;
    }

    @Override
    public Emprestimos encontrarPorID(int id) {
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getId(), id)){
                return emprestimo;
            }
        }
        return null;
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
