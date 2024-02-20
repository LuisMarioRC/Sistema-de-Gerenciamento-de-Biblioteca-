package daoTestes;

import com.example.dao.DAO;
import com.example.excecoes.EmprestimosException;
import com.example.excecoes.LivroException;
import com.example.excecoes.ReservaException;
import com.example.excecoes.UsuarioException;
import com.example.model.Emprestimos;
import com.example.model.Livro;
import com.example.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste responsável para realizar testes referente aos métodos do EmprestimosDAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see DAO ;
 * @see EmprestimosException
 * @see LivroException
 * @see ReservaException
 * @see UsuarioException
 * @see Emprestimos
 * @see Livro
 * @see Usuario
 * @see org.junit.jupiter.api.AfterEach
 * @see org.junit.jupiter.api.BeforeEach
 * @see org.junit.jupiter.api.Test
 * @see java.time.LocalDate;
 * @see java.util.ArrayList
 */
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
        cassio = DAO.getUsuarioDAO().criar(new Usuario("cassio", "rua b", "22 555555",1234));
        rogerio = DAO.getUsuarioDAO().criar(new Usuario("rogerio", "rua b", "22 777777",1234));
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

    /**
     * Teste para validar a multa de um usuario
     * Seta o fim da multa de um usuario que ja esta como emprestimo ativo e atualiza
     * Depois chama o método para validar multa duas vezes, na primeira passa uma data onde nao estará multado
     * E na segunda chamada passada a data onde esta multado.
     */
    @Test
    void validaMulta() throws UsuarioException {
        cassio.setFimDaMulta(LocalDate.of(2023,10,1));
        DAO.getUsuarioDAO().atualizar(cassio);
        boolean naoMultado =DAO.getEmprestimosDAO().validaMulta(cassio,"02/10/2023");
        assertTrue(naoMultado);
        boolean multado =DAO.getEmprestimosDAO().validaMulta(cassio,"30/09/2023");
        assertFalse(multado);
    }

    /**
     * Teste para verificar o número de livros que estão emprestados no momento
     * No primeiro assert, chama o metodo e verifica se o tamanho corresponde
     * No segundo, desativa um andamento e faz a mesma verificação
     */
    @Test
    void numLivroEmprestados() throws EmprestimosException {
        assertEquals(DAO.getEmprestimosDAO().numLivrosEmprestados(),2);
        emprestimo1.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimo1);
        assertEquals(DAO.getEmprestimosDAO().numLivrosEmprestados(),1);
    }

    /**
     * Teste que verifica o mumero de livros que estão atrasados
     * Instacia uma data de devolução atrsada e verifica.
     */
    @Test
    void numLivroAtrasado(){
        emprestimo2.setDataDevolucao(LocalDate.of(2023,10,1));
        emprestimo1.setDataDevolucao(LocalDate.of(2023,10,1));
        assertEquals(DAO.getEmprestimosDAO().numLivroAtrasado(),2);
    }

    /**
     * Teste para verificar o historico de empréstimos de um usuário específico
     * Chama o metodo que faz essa condição e verifica se o tamanho é o esperado;
     */
    @Test
    void historicoEmprestimosUsuario() throws EmprestimosException {
        ArrayList<Emprestimos> emprestimosPorUsuario= DAO.getEmprestimosDAO().historicoEmprestimosUsuario(rogerio);
        assertEquals(emprestimosPorUsuario.size(),2);
        emprestimo1.setAndamento(false);
        DAO.getEmprestimosDAO().atualizar(emprestimo1);
        ArrayList<Emprestimos> emprestimosTest2= DAO.getEmprestimosDAO().historicoEmprestimosUsuario(rogerio);
        assertEquals(emprestimosTest2.size(),2);
    }

    /**
     *Teste para verificar o livro mais popular, o livro que foi mais emprestado
     * Chama o metodo que retorna uma lista com os livros que foi mais emprestados e verifica se o tamanho esta certo
     * Depois faz altereção e o emprestimo de mais um livro, assim alterando qual livro foi mais emprestado
     * E faz a verificação novamente
     */
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

    /**
     * Teste "criar" que instancia um Emprestimo e verifica através do assertEquals se o emprestimo foi criado
     * A verificação ocorre atraves do método que econtra o objeto criado no EmprestimosDAO.
     */
    @Test
    void criar() throws LivroException, UsuarioException, ReservaException, EmprestimosException {
        Emprestimos criado= DAO.getEmprestimosDAO().criar(new Emprestimos(livro2,cassio,"02/10/2023"));
        Emprestimos esperado = DAO.getEmprestimosDAO().encontrarPorID(2);
        assertEquals(criado,esperado);
    }

    /**
     * Teste falho ao excluir um objeto
     * É passado para escluir um objeto que não foi criado no EmprestimosDAO assim lança uma exceção
     * A exceção é capturada e verifica se foi a esperada
     */
    @Test
    void failExcluir() throws LivroException, UsuarioException, ReservaException {
        try{
            DAO.getEmprestimosDAO().excluir(semCriar);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.EXCLUIR,e.getMessage());
        }
    }

    /**
     * Teste "excuir" que remove o objeto da lista no EmprestimosDAO
     * Verifica o tamanho esperado depois da exclusão e se o emprestimo que sobrou é o esperado
     */
    @Test
    void excluir() throws EmprestimosException {
        DAO.getEmprestimosDAO().excluir(emprestimo1);
        assertEquals(DAO.getEmprestimosDAO().encontrarTodos().size(),1);
        assertEquals(DAO.getEmprestimosDAO().encontrarTodos().get(0),emprestimo2);
    }

    /**
     * Teste para excluir todos os objetos da lista
     * Após a exclusão, verifica se o tamanho da lista foi zerada
     */
    @Test
    void exluirTodos(){
        DAO.getEmprestimosDAO().excluirTodos();
        assertEquals(DAO.getEmprestimosDAO().encontrarTodos().size(),0);
    }

    /**
     * Teste falho ao atualizar um objeto que não esta na lista do EmprestimosDAO
     * Lança uma exceção ao não encontrar o objeto
     * Captura a exeção e verifica se foi a esperada
     */
    @Test
    void failAtualizar() throws LivroException, UsuarioException, ReservaException {
        try{
            DAO.getEmprestimosDAO().atualizar(semCriar);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.ATUALIZAR,e.getMessage());
        }
    }

    /**
     * Teste para atualizar um objeto
     * Seta outra informação em um objeto que ja criado e atualiza
     * Por fim, verifica se o objeto atualizado foi o esperado
     */
    @Test
    void atualizar() throws EmprestimosException {
        emprestimo2.setUsuario(cassio);
        Emprestimos atualizado = DAO.getEmprestimosDAO().atualizar(emprestimo2);
        assertEquals(atualizado,emprestimo2);
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um id que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failEcontraPorId(){
        try{
            DAO.getEmprestimosDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.BUSCAR,e.getMessage());
        }
    }

    /**
     *Teste para econtrar o administrador pelo id e verifica se o retornado é o esperado
     */
    @Test
    void econtraPorId() throws EmprestimosException {
        Emprestimos econtrado= DAO.getEmprestimosDAO().encontrarPorID(1);
        assertEquals(econtrado,emprestimo2);
    }

    /**
     * Teste para econtrar o emprestimos ativos por usuario
     * Cria emprestimos para o usuario e verifica se o tamanho da lista de emprestimos ativos dele é o esperado
     * Depois faz modificações e as mesmas verificações
     */
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

    /**
     * Teste falho para econtrar por id do livro
     * Atribui a busca com um id que não tem livro cadastrado, assim vai lança uma exceção
     * captura a exceção e verifica se foi a esperada
     */
    @Test
    void failEncontraPorIdDoLivro(){
        try{
            DAO.getEmprestimosDAO().encontraPorIdDoLivro(100);
            fail("Uma exceção deveria ser lançada");
        } catch (EmprestimosException e) {
            assertEquals(EmprestimosException.BUSCAR,e.getMessage());
        }
    }

    /**
     * Teste para econtrar emprestimos por id do livro
     * Chama a busca e verifica se o emprestimos encontrado foi o esperado
     */
    @Test
    void econtraPorIdDoLivro() throws EmprestimosException {
        Emprestimos emprestimo = DAO.getEmprestimosDAO().encontraPorIdDoLivro(2);
        assertEquals(emprestimo.getLivro(),livro3);
    }
}
