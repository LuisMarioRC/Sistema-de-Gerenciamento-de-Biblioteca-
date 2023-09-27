package dao.excecoes;

import model.Operador;

public class OperadorException extends Exception{
    private Operador operador;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";

    public OperadorException(String texto, Operador operador){
        super(texto);
        this.operador = operador;
    }

    public OperadorException(String texto){
        super(texto);
    }

}
