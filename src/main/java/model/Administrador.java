package model;

public class Administrador extends Bibliotecario{

    public Administrador(){

    }

    public void bloquearConta(Operador conta){
        conta.setStatus(false);   // Muda o status da conta que deseja bloquear para false;
    }



}
