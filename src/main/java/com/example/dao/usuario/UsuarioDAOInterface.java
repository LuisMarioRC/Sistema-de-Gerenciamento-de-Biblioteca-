package com.example.dao.usuario;

import com.example.dao.CRUD;
import com.example.model.Usuario;

/**
 * Interface responsável por implementar os metodos que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @see CRUD
 * @see Usuario
 */
public interface UsuarioDAOInterface extends CRUD<Usuario, Exception> {
}
