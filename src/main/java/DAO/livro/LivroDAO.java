package DAO.livro;


import DAO.excecoes.LivroException;
import model.Livro;
import java.util.ArrayList;
import java.util.Objects;

public class LivroDAO implements LivroDAOinterface{
    private  ArrayList<Livro> listLivros;

    private int proximoID;
    public LivroDAO(){
        this.listLivros = new ArrayList<>();
        this.proximoID=0;
    }

    private int getProximoID() {
        return this.proximoID++;
    }


    public void registraLivro(String titulo, String autor,String editora,Integer isbn,Integer anoDePublicacao,String categoria){
        Livro livro= new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setAnoDePublicacao(anoDePublicacao);
        livro.setCategoria(categoria);
        livro.setDisponibilidade(true);
        this.criar(livro);
    }


    public ArrayList<Livro> pesquisaPorTitulo(String titulo) throws LivroException{
        ArrayList<Livro> listaPorTitulos= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getTitulo(), titulo)){
                listaPorTitulos.add(livro);
            }
        }
        if (listLivros.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorTitulos;
    }


    public ArrayList<Livro> pesquisaPorisbn(Integer isbn) throws LivroException{
        ArrayList<Livro> listaPorIsbn= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getIsbn(), isbn)){
                listaPorIsbn.add(livro);
            }
        }
        if (listLivros.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorIsbn;
    }

    public ArrayList<Livro> pesquisaPorCategoria(String categoria) throws LivroException{
        ArrayList<Livro> listaPorCategoria= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getCategoria(), categoria)){
                listaPorCategoria.add(livro);
            }
        }
        if (listLivros.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorCategoria;
    }

    public ArrayList<Livro> pesquisaPorAutor(String autor) throws LivroException{
        ArrayList<Livro> listaPorAutor= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getAutor(), autor)){
                listaPorAutor.add(livro);
            }
        }
        if (listLivros.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorAutor;
    }


    @Override
    public Livro criar(Livro obj) {
        obj.setId(this.getProximoID());
        this.listLivros.add(obj);
        return obj;
    }

    @Override
    public void excluir(Livro obj) {
        this.listLivros.remove(obj);
    }

    @Override
    public void excluirTodos() {
        this.listLivros = new ArrayList<>();
        this.proximoID=0;

    }

    @Override
    public Livro atualizar(Livro obj) {
        int index = this.listLivros.indexOf(obj);
        this.listLivros.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Livro> encontrarTodos() {
        return this.listLivros;
    }

    @Override
    public Livro encontrarPorID(int id) throws LivroException {
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getId(), id)){
                return livro;
            }
        }
        throw new LivroException(LivroException.BUSCAR);
    }
}