package test;



import dao.DAO;
import dao.excecoes.AdministradorException;
import model.Administrador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;



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

    @Test
    void criar() throws AdministradorException {
        Administrador criado = DAO.getAdministradorDAO().criar(new Administrador("Administrador1", 345));
        Administrador esperado = DAO.getAdministradorDAO().encontrarPorID(3);
        assertEquals(criado, esperado);
    }
    @Test
    void failExcluir() throws AdministradorException{
        try{
            DAO.getAdministradorDAO().excluir(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (AdministradorException e) {
            assertEquals(AdministradorException.EXCLUIR,e.getMessage());
        }
    }
    @Test
    void exluir() throws AdministradorException {
        DAO.getAdministradorDAO().excluir(cauly);
        assertEquals(DAO.getAdministradorDAO().encontrarTodos().size(),2);
    }
    @Test
    void excluirTodos(){
        DAO.getAdministradorDAO().excluirTodos();
        assertEquals(DAO.getAdministradorDAO().encontrarTodos().size(),0);
    }

    @Test
    void failAtualizar() throws AdministradorException{
        try{
            DAO.getAdministradorDAO().atualizar(nulo);
            fail("Uma exceção deveria ser levantada");
        } catch (AdministradorException e) {
            assertEquals(AdministradorException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void atualizar() throws AdministradorException {
        everaldo.setNome("New name");
        everaldo.setSenha(567);
        Administrador atualizado = DAO.getAdministradorDAO().atualizar(everaldo);
        assertEquals(atualizado,everaldo);
    }

    @Test
     void encontraTodos(){
        ArrayList<Administrador> todosOsAdministradors= DAO.getAdministradorDAO().encontrarTodos();
        assertEquals(todosOsAdministradors.size(),3);
    }

    @Test
    void failEcontraPorId() throws AdministradorException{
        try{
            DAO.getAdministradorDAO().encontrarPorID(10);
            fail("Uma exceção deveria ser lançada");
        } catch (AdministradorException e) {
            assertEquals(AdministradorException.BUSCAR,e.getMessage());
        }
    }
    @Test
    void econtraPorId() throws AdministradorException {
        Administrador encontrado= DAO.getAdministradorDAO().encontrarPorID(1);
        assertEquals(encontrado,everaldo);
    }

}
