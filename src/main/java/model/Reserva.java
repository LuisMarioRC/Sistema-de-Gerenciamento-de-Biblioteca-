package model;

import dao.DAO;

public class Reserva {
    private Integer idLivro;
    private Usuario usuario;

    public Reserva(Integer idLivro,Usuario usuario, String dataHoje){
        if (usuario.getStatus()
                && DAO.getEmprestimosDAO().validaMulta(usuario,dataHoje)
                && !DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario)) {
            this.idLivro = idLivro;
            this.usuario=usuario;
        }
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
