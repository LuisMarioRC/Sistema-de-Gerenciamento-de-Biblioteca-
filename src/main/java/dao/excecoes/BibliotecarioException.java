package dao.excecoes;

import model.Bibliotecario;

/**
 * Classe responsável por criar mensagens estáticas de Exceções
 * @author Luis Mario
 * @author Gabril Henry
 * @see model.Bibliotecario
 */
public class BibliotecarioException extends Exception {

    private Bibliotecario bibliotecario;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";


    public BibliotecarioException(String texto){
        super(texto);
    }

}