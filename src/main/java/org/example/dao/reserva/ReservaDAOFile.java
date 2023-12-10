package org.example.dao.reserva;

import org.example.excecoes.ReservaException;
import org.example.model.Reserva;
import org.example.model.Usuario;
import org.example.utils.FileMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ReservaDAOFile implements ReservaDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "reservas";

    public ReservaDAOFile(){
        arquivo = FileMethods.createFile(NAMEFILE);
    }

    @Override
    public void excluirTodos() {
        FileMethods.apagarConteudoArquivo(arquivo);
    }

    @Override
    public ArrayList<Reserva> encontrarTodos() {
        Map<Integer, ArrayList<Reserva>> reservas = this.getReservas();
        ArrayList<Reserva> reservasGerais = new ArrayList<>();
        for (Integer idDoLivro : reservas.keySet()) {
            ArrayList<Reserva> listDeReservas = reservas.get(idDoLivro);
            reservasGerais.addAll(listDeReservas);
        }
        return reservasGerais;
    }

    @Override
    public Reserva criar(Reserva obj) {
        Map<Integer, ArrayList<Reserva>> reservas = this.getReservas();
        if (!reservas.containsKey(obj.getIdLivro())) {
            // Se o livro não estiver no map, crie uma nova lista de reservas
            reservas.put(obj.getIdLivro(), new ArrayList<>());
        }
        reservas.get(obj.getIdLivro()).add(obj);
        FileMethods.sobreescreverArquivoMap(arquivo, reservas);
        return obj;
    }

    @Override
    public void excluir(Reserva obj) throws Exception {
        Map<Integer, ArrayList<Reserva>> reservas = this.getReservas();
        ArrayList<Reserva> listDeReservas = reservas.get(obj.getIdLivro());
        listDeReservas.remove(obj);
        reservas.put(obj.getIdLivro(), listDeReservas);
        FileMethods.sobreescreverArquivoMap(arquivo, reservas);
    }


    @Override
    public Reserva atualizar(Reserva obj) throws Exception {
        Map<Integer, ArrayList<Reserva>> reservas = getReservas();
        ArrayList<Reserva> listDeReservas = reservas.get(obj.getIdLivro());
        int index = listDeReservas.indexOf(obj);
        listDeReservas.remove(obj);
        listDeReservas.add(index, obj);
        reservas.put(obj.getIdLivro(), listDeReservas);
        FileMethods.sobreescreverArquivoMap(arquivo, reservas);
        return obj;
    }

    @Override
    public Reserva encontrarPorID(int id) throws Exception {
        ArrayList<Reserva> ListReserva = this.encontrarTodos();
        for (Reserva reservas : ListReserva) {
            if (reservas.getIdLivro().equals(id)) {
                return reservas;
            }
        }
        throw new ReservaException(ReservaException.BUSCAR);
    }

    @Override
    public boolean primeiroUsuarioNaLista(Integer idLivro, Usuario usuario) {
        ArrayList<Reserva> listaDeReserva = this.getReservasParaLivro(idLivro);
        if (!listaDeReserva.isEmpty()) {
            return listaDeReserva.get(0).getUsuario().equals(usuario);
        }
        return false;
    }

    @Override
    public boolean verificaReserva(Integer idLivro) {
        Map<Integer, ArrayList<Reserva>> reservas = this.getReservas();
        if (reservas.containsKey(idLivro)) {
            ArrayList<Reserva> lista = reservas.get(idLivro);
            // "true" corresponde se tem reserva
            return !lista.isEmpty();
        } else {
            return false; //"false" corresponde que nao tem reserva (a lista esta vazia)
        }
    }

    @Override
    public void retiraUsuarioDaLista(Integer idLivro) {
        Map<Integer, ArrayList<Reserva>> reservas = this.getReservas();
        ArrayList<Reserva> reservasDoLivro = this.getReservasParaLivro(idLivro);
        if (!reservasDoLivro.isEmpty()) {
            reservasDoLivro.remove(0);
            reservas.put(idLivro, reservasDoLivro);
            FileMethods.sobreescreverArquivoMap(arquivo,reservas);
        }

    }

    @Override
    public ArrayList<Reserva> getReservasParaLivro(Integer idLivro) {
        Map<Integer, ArrayList<Reserva>> reservas = this.getReservas();
        return reservas.getOrDefault(idLivro, new ArrayList<>());
    }

    @Override
    public Integer numLivrosReservados() {
        return FileMethods.consultarArquivoList(arquivo).size();
    }

    @Override
    public ArrayList<Reserva> reservasDeUsuario(Usuario usuario) {
        ArrayList<Reserva> Listreservas = this.encontrarTodos();
        ArrayList<Reserva> reservasDeUsuario = new ArrayList<>();
        for (Reserva reservas: Listreservas){
            if (reservas.getUsuario().equals(usuario)){
                reservasDeUsuario.add(reservas);
            }
        }
        return reservasDeUsuario;
    }

    @Override
    public Map<Integer, ArrayList<Reserva>> getReservas() {
        return FileMethods.consultarArquivoMap(arquivo);
    }
}
