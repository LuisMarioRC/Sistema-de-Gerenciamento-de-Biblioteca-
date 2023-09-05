package DAO;

import model.Livro;
import java.util.ArrayList;
import java.util.Objects;

public class LivroDAO {
    private  ArrayList<Livro> listLivros;

    private int proximoID;
    public LivroDAO(){
        this.listLivros = new ArrayList<>();
        this.proximoID=1;
    }

    private int getProximoID() {
        return this.proximoID++;
    }

    public ArrayList<Livro> getListLivros(){
        return this.listLivros;
    }
    public void addLivro(Livro livro) {
        livro.setId(this.getProximoID());
        this.listLivros.add(livro);
    }


    public ArrayList<Livro> pesquisaPorTitulo(String titulo){
        ArrayList<Livro> listaPorTitulos= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getTitulo(), titulo)){
                listaPorTitulos.add(livro);
            }
        }
        return listaPorTitulos;
    }
    public ArrayList<Livro> pesquisaPorisbn(Integer isbn){
        ArrayList<Livro> listaPorIsbn= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getIsbn(), isbn)){
                listaPorIsbn.add(livro);
            }
        }
        return listaPorIsbn;
    }

    public ArrayList<Livro> pesquisaPorCategoria(String categoria){
        ArrayList<Livro> listaPorCategoria= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getCategoria(), categoria)){
                listaPorCategoria.add(livro);
            }
        }
        return listaPorCategoria;
    }
    public ArrayList<Livro> pesquisaPorAutor(String autor){
        ArrayList<Livro> listaPorAutor= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getAutor(), autor)){
                listaPorAutor.add(livro);
            }
        }
        return listaPorAutor;
    }

    public Livro pesquisaID(int id){
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getId(), id)){
                return livro;
            }
        }
        return null;
    }


}

