package com.example.dao;



import java.util.ArrayList;

/**
 * Interface responsável por implementar os metodos CRUD que serão subescrevidos no DAO
 * @author Luis Mario
 * @author Gabriel Henry
 * @param <T> Objeto
 * @param <E> Exceção
 * @see java.util.ArrayList
 */
public interface CRUD <T, E extends Exception> {

    /**
     * Cria novo objeto T
     *
     * @param obj
     * @return
     */
    public T criar(T obj);

    /**
     * remove objeto T
     * @param obj
     */

    public void excluir(T obj) throws E;

    /**
     * remove todos os objetos
     */
    public void excluirTodos();

    /**
     * atualiza objeto T na sua coleção
     * @param obj
     * @return
     */
    public T atualizar(T obj) throws E;

    /**
     * consulta coleção de objetos T
     * @return
     */
    public ArrayList<T> encontrarTodos();

    /**
     * consulta objeto T na coleção pelo ID
     * @param  id
     * @return
     */
    public T encontrarPorID(int id) throws E;
}
