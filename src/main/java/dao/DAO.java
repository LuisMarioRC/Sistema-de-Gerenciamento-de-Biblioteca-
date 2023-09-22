package dao;

import dao.administrador.AdministradorDAO;
import dao.bibliotecario.BibliotecarioDAO;
import dao.emprestimos.EmprestimosDAO;
import dao.livro.LivroDAO;
import dao.operador.OperadorDAO;
import dao.usuario.UsuarioDAO;

public class DAO {

    private static LivroDAO livroDao;
    private static EmprestimosDAO emprestimosDao;
    private static OperadorDAO operadorDao;
    private static UsuarioDAO usuarioDao;
    private static AdministradorDAO administradorDao;
    private static BibliotecarioDAO bibliotecarioDao;


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

    public static OperadorDAO getOperadorDAO() {
        if (operadorDao == null) {
            operadorDao = new OperadorDAO();
        }
        return operadorDao;
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


