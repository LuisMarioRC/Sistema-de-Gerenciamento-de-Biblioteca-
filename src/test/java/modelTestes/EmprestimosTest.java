package modelTestes;



import com.example.dao.DAO;
import com.example.excecoes.EmprestimosException;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Emprestimos;
import com.example.model.Livro;
import com.example.model.Reserva;
import com.example.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar o teste dos métodos da classe Empréstimos model
 * @author Gabriel Henry
 * @author Luis Mario
 * @see DAO ;
 * @see EmprestimosException
 * @see LivroException
 * @see ReservaException
 * @see UsuarioException
 * @see Emprestimos ;
 * @see Livro
 * @see Reserva
 * @see Usuario
 * @see org.junit.jupiter.api.AfterEach
 * @see org.junit.jupiter.api.BeforeEach
 * @see org.junit.jupiter.api.Test
 * @see java.time.LocalDate
 * @see static org.junit.jupiter.api.Assertions.*
 */

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

    /**
     *Teste que confere se o usuario consegue realizar um empréstimo estando atrasado
     */
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

    /**
     *Teste que confere se é possível realizar empréstimo com um livro que não está disponível
     */
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

    /**
     * Teste que confere se o usuário consegue realizar um empréstimo mesmo estando bloqueado
     */
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

    /**
     * Teste que confere se o usuário consegue realizar um empréstimo mesmo estando multado
     * @throws UsuarioException
     */
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

    /**
     * Teste que confere se o usuário consegue realizar um empréstimo mesmo atingindo o limite de empréstimos
     */
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

    /**
     * Teste que confere se a reserva realmente está sendo efetivada pelo primeiro usuário da lista de reserva

     */
    @Test
    void failEmprestimoConstrutorVerificaReserva() throws LivroException, UsuarioException ,ReservaException{
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

    /**
     * Teste que confere se os usuários estão sendo multados corretamente  ou não
     * de acordo com a data da devolução dos seus livros
     * @throws LivroException
     * @throws UsuarioException
     * @throws ReservaException
     * @throws EmprestimosException
     */
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

    /**
     * Teste que certifica se o livro realmente foi devolvido através da análise do seu atributo
     * de andamento do empréstimo
     * @throws LivroException
     * @throws UsuarioException
     * @throws ReservaException
     * @throws EmprestimosException
     */
    @Test
    void registraDevolucao() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        Emprestimos emprestimos1 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"03/10/2023"));
        assertTrue(emprestimos1.getAndamento());
        emprestimos1.registraDevolucao(livro1, cassio, LocalDate.of(2023, 10, 5));
        assertFalse(emprestimos1.getAndamento());
    }

    /**
     * Teste que confere que o usuário não consegue renovar o empréstimo de um livro,
     * pois o mesmo ja atingiu o limite de renovações
     * @throws LivroException
     * @throws UsuarioException
     * @throws ReservaException
     * @throws EmprestimosException
     */
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

    /**
     * Teste que confere se o usuário consegue realizar a renovação dentro do período
     * de andamento do livro que está sendo emprestado
     * @throws EmprestimosException
     * @throws UsuarioException
     * @throws ReservaException
     * @throws LivroException
     */
    @Test
    void renovar() throws EmprestimosException, UsuarioException, ReservaException, LivroException {
        Emprestimos emprestimos1 = DAO.getEmprestimosDAO().criar(new Emprestimos(livro1,cassio,"01/10/2023"));
        assertTrue(emprestimos1.renovar(livro1, cassio, "02/10/2023"));
    }
}

