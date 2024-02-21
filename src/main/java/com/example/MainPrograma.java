package com.example;

import com.example.dao.DAO;
import com.example.model.Administrador;
import com.example.model.Bibliotecario;
import com.example.model.Usuario;

/**
 * A classe MainPrograma é responsável por iniciar o programa e realizar algumas operações iniciais.
 * Neste caso, ela cria instâncias de Bibliotecario, Administrador e Usuario utilizando o DAO correspondente
 * para persistir essas instâncias no banco de dados.
 */
public class MainPrograma {
    /**
     * O método principal que inicia o programa.
     *
     * @param args Os argumentos da linha de comando (não são usados neste programa).
     */
    public static void main(String[] args) {
        // Cria um novo bibliotecário e persiste no banco de dados
        Bibliotecario lucas = DAO.getBibliotecarioDAO().criar(new Bibliotecario("lucas", 1234));

        // Cria um novo administrador e persiste no banco de dados
        Administrador joao = DAO.getAdministradorDAO().criar(new Administrador("joao",1234));

        // Cria um novo usuário e persiste no banco de dados
        Usuario roberto = DAO.getUsuarioDAO().criar(new Usuario("roberto","Feira VI","75123456789",1234));
    }
}
