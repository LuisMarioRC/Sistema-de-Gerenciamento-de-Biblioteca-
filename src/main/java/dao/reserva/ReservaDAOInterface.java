package dao.reserva;

import dao.CRUD;
import model.Reserva;
import model.Usuario;

import java.util.ArrayList;
import java.util.Map;

public interface ReservaDAOInterface extends CRUD<Reserva, Exception> {
    public boolean primeiroUsuarioNaLista(Integer idLivro, Usuario usuario);
    public boolean verificaReserva(Integer idLivro);
    public void retiraUsuarioDaLista(Integer idLivro);
    public ArrayList<Reserva> getReservasParaLivro(Integer idLivro);
    public Integer numLivrosReservados();


    public Map<Integer, ArrayList<Reserva>> getReservas();
}