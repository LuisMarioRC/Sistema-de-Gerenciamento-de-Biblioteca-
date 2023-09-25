package dao.reserva;

import model.Livro;
import model.Reserva;
import model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservaDAO implements ReservaDAOInterface{

    private Map<Livro, ArrayList<Usuario>> reservas = new HashMap<>();

    public ReservaDAO() {
        this.reservas = new HashMap<Livro, ArrayList<Usuario>>();
    }

    // Método para obter a lista de reservas para um livro específico
    public ArrayList<Usuario> getReservasParaLivro(Livro livro) {
        return reservas.getOrDefault(livro, new ArrayList<>());
    }

    //retorna toda o map;
    public Map<Livro, ArrayList<Usuario>> getReservas(){
        return this.reservas;
    }


    //vericar se para reservar, será o livro ou Id do livro.
    @Override
    public Reserva criar(Reserva obj) {
        if (!reservas.containsKey(obj.getLivro())) {
            // Se o livro não estiver no map, crie uma nova lista de reservas
            reservas.put(obj.getLivro(), new ArrayList<>());
        }
        reservas.get(obj.getLivro()).add(obj.getUsuario());
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
