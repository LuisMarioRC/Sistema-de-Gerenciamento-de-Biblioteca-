package daoTestes;

import com.example.dao.DAO;
import com.example.excecoes.LivroException;
import com.example.model.Livro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe de teste reponsável por gerar teste para o LivroDAO
 * @author Luis Mario
 * @author Gabriel Henry
 *  @see DAO
 *  @see LivroException
 *  @see Livro
 *  @see org.junit.jupiter.api.AfterEach
 *  @see org.junit.jupiter.api.BeforeEach
 *  @see org.junit.jupiter.api.Test
 *  @see java.util.ArrayList
 */
public class LivroDAOTest {

    Livro pequeno;
    Livro grande;
    Livro medio;
    Livro semCriar;

    @BeforeEach
    void setUp(){
        pequeno = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        grande = DAO.getLivroDAO().criar(new Livro("grande","Gabriel Henry","Gringa",1101,1999,"Ação"));
        medio = DAO.getLivroDAO().criar(new Livro("medio","Mohamed","Arabia",1107,1899,"Guerra"));
        Livro semCriar= new Livro("naoCriado","not","not",111,111,"not");
    }
    @AfterEach
    void tearDown(){
        DAO.getLivroDAO().excluirTodos();
    }

    /**
     * Teste "criar" que instancia um Livro e verifica através do assertEquals se o livro foi criado
     * A verificação ocorre atraves do método que econtra o objeto criado no LivroDAO.
     */
    @Test
    void criar() throws LivroException {
        Livro atual = DAO.getLivroDAO().criar(new Livro("Esperado", "Rafael", "BST livros",441,1899,"Tiro"));
        Livro esperado = DAO.getLivroDAO().encontrarPorID(atual.getId());

        assertEquals(atual, esperado,"Esse texte deve passar");
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um ISBN que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failpesquisaPorisbn() throws LivroException {
        try{
            DAO.getLivroDAO().pesquisaPorisbn(1);
            fail("Uma exceção deveria ser levantada");
        } catch(Exception e){
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Livro pelo isbn e verifica se o retornado é o esperado
     */
    @Test
    void pesquisaPorisbn() throws LivroException {
        Livro isbnTeste = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorisbn(2577);
        assertEquals(lista.size(),2,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
        assertEquals(lista.get(1),isbnTeste,"Essa mensagem deve aparecer");
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um titulo que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failPesquisaPorTitulo(){
        try{
            DAO.getLivroDAO().pesquisaPorTitulo("The Devers");
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Livro pelo titulo e verifica se o retornado é o esperado
     */
    @Test
    void pesquisaPorTitulo() throws LivroException {
        Livro tituloTeste = DAO.getLivroDAO().criar(new Livro("pequeno","Luis Mario","Brasileira",2577,1989,"Romance"));
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorTitulo("pequeno");
        assertEquals(lista.size(),2,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
        assertEquals(lista.get(1),tituloTeste,"Essa mensagem deve aparecer");
    }

    /**
     * Teste falho ao tentar encontrar um objeto por uma categoria que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failPesquisaPorCategoria(){
        try{
            DAO.getLivroDAO().pesquisaPorCategoria("Inexistente");
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }
    /**
     *Teste para econtrar o Livro pela Categoria e verifica se o retornado é o esperado
     */
    @Test
    void pesquisaPorCategoria() throws LivroException {
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorCategoria("Romance");
        assertEquals(lista.size(),1,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
    }
    /**
     * Teste falho ao tentar encontrar um objeto por um autor que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failPesquisaPorAutor(){
        try{
            DAO.getLivroDAO().pesquisaPorAutor("Inexistente");
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR, e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Livro pelo autor e verifica se o retornado é o esperado
     */
    @Test
    void pesquisaPorAutor() throws LivroException {
        ArrayList<Livro> lista= DAO.getLivroDAO().pesquisaPorAutor("Luis Mario");
        assertEquals(lista.size(),1,"Essa mensagem deve aparecer");
        assertEquals(lista.get(0),pequeno,"Essa mensagem deve aparecer");
    }

    /**
     * Teste falho ao excluir um objeto
     * É passado para escluir um objeto que não foi criado no LivroDAO assim lança uma exceção
     * A exceção é capturada e verifica se foi a esperada
     */
    @Test
    void failExcluir(){
        try{
            DAO.getLivroDAO().excluir(semCriar);
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.EXCLUIR,e.getMessage());
        }
    }

    /**
     * Teste "excuir" que remove o objeto da lista no LivroDAO
     * E verifica o tamanho esperado depois da exclusão
     */
    @Test
    void exluir() throws LivroException {
        DAO.getLivroDAO().excluir(pequeno);
        assertEquals(DAO.getLivroDAO().encontrarTodos().size(),2);
    }

    /**
     * Teste para excluir todos os objetos da lista
     * Após a exclusão, verifica se o tamanho da lista foi zerada
     */
    @Test
    void excluirTodos(){
        DAO.getLivroDAO().excluirTodos();
        assertEquals(DAO.getLivroDAO().encontrarTodos().size(),0);
    }

    /**
     * Teste falho ao atualizar um objeto que não esta na lista do LivroDAO
     * Lança uma exceção ao não encontrar o objeto
     * Captura a exeção e verifica se foi a esperada
     */
    @Test
    void failAtualizar(){
        try{
            DAO.getLivroDAO().atualizar(semCriar);
            fail("Uma exceção deveria ser levantada");
        } catch (LivroException e) {
            assertEquals(LivroException.ATUALIZAR,e.getMessage());
        }
    }

    /**
     * Teste para atualizar um objeto do tipo Livro
     * Seta outra informação em um objeto que ja criado e atualiza
     * Por fim, verifica se o objeto atualizado foi o esperado
     */
    @Test
    void atualizar() throws LivroException {
        medio.setAutor("Novo Autor");
        medio.setIsbn(1999);
        Livro atualizado =DAO.getLivroDAO().atualizar(medio);
        assertEquals(atualizado,medio);
    }

    /**
     * Teste para econtrar todos os objetos no DAO e verifica se é o tamanho esperado
     */
    @Test
    void encontraTodos(){
        ArrayList<Livro> todosOsLivros= DAO.getLivroDAO().encontrarTodos();
        assertEquals(todosOsLivros.size(),3);
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um id que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failEcontraPorId(){
        try{
            DAO.getLivroDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (LivroException e) {
            assertEquals(LivroException.BUSCAR,e.getMessage());
        }
    }

    /**
     *Teste para econtrar o Livro pelo id e verifica se o retornado é o esperado
     */
    @Test
    void econtraPorId() throws LivroException {
        Livro econtrado= DAO.getLivroDAO().encontrarPorID(2);
        assertEquals(econtrado,medio);
    }
}
