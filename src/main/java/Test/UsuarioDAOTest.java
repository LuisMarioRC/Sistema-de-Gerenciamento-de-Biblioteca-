package test;

import dao.DAO;
import dao.excecoes.UsuarioException;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class UsuarioDAOTest {
    Usuario cassio;
    Usuario fagner;
    Usuario romero;
    Usuario notExisted;

    @BeforeEach
    void setUp(){
        cassio = DAO.getUsuarioDAO().criar(new Usuario("cassio","rua A","11 22222"));
        fagner = DAO.getUsuarioDAO().criar(new Usuario("fagner","rua B","22 22222"));
        romero = DAO.getUsuarioDAO().criar(new Usuario("romero","rua C","33 33333"));
        notExisted = new Usuario("notExisted","notEndereço","notTel");
    }

    @AfterEach
    void tearDown(){
        DAO.getUsuarioDAO().excluirTodos();
    }

    @Test
    void criar() throws UsuarioException {
        Usuario criado = DAO.getUsuarioDAO().criar(new Usuario("esperado", "rua D", "44 44444"));
        Usuario esperado = DAO.getUsuarioDAO().encontrarPorID(3);
        assertEquals(criado, esperado);
    }

    @Test
    void failExcluir(){
        try{
            DAO.getUsuarioDAO().excluir(notExisted);
            fail("Uma exceção deveria ser levantada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.EXCLUIR,e.getMessage());
        }
    }

    @Test
    void exluir() throws UsuarioException {
        DAO.getUsuarioDAO().excluir(cassio);
        assertEquals(DAO.getUsuarioDAO().encontrarTodos().size(),2);
    }
    @Test
    void excluirTodos(){
        DAO.getUsuarioDAO().excluirTodos();
        assertEquals(DAO.getUsuarioDAO().encontrarTodos().size(),0);
    }

    @Test
    void failAtualizar(){
        try{
            DAO.getUsuarioDAO().atualizar(notExisted);
            fail("Uma exceção deveria ser levantada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.ATUALIZAR,e.getMessage());
        }
    }
    @Test
    void atualizar() throws UsuarioException {
        fagner.setNome("New name");
        fagner.setEndereco("TAQUERA");
        Usuario atualizado =DAO.getUsuarioDAO().atualizar(fagner);
        assertEquals(atualizado,fagner);
    }

    @Test
    void encontraTodos(){
        ArrayList<Usuario> todosOsUsuarios= DAO.getUsuarioDAO().encontrarTodos();
        assertEquals(todosOsUsuarios.size(),3);
    }

    @Test
    void failEcontraPorId(){
        try{
            DAO.getUsuarioDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void econtraPorId() throws UsuarioException {
        Usuario econtrado= DAO.getUsuarioDAO().encontrarPorID(2);
        assertEquals(econtrado,romero);
    }
}