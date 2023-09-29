package dao.reserva;

import model.Reserva;
import model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservaDAO implements ReservaDAOInterface {

    private Map<Integer, ArrayList<Reserva>> reservas = new HashMap<>();


    public ReservaDAO() {
        this.reservas = new HashMap<Integer, ArrayList<Reserva>>();
    }

    @Override
    public Integer numLivrosReservados() {
        return this.reservas.size();
    }

    @Override
    // Método para obter a lista de reservas para um livro específico
    public ArrayList<Reserva> getReservasParaLivro(Integer idLivro) {
        return reservas.getOrDefault(idLivro, new ArrayList<>());
    }

    @Override
    public boolean primeiroUsuarioNaLista(Integer idLivro, Usuario usuario) {
        ArrayList<Reserva> listaDeReserva = getReservasParaLivro(idLivro);
        return listaDeReserva.get(0).getUsuario().equals(usuario);
    }


    //retorna toda o map;
    public Map<Integer, ArrayList<Reserva>> getReservas() {
        return this.reservas;
    }

    @Override
    public boolean verificaReserva(Integer idLivro) {
        if (reservas.containsKey(idLivro)) {
            ArrayList<Reserva> lista = reservas.get(idLivro);
            // "true" corresponde se tem reserva
            return !lista.isEmpty();
        } else {
            return false; //"false" corresponde que nao tem reserva (a lista esta vazia)
        }
    }

    @Override
    public void retiraUsuarioDaLista(Integer idLivro, Usuario usuario) {
        ArrayList<Reserva> reservasDoLivro = this.getReservasParaLivro(idLivro);
        reservasDoLivro.remove(0);
        reservas.put(idLivro, reservasDoLivro);
    }


    @Override
    public Reserva criar(Reserva obj) {
        if (!reservas.containsKey(obj.getIdLivro())) {
            // Se o livro não estiver no map, crie uma nova lista de reservas
            reservas.put(obj.getIdLivro(), new ArrayList<>());
        }
        reservas.get(obj.getIdLivro()).add(obj);
        return obj;
    }

    @Override
    public void excluir(Reserva obj) throws Exception {
        ArrayList<Reserva> listDeReservas = reservas.get(obj.getIdLivro());
        listDeReservas.remove(obj);
        reservas.put(obj.getIdLivro(), listDeReservas);
    }

    @Override
    public void excluirTodos() {
        this.reservas = new HashMap<Integer, ArrayList<Reserva>>();
    }

    @Override
    public Reserva atualizar(Reserva obj) throws Exception {
        ArrayList<Reserva> listDeReservas = reservas.get(obj.getIdLivro());
        int index = listDeReservas.indexOf(obj);
        listDeReservas.remove(obj);
        listDeReservas.add(index, obj);
        reservas.put(obj.getIdLivro(), listDeReservas);
        return obj;
    }

    @Override
    public ArrayList<Reserva> encontrarTodos() {
        ArrayList<Reserva> reservasGerais = new ArrayList<>();
        for (Integer idDoLivro : reservas.keySet()) {
            ArrayList<Reserva> listDeReservas = reservas.get(idDoLivro);
            reservasGerais.addAll(listDeReservas);
        }
        return reservasGerais;
    }

    @Override
    public Reserva encontrarPorID(int id) throws Exception {
        for (Reserva reserva : this.encontrarTodos()) {
            if (reserva.getIdLivro().equals(id)) {
                return reserva;
            }
        }
        return null;
    }
}