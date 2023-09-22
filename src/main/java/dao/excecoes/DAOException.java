package dao.excecoes;

public class DAOException extends Exception {

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";


    public DAOException(String texto){
        super (texto);
    }

}
