package org.example.dao.reserva;

import org.example.excecoes.ReservaException;
import org.example.model.Reserva;
import org.example.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see ReservaException
 * @see Reserva
 * @see Usuario
 * @see java.util.ArrayList
 * @see java.util.HashMap
 * @see java.util.Map
 */
public class ReservaDAO implements ReservaDAOInterface {

    private Map<Integer, ArrayList<Reserva>> reservas = new HashMap<>();


    public ReservaDAO() {
        this.reservas = new HashMap<Integer, ArrayList<Reserva>>();
    }

    @Override
    public Integer numLivrosReservados() {
        return this.reservas.size();
    }

    /**
     * Método para obter a lista de reservas para um livro específico
     * @param idLivro que deseja ver as reservas
     * @return um ArrayList contendo as reservas
     */
    @Override
    public ArrayList<Reserva> getReservasParaLivro(Integer idLivro) {
        return reservas.getOrDefault(idLivro, new ArrayList<>());
    }

    /**
     * Método que verifica na lista se o usuário que está sendo passado como parâmetro é o primeiro da lista
     * da lista de reserva
     * @param idLivro que foi reservado
     * @param usuario que está sendo verificado na lista de reserva
     * @return false se não for, e true se for o primeiro da lista de reserva para esse livro
     */
    @Override
    public boolean primeiroUsuarioNaLista(Integer idLivro, Usuario usuario) {
        ArrayList<Reserva> listaDeReserva = getReservasParaLivro(idLivro);
        if (!listaDeReserva.isEmpty()) {
            return listaDeReserva.get(0).getUsuario().equals(usuario);
        }
        return false;
    }


    /**
     * Metodo que retorna todas as reservas já feitas em forma de hashMap
     * @return hashMap contendo todas as reservas
     */
    @Override
    public Map<Integer, ArrayList<Reserva>> getReservas() {
        return this.reservas;
    }

    /**
     * Método que verifica se existe alguma reserva para determinado livro
     * @param idLivro que deseja verificar se tem reservas
     * @return true que significa que tem reservas e false que não tem reservas
     */
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

    /**
     * Método que realiza a remoção do primeiro usuário da lista de reserva de determinado livro
     * @param idLivro que deseja remover o primero que esta na lista de reservas
     */
    @Override
    public void retiraUsuarioDaLista(Integer idLivro) {
        ArrayList<Reserva> reservasDoLivro = this.getReservasParaLivro(idLivro);
        if (!reservasDoLivro.isEmpty()) {
            reservasDoLivro.remove(0);
            reservas.put(idLivro, reservasDoLivro);
        }
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
        throw new ReservaException(ReservaException.BUSCAR);
    }

    /**
     * Método que econtra todas as reserva que um usuario específico tem
     * @param usuario que deseja filtrar as reservas
     * @return uma lista de reservas desse usuario
     */
    @Override
    public ArrayList<Reserva> reservasDeUsuario(Usuario usuario){
        ArrayList<Reserva> reservasDeUsuario = new ArrayList<>();
        for (Reserva reservas: this.encontrarTodos()){
            if (reservas.getUsuario().equals(usuario)){
                reservasDeUsuario.add(reservas);
            }
        }
        return reservasDeUsuario;
    }
}