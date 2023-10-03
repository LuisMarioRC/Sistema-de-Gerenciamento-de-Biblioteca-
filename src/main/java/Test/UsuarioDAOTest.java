package test;

import dao.DAO;
import dao.excecoes.UsuarioException;
import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe de teste reponsável por gerar teste para o UsuarioDAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.DAO
 * @see dao.excecoes.UsuarioException
 * @see model.Usuario
 * @see org.junit.jupiter.api.AfterEach
 * @see org.junit.jupiter.api.BeforeEach
 * @see org.junit.jupiter.api.Test
 * @see java.util.ArrayList
 */
public class UsuarioDAOTest {
    Usuario cassio;
    Usuario fagner;
    Usuario romero;
    Usuario notExisted;

    @BeforeEach
    void setUp(){
        cassio = DAO.getUsuarioDAO().criar(new Usuario("cassio","rua A","11 22222"));
        fagner = DAO.getUsuarioDAO().criar(new Usuario("fagner","rua B","22 22222"));
        romero = DAO.getUsuarioDAO().criar(new Usuario("romero","rua C","33 33333"));
        notExisted = new Usuario("notExisted","notEndereço","notTel");
    }

    @AfterEach
    void tearDown(){
        DAO.getUsuarioDAO().excluirTodos();
    }

    /**
     * Teste "criar" que instancia um Usuario e verifica através do assertEquals se o usuario foi criado
     * A verificação ocorre atraves do método que econtra o objeto criado no UsuarioDAO.
     */
    @Test
    void criar() throws UsuarioException {
        Usuario criado = DAO.getUsuarioDAO().criar(new Usuario("esperado", "rua D", "44 44444"));
        Usuario esperado = DAO.getUsuarioDAO().encontrarPorID(3);
        assertEquals(criado, esperado);
    }

    /**
     * Teste falho ao excluir um objeto
     * É passado para escluir um objeto que não foi criado no UsuarioDAO assim lança uma exceção
     * A exceção é capturada e verifica se foi a esperada
     */
    @Test
    void failExcluir(){
        try{
            DAO.getUsuarioDAO().excluir(notExisted);
            fail("Uma exceção deveria ser levantada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.EXCLUIR,e.getMessage());
        }
    }

    /**
     * Teste "excuir" que remove o objeto da lista no UsuarioDAO
     * E verifica o tamanho esperado depois da exclusão
     */
    @Test
    void exluir() throws UsuarioException {
        DAO.getUsuarioDAO().excluir(cassio);
        assertEquals(DAO.getUsuarioDAO().encontrarTodos().size(),2);
    }

    /**
     * Teste para excluir todos os objetos da lista
     * Após a exclusão, verifica se o tamanho da lista foi zerada
     */
    @Test
    void excluirTodos(){
        DAO.getUsuarioDAO().excluirTodos();
        assertEquals(DAO.getUsuarioDAO().encontrarTodos().size(),0);
    }

    /**
     * Teste falho ao atualizar um objeto que não esta na lista do UsuarioDAO
     * Lança uma exceção ao não encontrar o objeto
     * Captura a exeção e verifica se foi a esperada
     */
    @Test
    void failAtualizar(){
        try{
            DAO.getUsuarioDAO().atualizar(notExisted);
            fail("Uma exceção deveria ser levantada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.ATUALIZAR,e.getMessage());
        }
    }

    /**
     * Teste para atualizar um objeto do tipo Usuario
     * Seta outra informação em um objeto que ja criado e atualiza
     * Por fim, verifica se o objeto atualizado foi o esperado
     */
    @Test
    void atualizar() throws UsuarioException {
        fagner.setNome("New name");
        fagner.setEndereco("TAQUERA");
        Usuario atualizado =DAO.getUsuarioDAO().atualizar(fagner);
        assertEquals(atualizado,fagner);
    }

    /**
     * Teste para econtrar todos os objetos no DAO e verifica se é o tamanho esperado
     */
    @Test
    void encontraTodos(){
        ArrayList<Usuario> todosOsUsuarios= DAO.getUsuarioDAO().encontrarTodos();
        assertEquals(todosOsUsuarios.size(),3);
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um id que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failEcontraPorId(){
        try{
            DAO.getUsuarioDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (UsuarioException e) {
            assertEquals(UsuarioException.BUSCAR,e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Usuario pelo id e verifica se o retornado é o esperado
     */
    @Test
    void econtraPorId() throws UsuarioException {
        Usuario econtrado= DAO.getUsuarioDAO().encontrarPorID(2);
        assertEquals(econtrado,romero);
    }
}