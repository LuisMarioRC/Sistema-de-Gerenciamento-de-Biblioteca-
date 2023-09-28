package dao.emprestimos;

import dao.DAO;
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

    public void renovar(Livro livro, Usuario usuario) throws EmprestimosException {
        if (!verificaAtrasoDeUsuario(usuario) && !DAO.getReservaDAO().verificaReserva(livro.getId())) {
            Emprestimos emprestimo = encontraPorIdDoLivro(livro.getId());
            LocalDate dataDeDevolucao = emprestimo.getDataDevolucao();
            emprestimo.setDataDevolucao(dataDeDevolucao.plusDays(7));
            this.atualizar(emprestimo);
        }
    }

    public Integer numLivrosEmprestados(){
        return this.listDeEmprestimos.size();
    }


    public Integer numLivroAtrasado(){
        Integer numeroDeAtraso=1;
        LocalDate dataHoje= LocalDate.now();
        for (Emprestimos emprestimos : listDeEmprestimos){
            if (emprestimos.getAndamento() && dataHoje.isAfter(emprestimos.getDataDevolucao())){
                numeroDeAtraso ++;
            }
        }
        return numeroDeAtraso;
    }


    public ArrayList<Emprestimos> historioEmprestimosUsuario(Usuario usuario){
        ArrayList<Emprestimos> historicoEmprestimos= new ArrayList<>();
        for (Emprestimos emprestimos : listDeEmprestimos){
            if (Objects.equals(emprestimos.getUsuario(),usuario)){
                historicoEmprestimos.add(emprestimos);
            }
        }
        return historicoEmprestimos;
    }

    public void livroMaisPolular(){
        ArrayList<Livro> livroMaisPopular = new ArrayList<>();
        int maiorContagem = 0;
        for (Emprestimos emprestimos : listDeEmprestimos) {
            int contagem = contarElemento(listDeEmprestimos, emprestimos.getLivro());
            if (contagem > maiorContagem) {
                maiorContagem = contagem;
                livroMaisPopular.clear();
                livroMaisPopular.add(emprestimos.getLivro());
            } else if (contagem == maiorContagem && !livroMaisPopular.contains(emprestimos.getLivro())) {
                livroMaisPopular.add(emprestimos.getLivro());
            }
        }

    }

    private static int contarElemento(ArrayList<Emprestimos> list, Livro livro) {
        int contagem = 0;
        for (Emprestimos emprestimos : list) {
            if (emprestimos.getLivro().equals(livro)) {
                contagem++;
            }
        }
        return contagem;
    }

    public Boolean verificaAtrasoDeUsuario(Usuario usuario){
        LocalDate dataHoje= LocalDate.now();
        for (Emprestimos emprestimo: listDeEmprestimos){
            if (emprestimo.getUsuario() == usuario
                    && dataHoje.isAfter(emprestimo.getDataDevolucao())
                    && emprestimo.getAndamento()){
                return true;
            }
        }
        return false; // false equivale que o usuario NÂO tem atraso
    }


    @Override
    public Emprestimos criar(Emprestimos obj)  {
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
