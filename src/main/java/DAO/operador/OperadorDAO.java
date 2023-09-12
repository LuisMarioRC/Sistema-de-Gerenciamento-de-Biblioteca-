package DAO.operador;

import model.Operador;

import java.util.ArrayList;

public class OperadorDAO implements OperadorDAOInterface {




    public Operador cadastrarOperadores(String nome, String cargo, Integer senha,Integer numeroDeIdentificacao){
        Operador b= new Operador();
        b.setNome(nome);
        b.setCargo(cargo);
        b.setSenha(senha);
        b.setNumeroDeIdentificacao(numeroDeIdentificacao);
        b.setStatus(true);
        return b;
    }

    @Override
    public Operador criar(Operador obj) {
        return null;
    }

    @Override
    public void excluir(Operador obj) {

    }

    @Override
    public void excluirTodos() {

    }

    @Override
    public Operador atualizar(Operador obj) {
        return null;
    }

    @Override
    public ArrayList<Operador> encontrarTodos() {
        return null;
    }

    @Override
    public Operador encontrarPorID(int id) {
        return null;
    }
}
