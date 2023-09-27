package dao.excecoes;

import model.Bibliotecario;
import model.Operador;

public class BibliotecarioException extends Exception {

    private Bibliotecario bibliotecario;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";

    public BibliotecarioException(String texto, Bibliotecario bibliotecario){
        super(texto);
        this.bibliotecario = bibliotecario;
    }

    public BibliotecarioException(String texto){
        super(texto);
    }

}