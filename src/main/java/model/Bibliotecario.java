package model;

import java.util.Objects;

public class Bibliotecario  {
    private String nome;
    private Integer senha;
    private String cargo;
    private Integer numeroDeIdentificacao;

    public Bibliotecario(String nome,Integer senha){
        this.nome=nome;
        this.senha=senha;
        this.cargo="Biliotecario";
    }

    public Bibliotecario(String nome,Integer senha,String cargo){
        this.nome=nome;
        this.senha=senha;
        this.cargo=cargo;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        Bibliotecario that = (Bibliotecario) o;
        return Objects.equals(numeroDeIdentificacao, that.numeroDeIdentificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroDeIdentificacao);
    }

    @Override
    public String toString() {
        return "Bibliotecario{" +
                "nome='" + nome + '\'' +
                ", senha=" + senha +
                ", cargo='" + cargo + '\'' +
                ", numeroDeIdentificacao=" + numeroDeIdentificacao +
                '}';
    }
}
