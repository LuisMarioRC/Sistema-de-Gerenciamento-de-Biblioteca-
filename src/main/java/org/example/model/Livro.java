package org.example.model;


import java.io.Serializable;
import java.util.Objects;

/**
 * CLasse que representa o livro no sistema da biblioteca
 * @author Luis Mario
 * @author Gabriel Henry
 * @see java.util.Objects
 */
public class Livro implements Serializable{
    private String titulo;
    private String autor;
    private String editora;
    private Integer isbn;
    private Integer anoDePublicacao;
    private String categoria;
    private Boolean disponibilidade;
    private int id;

    /**
     * Construtor da classe livro que seta alguns atributos como entrada
     * O atributo disponibilidade é atribuído com o valor true automaticamente, que está disponivel;
     * @param titulo :String
     * @param autor : String
     * @param editora : String
     * @param isbn : Integer
     * @param anoDePublicacao : Integer
     * @param categoria : String
     */
    public Livro(String titulo, String autor,String editora,Integer isbn,Integer anoDePublicacao,String categoria) {
        this.titulo=titulo;
        this.autor=autor;
        this.editora=editora;
        this.isbn=isbn;
        this.anoDePublicacao=anoDePublicacao;
        this.categoria=categoria;
        this.disponibilidade=true; //Se tiver disponivel a disponibilidade é true
    }

    /**Getters e setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public void setAnoDePublicacao(Integer anoDePublicacao) {
        this.anoDePublicacao = anoDePublicacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return id == livro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", isbn=" + isbn +
                ", anoDePublicacao=" + anoDePublicacao +
                ", categoria='" + categoria + '\'' +
                ", disponibilidade=" + disponibilidade +
                ", id=" + id +
                '}';
    }
}