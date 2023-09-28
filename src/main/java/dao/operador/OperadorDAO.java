package dao.operador;

import dao.excecoes.OperadorException;
import model.Operador;

import java.util.ArrayList;
import java.util.Objects;

public class OperadorDAO implements OperadorDAOInterface {
    private ArrayList<Operador> listDeOperador;
    private int proximoID;

    private int getProximoID() {
        return this.proximoID++;
    }

    public OperadorDAO(){
        this.listDeOperador= new ArrayList<>();
        this.proximoID=0;
    }


    @Override
    public Operador criar(Operador obj) {
        obj.setNumeroDeIdentificacao(this.getProximoID());
        this.listDeOperador.add(obj);
        return obj;
    }

    @Override
    public void excluir(Operador obj) throws OperadorException {
        boolean remocao = this.listDeOperador.remove(obj);
        if (!remocao){
            throw new OperadorException (OperadorException .EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeOperador = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Operador atualizar(Operador obj) throws OperadorException {
        int index = this.listDeOperador.indexOf(obj);
        if (index == -1){
            throw new OperadorException (OperadorException .ATUALIZAR);
        }
        this.listDeOperador.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Operador> encontrarTodos() {
        return this.listDeOperador;
    }

    @Override
    public Operador encontrarPorID(int id) throws OperadorException  {
        for (Operador operador : listDeOperador){
            if (Objects.equals(operador.getNumeroDeIdentificacao(),id)){
                return operador;
            }
        }
        throw new OperadorException (OperadorException .BUSCAR);
    }


}
