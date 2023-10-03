package model;

/**
 * Classe que representa o Administrador no sistema da Biblioteca, extendendo da classe Bibliotecario
 * @author Gabriel Henry
 * @author Luis Mario
 */
public class Administrador extends Bibliotecario{

    public Administrador(String nome,Integer senha){
        super(nome,senha,"Administrador");

    }

    @Override
    public String toString() {
        return "Administrador{}";
    }
}
