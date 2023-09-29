package model;

import java.util.Objects;

public class Operador {
    private String nome;
    private Integer senha;
    private Integer numeroDeIdentificacao;
    private Boolean status;


    public Operador(String nome){
        this.nome= nome;
        this.status=true; //True siginifica que o operador não esta bloqueado
    }

    public Operador(String nome,Integer senha){
        this.nome=nome;
        this.senha=senha;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operador operador = (Operador) o;
        return Objects.equals(numeroDeIdentificacao, operador.numeroDeIdentificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroDeIdentificacao);
    }

    @Override
    public String toString() {
        return "Operador{" +
                "nome='" + nome + '\'' +
                ", senha=" + senha +
                ", numeroDeIdentificacao=" + numeroDeIdentificacao +
                ", status=" + status +
                '}';
    }
}
