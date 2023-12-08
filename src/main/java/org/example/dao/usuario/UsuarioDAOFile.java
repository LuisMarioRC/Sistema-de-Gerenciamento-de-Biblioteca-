package org.example.dao.usuario;

import org.example.excecoes.UsuarioException;
import org.example.model.Usuario;
import org.example.utils.FileMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class UsuarioDAOFile implements UsuarioDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "usuario";

    public UsuarioDAOFile(){
        arquivo = FileMethods.createFile(NAMEFILE);
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
        return FileMethods.consultarArquivoList(arquivo);
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
