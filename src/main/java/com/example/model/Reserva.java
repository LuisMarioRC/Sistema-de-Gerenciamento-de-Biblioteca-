package com.example.model;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa a Reserva no sistema da biblioteca
 * @author Luis Mario
 * @author Gabril Henry
 * @see DAO
 * @see LivroException
 * @see ReservaException
 * @see UsuarioException
 * @see java.io.Serializable
 */
public class Reserva implements Serializable {
    private Integer idLivro;
    private Usuario usuario;

    /**
     * Construtor da classe reserva
     * @param idLivro que deseja ser reservado
     * @param usuario que deseja reserva
     * @param dataHoje que está sendo rservado
     * Possui verificações que lançam exceções caso alguns dos dados nao for condizente
     * Verificações de status, multa, livro sem devolver após o prazo, disponibilidade do livro e limite de reservas
     */
    public Reserva(Integer idLivro,Usuario usuario, String dataHoje) throws UsuarioException, LivroException, ReservaException {
        if (!usuario.getStatus() ){
            throw new UsuarioException(UsuarioException.BLOQUEIO);
        }
        if (!DAO.getEmprestimosDAO().validaMulta(usuario,dataHoje)){
            throw new UsuarioException(UsuarioException.MULTADO);
        }
        if (DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario, dataHoje)){
            throw new UsuarioException(UsuarioException.ATRASO);
        }
        if (DAO.getLivroDAO().encontrarPorID(idLivro).getDisponibilidade()){
            throw new LivroException(LivroException.DISPONIBILIDADE);
        }
        if (DAO.getReservaDAO().reservasDeUsuario(usuario).size() >=2 ){
            throw new ReservaException(ReservaException.LIMITE);
        }
        this.idLivro = idLivro;
        this.usuario=usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(idLivro, reserva.idLivro);
    }


    @Override
    public String toString() {
        return "Reserva{" +
                "idLivro=" + idLivro +
                ", usuario=" + usuario +
                '}';
    }
}
