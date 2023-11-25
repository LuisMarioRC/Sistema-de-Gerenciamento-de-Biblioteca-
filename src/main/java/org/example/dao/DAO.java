package org.example.dao;

import org.example.dao.administrador.AdministradorDAO;
import org.example.dao.bibliotecario.BibliotecarioDAO;
import org.example.dao.emprestimos.EmprestimosDAO;
import org.example.dao.livro.LivroDAO;
import org.example.dao.reserva.ReservaDAO;
import org.example.dao.usuario.UsuarioDAO;

/**
 * Classe responável por fornecer instâncias de todas as classes DAO's
 * Atributos estáticos intanciando cada classe
 * @author Luis Mario
 * @author Gabriel Henry
 * @see AdministradorDAO
 * @see BibliotecarioDAO
 * @see EmprestimosDAO
 * @see LivroDAO
 * @see ReservaDAO
 * @see UsuarioDAO
 */
public class DAO {
    private static LivroDAO livroDao;
    private static EmprestimosDAO emprestimosDao;
    private static UsuarioDAO usuarioDao;
    private static AdministradorDAO administradorDao;
    private static BibliotecarioDAO bibliotecarioDao;
    private static ReservaDAO reservaDao;


    /**
     * @return A instância de code ReservaDAO.
     */
    public static ReservaDAO getReservaDAO(){
        if (reservaDao == null) {
            reservaDao = new ReservaDAO();
        }
        return reservaDao;
    }
    /**
     * @return A instância de code LivroDAO.
     */
    public static LivroDAO getLivroDAO() {
        if (livroDao == null) {
            livroDao = new LivroDAO();
        }
        return livroDao;
    }
    /**
     * @return A instância de code EmprestimosDAO.
     */
    public static EmprestimosDAO getEmprestimosDAO() {
        if (emprestimosDao == null) {
            emprestimosDao = new EmprestimosDAO();
        }
        return emprestimosDao;
    }
    /**
     * @return A instância de code UsuarioDAO.
     */
    public static UsuarioDAO getUsuarioDAO() {
        if (usuarioDao == null) {
            usuarioDao = new UsuarioDAO();
        }
        return usuarioDao;
    }
    /**
     * @return A instância de code AdministradorDAO.
     */
    public static AdministradorDAO getAdministradorDAO() {
        if (administradorDao == null) {
            administradorDao = new AdministradorDAO();
        }
        return administradorDao;
    }
    /**
     * @return A instância de code BibliotecarioDAO.
     */
    public static BibliotecarioDAO getBibliotecarioDAO() {
        if (bibliotecarioDao == null) {
            bibliotecarioDao = new BibliotecarioDAO();
        }
        return bibliotecarioDao;
    }
}


