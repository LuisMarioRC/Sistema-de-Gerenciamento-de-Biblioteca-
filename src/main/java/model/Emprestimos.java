package model;

import dao.DAO;
import dao.excecoes.EmprestimosException;
import dao.excecoes.LivroException;
import dao.excecoes.ReservaException;
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


    public Emprestimos(Livro livro, Usuario usuario,String dataHoje) throws LivroException, ReservaException, UsuarioException {
        if (DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario)) {
            throw new UsuarioException(UsuarioException.ATRASO);
        }
        if (!livro.getDisponibilidade()){
            throw new LivroException(LivroException.INDISPONIBILIDADE);
        }
        if (!usuario.getStatus()){
            throw new UsuarioException(UsuarioException.BLOQUEIO);
        }
        if (!DAO.getEmprestimosDAO().validaMulta(usuario,dataHoje)){
            throw new UsuarioException(UsuarioException.MULTADO);
        }
        if (DAO.getEmprestimosDAO().econtraPorUsuario(usuario).size() >= 3){
            throw new UsuarioException(UsuarioException.LIMITE);
        }
        if (!DAO.getReservaDAO().getReservasParaLivro(livro.getId()).isEmpty()) {
            if (DAO.getReservaDAO().primeiroUsuarioNaLista(livro.getId(),usuario) ) {
                DAO.getReservaDAO().retiraUsuarioDaLista(livro.getId());
            }else{
                throw new ReservaException(ReservaException.RESERVADO);
            }
        }
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        livro.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro);
        usuario.setFimDaMulta(null);
        DAO.getUsuarioDAO().atualizar(usuario);
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimos = LocalDate.parse(dataHoje, dataFormatada);
        this.dataDevolucao = dataEmprestimos.plus(Period.ofDays(7));
        this.andamento = true; //true significa ativo;
    }



    private void multas (Livro livro, Usuario usuario, LocalDate dataQueDevolveu) throws EmprestimosException, UsuarioException {
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        if (dataQueDevolveu.isAfter(emprestimo.getDataDevolucao())) {
            long diferencaEntreDias = ChronoUnit.DAYS.between(emprestimo.getDataDevolucao(),dataQueDevolveu);
            int diasDeMulta = Math.toIntExact(diferencaEntreDias * 2);
            usuario.setFimDaMulta(dataQueDevolveu.plusDays(diasDeMulta));
            DAO.getUsuarioDAO().atualizar(usuario);
        }

    }

    public void registraDevolucao(Livro livro, Usuario usuario, LocalDate dataQueDevolveu) throws EmprestimosException, LivroException, UsuarioException {
        this.multas(livro,usuario,dataQueDevolveu);
        Emprestimos emprestimoDoLivro = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        emprestimoDoLivro.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimoDoLivro);
        livro.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro);
    }

    public Boolean renovar(Livro livro, Usuario usuario, String dataHoje) throws EmprestimosException, UsuarioException, ReservaException {
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newDate = LocalDate.parse(dataHoje, dataFormatada);
        if (DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario)) {
            throw new UsuarioException(UsuarioException.ATRASO);
        }
        if (DAO.getReservaDAO().verificaReserva(livro.getId())){
            throw new ReservaException(ReservaException.RESERVADO);
        }
        if (!DAO.getEmprestimosDAO().validaMulta(usuario,dataHoje)){
            throw new UsuarioException(UsuarioException.MULTADO);
        }
        if (!usuario.getStatus()){
            throw new UsuarioException(UsuarioException.BLOQUEIO);
        }
        if (ChronoUnit.DAYS.between(emprestimo.getDataEmprestimos(),emprestimo.getDataDevolucao()) >=14 ){
            throw new EmprestimosException(EmprestimosException.RENOVAR);
        }

        LocalDate dataDeDevolucao = emprestimo.getDataDevolucao();
        emprestimo.setDataDevolucao(dataDeDevolucao.plusDays(7));
        DAO.getEmprestimosDAO().atualizar(emprestimo);
        return true;

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
                ", andamento=" + andamento +
                '}';
    }
}
