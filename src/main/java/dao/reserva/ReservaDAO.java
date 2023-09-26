package dao.reserva;

import model.Reserva;
import model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservaDAO implements ReservaDAOInterface{

    private Map<Integer, ArrayList<Usuario>> reservas = new HashMap<>();


    public ReservaDAO() {
        this.reservas = new HashMap<Integer, ArrayList<Usuario>>();
    }

    // Método para obter a lista de reservas para um livro específico
    public ArrayList<Usuario> getReservasParaLivro(Integer idLivro) {
        return reservas.getOrDefault(idLivro, new ArrayList<>());
    }

    //retorna toda o map;
    public Map<Integer, ArrayList<Usuario>> getReservas(){
        return this.reservas;
    }


    public boolean verificaReserva(Integer idLivro){
        if (reservas.containsKey(idLivro)){
            ArrayList<Usuario> lista= reservas.get(idLivro);
            // "true" corresponde se tem reserva
            return !lista.isEmpty();
        }else{
            return false; //"false" corresponde que nao tem reserva (a lista esta vazia)
        }
    }


    @Override
    public Reserva criar(Reserva obj) {
        if (!reservas.containsKey(obj.getIdLivro())) {
            // Se o livro não estiver no map, crie uma nova lista de reservas
            reservas.put(obj.getIdLivro(), new ArrayList<>());
        }
        reservas.get(obj.getIdLivro()).add(obj.getUsuario());
        return obj;
    }

    @Override
    public void excluir(Reserva obj) throws Exception {

    }

    @Override
    public void excluirTodos() {

    }

    @Override
    public Reserva atualizar(Reserva obj) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Reserva> encontrarTodos() {
        return null;
    }

    @Override
    public Reserva encontrarPorID(int id) throws Exception {
        return null;
    }
}
