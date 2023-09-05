package model;

import DAO.LivroDAO;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Bibliotecario b = new Bibliotecario();
        b.registraLivro("Os miseráveis","Luis Mario","Mario abc",55774,1987,"Romance");
        b.registraLivro("Os crueis","Roberto","Mario abc",55774,1987,"Romance");
        b.registraLivro("Os rapazes","Luis Mario","Mario abc",55774,1987,"Romance");
        b.registraLivro("Os womans","Luis Mario","Mario abc",55774,1987,"Romance");


        LivroDAO livrodao = new LivroDAO();
        ArrayList<Livro> livrosEcontrados = livrodao.pesquisaPorTitulo("Os crueis");

        for (Livro livro: livrosEcontrados){
            System.out.println(livro.getTitulo());
            System.out.println(livro.getAutor());
        }

        Date datahoje= new Date();
        System.out.println(datahoje);

    }
}
