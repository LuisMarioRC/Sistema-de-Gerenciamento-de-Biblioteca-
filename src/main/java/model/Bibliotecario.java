package model;


public class Bibliotecario extends Operador {

    public Bibliotecario(String nome,Integer senha){
        super(nome,senha);

    }

    @Override
    public String toString() {
        return "Bibliotecario{}";
    }
}
