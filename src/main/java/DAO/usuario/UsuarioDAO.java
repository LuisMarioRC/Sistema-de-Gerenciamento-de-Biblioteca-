package DAO.usuario;

import model.Usuario;

import java.util.ArrayList;

public class UsuarioDAO implements UsuarioDAOInterface{




    public Usuario cadastrarUsuario(String nome, String endereco, Integer telefone, Integer numeroDeIdentificacao){
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEndereco(endereco);
        u.setTelefone(telefone);
        u.setNumeroDeIdentificacao(numeroDeIdentificacao);
        u.setStatus(true);
        return u;
    }
    @Override
    public Usuario criar(Usuario obj) {
        return null;
    }

    @Override
    public void excluir(Usuario obj) {

    }

    @Override
    public void excluirTodos() {

    }

    @Override
    public Usuario atualizar(Usuario obj) {
        return null;
    }

    @Override
    public ArrayList<Usuario> encontrarTodos() {
        return null;
    }

    @Override
    public Usuario encontrarPorID(int id) {
        return null;
    }
}
