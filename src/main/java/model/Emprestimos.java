package model;

import dao.DAO;
import dao.excecoes.EmprestimosException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import static dao.DAO.*;

public class Emprestimos {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimos;
    private LocalDate dataDevolucao;
    private int id;



    public Emprestimos(Livro livro, Usuario usuario) throws EmprestimosException {
        //verificando se o usuario nao tem livro atrasado e se o livro está disponivel.
        if (!DAO.getEmprestimosDAO().verificaAtraso(usuario) && livro.getDisponibilidade()) {
            LocalDate dataDeEmprestimo = LocalDate.now();
            LocalDate dataDeDevolucao = dataDeEmprestimo.plusDays(7);
            livro.setDisponibilidade(false);
            this.livro = livro;
            this.usuario = usuario;
            this.dataEmprestimos = dataDeEmprestimo;
            this.dataDevolucao = dataDeDevolucao;
        }else{
            throw new EmprestimosException(EmprestimosException.EMPRESTAR);
        }
    }
    // Esta chamando a execeção do DAO nessa classe, verificar se é melhor fazer um class de execeção pra cada;
    public void multa (Livro livro, Usuario usuario) throws EmprestimosException {
        LocalDate datahoje = LocalDate.now();
        int idLivro = livro.getId();
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(idLivro);
        if (emprestimo != null) {
            // verificar se tem emprestimos com esse livro; (dps testar com exceção)
            long diferencaEntreDias = ChronoUnit.DAYS.between(datahoje, emprestimo.getDataDevolucao());
            if (diferencaEntreDias > 0) { // para calcular a multa, tem que ter diferença de dias
                Integer diasDeMulta = Math.toIntExact(diferencaEntreDias * 2);
                usuario.setMulta(diasDeMulta);
                //usuario.setStatus(false);  status é bloqueio, nao multa
            }
        }
    }


    //Tbm chamando a exceção do DAO
    public void registraDevolucao(Livro livro, Usuario usuario) throws EmprestimosException {
        this.multa(livro,usuario);
        //int idLivro = livro.getId();
        //Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(idLivro);
        livro.setDisponibilidade(true);
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
