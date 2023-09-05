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

    public void addLivro(Livro livro) {
        livro.setId(this.getProximoID());
        this.listLivros.add(livro);
    }

    public ArrayList<Livro> pesquisaPorTitulo(String titulo){
        ArrayList<Livro> livrosTitulos= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getTitulo(), titulo)){
               livrosTitulos.add(livro);
            }
        }
        return livrosTitulos;
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

