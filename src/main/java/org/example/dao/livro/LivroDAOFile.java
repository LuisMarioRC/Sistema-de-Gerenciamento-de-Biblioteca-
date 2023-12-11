package org.example.dao.livro;

import org.example.excecoes.LivroException;
import org.example.model.Livro;
import org.example.utils.FileMethods;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see LivroException
 * @see Livro
 * @see FileMethods
 * @see java.util.ArrayList
 * @see java.util.Objects
 * @see java.io.File
 */
public class LivroDAOFile implements LivroDAOinterface{
    File arquivo;
    private static final String NAMEFILE= "livros";

    public LivroDAOFile(){
        arquivo = FileMethods.createFile(NAMEFILE);
    }

    @Override
    public Livro criar(Livro obj) {
        ArrayList<Livro> livros = encontrarTodos() ;
        obj.setId(this.getProximoID(livros));
        livros.add(obj);
        FileMethods.sobreescreverArquivo(arquivo, livros);
        return obj;
    }

    @Override
    public void excluir(Livro obj) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        boolean remocao = listLivros.remove(obj);
        if (!remocao){
            throw new LivroException(LivroException.EXCLUIR,obj);
        }
        FileMethods.sobreescreverArquivo(arquivo, listLivros);
    }

    @Override
    public void excluirTodos() {
        FileMethods.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Livro atualizar(Livro obj) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        int index = listLivros.indexOf(obj);
        if (index == -1){
            throw new LivroException(LivroException.ATUALIZAR,obj);
        }
        listLivros.set(index, obj);
        FileMethods.sobreescreverArquivo(arquivo,listLivros);
        return obj;
    }

    @Override
    public ArrayList<Livro> encontrarTodos() {
        return  FileMethods.consultarArquivoList(arquivo);
    }

    @Override
    public Livro encontrarPorID(int id) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getId(), id)){
                return livro;
            }
        }
        throw new LivroException(LivroException.BUSCAR);
    }

    @Override
    public ArrayList<Livro> pesquisaPorTitulo(String titulo) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        ArrayList<Livro> listaPorTitulos= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getTitulo(), titulo)){
                listaPorTitulos.add(livro);
            }
        }
        if (listaPorTitulos.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorTitulos;
    }

    @Override
    public ArrayList<Livro> pesquisaPorisbn(Integer isbn) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        ArrayList<Livro> listaPorIsbn= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getIsbn(), isbn)){
                listaPorIsbn.add(livro);
            }
        }
        if (listaPorIsbn.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorIsbn;
    }

    @Override
    public ArrayList<Livro> pesquisaPorCategoria(String categoria) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        ArrayList<Livro> listaPorCategoria= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getCategoria(), categoria)){
                listaPorCategoria.add(livro);
            }
        }
        if (listaPorCategoria.isEmpty() ){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorCategoria;
    }

    @Override
    public ArrayList<Livro> pesquisaPorAutor(String autor) throws LivroException {
        ArrayList<Livro> listLivros = encontrarTodos();
        ArrayList<Livro> listaPorAutor= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getAutor(), autor)){
                listaPorAutor.add(livro);
            }
        }
        if (listaPorAutor.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorAutor;
    }


    private int getProximoID(ArrayList<Livro> livros){
        return (livros.size());
    }
}
