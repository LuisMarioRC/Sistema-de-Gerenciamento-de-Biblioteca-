package model;
import java.util.Date;


public class Emprestimos {
    private String livro;
    private String usuario;
    private Date dataEmprestimos;
    private Date dataDevolucao;
    private Integer multa;
    private Boolean status;

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
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
