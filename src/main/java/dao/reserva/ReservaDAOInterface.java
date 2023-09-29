package dao.reserva;

import dao.CRUD;
import model.Reserva;
import model.Usuario;

import java.util.ArrayList;

public interface ReservaDAOInterface extends CRUD<Reserva, Exception> {
    public boolean primeiroUsuarioNaLista(Integer idLivro, Usuario usuario);
    public boolean verificaReserva(Integer idLivro);
    public void retiraUsuarioDaLista(Integer idLivro , Usuario usuario);
    public ArrayList<Reserva> getReservasParaLivro(Integer idLivro);
    public Integer numLivrosReservados();




}

