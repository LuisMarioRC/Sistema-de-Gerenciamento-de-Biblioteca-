package modelTestes;

import dao.DAO;
import dao.excecoes.UsuarioException;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    Usuario um;

    @BeforeEach
    void setUp(){
        um =DAO.getUsuarioDAO().criar(new Usuario("Um","Rua J","11 111"));
    }

    @AfterEach
    void tearDow(){
        DAO.getUsuarioDAO().excluirTodos();
    }
    @Test
     void bloquearConta() throws UsuarioException {
        um.bloquearConta(um);
        assertFalse(um.getStatus());
    }
    @Test
    void desbloquearConta() throws UsuarioException {
        um.desbloqueiaConta(um);
        assertTrue(um.getStatus());
    }
}
