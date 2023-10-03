package dao.usuario;

import dao.CRUD;
import model.Usuario;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see dao.CRUD
 * @see model.Usuario
 */
public interface UsuarioDAOInterface extends CRUD<Usuario, Exception> {
}
