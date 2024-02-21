package com.example.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa o Bibliotecario no sistema da Biblioteca
 * @author Gabriel Henry
 * @author Luis Mario
 * @see java.util.Objects
 * @see java.io.Serializable
 */
public class Bibliotecario implements Serializable {
    private String nome;
    private Integer senha;
    private String cargo;
    private Integer numeroDeIdentificacao;

    /**
     * Construtor da classe Biliotecario
     * O atributo de cargo é automaticamente setado como "Bibliotecário
     * Numero de identificação iniciado com o valor padrão
     * @param nome o nome do bibliotecario
     * @param senha a senha de acesso do bibliotecario
     */
    public Bibliotecario(String nome,Integer senha){
        this.nome=nome;
        this.senha=senha;
        this.cargo="Bibliotecário";
        this.numeroDeIdentificacao=-1;
    }

    /**
     * Construtor da classe Bibliotecario que está sendo usado pelo Administrador
     * Numero de identificação iniciado com o valor padrão
     * @param nome o administrador
     * @param senha a senha do administrador
     * @param cargo o cargo do administrador
     */
    public Bibliotecario(String nome,Integer senha,String cargo){
        this.nome=nome;
        this.senha=senha;
        this.cargo=cargo;
        this.numeroDeIdentificacao=-1;
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
