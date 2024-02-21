package com.example.utils;

/**
 * A classe SessionLogin é uma classe utilitária que gerencia a sessão do usuário.
 * Ela permite fazer login, logout e obter informações sobre o usuário atualmente logado na sessão.
 */
public abstract class SessionLogin {

    /** O objeto que representa o usuário atualmente logado na sessão. */
    private static Object userInSession;

    /**
     * Obtém o usuário atualmente logado na sessão.
     *
     * @return O usuário atualmente logado na sessão.
     */
    public static Object getUserInSession(){
        return userInSession;
    }

    /**
     * Realiza o login do usuário na sessão.
     *
     * @param user O usuário a ser logado na sessão.
     */
    public static void loginUser(Object user){
        userInSession = user;
    }

    /**
     * Realiza o logout do usuário na sessão, removendo-o da sessão.
     */
    public static void logoutUser(){
        userInSession= null;
    }
}
