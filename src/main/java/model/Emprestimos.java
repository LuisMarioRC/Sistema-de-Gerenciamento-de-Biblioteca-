package model;

import java.time.LocalDate;
import java.util.Objects;


public class Emprestimos {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimos;
    private LocalDate dataDevolucao;
    private int id;


    public Emprestimos(Livro livro, Usuario usuario){
        //Tem q verificar se o livro ta disponivel
        LocalDate dataDeEmprestimo= LocalDate.now();
        LocalDate dataDeDevolucao = dataDeEmprestimo.plusDays(7);
        livro.setDisponibilidade(false);
        this.livro=livro;
        this.usuario=usuario;
        this.dataEmprestimos=dataDeEmprestimo;
        this.dataDevolucao=dataDeDevolucao;
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

    public LocalDate getDataEmprestimos() {
        return dataEmprestimos;
    }

    public void setDataEmprestimos(LocalDate dataEmprestimos) {
        this.dataEmprestimos = dataEmprestimos;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Emprestimos{" +
                "livro=" + livro +
                ", usuario=" + usuario +
                ", dataEmprestimos=" + dataEmprestimos +
                ", dataDevolucao=" + dataDevolucao +
                ", id=" + id +
                '}';
    }
}
