package dao.usuario;


import excecoes.UsuarioException;
import model.Usuario;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see excecoes.UsuarioException
 * @see model.Usuario
 * @see java.util.ArrayList
 * @see java.util.Objects
 */
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
    public void excluir(Usuario obj) throws UsuarioException {
        boolean remocao =this.listDeUsuario.remove(obj);
        if (!remocao){
            throw new UsuarioException(UsuarioException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeUsuario = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Usuario atualizar(Usuario obj) throws UsuarioException{
        int index = this.listDeUsuario.indexOf(obj);
        if (index == -1){
            throw new UsuarioException(UsuarioException.ATUALIZAR);
        }
        this.listDeUsuario.set(index,obj);
        return obj;
    }

    @Override
    public ArrayList<Usuario> encontrarTodos() {
        return this.listDeUsuario;
    }

    @Override
    public Usuario encontrarPorID(int id)throws UsuarioException {
        for (Usuario usuario : listDeUsuario){
            if (Objects.equals(usuario.getNumeroDeIdentificacao(),id)){
                return usuario;
            }
        }
        throw new UsuarioException(UsuarioException.BUSCAR);
    }

}
