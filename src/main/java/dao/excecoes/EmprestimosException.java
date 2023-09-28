package dao.excecoes;

import model.Emprestimos;

public class EmprestimosException extends Exception{

    private Emprestimos emprestimos;

    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String EMPRESTAR= "Operação de EMPRESTIMO não realizada";
    public static final String BUSCAR= "Operação de BUSCA não realizada";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";

    public EmprestimosException(String texto){
        super (texto);
    }
}