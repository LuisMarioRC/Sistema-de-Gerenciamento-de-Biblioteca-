package org.example.dao.usuario;

import org.example.dao.CRUD;
import org.example.model.Usuario;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see CRUD
 * @see Usuario
 */
public interface UsuarioDAOInterface extends CRUD<Usuario, Exception> {
}
