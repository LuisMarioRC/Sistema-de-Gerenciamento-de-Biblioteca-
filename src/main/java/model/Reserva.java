package model;

import dao.DAO;

public class Reserva {
    private Integer idLivro;
    private Usuario usuario;


    public Reserva(Integer idLivro,Usuario usuario){
        if (usuario.getStatus()
                && usuario.getMulta() == 0
                && !DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario)) {
            this.idLivro = idLivro;
            this.setUsuario(usuario);
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
