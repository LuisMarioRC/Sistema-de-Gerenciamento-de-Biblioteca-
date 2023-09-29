package model;

public class Administrador extends Bibliotecario{

    public Administrador(String nome,Integer senha){
        super(nome,senha);

    }

    @Override
    public String toString() {
        return "Administrador{}";
    }
}
