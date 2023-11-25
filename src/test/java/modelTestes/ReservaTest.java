package modelTestes;


import org.example.dao.DAO;
import org.example.excecoes.EmprestimosException;
import org.example.excecoes.LivroException;
import org.example.excecoes.ReservaException;
import org.example.excecoes.UsuarioException;
import org.example.model.Emprestimos;
import org.example.model.Livro;
import org.example.model.Reserva;
import org.example.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe responsável por realizar o teste dos métodos da classe reserva model
 * @author Gabriel Henry
 * @author Luis Mario
 * @see DAO ;
 * @see EmprestimosException
 * @see LivroException
 * @see ReservaException
 * @see UsuarioException
 * @see Emprestimos
 * @see Livro
 * @see Reserva
 * @see Usuario
 * @see org.junit.jupiter.api.AfterEach
 * @see org.junit.jupiter.api.BeforeEach
 * @see org.junit.jupiter.api.Test
 * @see java.time.LocalDate
 */
public class ReservaTest {

    Usuario usuario1;
    Usuario usuario2;
    Livro livro1;
    Livro livro2;
    Reserva reserva1;




    @BeforeEach
    void setUp() throws LivroException, UsuarioException, ReservaException {
        usuario1 = DAO.getUsuarioDAO().criar(new Usuario("usuario 1","Rua A","11 1111"));
        usuario2 =DAO.getUsuarioDAO().criar(new Usuario("usuario 2","Rua B","22 2222"));
        livro1 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro1.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro1);
        livro2 = DAO.getLivroDAO().criar(new Livro("grande","Gabriel Henry","Gringa",1101,1999,"Ação"));
        DAO.getLivroDAO().atualizar(livro1);
        reserva1= DAO.getReservaDAO().criar(new Reserva(0,usuario1,"01/10/2023"));
    }

    @AfterEach
    void tearDown(){
        DAO.getReservaDAO().excluirTodos();
        DAO.getUsuarioDAO().excluirTodos();
        DAO.getLivroDAO().excluirTodos();
        DAO.getEmprestimosDAO().excluirTodos();
    }

    /**
     * Teste que confere que o não usuário consegue realizar uma reserva estando bloqueado
     */
    @Test
    void failreservaConstrutorStatusBloqueado() throws UsuarioException,LivroException,ReservaException {
        usuario1.setStatus(false);
        DAO.getUsuarioDAO().atualizar(usuario1);
        try{
            new Reserva(0,usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.BLOQUEIO,e.getMessage());
        }
    }

    /**
     * Teste que certifica que o usuário não consegue realizar uma renovação estando multado
     */
    @Test
    void failreservaConstrutorUsuarioMulta() throws UsuarioException,LivroException,ReservaException {
        usuario1.setFimDaMulta(LocalDate.of(2023,10,2));
        DAO.getUsuarioDAO().atualizar(usuario1);
        try{
            new Reserva(0,usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.MULTADO,e.getMessage());
        }
    }

    /**
     * Teste que certifica que o usuário não consegue fazer uma reserva estando atrasado
     */
    @Test
    void failreservaConstrutorUsuarioAtrasado() throws UsuarioException, LivroException, ReservaException, EmprestimosException {
        Emprestimos atrasado =DAO.getEmprestimosDAO().criar(new Emprestimos(livro2,usuario1,"01/09/2023"));
        atrasado.setDataDevolucao(LocalDate.of(2023,9,30));
        DAO.getEmprestimosDAO().atualizar(atrasado);
        try{
            new Reserva(0,usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.ATRASO,e.getMessage());
        }
    }

    /**
     * Teste que certifica que o usuário não consegue reservar um livro que está disponível
     */
    @Test
    void failreservaConstrutorLivroDisponivel() throws UsuarioException, LivroException,ReservaException {
        livro2.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro2);
        try{
            new Reserva(livro2.getId(),usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (LivroException e) {
            assertEquals(LivroException.DISPONIBILIDADE,e.getMessage());
        }
    }

    /**
     * Teste que certifica que o usuário não consegue realizar mais de duas reservas
     */
    @Test
    void failreservaConstrutorLimiteDeReservas() throws LivroException, UsuarioException, ReservaException {
        livro2.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro2);
        DAO.getReservaDAO().criar(new Reserva(1,usuario1,"02/10/2023"));
        Livro livro3= DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro3.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro3);
        try{
            DAO.getReservaDAO().criar(new Reserva(livro3.getId(),usuario1,"02/10/2023"));
            fail("Uma exceção deveria ser lançada");
        }catch (ReservaException e){
            assertEquals(ReservaException.LIMITE,e.getMessage());
        }
    }

}
