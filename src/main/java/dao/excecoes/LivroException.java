package dao.excecoes;

import model.Livro;

/**
 * Classe responsável por criar mensagens estáticas de Exceções
 * @author Luis Mario
 * @author Gabril Henry
 * @see model.Livro
 */
public class LivroException extends Exception{

    private Livro livro;
    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";
    public static final String INDISPONIBILIDADE = "Livro INDISPONÍVEL";
    public static final String DISPONIBILIDADE = "Livro DISPONÍVEL para emprestimos";


    public LivroException(String texto, Livro livro){
        super (texto);
        this.livro=livro;
    }
    public LivroException(String texto){
        super(texto);
    }

}
