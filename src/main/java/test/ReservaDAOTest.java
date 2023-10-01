package test;

import dao.DAO;
import dao.excecoes.LivroException;
import dao.excecoes.ReservaException;
import dao.excecoes.UsuarioException;
import model.Livro;
import model.Reserva;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;


public class ReservaDAOTest {
    Usuario usuario1;
    Usuario usuario2;
    Livro livro1;
    Livro livro2;
    Reserva reserva1;




    @BeforeEach
    void setUp() throws LivroException, UsuarioException {
        usuario1 =DAO.getUsuarioDAO().criar(new Usuario("usuario 1","Rua A","11 1111"));
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
    void criar() throws LivroException, UsuarioException {
        Reserva criado = DAO.getReservaDAO().criar(new Reserva(0,usuario2,"01/10/2023"));
        ArrayList<Reserva> listaEsperada = DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(listaEsperada.size(),2);
        assertEquals(listaEsperada.get(1),criado);
        assertEquals(listaEsperada.get(1).getIdLivro(),0);
        assertEquals(listaEsperada.get(1).getUsuario(),usuario2);
    }

    @Test
    void getReservasParaLivro() throws LivroException, UsuarioException {
        // se a chave não existir o metodo retorna uma lista vazia
        ArrayList<Reserva> listaSemReserva= DAO.getReservaDAO().getReservasParaLivro(100);
        assertEquals(listaSemReserva.size(),0);

        livro2.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro2);
        DAO.getReservaDAO().criar(new Reserva(livro2.getId(),usuario1,"01/10/2023"));
        DAO.getReservaDAO().criar(new Reserva(livro2.getId(), usuario2,"01/10/2023"));
        ArrayList<Reserva> listaComReserva = DAO.getReservaDAO().getReservasParaLivro(livro2.getId());
        assertEquals(listaComReserva.size(),2);
        assertEquals(listaComReserva.get(0).getUsuario(),usuario1);
    }

    @Test
    void primeiroUsuarioNaLista(){
        boolean retorno1= DAO.getReservaDAO().primeiroUsuarioNaLista(0,usuario2);
        assertFalse(retorno1);
        boolean retorno2 = DAO.getReservaDAO().primeiroUsuarioNaLista(0,usuario1);
        assertTrue(retorno2);
    }

    @Test
    void getReservas(){
        Map<Integer, ArrayList<Reserva>> mapaDeReservas= DAO.getReservaDAO().getReservas();
        assertEquals(mapaDeReservas.size(),1);
    }

    @Test
    void verificarReserva(){
        boolean retornoCerto = DAO.getReservaDAO().verificaReserva(0);
        assertTrue(retornoCerto);
        boolean retornoFalso = DAO.getReservaDAO().verificaReserva(40);
        assertFalse(retornoFalso);
    }

    @Test
    void retiraUsuarioDaLista(){
        ArrayList<Reserva> velhaLista= DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(velhaLista.size(),1);
        DAO.getReservaDAO().retiraUsuarioDaLista(0);
        ArrayList<Reserva> novaLista= DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(novaLista.size(),0);
    }

    @Test
    void excluir() throws Exception {
        livro2.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro2);
        Reserva reserva2 = DAO.getReservaDAO().criar(new Reserva(livro2.getId(),usuario1,"01/10/2023"));
        DAO.getReservaDAO().excluir(reserva1);
        Map<Integer, ArrayList<Reserva>> mapaDeReservas =DAO.getReservaDAO().getReservas();
        assertNotNull(mapaDeReservas);
    }

    @Test
    void excluirTodos(){
        DAO.getReservaDAO().excluirTodos();
        assertEquals(DAO.getReservaDAO().getReservas().size(),0);
    }

    @Test
    void atualiza() throws Exception {
        reserva1.setUsuario(usuario2);
        DAO.getReservaDAO().atualizar(reserva1);
        ArrayList<Reserva> listaDeReservas= DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(listaDeReservas.get(0).getUsuario(),usuario2);
    }

    @Test
    void econtrarTodos(){
        ArrayList<Reserva> listaDeReserva = DAO.getReservaDAO().encontrarTodos();
        assertEquals(listaDeReserva.size(),1);
    }

    @Test
    void failEcontraPorId() throws Exception {
        try{
            Reserva notReserva= DAO.getReservaDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        }catch (ReservaException e){
            assertEquals(ReservaException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void encontraPorId() throws Exception {
        Reserva econtrada = DAO.getReservaDAO().encontrarPorID(0);
        assertEquals(econtrada,reserva1);
}
}
