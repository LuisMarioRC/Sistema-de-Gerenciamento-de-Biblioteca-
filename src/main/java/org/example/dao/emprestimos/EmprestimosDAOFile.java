package org.example.dao.emprestimos;

import org.example.excecoes.EmprestimosException;
import org.example.model.Emprestimos;
import org.example.model.Livro;
import org.example.model.Usuario;
import org.example.utils.FileMethods;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public abstract class EmprestimosDAOFile implements EmprestimosDAOinterface {
    File arquivo;
    private static final String NAMEFILE = "emprestimos";

    public EmprestimosDAOFile(){
        arquivo = FileMethods.createFile(NAMEFILE);
    }

    @Override
    public ArrayList<Emprestimos> encontrarTodos() {
        return  FileMethods.consultarArquivoList(arquivo);
    }

    @Override
    public void excluirTodos() {
        FileMethods.apagarConteudoArquivo(arquivo);
    }


    @Override
    public Emprestimos criar(Emprestimos obj)  {
        ArrayList<Emprestimos> emprestimos = encontrarTodos() ;
        obj.setId(this.getProximoID(emprestimos));
        emprestimos.add(obj);
        FileMethods.sobreescreverArquivo(arquivo, emprestimos);
        return obj;
    }

    @Override
    public void excluir(Emprestimos obj) throws EmprestimosException {
        ArrayList<Emprestimos> emprestimos = encontrarTodos();
        boolean remocao = emprestimos.remove(obj);
        if (!remocao) {
            throw new EmprestimosException(EmprestimosException.EXCLUIR);
        }
        FileMethods.sobreescreverArquivo(arquivo, emprestimos);
    }

    @Override
    public Emprestimos atualizar(Emprestimos obj) throws EmprestimosException {
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos() ;
        int index = listDeEmprestimos.indexOf(obj);
        if (index == -1){
            throw new EmprestimosException(EmprestimosException.ATUALIZAR);
        }
        listDeEmprestimos.set(index, obj);
        FileMethods.sobreescreverArquivo(arquivo, listDeEmprestimos);
        return obj;
    }


    @Override
    public Emprestimos encontrarPorID(int id) throws EmprestimosException {
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getId(), id)){
                return emprestimo;
            }
        }
        throw new EmprestimosException(EmprestimosException.BUSCAR);
    }

    @Override
    public ArrayList<Emprestimos> econtraPorUsuario(Usuario usuario){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        ArrayList<Emprestimos> emprestimosPorUsuario= new ArrayList<>();
        for (Emprestimos emprestimos : listDeEmprestimos){
            if (Objects.equals(emprestimos.getUsuario().getNumeroDeIdentificacao(), usuario.getNumeroDeIdentificacao())){
                if (emprestimos.getAndamento()) {
                    emprestimosPorUsuario.add(emprestimos);
                }
            }
        }
        return emprestimosPorUsuario;
    }

    @Override
    public Emprestimos encontraPorIdDoLivro(int id) throws EmprestimosException {
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        for (Emprestimos emprestimo : listDeEmprestimos ){
            if (Objects.equals(emprestimo.getLivro().getId(), id)){
                if (emprestimo.getAndamento()) {
                    return emprestimo;
                }
            }
        }
        throw new EmprestimosException(EmprestimosException.BUSCAR);
    }

    /*@Override
    public boolean validaMulta(Usuario usuario,String dataHoje){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newDate = LocalDate.parse(dataHoje, dataFormatada);
        if (usuario.getFimDaMulta() == null){
            return true;
        } else return newDate.isAfter(usuario.getFimDaMulta());
    }*/

    @Override
    public Integer numLivrosEmprestados(){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        int contagemDeLivroEmprestados=0;
        for (Emprestimos emprestimo: listDeEmprestimos){
            if (emprestimo.getAndamento()){
                contagemDeLivroEmprestados++;
            }
        }
        return contagemDeLivroEmprestados;
    }

    @Override
    public Integer numLivroAtrasado(){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        Integer numeroDeAtraso=0;
        LocalDate dataHoje= LocalDate.now();
        for (Emprestimos emprestimos : listDeEmprestimos){
            if (emprestimos.getAndamento() && dataHoje.isAfter(emprestimos.getDataDevolucao())){
                numeroDeAtraso ++;
            }
        }
        return numeroDeAtraso;
    }

    @Override
    public ArrayList<Emprestimos> historicoEmprestimosUsuario(Usuario usuario){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        ArrayList<Emprestimos> historicoEmprestimos= new ArrayList<>();
        for (Emprestimos emprestimos : listDeEmprestimos){
            if (Objects.equals(emprestimos.getUsuario(),usuario)){
                historicoEmprestimos.add(emprestimos);
            }
        }
        return historicoEmprestimos;
    }

    @Override
    public ArrayList<Livro> livroMaisPolular(){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
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
        return livroMaisPopular;
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

    @Override
    public Boolean verificaAtrasoDeUsuario(Usuario usuario,String dataHoje){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newDate = LocalDate.parse(dataHoje, dataFormatada);
        for (Emprestimos emprestimo: listDeEmprestimos){
            if (emprestimo.getUsuario() == usuario
                    && newDate.isAfter(emprestimo.getDataDevolucao())
                    && emprestimo.getAndamento()){
                return true;
            }
        }
        return false; // false equivale que o usuario NÂO tem atraso
    }

    private int getProximoID(ArrayList<Emprestimos> emprestimos){
        int cont =-1;
        for (Emprestimos e : emprestimos){
            cont++;
        }
        return (cont+1);
    }

}

