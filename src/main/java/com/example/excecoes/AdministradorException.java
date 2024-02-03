package com.example.excecoes;

import com.example.model.Administrador;

/**
 * Classe responsável por criar mensagens estáticas de Exceções
 * @author Luis Mario
 * @author Gabril Henry
 * @see Administrador
 */
public class AdministradorException extends Exception{
    private Administrador administrador;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";


    public AdministradorException(String texto){
        super(texto);
    }
}
