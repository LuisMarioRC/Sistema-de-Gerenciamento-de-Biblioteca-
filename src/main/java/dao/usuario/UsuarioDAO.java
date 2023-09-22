package dao.usuario;

import dao.DAO;
import dao.excecoes.DAOException;
import model.Usuario;

import java.util.ArrayList;
import java.util.Objects;

public class UsuarioDAO implements UsuarioDAOInterface{

    private ArrayList<Usuario> listDeUsuario;
    private int proximoID;

    private int getProximoID(){
        return this.proximoID++;
    }

    public UsuarioDAO(){
        this.listDeUsuario = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Usuario criar(Usuario obj) {
        obj.setNumeroDeIdentificacao(this.getProximoID());
        this.listDeUsuario.add(obj);
        return obj;
    }

    @Override
    public void excluir(Usuario obj) throws DAOException {
        boolean remocao =this.listDeUsuario.remove(obj);
        if (!remocao){
            throw new DAOException(DAOException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeUsuario = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Usuario atualizar(Usuario obj) throws DAOException{
        int index = this.listDeUsuario.indexOf(obj);
        if (index == -1){
            throw new DAOException(DAOException.ATUALIZAR);
        }
        this.listDeUsuario.set(index,obj);
        return obj;
    }

    @Override
    public ArrayList<Usuario> encontrarTodos() {
        return this.listDeUsuario;
    }

    @Override
    public Usuario encontrarPorID(int id)throws DAOException {
        for (Usuario usuario : listDeUsuario){
            if (Objects.equals(usuario.getNumeroDeIdentificacao(),id)){
                return usuario;
            }
        }
        throw new DAOException(DAOException.BUSCAR);
    }


}
