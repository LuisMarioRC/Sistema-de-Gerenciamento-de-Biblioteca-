package model;

import dao.emprestimos.EmprestimosDAO;
import dao.livro.LivroDAO;
import dao.usuario.UsuarioDAO;

public class Main {
    public static void main(String[] args) {
        Livro li = new Livro("recaid", "eu", "Minha", 4478, 195, "romance");
        LivroDAO livrodao= new LivroDAO();
        livrodao.criar(li);

        System.out.println(livrodao.encontrarTodos());


        Usuario user = new Usuario("Luis","Rua J", 4654554);

        UsuarioDAO ud= new UsuarioDAO();
        ud.criar(user);


        System.out.println(ud.encontrarTodos());

        Emprestimos emp= new Emprestimos(li,user);

        EmprestimosDAO empd= new EmprestimosDAO();
        empd.criar(emp);

        System.out.println(empd.encontrarTodos());


    }
}
