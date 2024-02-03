package com.example.dao;

import com.example.dao.administrador.AdministradorDAO;
import com.example.dao.administrador.AdministradorDAOFile;
import com.example.dao.bibliotecario.BibliotecarioDAO;
import com.example.dao.bibliotecario.BibliotecarioDAOFile;
import com.example.dao.emprestimos.EmprestimosDAO;
import com.example.dao.emprestimos.EmprestimosDAOFile;
import com.example.dao.livro.LivroDAO;
import com.example.dao.livro.LivroDAOFile;
import com.example.dao.reserva.ReservaDAO;
import com.example.dao.reserva.ReservaDAOFile;
import com.example.dao.usuario.UsuarioDAO;
import com.example.dao.usuario.UsuarioDAOFile;

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
    private static LivroDAOFile livroDao;
    private static EmprestimosDAOFile emprestimosDao;
    private static UsuarioDAOFile usuarioDao;
    private static AdministradorDAOFile administradorDao;
    private static BibliotecarioDAOFile bibliotecarioDao;
    private static ReservaDAOFile reservaDao;


    /**
     * @return A instância de code ReservaDAO.
     */
    public static ReservaDAOFile getReservaDAO(){
        if (reservaDao == null) {
            reservaDao = new ReservaDAOFile();
        }
        return reservaDao;
    }
    /**
     * @return A instância de code LivroDAO.
     */
    public static LivroDAOFile getLivroDAO() {
        if (livroDao == null) {
            livroDao = new LivroDAOFile();
        }
        return livroDao;
    }
    /**
     * @return A instância de code EmprestimosDAO.
     */
    public static EmprestimosDAOFile getEmprestimosDAO() {
        if (emprestimosDao == null) {
            emprestimosDao = new EmprestimosDAOFile();
        }
        return emprestimosDao;
    }
    /**
     * @return A instância de code UsuarioDAO.
     */
    public static UsuarioDAOFile getUsuarioDAO() {
        if (usuarioDao == null) {
            usuarioDao = new UsuarioDAOFile();
        }
        return usuarioDao;
    }
    /**
     * @return A instância de code AdministradorDAO.
     */
    public static AdministradorDAOFile getAdministradorDAO() {
        if (administradorDao == null) {
            administradorDao = new AdministradorDAOFile();
        }
        return administradorDao;
    }
    /**
     * @return A instância de code BibliotecarioDAO.
     */
    public static BibliotecarioDAOFile getBibliotecarioDAO() {
        if (bibliotecarioDao == null) {
            bibliotecarioDao = new BibliotecarioDAOFile();
        }
        return bibliotecarioDao;
    }
}


