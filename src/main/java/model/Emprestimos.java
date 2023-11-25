package model;

import dao.DAO;
import excecoes.EmprestimosException;
import excecoes.LivroException;
import excecoes.ReservaException;
import excecoes.UsuarioException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Classe que representa o emprétimo do livro no sistema da biblioteca
 * @author Gabriel Henry
 * @author Luis Mario
 * @see dao.DAO
 * @see excecoes.EmprestimosException
 * @see excecoes.LivroException
 * @see excecoes.ReservaException
 * @see excecoes.UsuarioException
 * @see java.time.LocalDate
 * @see java.time.Period
 * @see java.time.format.DateTimeFormatter
 * @see java.time.temporal.ChronoUnit
 * @see java.util.Objects
 */
public class Emprestimos {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimos;
    private LocalDate dataDevolucao;
    private int id;
    private Boolean andamento; //true = o emprestimo esta ativo, false = o emprestimo foi delvolvido

    /**
     * Construtor da classe emprestimos
     * @param livro que deseja fazer o empréstimo
     * @param usuario que está realizando o empréstimos
     * @param dataHoje que o empréstimo esta sendo realizado
     * Possui verificações que lançam exceções caso alguns dos dados nao for condizente
     * Verificações de livro sem devolver, disponibilidade do livro, usuário com estatus bloqueado, multa,
     *                 limite de empréstimos, se tem reserva para esse livro e se o usuario que esta em primeiro
     *                 na lista de reserva é quem esta tentando pegar o livro.
     */
    public Emprestimos(Livro livro, Usuario usuario,String dataHoje) throws LivroException, ReservaException, UsuarioException {
        if (DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario,dataHoje)) {
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


    /**
     * Método que calcula o período de multa aplicado ao Usuário, sendo o dobro dos dias de atraso.
     * @param livro que foi emprestado
     * @param usuario que realizou o emprestimo
     * @param dataQueDevolveu data que o usuario devolveu o livro para a biblioteca
     */
    private void multas (Livro livro, Usuario usuario, LocalDate dataQueDevolveu) throws EmprestimosException, UsuarioException {
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        if (dataQueDevolveu.isAfter(emprestimo.getDataDevolucao())) {
            long diferencaEntreDias = ChronoUnit.DAYS.between(emprestimo.getDataDevolucao(),dataQueDevolveu);
            int diasDeMulta = Math.toIntExact(diferencaEntreDias * 2);
            usuario.setFimDaMulta(dataQueDevolveu.plusDays(diasDeMulta));
            DAO.getUsuarioDAO().atualizar(usuario);
        }

    }

    /**
     * Método que registra a devolução do livro, alterando seu status de andamento do emprestimo para falso,
     * reativando sua disponibilidade e o atualizando.
     * @param livro que foi emprestado
     * @param usuario que realizou o emprestimo
     * @param dataQueDevolveu data que o usuario devolveu o livro
     */
    public void registraDevolucao(Livro livro, Usuario usuario, LocalDate dataQueDevolveu) throws EmprestimosException, LivroException, UsuarioException {
        this.multas(livro,usuario,dataQueDevolveu);
        Emprestimos emprestimoDoLivro = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        emprestimoDoLivro.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimoDoLivro);
        livro.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro);
    }

    /**
     * Construtor de renovaçõo da classe empréstimo
     *
     * @param livro que será renovado
     * @param usuario que deseja renovar o livro
     * @param dataHoje data que está sendo realizada a renovação
     * Possui verificações que lançam exceções caso alguns dos dados nao for condizente
     * Verificações de para saber se o usuario está com algum livro atrasado, se o realmente já está reservado
     *                para que possa ser realizada a renovação, se o usuario está multado ou bloqueado e se
     *                o mesmo já atingiu seu limite de renovações.
     */
    public Boolean renovar(Livro livro, Usuario usuario, String dataHoje) throws EmprestimosException, UsuarioException, ReservaException {
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(livro.getId());
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newDate = LocalDate.parse(dataHoje, dataFormatada);
        if (DAO.getEmprestimosDAO().verificaAtrasoDeUsuario(usuario,dataHoje)) {
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
