package com.example.dao.administrador;

import com.example.excecoes.AdministradorException;
import com.example.model.Administrador;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see AdministradorException
 * @see Administrador
 * @see java.util.ArrayList
 * @see java.util.Objects
 */
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
        return obj;
    }

    @Override
    public void excluir(Administrador obj) throws AdministradorException {
        boolean remocao =this.listDeAdministrador.remove(obj);
        if (!remocao){
            throw new AdministradorException(AdministradorException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeAdministrador= new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Administrador atualizar(Administrador obj) throws AdministradorException{
        int index = this.listDeAdministrador.indexOf(obj);
        if (index == -1){
            throw new AdministradorException(AdministradorException.ATUALIZAR);
        }
        this.listDeAdministrador.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Administrador> encontrarTodos() {
        return this.listDeAdministrador;
    }

    @Override
    public Administrador encontrarPorID(int id) throws AdministradorException {
        for (Administrador administrador: listDeAdministrador){
            if (Objects.equals(administrador.getNumeroDeIdentificacao(), id)){
                return administrador;
            }
        }
        throw new AdministradorException(AdministradorException.BUSCAR);
    }


}
