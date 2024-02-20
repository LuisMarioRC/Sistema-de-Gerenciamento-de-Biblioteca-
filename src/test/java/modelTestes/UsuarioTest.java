package modelTestes;

import com.example.dao.DAO;
import com.example.excecoes.UsuarioException;
import com.example.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar o teste dos métodos da classe Usuario model
 * @author Gabriel Henry
 * @author Luis Mario
 * @see DAO ;
 * @see UsuarioException ;
 * @see Usuario ;
 * @see org.junit.jupiter.api.AfterEach;
 * @see org.junit.jupiter.api.BeforeEach;
 * @see org.junit.jupiter.api.Test;
 */

public class UsuarioTest {

    Usuario um;

    @BeforeEach
    void setUp(){
        um =DAO.getUsuarioDAO().criar(new Usuario("Um","Rua J","11 111",1234));
    }

    @AfterEach
    void tearDow(){
        DAO.getUsuarioDAO().excluirTodos();
    }

    /**
     * Teste que certifica se o usuário está sendo bloqueado
     */
    @Test
     void bloquearConta() throws UsuarioException {
        um.bloquearConta(um);
        assertFalse(um.getStatus());
    }
    /**
     * Teste que certifica se o usuário está sendo desbloqueado
     */
    @Test
    void desbloquearConta() throws UsuarioException {
        um.desbloqueiaConta(um);
        assertTrue(um.getStatus());
    }
}
