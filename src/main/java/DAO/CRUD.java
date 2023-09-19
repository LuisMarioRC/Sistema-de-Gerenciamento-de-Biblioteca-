package DAO;

import java.util.ArrayList;


public interface CRUD <T> {

    /**
     * Cria novo objeto
     *
     * @param obj
     * @return
     */
    public T criar(T obj);

    /**
     * Deleta um objeto
     *
     * @param obj
     * @return
     */
    public void excluir(T obj);

    /**
     * Detela todos os dados
     */
    public void excluirTodos();

    /**
     * Atualiza um objeto
     *
     * @param obj
     * @return
     */
    public T atualizar(T obj);

    /**
     * Ler toda a lista de dados
     *
     * @return
     */
    public ArrayList<T> encontrarTodos();

    /**
     * Encontra um objeto específico pelo id
     *
     * @param id
     * @return
     */
    public T encontrarPorID(int id);
}
