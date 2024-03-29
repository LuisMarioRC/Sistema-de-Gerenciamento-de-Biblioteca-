package com.example.dao.usuario;

import com.example.excecoes.UsuarioException;
import com.example.model.Usuario;
import com.example.utils.FileMethods;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see UsuarioException
 * @see Usuario
 * @see java.util.ArrayList
 * @see java.util.Objects
 * @see FileMethods
 * @see java.io.File
 */
public class UsuarioDAOFile implements UsuarioDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "usuario";

    public UsuarioDAOFile(){
        arquivo = FileMethods.criarArquivo(NAMEFILE);
    }

    @Override
    public Usuario criar(Usuario obj) {
        ArrayList<Usuario> usuario = encontrarTodos() ;
        obj.setNumeroDeIdentificacao(this.getProximoID(encontrarTodos()));
        usuario.add(obj);
        FileMethods.sobreescreverArquivo(arquivo, usuario);
        return obj;
    }

    @Override
    public void excluir(Usuario obj) throws UsuarioException {
        ArrayList<Usuario> usuario = encontrarTodos() ;
        boolean remocao = usuario.remove(obj);
        if (!remocao){
            throw new UsuarioException(UsuarioException.EXCLUIR);
        }
        FileMethods.sobreescreverArquivo(arquivo, usuario);

    }

    @Override
    public void excluirTodos() {FileMethods.apagarConteudoArquivo(arquivo);}

    @Override
    public Usuario atualizar(Usuario obj) throws UsuarioException{
        ArrayList<Usuario> usuario = encontrarTodos();
        int index = usuario.indexOf(obj);
        if (index == -1){
            throw new UsuarioException(UsuarioException.ATUALIZAR);
        }
        usuario.set(index,obj);
        FileMethods.sobreescreverArquivo(arquivo, usuario);
        return obj;
    }

    @Override
    public ArrayList<Usuario> encontrarTodos() {
        return FileMethods.lerArquivoList(arquivo);
    }

    @Override
    public Usuario encontrarPorID(int id)throws UsuarioException {
        ArrayList<Usuario> usuarios = encontrarTodos();
        for (Usuario usuario : usuarios){
            if (Objects.equals(usuario.getNumeroDeIdentificacao(),id)){
                return usuario;
            }
        }
        throw new UsuarioException(UsuarioException.BUSCAR);
    }

    private int getProximoID(ArrayList<Usuario> usuario){
        return (usuario.size());
    }

}
