package daoTestes;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Livro;
import com.example.model.Reserva;
import com.example.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe de teste reponsável por gerar teste para o ReservaDAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see DAO
 * @see LivroException
 * @see ReservaException
 * @see UsuarioException
 * @see Livro
 * @see Reserva
 * @see Usuario
 * @see org.junit.jupiter.api.AfterEach
 * @see org.junit.jupiter.api.BeforeEach
 * @see org.junit.jupiter.api.Test
 * @see java.util.ArrayList
 * @see java.util.Map
 */
public class ReservaDAOTest {
    Usuario usuario1;
    Usuario usuario2;
    Livro livro1;
    Livro livro2;
    Reserva reserva1;




    @BeforeEach
    void setUp() throws LivroException, UsuarioException ,ReservaException{
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
        DAO.getLivroDAO().excluirTodos();
        DAO.getUsuarioDAO().excluirTodos();
    }

    /**
     * Teste "criar" que instancia uma Reserva e verifica através do assertEquals se a reserva foi criada
     * A verificação ocorre atraves do método que econtra o objeto criado no ReservaDAO.
     */
    @Test
    void criar() throws LivroException, UsuarioException,ReservaException {
        Reserva criado = DAO.getReservaDAO().criar(new Reserva(0,usuario2,"01/10/2023"));
        ArrayList<Reserva> listaEsperada = DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(listaEsperada.size(),2);
        assertEquals(listaEsperada.get(1),criado);
        assertEquals(listaEsperada.get(1).getIdLivro(),0);
        assertEquals(listaEsperada.get(1).getUsuario(),usuario2);
    }

    /**
     *Teste para verificar as reservas de um livro específico
     * Primerio, passa no metodo um id que não está registrado, assim verifica se o tamanho da lista retornada é zero
     * Depois atribuir reservas para um livro faz vefiricações se o retorno foi o esperado
     */
    @Test
    void getReservasParaLivro() throws LivroException, UsuarioException,ReservaException {
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

    /**
     * Teste para verificar se ousuario na lista de reservas
     * para um livro específico é o primeiro
     * Assim verifica se o retorno como valor booleanoo foi o esperado
     */
    @Test
    void primeiroUsuarioNaLista(){
        boolean retorno1= DAO.getReservaDAO().primeiroUsuarioNaLista(0,usuario2);
        assertFalse(retorno1);
        boolean retorno2 = DAO.getReservaDAO().primeiroUsuarioNaLista(0,usuario1);
        assertTrue(retorno2);
    }

    /**
     * Teste que verifica todas as reservas que exitem
     * Retorna em forma de "hashMap"
     */
    @Test
    void getReservas(){
        Map<Integer, ArrayList<Reserva>> mapaDeReservas= DAO.getReservaDAO().getReservas();
        assertEquals(mapaDeReservas.size(),1);
    }

    /**
     * Teste para verificar se tem reserva para um livro específico
     */
    @Test
    void verificarReserva(){
        boolean retornoCerto = DAO.getReservaDAO().verificaReserva(0);
        assertTrue(retornoCerto);
        boolean retornoFalso = DAO.getReservaDAO().verificaReserva(40);
        assertFalse(retornoFalso);
    }

    /**
     * Teste para verificar se removeu o primeiro usuario na lista de reserva de um livro específico
     */
    @Test
    void retiraUsuarioDaLista(){
        ArrayList<Reserva> velhaLista= DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(velhaLista.size(),1);
        DAO.getReservaDAO().retiraUsuarioDaLista(0);
        ArrayList<Reserva> novaLista= DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(novaLista.size(),0);
    }

    /**
     * Teste que remove uma reserva ja criada
     * Primeiro cria uma reserva e depois faz a remoção
     * Por fim, verifica se o tamanho da reserta foi o esperado;
     */
    @Test
    void excluir() throws Exception {
        livro2.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro2);
        Reserva reserva2 = DAO.getReservaDAO().criar(new Reserva(livro2.getId(),usuario1,"01/10/2023"));
        DAO.getReservaDAO().excluir(reserva1);
        Map<Integer, ArrayList<Reserva>> mapaDeReservas =DAO.getReservaDAO().getReservas();
        assertNotNull(mapaDeReservas);
    }

    /**
     * Teste que remove toda a reserva e verifica se as reservas estão vazias
     */
    @Test
    void excluirTodos(){
        DAO.getReservaDAO().excluirTodos();
        assertEquals(DAO.getReservaDAO().getReservas().size(),0);
    }

    /**
     * Teste que atualiza uma reserva
     * Seta a informao de uma reserva para outra desejada e atualiza
     * Por fim, verifca se a atualização foi feita
     */
    @Test
    void atualiza() throws Exception {
        reserva1.setUsuario(usuario2);
        DAO.getReservaDAO().atualizar(reserva1);
        ArrayList<Reserva> listaDeReservas= DAO.getReservaDAO().getReservasParaLivro(0);
        assertEquals(listaDeReservas.get(0).getUsuario(),usuario2);
    }

    /**
     * Teste que econtra todas as reservas em forma de "ArrayList" e verifica se o tamanho é o esperado
     */
    @Test
    void econtrarTodos(){
        ArrayList<Reserva> listaDeReserva = DAO.getReservaDAO().encontrarTodos();
        assertEquals(listaDeReserva.size(),1);
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um id que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failEcontraPorId() throws Exception {
        try{
            Reserva notReserva= DAO.getReservaDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        }catch (ReservaException e){
            assertEquals(ReservaException.BUSCAR,e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Livro pelo id e verifica se o retornado é o esperado
     */
    @Test
    void encontraPorId() throws Exception {
        Reserva econtrada = DAO.getReservaDAO().encontrarPorID(0);
        assertEquals(econtrada,reserva1);
    }

    /**
     *Teste paea encontra todas as reserva que um usuario tem
     * Cria uma reserva para esse usuario e verifica se o tamanho da lista de reserva foi o esperado
     */
    @Test
    void reservasDeUsuario() throws LivroException, UsuarioException, ReservaException {
        livro2.setDisponibilidade(false);
        DAO.getLivroDAO().atualizar(livro2);
        DAO.getReservaDAO().criar(new Reserva(1,usuario1,"02/10/2023"));
        assertEquals(DAO.getReservaDAO().reservasDeUsuario(usuario1).size(),2);
    }
}
