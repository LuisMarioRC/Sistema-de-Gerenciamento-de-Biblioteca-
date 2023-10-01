package test;

import dao.DAO;
import dao.excecoes.LivroException;
import model.Livro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroDAOTest {

    Livro pequeno;
    Livro grande;
    Livro medio;


    @BeforeEach
    void setUp(){
        pequeno = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        grande = DAO.getLivroDAO().criar(new Livro("grande","Gabriel Henry","Gringa",1101,1999,"Ação"));
        medio = DAO.getLivroDAO().criar(new Livro("medio","Mohamed","Arabia",1107,1899,"Guerra"));

    }
    @AfterEach
    void tearDown(){
        DAO.getLivroDAO().excluirTodos();
    }

    @Test
    void criar() throws LivroException {
        Livro atual = DAO.getLivroDAO().criar(new Livro("Esperado", "Rafael", "BST livros",441,1899,"Tiro"));
        Livro esperado = DAO.getLivroDAO().encontrarPorID(atual.getId());

        assertEquals(atual, esperado,"Esse texte deve passar");
    }


    @Test
    void failpesquisaPorisbn() throws LivroException {
        try{
            DAO.getLivroDAO().pesquisaPorisbn(1);
            fail("Uma exceção deveria ser levantada");
        } catch(Exception e){
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }

    }

}
