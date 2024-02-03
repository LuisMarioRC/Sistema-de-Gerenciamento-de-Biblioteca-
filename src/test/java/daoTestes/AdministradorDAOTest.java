package daoTestes;



import com.example.dao.DAO;
import com.example.excecoes.AdministradorException;
import com.example.model.Administrador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe de teste responsável para realizar testes referente aos métodos do AdministradorDAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see DAO ;
 * @see AdministradorException ;
 * @see Administrador ;
 * @see org.junit.jupiter.api.AfterEach;
 * @see org.junit.jupiter.api.BeforeEach;
 * @see org.junit.jupiter.api.Test;
 * @see java.util.ArrayList;
 */
public class AdministradorDAOTest {

    Administrador cauly;
    Administrador everaldo;
    Administrador notExisted;
    Administrador nulo;

    @BeforeEach
    void setUp() {
        cauly = DAO.getAdministradorDAO().criar(new Administrador("everaldo", 1234));
        everaldo = DAO.getAdministradorDAO().criar(new Administrador("cauly", 1234));
        notExisted = DAO.getAdministradorDAO().criar(new Administrador("notExisted", 0));
        nulo = new Administrador("nulo",411);
    }

    @AfterEach
    void tearDown() {
        DAO.getAdministradorDAO().excluirTodos();
    }

    /**
     * Teste "criar" que instancia um Administrador e verifica através do assertEquals se o administrador foi criado
     * A verificação ocorre atraves do método que econtra o objeto criado no AdministardorDAO.
     */
    @Test
    void criar() throws AdministradorException {
        Administrador criado = DAO.getAdministradorDAO().criar(new Administrador("Administrador1", 345));
        Administrador esperado = DAO.getAdministradorDAO().encontrarPorID(3);
        assertEquals(criado, esperado);
    }

    /**
     * Teste falho ao excluir um objeto do tipo administrador
     * É passado para escluir um objeto que não foi criado no AdministardorDAO assim lança uma exceção
     * A exceção é capturada e verifica se foi a esperada
     */
    @Test
    void failExcluir() throws AdministradorException{
        try{
            DAO.getAdministradorDAO().excluir(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (AdministradorException e) {
            assertEquals(AdministradorException.EXCLUIR,e.getMessage());
        }
    }

    /**
     * Teste "excuir" que remove o objeto da lista no AdministardorDAO
     * E verifica o tamanho esperado depois da exclusão
     */
    @Test
    void exluir() throws AdministradorException {
        DAO.getAdministradorDAO().excluir(cauly);
        assertEquals(DAO.getAdministradorDAO().encontrarTodos().size(),2);
    }

    /**
     * Teste para excluir todos os objetos da lista
     * Após a exclusão, verifica se o tamanho da lista foi zerada
     */
    @Test
    void excluirTodos(){
        DAO.getAdministradorDAO().excluirTodos();
        assertEquals(DAO.getAdministradorDAO().encontrarTodos().size(),0);
    }

    /**
     * Teste falho ao atualizar um objeto que não esta na lista do AdministardorDAO
     * Lança uma exceção ao não encontrar o objeto
     * Captura a exeção e verifica se foi a esperada
     */
    @Test
    void failAtualizar() throws AdministradorException{
        try{
            DAO.getAdministradorDAO().atualizar(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (AdministradorException e) {
            assertEquals(AdministradorException.ATUALIZAR,e.getMessage());
        }
    }

    /**
     * Teste para atualizar um objeto do tipo Administrador
     * Seta outra informação em um objeto que ja criado e atualiza
     * Por fim, verifica se o objeto atualizado foi o esperado
     */
    @Test
    void atualizar() throws AdministradorException {
        everaldo.setNome("New name");
        everaldo.setSenha(567);
        Administrador atualizado = DAO.getAdministradorDAO().atualizar(everaldo);
        assertEquals(atualizado,everaldo);
    }

    /**
     * Teste para econtrar todos os objetos no DAO e verifica se é o tamanho esperado
     */
    @Test
     void encontraTodos(){
        ArrayList<Administrador> todosOsAdministradors= DAO.getAdministradorDAO().encontrarTodos();
        assertEquals(todosOsAdministradors.size(),3);
    }

    /**
     * Teste falho ao tentar encontrar um objeto por um id que não existe, assim lança uma exceção
     * Captura uma a exeção e compara se é a esperada
     */
    @Test
    void failEcontraPorId() throws AdministradorException{
        try{
            DAO.getAdministradorDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (AdministradorException e) {
            assertEquals(AdministradorException.BUSCAR,e.getMessage());
        }
    }

    /**
     *Teste para econtrar o administrador pelo id e verifica se o retornado é o esperado
     */
    @Test
    void econtraPorId() throws AdministradorException {
        Administrador encontrado= DAO.getAdministradorDAO().encontrarPorID(1);
        assertEquals(encontrado,everaldo);
    }

}
