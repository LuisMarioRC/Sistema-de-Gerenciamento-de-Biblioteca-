package dao.excecoes;

import model.Emprestimos;

/**
 * Classe responsável por criar mensagens estáticas de Exceções
 * @author Luis Mario
 * @author Gabril Henry
 * @see model.Emprestimos
 */
public class EmprestimosException extends Exception{

    private Emprestimos emprestimos;

    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String EMPRESTAR= "Operação de EMPRESTIMO não realizada";
    public static final String BUSCAR= "Operação de BUSCA não realizada";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";
    public static final String RENOVAR = "Limite de RENOVAÇÂO atingida.";

    public EmprestimosException(String texto){
        super (texto);
    }
}
