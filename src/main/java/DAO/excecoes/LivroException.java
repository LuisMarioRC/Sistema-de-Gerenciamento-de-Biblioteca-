package DAO.excecoes;

import model.Livro;

public class LivroException extends Exception{

    private Livro livro;
    public static final String BUSCAR = "Operação de BUSCAR não encontrada";


    public LivroException(String texto, Livro livro){
        super (texto);
        this.livro=livro;
    }
    public LivroException(String texto){
        super(texto);
    }

}
