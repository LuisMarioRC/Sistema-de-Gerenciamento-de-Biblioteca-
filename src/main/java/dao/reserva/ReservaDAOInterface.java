package dao.reserva;

import dao.CRUD;
import model.Reserva;
import model.Usuario;

import java.util.ArrayList;
import java.util.Map;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.CRUD
 * @see model.Reserva
 * @see model.Usuario
 * @see java.util.ArrayList
 * @see java.util.Map
 */
public interface ReservaDAOInterface extends CRUD<Reserva, Exception> {
    public boolean primeiroUsuarioNaLista(Integer idLivro, Usuario usuario);
    public boolean verificaReserva(Integer idLivro);
    public void retiraUsuarioDaLista(Integer idLivro);
    public ArrayList<Reserva> getReservasParaLivro(Integer idLivro);
    public Integer numLivrosReservados();

    public ArrayList<Reserva> reservasDeUsuario(Usuario usuario);
    public Map<Integer, ArrayList<Reserva>> getReservas();
}