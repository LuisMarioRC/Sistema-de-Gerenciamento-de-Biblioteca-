package model;

import dao.DAO;
import dao.excecoes.EmprestimosException;
import dao.excecoes.LivroException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class Emprestimos {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimos;
    private LocalDate dataDevolucao;
    private int id;
    private Boolean andamento;


    //talvez precisa por a verificação no metodo criar do DAO.



    public Emprestimos(Livro livro, Usuario usuario) throws EmprestimosException, LivroException {
        //verificando se o usuario nao tem livro atrasado e se o livro está disponivel e se n tem reserva para esse livro.
        ArrayList<Usuario > listaDeReserva =DAO.getReservaDAO().getReservasParaLivro(livro.getId());
        if (!DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario) && livro.getDisponibilidade() && !listaDeReserva.isEmpty()) {
            LocalDate dataDeEmprestimo = LocalDate.now();
            LocalDate dataDeDevolucao = dataDeEmprestimo.plusDays(7);
            livro.setDisponibilidade(false);
            DAO.getLivroDAO().atualizar(livro);
            this.livro = livro;
            this.usuario = usuario;
            this.dataEmprestimos = dataDeEmprestimo;
            this.dataDevolucao = dataDeDevolucao;
            this.setAndamento(true); //true significa ativo;
        }else{
            throw new EmprestimosException(EmprestimosException.EMPRESTAR);
        }
    }

    public void multa (Livro livro, Usuario usuario) throws EmprestimosException {
        LocalDate datahoje = LocalDate.now();
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        if (emprestimo != null) {
            // verificar se tem emprestimos com esse livro;
            long diferencaEntreDias = ChronoUnit.DAYS.between(datahoje, emprestimo.getDataDevolucao());
            if (diferencaEntreDias > 0) { // para calcular a multa, tem que ter diferença de dias
                Integer diasDeMulta = Math.toIntExact(diferencaEntreDias * 2);
                usuario.setMulta(diasDeMulta);
            }
        }
    }


    public Livro registraDevolucao(Livro livro, Usuario usuario) throws EmprestimosException, LivroException {
        this.multa(livro,usuario);
        livro.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro);
        return livro;
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

    public Boolean getAndamento() {
        return andamento;
    }

    public void setAndamento(Boolean andamento) {
        this.andamento = andamento;
    }
}
