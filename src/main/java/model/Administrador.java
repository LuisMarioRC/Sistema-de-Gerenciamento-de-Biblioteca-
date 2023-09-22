package model;

public class Administrador extends Bibliotecario{

    public Administrador(String nome, String cargo,Integer senha){
        super(nome,cargo,senha);

    }


    @Override
    public String toString() {
        return "Administrador{}";
    }
}
