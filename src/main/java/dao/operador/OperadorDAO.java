package dao.operador;

import dao.excecoes.DAOException;
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

    public void bloquearConta(Operador conta){
        conta.setStatus(false);   // Muda o status da conta que deseja bloquear para false;
    }


    @Override
    public Operador criar(Operador obj) {
        obj.setNumeroDeIdentificacao(this.getProximoID());
        this.listDeOperador.add(obj);
        return obj;
    }

    @Override
    public void excluir(Operador obj) throws DAOException{
        boolean remocao = this.listDeOperador.remove(obj);
        if (!remocao){
            throw new DAOException(DAOException.EXCLUIR);
        }
    }

    @Override
    public void excluirTodos() {
        this.listDeOperador = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Operador atualizar(Operador obj) throws DAOException{
        int index = this.listDeOperador.indexOf(obj);
        if (index == -1){
            throw new DAOException(DAOException.ATUALIZAR);
        }
        this.listDeOperador.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Operador> encontrarTodos() {
        return this.listDeOperador;
    }

    @Override
    public Operador encontrarPorID(int id) throws DAOException {
        for (Operador operador : listDeOperador){
            if (Objects.equals(operador.getNumeroDeIdentificacao(),id)){
                return operador;
            }
        }
        throw new DAOException(DAOException.BUSCAR);
    }


}
