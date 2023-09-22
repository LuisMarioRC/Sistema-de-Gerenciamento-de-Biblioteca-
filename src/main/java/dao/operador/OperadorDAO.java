package dao.operador;

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


    /**public Operador cadastrarOperadores(String nome, String cargo, Integer senha){
        Operador operador= new Operador();
        operador.setNome(nome);
        operador.setCargo(cargo);
        operador.setSenha(senha);
        operador.setStatus(true);
        return operador;
    }*/

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
    public void excluir(Operador obj) {
        this.listDeOperador.remove(obj);
    }

    @Override
    public void excluirTodos() {
        this.listDeOperador = new ArrayList<>();
        this.proximoID=0;
    }

    @Override
    public Operador atualizar(Operador obj) {
        int index = this.listDeOperador.indexOf(obj);
        this.listDeOperador.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Operador> encontrarTodos() {
        return this.listDeOperador;
    }

    @Override
    public Operador encontrarPorID(int id) {
        for (Operador operador : listDeOperador){
            if (Objects.equals(operador.getNumeroDeIdentificacao(),id)){
                return operador;
            }
        }
        return null;
    }


}
