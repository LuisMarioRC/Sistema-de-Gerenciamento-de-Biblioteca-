package model;

import dao.DAO;
import dao.excecoes.UsuarioException;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {

    private String endereco;
    private String telefone;
    private LocalDate fimDaMulta;
    private String nome;
    private Boolean status; //true = ativo, false= bloqueado
    private Integer numeroDeIdentificacao;



    public Usuario(String nome,String endereco,String telefone){
        this.nome=nome;
        this.endereco=endereco;
        this.telefone=telefone;
        this.fimDaMulta = null;
        this.status=true;
    }

    public Usuario bloquearConta(Usuario usuario) throws UsuarioException{
        usuario.setStatus(false);// Muda o status da conta que deseja bloquear para false;
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
