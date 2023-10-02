package modelTestes;



import dao.DAO;
import dao.excecoes.EmprestimosException;
import dao.excecoes.LivroException;
import dao.excecoes.ReservaException;
import dao.excecoes.UsuarioException;
import model.Emprestimos;
import model.Livro;
import model.Reserva;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class EmprestimosTest {
    Usuario cassio;
    Usuario rogerio;
    Usuario notExisted;
    Livro livro1;
    Livro livro2;
    Livro livro3;
    Livro livro4;


    @BeforeEach
    void setup(){
        cassio = DAO.getUsuarioDAO().criar(new Usuario("cassio", "rua b", "22 555555"));
        rogerio = DAO.getUsuarioDAO().criar(new Usuario("rogerio", "rua b", "22 777777"));
        notExisted = new Usuario("notExisted", "----", "-----");
        livro1 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro2 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro3 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro4 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));

    }
    @AfterEach
    void tearDown(){
        DAO.getLivroDAO().excluirTodos();
        DAO.getUsuarioDAO().excluirTodos();
        DAO.getReservaDAO().excluirTodos();
        DAO.getEmprestimosDAO().excluirTodos();
    }

    @Test
    void failEmprestimoConstrutorUsuarioAtrasado() throws UsuarioException, LivroException, ReservaException, EmprestimosException {
        Emprestimos atrasado = DAO.getEmprestimosDAO().criar(new Emprestimos(livro2,cassio,"01/09/2023"));
        atrasado.setDataDevolucao(LocalDate.of(2023,9,30));
        DAO.getEmprestimosDAO().atualizar(atrasado);
        try{
            new Emprestimos(livro2, cassio, "01/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.ATRASO,e.getMessage());
        }
    }

    @Test
    void failEmprestimoConstrutorLivroIndisponivel() throws UsuarioException, LivroException, ReservaException {
        livro2.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro2);
        try{
            new Emprestimos(livro2,cassio,"01/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch (LivroException e) {
            assertEquals(LivroException.INDISPONIBILIDADE, e.getMessage());
        }
    }

    @Test
    void failEmprestimoConstrutorStatusBloqueado() throws UsuarioException,LivroException, ReservaException {
        cassio.setStatus(false);
        DAO.getUsuarioDAO().atualizar(cassio);
        try{
            new Emprestimos(livro2, cassio, "01/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.BLOQUEIO,e.getMessage());
        }
    }

    @Test
    void failEmprestimoConstrutorUsuarioMulta() throws UsuarioException,LivroException, ReservaException {
        cassio.setFimDaMulta(LocalDate.of(2023,10,2));
        DAO.getUsuarioDAO().atualizar(cassio);
        try{
            new Emprestimos(livro2,cassio,"01/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.MULTADO,e.getMessage());
        }
    }

    @Test
    void failEmprestimoConstrutorLimiteDeLivros() throws LivroException, UsuarioException, ReservaException {
        DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"01/10/2023"));
        DAO.getEmprestimosDAO().criar(new Emprestimos(livro2,cassio,"01/10/2023"));
        DAO.getEmprestimosDAO().criar(new Emprestimos(livro3,cassio,"01/10/2023"));
        try{
            new Emprestimos(livro4,cassio,"01/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch (UsuarioException e){
            assertEquals(UsuarioException.LIMITE,e.getMessage());
        }
    }

    @Test
    void failEmprestimoConstrutorVerificaReserva() throws LivroException, UsuarioException {
        livro1.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro1);
        DAO.getReservaDAO().criar(new Reserva(0,cassio,"01/10/2023"));
        DAO.getReservaDAO().criar(new Reserva(0,rogerio,"01/10/2023"));
        livro1.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro1);
        try{
            new Emprestimos(livro1, rogerio, "01/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch (ReservaException e){
            assertEquals(ReservaException.RESERVADO,e.getMessage());
        }
    }

    @Test
    void multa() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        Emprestimos emprestimo2 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro2,rogerio,"03/10/2023"));
        emprestimo2.registraDevolucao(livro2, rogerio, LocalDate.of(2023, 10, 7));
        assertNull(rogerio.getFimDaMulta());
        Emprestimos emprestimo1 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"03/10/2023"));
        emprestimo1.registraDevolucao(livro1, cassio, LocalDate.of(2023, 10, 20));
        assertNotNull(cassio.getFimDaMulta());
        assertEquals(cassio.getFimDaMulta(), LocalDate.of(2023, 11, 9));

    }

    @Test
    void registraDevolucao() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        Emprestimos emprestimos1 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"03/10/2023"));
        assertTrue(emprestimos1.getAndamento());
        emprestimos1.registraDevolucao(livro1, cassio, LocalDate.of(2023, 10, 5));
        assertFalse(emprestimos1.getAndamento());
    }

    @Test
    void failLimiteDeRenovacao() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        Emprestimos emprestimos1 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"01/10/2023"));
        emprestimos1.renovar(livro1, cassio, "02/10/2023");
        try{
            emprestimos1.renovar(livro1, cassio, "06/10/2023");
            fail("Uma exceção deveria ser lançada");
        } catch(EmprestimosException e){
            assertEquals(EmprestimosException.RENOVAR, e.getMessage());
        }
    }
    @Test
    void renovar() throws EmprestimosException, UsuarioException, ReservaException, LivroException {
        Emprestimos emprestimos1 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"01/10/2023"));
        assertTrue(emprestimos1.renovar(livro1, cassio, "02/10/2023"));
    }
}

