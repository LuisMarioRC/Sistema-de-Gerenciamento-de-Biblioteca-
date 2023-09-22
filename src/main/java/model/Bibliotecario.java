package model;


public class Bibliotecario extends Operador {

    public Bibliotecario(String nome, String cargo,Integer senha){
        super(nome,cargo,senha);

    }

    @Override
    public String toString() {
        return "Bibliotecario{}";
    }
}
