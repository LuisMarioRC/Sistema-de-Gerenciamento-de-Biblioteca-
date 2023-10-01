package test;
import dao.DAO;
import dao.excecoes.LivroException;
import dao.excecoes.UsuarioException;
import model.Livro;
import model.Reserva;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;


public class ReservaDAOTest {
    private Usuario usuario1;
    private Usuario usuario2;
    private Livro livro1;
    private Livro livro2;



    @BeforeEach
    void setUp() throws LivroException {
        usuario1 =DAO.getUsuarioDAO().criar(new Usuario("usuario 1","Rua A","11 1111"));
        livro1.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro1);
        usuario2 =DAO.getUsuarioDAO().criar(new Usuario("usuario 2","Rua B","22 2222"));
        livro1 = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        livro2 = DAO.getLivroDAO().criar(new Livro("grande","Gabriel Henry","Gringa",1101,1999,"Ação"));
    }

    @AfterEach
    void tearDown(){
        DAO.getReservaDAO().excluirTodos();
    }

    @Test
    void criar() throws LivroException, UsuarioException {
        Reserva criado = DAO.getReservaDAO().criar(new Reserva(0,usuario2,"01/10/2023"));
        ArrayList<Reserva> listaEsperada = DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(listaEsperada.size(),1);
        assertEquals(listaEsperada.get(0),criado);
        assertEquals(listaEsperada.get(0).getIdLivro(),0);
        assertEquals(listaEsperada.get(0).getUsuario(),usuario2);
    }


}
