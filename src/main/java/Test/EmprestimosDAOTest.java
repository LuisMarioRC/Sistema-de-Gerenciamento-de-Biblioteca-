package test;

import dao.DAO;
import dao.emprestimos.EmprestimosDAO;
import dao.excecoes.EmprestimosException;
import dao.excecoes.LivroException;
import dao.excecoes.ReservaException;
import dao.excecoes.UsuarioException;
import model.Emprestimos;
import model.Livro;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class EmprestimosDAOTest {
    Usuario cassio;
    Usuario rogerio;
    Livro livro1;
    Livro livro2;
    Livro livro3;
    Livro livro4;
    Emprestimos emprestimo1;
    Emprestimos emprestimo2;
    Emprestimos semCriar;


    @BeforeEach
    void setup() throws LivroException, UsuarioException, ReservaException {
        cassio = DAO.getUsuarioDAO().criar(new Usuario("cassio", "rua b", "22 555555"));
        rogerio = DAO.getUsuarioDAO().criar(new Usuario("rogerio", "rua b", "22 777777"));
        livro1 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro2 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro3 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro4 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        emprestimo1= DAO.getEmprestimosDAO().criar(new Emprestimos(livro4,rogerio,"01/10/2023"));
        emprestimo2= DAO.getEmprestimosDAO().criar(new Emprestimos(livro3,rogerio,"01/10/2023"));

    }
    @AfterEach
    void tearDown(){
        DAO.getLivroDAO().excluirTodos();
        DAO.getUsuarioDAO().excluirTodos();
        DAO.getReservaDAO().excluirTodos();
        DAO.getEmprestimosDAO().excluirTodos();
    }


    @Test
    void validaMulta() throws UsuarioException {
        cassio.setFimDaMulta(LocalDate.of(2023,10,1));
        DAO.getUsuarioDAO().atualizar(cassio);
        boolean naoMultado =DAO.getEmprestimosDAO().validaMulta(cassio,"02/10/2023");
        assertTrue(naoMultado);
        boolean multado =DAO.getEmprestimosDAO().validaMulta(cassio,"30/09/2023");
        assertFalse(multado);

    }
    @Test
    void numLivroEmprestados() throws EmprestimosException {
        assertEquals(DAO.getEmprestimosDAO().numLivrosEmprestados(),2);
        emprestimo1.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimo1);
        assertEquals(DAO.getEmprestimosDAO().numLivrosEmprestados(),1);
    }

    @Test
    void historicoEmprestimosUsuario() throws EmprestimosException {
        ArrayList<Emprestimos> emprestimosPorUsuario= DAO.getEmprestimosDAO().historicoEmprestimosUsuario(rogerio);
        assertEquals(emprestimosPorUsuario.size(),2);
        emprestimo1.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimo1);
        ArrayList<Emprestimos> emprestimosTest2= DAO.getEmprestimosDAO().historicoEmprestimosUsuario(rogerio);
        assertEquals(emprestimosTest2.size(),2);
    }

    @Test
    void livroMaisPopular() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        ArrayList<Livro> livroMaisPopular = DAO.getEmprestimosDAO().livroMaisPolular();
        assertEquals(livroMaisPopular.size(),2);
        emprestimo2.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimo2);
        livro3.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro3);
        DAO.getEmprestimosDAO().criar(new Emprestimos(livro3,cassio,"01/10/2023"));
        ArrayList<Livro> livroMaisPopular2 = DAO.getEmprestimosDAO().livroMaisPolular();
        assertEquals(livroMaisPopular2.size(),1);
        assertEquals(livroMaisPopular2.get(0),livro3);
    }

    @Test
    void criar() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        Emprestimos criado= DAO.getEmprestimosDAO().criar(new Emprestimos(livro2,cassio,"02/10/2023"));
        Emprestimos esperado = DAO.getEmprestimosDAO().encontrarPorID(2);
        assertEquals(criado,esperado);
    }

    @Test
    void failExcluir() throws LivroException, UsuarioException, ReservaException {
        try{
            DAO.getEmprestimosDAO().excluir(semCriar);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.EXCLUIR,e.getMessage());
        }
    }
    @Test
    void excluir() throws EmprestimosException {
        DAO.getEmprestimosDAO().excluir(emprestimo1);
        assertEquals(DAO.getEmprestimosDAO().encontrarTodos().size(),1);
        assertEquals(DAO.getEmprestimosDAO().encontrarTodos().get(0),emprestimo2);
    }

    @Test
    void failAtualizar() throws LivroException, UsuarioException, ReservaException {
        try{
            DAO.getEmprestimosDAO().atualizar(semCriar);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.ATUALIZAR,e.getMessage());
        }
    }
    @Test
    void atualizar() throws EmprestimosException {
        emprestimo2.setUsuario(cassio);
        Emprestimos atualizado = DAO.getEmprestimosDAO().atualizar(emprestimo2);
        assertEquals(atualizado,emprestimo2);
    }

    @Test
    void failEcontraPorId(){
        try{
            DAO.getEmprestimosDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void econtraPorId() throws EmprestimosException {
        Emprestimos econtrado= DAO.getEmprestimosDAO().encontrarPorID(1);
        assertEquals(econtrado,emprestimo2);
    }

    @Test
    void econtraPorUsuario() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"02/10/2023"));
        ArrayList<Emprestimos> emprestimoDoUsuario= DAO.getEmprestimosDAO().econtraPorUsuario(rogerio);
        assertEquals(emprestimoDoUsuario.size(),2);
        assertEquals(emprestimoDoUsuario.get(0).getUsuario(),rogerio);
        emprestimo2.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimo2);
        ArrayList<Emprestimos> emprestimoDoUsuario2= DAO.getEmprestimosDAO().econtraPorUsuario(rogerio);
        assertEquals(emprestimoDoUsuario2.size(),1);
    }

    @Test
    void failEncontraPorIdDoLivro(){
        try{
            DAO.getEmprestimosDAO().encontraPorIdDoLivro(100);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void econtraPorIdDoLivro() throws EmprestimosException {
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(2);
        assertEquals(emprestimo.getLivro(),livro3);
    }
}
