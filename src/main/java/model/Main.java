package model;

import DAO.LivroDAO;

import java.util.ArrayList;
import java.util.Date;
                                        //main para alguns testes
public class Main {
    public static void main(String[] args) {
        Bibliotecario b = new Bibliotecario();
        Livro l1= b.registraLivro("Os crueis","Luis Mario","Mario abc",55774,1987,"Romance");
        Livro l2= b.registraLivro("Os crueis","Roberto","Mario abc",55774,1987,"Romance");
        Livro l3= b.registraLivro("Os loucos","Luis Mario","Mario abc",55774,1987,"Romance");
        Livro l4= b.registraLivro("Os crueis","Luis Mario","Mario abc",55774,1987,"Romance");


        LivroDAO livrodao = new LivroDAO();

        livrodao.addLivro(l1);
        livrodao.addLivro(l2);
        livrodao.addLivro(l3);
        livrodao.addLivro(l4);
        ArrayList<Livro> ab = livrodao.pesquisaPorTitulo("Os crueis");

        for (Livro livro: ab){
            System.out.println(ab);
        }



    }
}
