package test;

import dao.DAO;
import dao.excecoes.LivroException;
import model.Livro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class LivroDAOTest {

    Livro pequeno;
    Livro grande;
    Livro medio;
    Livro semCriar;

    @BeforeEach
    void setUp(){
        pequeno = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        grande = DAO.getLivroDAO().criar(new Livro("grande","Gabriel Henry","Gringa",1101,1999,"Ação"));
        medio = DAO.getLivroDAO().criar(new Livro("medio","Mohamed","Arabia",1107,1899,"Guerra"));
        Livro semCriar= new Livro("naoCriado","not","not",111,111,"not");
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

    @Test
    void pesquisaPorisbn() throws LivroException {
        Livro isbnTeste = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorisbn(2577);
        assertEquals(lista.size(),2,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
        assertEquals(lista.get(1),isbnTeste,"Essa mensagem deve aparecer");
    }

    @Test
    void failPesquisaPorTitulo(){
        try{
            DAO.getLivroDAO().pesquisaPorTitulo("The Devers");
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }

    @Test
    void pesquisaPorTitulo() throws LivroException {
        Livro tituloTeste = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorTitulo("pequeno");
        assertEquals(lista.size(),2,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
        assertEquals(lista.get(1),tituloTeste,"Essa mensagem deve aparecer");
    }

    @Test
    void failPesquisaPorCategoria(){
        try{
            DAO.getLivroDAO().pesquisaPorCategoria("Inexistente");
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }

    @Test
    void pesquisaPorCategoria() throws LivroException {
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorCategoria("Romance");
        assertEquals(lista.size(),1,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
    }

    @Test
    void failPesquisaPorAutor(){
        try{
            DAO.getLivroDAO().pesquisaPorAutor("Inexistente");
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }
    @Test
    void pesquisaPorAutor() throws LivroException {
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorAutor("Luis Mario");
        assertEquals(lista.size(),1,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
    }

    @Test
    void failExcluir(){
        try{
            DAO.getLivroDAO().excluir(semCriar);
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.EXCLUIR,e.getMessage());
        }
    }

    @Test
    void exluir() throws LivroException {
        DAO.getLivroDAO().excluir(pequeno);
        assertEquals(DAO.getLivroDAO().encontrarTodos().size(),2);
    }

    @Test
    void excluirTodos(){
        DAO.getLivroDAO().excluirTodos();
        assertEquals(DAO.getLivroDAO().encontrarTodos().size(),0);
    }

    @Test
    void failAtualizar(){
        try{
            DAO.getLivroDAO().atualizar(semCriar);
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.ATUALIZAR,e.getMessage());
        }
    }
    @Test
    void atualizar() throws LivroException {
        medio.setAutor("Novo Autor");
        medio.setIsbn(1999);
        Livro atualizado =DAO.getLivroDAO().atualizar(medio);
        assertEquals(atualizado,medio);
    }

    @Test
    void encontraTodos(){
        ArrayList<Livro> todosOsLivros= DAO.getLivroDAO().encontrarTodos();
        assertEquals(todosOsLivros.size(),3);
    }

    @Test
    void failEcontraPorId(){
        try{
            DAO.getLivroDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR,e.getMessage());
        }
    }

    @Test
    void econtraPorId() throws LivroException {
        Livro econtrado= DAO.getLivroDAO().encontrarPorID(2);
        assertEquals(econtrado,medio);
    }
}
