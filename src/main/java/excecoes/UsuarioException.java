package excecoes;

import model.Usuario;
/**
 * Classe responsável por criar mensagens estáticas de Exceções
 * @author Luis Mario
 * @author Gabril Henry
 * @see model.Usuario
 */

public class UsuarioException extends Exception{
    private Usuario usuario;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";
    public static final String LIMITE = "Limite de EMPRÉSTIMOS atingido";
    public static final String MULTADO = "Usuário com MULTA pendente";
    public static final String BLOQUEIO = "Usuário BLOQUEADO";
    public static final String ATRASO = "Usuário com livro PENDENTE à devolução";

    public UsuarioException(String texto, Usuario usuario){
        super(texto);
        this.usuario = usuario;
    }

    public UsuarioException(String texto){
        super(texto);
    }

}
