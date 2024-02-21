package com.example.excecoes;

import com.example.model.Bibliotecario;

/**
 * Classe responsável por criar mensagens estáticas de Exceções
 * @author Luis Mario
 * @author Gabril Henry
 * @see Bibliotecario
 */
public class BibliotecarioException extends Exception {

    private Bibliotecario bibliotecario;

    public static final String BUSCAR = "Operação de BUSCAR não encontrada";
    public static final String EXCLUIR = "Operação de EXCLUSÃO não realizada.";
    public static final String ATUALIZAR = "Operação de ATUALIZAÇÃO não realizada.";
    public static final String SENHA_INVALIDA = "A senha está INCORRETA!";


    public BibliotecarioException(String texto){
        super(texto);
    }

}