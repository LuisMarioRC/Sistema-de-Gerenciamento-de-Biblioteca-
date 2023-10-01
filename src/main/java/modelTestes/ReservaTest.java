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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
public class ReservaTest {

    Usuario usuario1;
    Usuario usuario2;
    Livro livro1;
    Livro livro2;
    Reserva reserva1;




    @BeforeEach
    void setUp() throws LivroException, UsuarioException {
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
    }

    @Test
    void failreservaConstrutorStatusBloqueado() throws UsuarioException,LivroException {
        usuario1.setStatus(false);
        DAO.getUsuarioDAO().atualizar(usuario1);
        try{
            new Reserva(0,usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.BLOQUEIO,e.getMessage());
        }
    }
    @Test
    void failreservaConstrutorUsuarioMulta() throws UsuarioException,LivroException {
        usuario1.setFimDaMulta(LocalDate.of(2023,10,2));
        DAO.getUsuarioDAO().atualizar(usuario1);
        try{
            new Reserva(0,usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.MULTADO,e.getMessage());
        }
    }
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
    @Test
    void failreservaConstrutorLivroDisponivel() throws UsuarioException, LivroException {
        livro2.setDisponibilidade(true);
        DAO.getLivroDAO().atualizar(livro2);
        try{
            new Reserva(livro2.getId(),usuario1,"01/10/2023");
            fail("Uma exeção deveria ser lançada");
        } catch (LivroException e) {
            assertEquals(LivroException.DISPONIBILIDADE,e.getMessage());
        }
    }

}
