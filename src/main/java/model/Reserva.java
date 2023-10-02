package model;

import dao.DAO;
import dao.excecoes.LivroException;
import dao.excecoes.ReservaException;
import dao.excecoes.UsuarioException;

public class Reserva {
    private Integer idLivro;
    private Usuario usuario;

    public Reserva(Integer idLivro,Usuario usuario, String dataHoje) throws UsuarioException, LivroException, ReservaException {
        if (!usuario.getStatus() ){
            throw new UsuarioException(UsuarioException.BLOQUEIO);
        }
        if (!DAO.getEmprestimosDAO().validaMulta(usuario,dataHoje)){
            throw new UsuarioException(UsuarioException.MULTADO);
        }
        if (DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario)){
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
    public String toString() {
        return "Reserva{" +
                "idLivro=" + idLivro +
                ", usuario=" + usuario +
                '}';
    }
}
