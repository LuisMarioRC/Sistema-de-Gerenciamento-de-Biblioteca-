package model;
import DAO.LivroDAO;

import java.util.Date;


public class Emprestimos {
    private Livro livro;
    private Usuario usuario;
    private Date dataEmprestimos;
    private Date dataDevolucao;
    private Integer multa;
    private Boolean status;


    private LivroDAO livrodao;

    public Emprestimos(){
        livrodao= new LivroDAO();
    }


    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataEmprestimos() {
        return dataEmprestimos;
    }

    public void setDataEmprestimos(Date dataEmprestimos) {
        this.dataEmprestimos = dataEmprestimos;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Integer getMulta() {
        return multa;
    }

    public void setMulta(Integer multa) {
        this.multa = multa;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
