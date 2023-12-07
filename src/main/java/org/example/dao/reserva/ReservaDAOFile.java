/*package org.example.dao.reserva;

import org.example.excecoes.ReservaException;
import org.example.model.Reserva;
import org.example.utils.FileMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ReservaDAOFile implements ReservaDAOInterface {
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
        return  FileMethods.consultarArquivoList(arquivo);
    }

    @Override
    public Reserva criar(Reserva obj) {
        ArrayList<Reserva> reservas = encontrarTodos();
        if (!reservas.containsKey(obj.getIdLivro())) {
            // Se o livro não estiver no map, crie uma nova lista de reservas
            reservas.put(obj.getIdLivro(), new ArrayList<>());
        }
        reservas.get(obj.getIdLivro()).add(obj);
        FileMethods.sobreescreverArquivo(arquivo, reservas);
        return obj;
    }

    @Override
    public void excluir(Reserva obj) throws Exception {
        ArrayList<Reserva> reservas = encontrarTodos();
        ArrayList<Reserva> listDeReservas = reservas.get(obj.getIdLivro());
        listDeReservas.remove(obj);
        reservas.put(obj.getIdLivro(), listDeReservas);
        FileMethods.sobreescreverArquivo(arquivo, reservas);
    }


    @Override
    public Reserva atualizar(Reserva obj) throws Exception {
        ArrayList<Reserva> reservas = encontrarTodos();
        ArrayList<Reserva> listDeReservas = reservas.get(obj.getIdLivro());
        int index = listDeReservas.indexOf(obj);
        listDeReservas.remove(obj);
        listDeReservas.add(index, obj);
        reservas.put(obj.getIdLivro(), listDeReservas);
        FileMethods.sobreescreverArquivo(arquivo, reservas);
        return obj;
    }

    @Override
    public Reserva encontrarPorID(int id) throws Exception {
        ArrayList<Reserva> reservas = encontrarTodos();
        for (Reserva reservas : this.encontrarTodos()) {
            if (reservas.getIdLivro().equals(id)) {
                return reservas;
            }
        }
        throw new ReservaException(ReservaException.BUSCAR);
    }

}
*/