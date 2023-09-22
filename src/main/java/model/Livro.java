package model;


import java.util.Objects;

public class Livro {
    private String titulo;
    private String autor;
    private String editora;
    private Integer isbn;
    private Integer anoDePublicacao;
    private String categoria;
    private Boolean disponibilidade;
    private int id;



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