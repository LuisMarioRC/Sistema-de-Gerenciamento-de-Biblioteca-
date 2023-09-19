package DAO.administrador;

import model.Administrador;
import model.Livro;

import java.util.ArrayList;
import java.util.Objects;

public class AdministradorDAO implements AdministradorDAOInterface{


    private ArrayList<Administrador> listDeAdministrador;

    private int proximoID;

    public int getProximoID() {
        return this.proximoID++;
    }

    public AdministradorDAO(){
        this.listDeAdministrador = new ArrayList<>();
        this.proximoID=0;
    }
    @Override
    public Administrador criar(Administrador obj) {
        obj.setNumeroDeIdentificacao(this.getProximoID());
        this.listDeAdministrador.add(obj);
        return null;
    }

    @Override
    public void excluir(Administrador obj) {
        this.listDeAdministrador.remove(obj);
    }

    @Override
    public void excluirTodos() {
        this.listDeAdministrador= new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Administrador atualizar(Administrador obj) {
        int index = this.listDeAdministrador.indexOf(obj);
        this.listDeAdministrador.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Administrador> encontrarTodos() {
        return this.listDeAdministrador;
    }

    @Override
    public Administrador encontrarPorID(int id) {
        for (Administrador administrador: listDeAdministrador){
            if (Objects.equals(administrador.getNumeroDeIdentificacao(), id)){
                return administrador;
            }
        }
        return null;
    }


}
