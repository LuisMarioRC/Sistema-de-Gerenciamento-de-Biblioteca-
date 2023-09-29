package model;

import dao.DAO;
import dao.excecoes.EmprestimosException;
import dao.excecoes.LivroException;
import dao.excecoes.UsuarioException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Emprestimos {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimos;
    private LocalDate dataDevolucao;
    private int id;
    private Boolean andamento; //true = o emprestimo esta ativo, false = o emprestimo foi delvolvido


    public Emprestimos(Livro livro, Usuario usuario,String dataHoje) throws EmprestimosException, LivroException {
        //verificando se o usuario nao tem livro atrasado, se o livro está disponivel, se n tem reserva para esse livro,
        // se o usuario é o primeiro na lista de reserva, se o usuario não esta bloqueado e se tem multa.
        if (!DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario)
                && livro.getDisponibilidade()
                && DAO.getReservaDAO().primeiroUsuarioNaLista(livro.getId(),usuario)
                && usuario.getStatus()
                && usuario.getMulta() == 0
                && usuario.getNumEmprestimos() <= 3) {
            DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            livro.setDisponibilidade(false);
            DAO.getLivroDAO().atualizar(livro);
            this.livro = livro;
            this.usuario = usuario;
            this.dataEmprestimos = LocalDate.parse(dataHoje,dataFormatada);
            this.dataDevolucao = dataEmprestimos.plus(Period.ofDays(7));
            this.setAndamento(true); //true significa ativo;
        }else{
            throw new EmprestimosException(EmprestimosException.EMPRESTAR);
        }
    }

    public void multa (Livro livro, Usuario usuario) throws EmprestimosException, UsuarioException {
        LocalDate datahoje = LocalDate.now();
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        if (emprestimo != null) {
            // verificar se tem emprestimos com esse livro;
            long diferencaEntreDias = ChronoUnit.DAYS.between(datahoje, emprestimo.getDataDevolucao());
            if (diferencaEntreDias > 0) { // para calcular a multa, tem que ter diferença de dias
                Integer diasDeMulta = Math.toIntExact(diferencaEntreDias * 2);
                usuario.setMulta(diasDeMulta);
                DAO.getUsuarioDAO().atualizar(usuario);
            }
        }
    }


    public Livro registraDevolucao(Livro livro, Usuario usuario) throws EmprestimosException, LivroException, UsuarioException {
        this.multa(livro,usuario);
        Emprestimos emprestimoDoLivro = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        emprestimoDoLivro.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimoDoLivro);
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

    public Boolean getAndamento() {
        return andamento;
    }

    public void setAndamento(Boolean andamento) {
        this.andamento = andamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimos that = (Emprestimos) o;
        return id == that.id;
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
