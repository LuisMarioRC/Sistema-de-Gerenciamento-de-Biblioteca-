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

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see EmprestimosException
 * @see Emprestimos
 * @see Livro
 * @see Usuario
 * @see java.time.LocalDate
 * @see java.time.format.DateTimeFormatter
 * @see java.util
 * @see java.io.File
 * @see FileMethods
 * @see java.util.ArrayList
 * @see java.util.Objects
 */
public class EmprestimosDAOFile implements EmprestimosDAOinterface {
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

    /**
     * Metodo para encontrar os emprestimos ativo por um usuario específico
     * @param usuario que sera filtrado os emprestimos
     * @return uma lista de emprestimos do usuario que esta como parámetro
     */
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

    /**
     * Metodo para econtrar o emprestimo que esta ativo por id do livro
     * @param id do livro que deseja filtra o emprestimo
     * @return o emprestimos do id do livro
     * @throws EmprestimosException , caso não econtrar lança uma exceção de BUSCA
     */
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

    /**
     * Método que verifica se o usuario está multado
     * @param usuario que é verificado
     * @param dataHoje data em que é verificada se o usuario está multado
     * @return um valor booleano, false se estiver com multa, true se a multa ja acabou
     */
    @Override
    public boolean validaMulta(Usuario usuario,String dataHoje){
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newDate = LocalDate.parse(dataHoje, dataFormatada);
        if (usuario.getFimDaMulta() == null){
            return true;
        } else return newDate.isAfter(usuario.getFimDaMulta());
    }

    /**
     * Método responsável por analisar quantos livros estão sendo emprestados no momento
     * @return um inteiro com o número de livros
     */
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

    /**
     * Método responsável por analisar o número de livros atrasados no momento
     * @return um inteiro contendo o numero de livros emprestado
     */
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

    /**
     * Método resposável por retornar o hitórico de todos os emprestimos realizados por um Usuário
     * @param usuario que é submetido a verificação de histórico de empréstimos
     * @return uma lista do historico de emprestimo do usuario
     */
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

    /**
     * Método responsavel por retornar o(s) livro(s) que foi emprestado mais vezes
     * Verifica quantas vez um lirvo aparece na lista através de outro método, e guarda em uma lista
     * Cada vez que um livro aparecer mais vezes, limpa a lista e adiciona o novo livro
     * @return uma lista correspondendo o(s) livro(s) mais populares
     */
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

    /**
     * Método responsavel por analisar quantos livros iguais existem em uma lista
     * @param list lista de empréstimos realizados
     * @param livro a ser analisado
     * @return o numero de elemento que estao iguais
     */
    private static int contarElemento(ArrayList<Emprestimos> list, Livro livro) {
        int contagem = 0;
        for (Emprestimos emprestimos : list) {
            if (emprestimos.getLivro().equals(livro)) {
                contagem++;
            }
        }
        return contagem;
    }

    /**
     * Método que verifica se a data do empréstimo que está em andamento está atrasado
     * @param usuario que é usado para verificar se tem devolução atrsada
     * @return true que equivale que tem devolução atrasada e false se nao estiver atraso
     */
    @Override
    public Boolean verificaAtrasoDeUsuario(Usuario usuario,String dataHoje){
        ArrayList<Emprestimos> listDeEmprestimos = encontrarTodos();
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newDate = LocalDate.parse(dataHoje, dataFormatada);
        for (Emprestimos emprestimo: listDeEmprestimos){
            if (Objects.equals(emprestimo.getUsuario().getNumeroDeIdentificacao(), usuario.getNumeroDeIdentificacao())
                    && newDate.isAfter(emprestimo.getDataDevolucao())
                    && emprestimo.getAndamento()){
                return true;
            }
        }
        return false; // false equivale que o usuario NÂO tem atraso
    }

    private int getProximoID(ArrayList<Emprestimos> emprestimos){
        return(emprestimos.size());
    }

}

