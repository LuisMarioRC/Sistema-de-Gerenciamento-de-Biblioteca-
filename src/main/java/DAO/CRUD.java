package DAO;



import java.util.ArrayList;


public interface CRUD <T, E extends Exception> {

    // Cria um novo objeto
    public T criar(T obj);

    // Excluir um objeto
    public void excluir(T obj);

    // Excluir todos os objetos
    public void excluirTodos();

    // Atualizar um objeto
    public T atualizar(T obj);

    // Econtrar todos os objetos
    public ArrayList<T> encontrarTodos();


    // Econtrar o objeto por id
    public T encontrarPorID(int id) throws E;
}
