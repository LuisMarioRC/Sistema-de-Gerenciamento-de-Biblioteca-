package com.example.model;

import com.example.dao.DAO;
import com.example.excecoes.UsuarioException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa o usuario no sistema da Biblioteca, possuindo métodos que alteram seu status no sistema
 * @author Gabriel Henry
 * @author Luis Mario
 * @see DAO
 * @see UsuarioException
 * @see java.time.LocalDate
 * @see java.util.Objects
 */
public class Usuario implements Serializable {

    private String endereco;
    private String telefone;
    private LocalDate fimDaMulta;
    private String nome;
    private Boolean status; //true = ativo, false= bloqueado
    private Integer numeroDeIdentificacao;
    private Integer senha;



    /**
     * Construtor da classe Usuario.
     * O parametro fimDaMulta inicia com o valor null, que representa que não está com multa prevista.
     * O parámetro status inicia com o booleano true, que significa que o usuario não está bloqueado.
     * @param nome o nome do usuário
     * @param endereco o endereço do usuário
     * @param telefone o telefone do usuário
     */
    public Usuario(String nome,String endereco,String telefone,Integer senha){
        this.nome=nome;
        this.endereco=endereco;
        this.telefone=telefone;
        this.fimDaMulta = null;
        this.status=true;
        this.numeroDeIdentificacao=-1;
        this.senha=senha;
    }

    /**
     * Método que bloqueia conta, onde muda o status do usuario para false
     * @param usuario para bloquear
     * @return usuario bloqueado
     * @throws UsuarioException de erro de atualização
     */
    public Usuario bloquearConta(Usuario usuario) throws UsuarioException{
        usuario.setStatus(false);// Muda o status da conta que deseja bloquear para false;
        DAO.getUsuarioDAO().atualizar(usuario);
        return usuario;
    }

    /**
     * Método que desbloqueia conta, onde muda o status do usuario para true
     * @param usuario para desbloquear
     * @return usuario desbloqueado
     * @throws UsuarioException de erro de atualização
     */
    public Usuario desbloqueiaConta(Usuario usuario) throws UsuarioException{
        usuario.setStatus(true);// Muda o status da conta que desbloquear para true;
        DAO.getUsuarioDAO().atualizar(usuario);
        return usuario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getFimDaMulta() {
        return fimDaMulta;
    }

    public void setFimDaMulta(LocalDate fimDaMulta) {
        this.fimDaMulta = fimDaMulta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getNumeroDeIdentificacao() {
        return numeroDeIdentificacao;
    }

    public void setNumeroDeIdentificacao(Integer numeroDeIdentificacao) {
        this.numeroDeIdentificacao = numeroDeIdentificacao;
    }

    public Integer getSenha() {
        return senha;
    }

    public void setSenha(Integer senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(numeroDeIdentificacao, usuario.numeroDeIdentificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroDeIdentificacao);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", fimDaMulta=" + fimDaMulta +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                ", numeroDeIdentificacao=" + numeroDeIdentificacao +
                '}';
    }
}
