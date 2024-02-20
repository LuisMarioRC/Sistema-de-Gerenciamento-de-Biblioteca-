package com.example.utils;



public abstract class SessionLogin {

    private static Object userInSession;

    public static Object getUserInSession(){
        return userInSession;
    }
    public static void loginUser(Object user){
        userInSession = user;
    }

    public static void logoutUser(){
        userInSession= null;
    }
 }

