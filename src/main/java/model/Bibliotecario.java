package model;

import DAO.LivroDAO;

public class Bibliotecario extends Operador {

    private LivroDAO livrodao;
    public Bibliotecario(){
        livrodao = new LivroDAO();
    }



    public void registraLivro(String titulo, String autor,String editora,Integer isbn,Integer anoDePublicacao,String categoria){
        Livro livro= new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setAnoDePublicacao(anoDePublicacao);
        livro.setCategoria(categoria);
        livro.setDisponibilidade(true);
        livrodao.addLivro(livro);
    }

}
