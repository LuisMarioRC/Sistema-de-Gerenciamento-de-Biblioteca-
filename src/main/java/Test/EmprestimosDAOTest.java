package test;

import dao.DAO;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class EmprestimosDAOTest {
    Usuario cassio;
    Usuario rogerio;
    Usuario notExisted;

    @BeforeEach
    void setup(){
        cassio = DAO.getUsuarioDAO().criar(new Usuario("cassio", "rua b", "22 555555"));
        rogerio = DAO.getUsuarioDAO().criar(new Usuario("rogerio", "rua b", "22 777777"));
        notExisted = new Usuario("notExisted", "----", "-----");
    }
    @AfterEach
    void tearDown(){
        DAO.getAdministradorDAO().excluirTodos();
    }

}
