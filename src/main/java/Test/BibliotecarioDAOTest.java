package test;

import dao.DAO;
import dao.excecoes.BibliotecarioException;
import model.Bibliotecario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;



public class BibliotecarioDAOTest {

    Bibliotecario luciano;
    Bibliotecario lucas;
    Bibliotecario notExisted;
    Bibliotecario nulo;

    @BeforeEach
    void setUp() {
        luciano = DAO.getBibliotecarioDAO().criar(new Bibliotecario("luciano", 1234));
        lucas = DAO.getBibliotecarioDAO().criar(new Bibliotecario("lucas", 1234));
        notExisted = DAO.getBibliotecarioDAO().criar(new Bibliotecario("notExisted", 0));
        nulo = new Bibliotecario("nulo",411);
    }

    @AfterEach
    void tearDown() {
        DAO.getBibliotecarioDAO().excluirTodos();
    }

    @Test
    void criar() throws BibliotecarioException {
        Bibliotecario criado = DAO.getBibliotecarioDAO().criar(new Bibliotecario("bibliotecario1", 345));
        Bibliotecario esperado = DAO.getBibliotecarioDAO().encontrarPorID(3);
        assertEquals(criado, esperado);
    }
    @Test
    void failExcluir() throws BibliotecarioException{
        try{
            DAO.getBibliotecarioDAO().excluir(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (BibliotecarioException e) {
            assertEquals(BibliotecarioException.EXCLUIR,e.getMessage());
        }
    }
    @Test
    void exluir() throws BibliotecarioException {
        DAO.getBibliotecarioDAO().excluir(luciano);
        assertEquals(DAO.getBibliotecarioDAO().encontrarTodos().size(),2);
    }
    @Test
    void excluirTodos(){
        DAO.getBibliotecarioDAO().excluirTodos();
        assertEquals(DAO.getBibliotecarioDAO().encontrarTodos().size(),0);
    }

    @Test
    void failAtualizar() throws BibliotecarioException{
        try{
            DAO.getBibliotecarioDAO().atualizar(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (BibliotecarioException e) {
            assertEquals(BibliotecarioException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void atualizar() throws BibliotecarioException {
        lucas.setNome("New name");
        lucas.setSenha(567);
        Bibliotecario atualizado = DAO.getBibliotecarioDAO().atualizar(lucas);
        assertEquals(atualizado,lucas);
    }

    @Test
    void encontraTodos(){
        ArrayList<Bibliotecario> todosOsBibliotecarios= DAO.getBibliotecarioDAO().encontrarTodos();
        assertEquals(todosOsBibliotecarios.size(),3);
    }

    @Test
    void failEcontraPorId() throws BibliotecarioException{
        try{
            DAO.getBibliotecarioDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (BibliotecarioException e) {
            assertEquals(BibliotecarioException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void econtraPorId() throws BibliotecarioException {
        Bibliotecario encontrado= DAO.getBibliotecarioDAO().encontrarPorID(1);
        assertEquals(encontrado,lucas);
}

}
