package dao.bibliotecario;

import model.Bibliotecario;

import java.util.ArrayList;
import java.util.Objects;

public class BibliotecarioDAO implements BibliotecarioDAOInterface {


    private ArrayList<Bibliotecario> listDeBibliotecario;
    private int proximoID;

    private int getProximoID() {
        return this.proximoID++;
    }

    public BibliotecarioDAO(){
        this.listDeBibliotecario = new ArrayList<>();
        this.proximoID=0;
    }


    @Override
    public Bibliotecario criar(Bibliotecario obj) {
        obj.setNumeroDeIdentificacao(this.getProximoID());
        this.listDeBibliotecario.add(obj);
        return obj;
    }

    @Override
    public void excluir(Bibliotecario obj) {
        this.listDeBibliotecario.remove(obj);
    }

    @Override
    public void excluirTodos() {
        this.listDeBibliotecario= new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Bibliotecario atualizar(Bibliotecario obj) {
        int index= this.listDeBibliotecario.indexOf(obj);
        this.listDeBibliotecario.set(index,obj);
        return obj;
    }

    @Override
    public ArrayList<Bibliotecario> encontrarTodos() {
        return this.listDeBibliotecario;
    }

    @Override
    public Bibliotecario encontrarPorID(int id) {
        for (Bibliotecario bibliotecario: listDeBibliotecario){
            if (Objects.equals(bibliotecario.getNumeroDeIdentificacao(), id)){
                return bibliotecario;
            }
        }
        return null;
    }


}
