package test;

import dao.DAO;
import dao.excecoes.BibliotecarioException;
import model.Bibliotecario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Classe de teste responsável para realizar testes referente aos métodos do BibliotecarioDAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.DAO
 * @see dao.excecoes.BibliotecarioException
 * @see model.Bibliotecario
 * @see org.junit.jupiter.api.AfterEach
 * @see org.junit.jupiter.api.BeforeEach
 * @see org.junit.jupiter.api.Test
 * @see java.util.ArrayList
 */
public class BibliotecarioDAOTest {

    Bibliotecario luciano;
    Bibliotecario lucas;
    Bibliotecario notExisted;
    Bibliotecario nulo;

    @BeforeEach
    void setUp() {
        luciano = DAO.getBibliotecarioDAO().criar(new Bibliotecario("luciano", 1234));
        lucas = DAO.getBibliotecarioDAO().criar(new Bibliotecario("lucas", 1234));
        notExisted = DAO.getBibliotecarioDAO().criar(new Bibliotecario("notExisted", 0));
        nulo = new Bibliotecario("nulo",411);
    }

    @AfterEach
    void tearDown() {
        DAO.getBibliotecarioDAO().excluirTodos();
    }

    /**
     * Teste para criar um Administrador
     * Verifica se foi criado atráves de um método de busca
     */
    @Test
    void criar() throws BibliotecarioException {
        Bibliotecario criado = DAO.getBibliotecarioDAO().criar(new Bibliotecario("bibliotecario1", 345));
        Bibliotecario esperado = DAO.getBibliotecarioDAO().encontrarPorID(3);
        assertEquals(criado, esperado);
    }

    /**
     * Teste falho ao excluir um objeto do tipo Bibliotecario
     * É passado para escluir um objeto que não foi criado no BibliotecarioDAO assim lança uma exceção
     * A exceção é capturada e verifica se foi a esperada
     */
    @Test
    void failExcluir() throws BibliotecarioException{
        try{
            DAO.getBibliotecarioDAO().excluir(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (BibliotecarioException e) {
            assertEquals(BibliotecarioException.EXCLUIR,e.getMessage());
        }
    }

    /**
     * Teste "excuir" que remove o objeto da lista no BibliotecarioDAO
     * E verifica o tamanho esperado depois da exclusão
     */
    @Test
    void exluir() throws BibliotecarioException {
        DAO.getBibliotecarioDAO().excluir(luciano);
        assertEquals(DAO.getBibliotecarioDAO().encontrarTodos().size(),2);
    }
    /**
     * Teste para excluir todos os objetos da lista
     * Após a exclusão, verifica se o tamanho da lista foi zerada
     */
    @Test
    void excluirTodos(){
        DAO.getBibliotecarioDAO().excluirTodos();
        assertEquals(DAO.getBibliotecarioDAO().encontrarTodos().size(),0);
    }

    /**
     * Teste falho ao atualizar um objeto que não esta na lista do BibliotecarioDAO
     * Lança uma exceção ao não encontrar o objeto
     * Captura a exeção e verifica se foi a esperada
     */
    @Test
    void failAtualizar() throws BibliotecarioException{
        try{
            DAO.getBibliotecarioDAO().atualizar(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (BibliotecarioException e) {
            assertEquals(BibliotecarioException.BUSCAR,e.getMessage());
        }
    }

    /**
     * Teste para atualizar um objeto do tipo Bibliotecario
     * Seta outra informação em um objeto que ja criado e atualiza
     * Por fim, verifica se o objeto atualizado foi o esperado
     */
    @Test
    void atualizar() throws BibliotecarioException {
        lucas.setNome("New name");
        lucas.setSenha(567);
        Bibliotecario atualizado = DAO.getBibliotecarioDAO().atualizar(lucas);
        assertEquals(atualizado,lucas);
    }

    /**
     * Teste para econtrar todos os objetos no DAO e verifica se é o tamanho esperado
     */
    @Test
    void encontraTodos(){
        ArrayList<Bibliotecario> todosOsBibliotecarios= DAO.getBibliotecarioDAO().encontrarTodos();
        assertEquals(todosOsBibliotecarios.size(),3);
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um id que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failEcontraPorId() throws BibliotecarioException{
        try{
            DAO.getBibliotecarioDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (BibliotecarioException e) {
            assertEquals(BibliotecarioException.BUSCAR,e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Bibliotecario pelo id e verifica se o retornado é o esperado
     */
    @Test
    void econtraPorId() throws BibliotecarioException {
        Bibliotecario encontrado= DAO.getBibliotecarioDAO().encontrarPorID(1);
        assertEquals(encontrado,lucas);
    }
}
