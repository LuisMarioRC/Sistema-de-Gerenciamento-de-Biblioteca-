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

    //Construtor vazio
    public Livro() {

    }

    //Getters e setters

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
    public String toString(){
        return "\nTítulo: " + titulo +
                "\nAutor: " + autor +
                "\nEditora: " + editora +
                "\nISBN: " + isbn +
                "\nAno de Publicação: " +anoDePublicacao+
                "\nCategoria: " + categoria+
                "\nDisponibilidade: " +disponibilidade+
                "\nID: "+id;
    }
}



