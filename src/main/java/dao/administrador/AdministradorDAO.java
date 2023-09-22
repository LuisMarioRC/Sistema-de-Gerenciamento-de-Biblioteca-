package dao.administrador;

import dao.excecoes.DAOException;
import model.Administrador;

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
    public void excluir(Administrador obj) throws DAOException {
        boolean remocao =this.listDeAdministrador.remove(obj);
        if (!remocao){
            throw new DAOException(DAOException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeAdministrador= new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Administrador atualizar(Administrador obj) throws DAOException{
        int index = this.listDeAdministrador.indexOf(obj);
        if (index == -1){
            throw new DAOException(DAOException.ATUALIZAR);
        }
        this.listDeAdministrador.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Administrador> encontrarTodos() {
        return this.listDeAdministrador;
    }

    @Override
    public Administrador encontrarPorID(int id) throws DAOException {
        for (Administrador administrador: listDeAdministrador){
            if (Objects.equals(administrador.getNumeroDeIdentificacao(), id)){
                return administrador;
            }
        }
        throw new DAOException(DAOException.BUSCAR);
    }


}
