package dao.excecoes;

import model.Bibliotecario;
import model.Usuario;

public class UsuarioException extends Exception{
    private Usuario usuario;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";

    public UsuarioException(String texto, Usuario usuario){
        super(texto);
        this.usuario = usuario;
    }

    public UsuarioException(String texto){
        super(texto);
    }

}
