package dao;

import dao.administrador.AdministradorDAO;
import dao.bibliotecario.BibliotecarioDAO;
import dao.emprestimos.EmprestimosDAO;
import dao.livro.LivroDAO;
import dao.reserva.ReservaDAO;
import dao.usuario.UsuarioDAO;

/**
 * Classe responável por fornecer instâncias de todas as classes DAO's
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.administrador.AdministradorDAO
 * @see dao.bibliotecario.BibliotecarioDAO
 * @see dao.emprestimos.EmprestimosDAO
 * @see dao.livro.LivroDAO
 * @see dao.reserva.ReservaDAO
 * @see dao.usuario.UsuarioDAO
 */
public class DAO {

    private static LivroDAO livroDao;
    private static EmprestimosDAO emprestimosDao;
    private static UsuarioDAO usuarioDao;
    private static AdministradorDAO administradorDao;
    private static BibliotecarioDAO bibliotecarioDao;
    private static ReservaDAO reservaDao;


    public static ReservaDAO getReservaDAO(){
        if (reservaDao == null) {
            reservaDao = new ReservaDAO();
        }
        return reservaDao;
    }

    public static LivroDAO getLivroDAO() {
        if (livroDao == null) {
            livroDao = new LivroDAO();
        }
        return livroDao;
    }

    public static EmprestimosDAO getEmprestimosDAO() {
        if (emprestimosDao == null) {
            emprestimosDao = new EmprestimosDAO();
        }
        return emprestimosDao;
    }

    public static UsuarioDAO getUsuarioDAO() {
        if (usuarioDao == null) {
            usuarioDao = new UsuarioDAO();
        }
        return usuarioDao;
    }

    public static AdministradorDAO getAdministradorDAO() {
        if (administradorDao == null) {
            administradorDao = new AdministradorDAO();
        }
        return administradorDao;
    }

    public static BibliotecarioDAO getBibliotecarioDAO() {
        if (bibliotecarioDao == null) {
            bibliotecarioDao = new BibliotecarioDAO();
        }
        return bibliotecarioDao;
    }
}


