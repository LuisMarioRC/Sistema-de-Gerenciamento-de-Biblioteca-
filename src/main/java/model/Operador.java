package model;


import java.util.Objects;

public class Operador {
    private String nome;
    private String cargo;
    private Integer senha;
    private Integer numeroDeIdentificacao;
    private Boolean status;


    public Operador(String nome){
        this.nome= nome;
        this.status=true;
    }

    public Operador(String nome, String cargo,Integer senha){
        this.nome=nome;
        this.cargo=cargo;
        this.senha=senha;
        this.status=true; // True siginifica que o operador não esta bloqueado
    }

    public void bloquearConta(Operador conta){
        conta.setStatus(false);   // Muda o status da conta que deseja bloquear para false;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getSenha() {
        return senha;
    }

    public void setSenha(Integer senha) {
        this.senha = senha;
    }

    public Integer getNumeroDeIdentificacao() {
        return numeroDeIdentificacao;
    }

    public void setNumeroDeIdentificacao(Integer numeroDeIdentificacao) {
        this.numeroDeIdentificacao = numeroDeIdentificacao;
    }


    @Override
    public int hashCode() {
        return Objects.hash(numeroDeIdentificacao);
    }

    @Override
    public String toString() {
        return "Operador{" +
                "nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", senha=" + senha +
                ", numeroDeIdentificacao=" + numeroDeIdentificacao +
                ", status=" + status +
                '}';
    }
}
