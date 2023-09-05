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


    public Emprestimos(){
        livro = new Livro();
        usuario = new Usuario();
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

    public void registrarEmprestimos(Livro livro, Usuario usuario){
        Emprestimos em = new Emprestimos();
        Date dataDeEmprestimo= new Date();
        em.setLivro(livro);
        em.setUsuario(usuario);                         //Código imcompleto, precisa fazer o incremento de 7 dias para a devolução
        em.setDataEmprestimos(dataDeEmprestimo);        // também precisa verificar se o usuario está bloqueado;

    }


}
