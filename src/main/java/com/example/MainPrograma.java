package com.example;

import com.example.dao.DAO;
import com.example.model.Administrador;
import com.example.model.Bibliotecario;
import com.example.model.Usuario;


public class MainPrograma {
    public static void main(String[] args) {
        Bibliotecario lucas = DAO.getBibliotecarioDAO().criar(new Bibliotecario("lucas", 1234));
        Administrador joao = DAO.getAdministradorDAO().criar(new Administrador("joao",1234));
        Usuario roberto = DAO.getUsuarioDAO().criar(new Usuario("roberto","Feira VI","75123456789",1234));
    }
}
