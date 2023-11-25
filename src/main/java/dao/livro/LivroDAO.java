package dao.livro;


import excecoes.LivroException;
import model.Livro;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see excecoes.LivroException
 * @see model.Livro
 * @see java.util.ArrayList
 * @see java.util.Objects
 */
public class LivroDAO implements LivroDAOinterface{
    private  ArrayList<Livro> listLivros;

    private int proximoID;
    public LivroDAO(){
        this.listLivros = new ArrayList<>();
        this.proximoID=0;
    }

    private int getProximoID() {
        return this.proximoID++;
    }


    @Override
    public ArrayList<Livro> pesquisaPorTitulo(String titulo) throws LivroException{
        ArrayList<Livro> listaPorTitulos= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getTitulo(), titulo)){
                listaPorTitulos.add(livro);
            }
        }
        if (listaPorTitulos.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorTitulos;
    }

    @Override
    public ArrayList<Livro> pesquisaPorisbn(Integer isbn) throws LivroException{
        ArrayList<Livro> listaPorIsbn= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getIsbn(), isbn)){
                listaPorIsbn.add(livro);
            }
        }
        if (listaPorIsbn.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorIsbn;
    }

    @Override
    public ArrayList<Livro> pesquisaPorCategoria(String categoria) throws LivroException{
        ArrayList<Livro> listaPorCategoria= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getCategoria(), categoria)){
                listaPorCategoria.add(livro);
            }
        }
        if (listaPorCategoria.isEmpty() ){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorCategoria;
    }

    @Override
    public ArrayList<Livro> pesquisaPorAutor(String autor) throws LivroException{
        ArrayList<Livro> listaPorAutor= new ArrayList<>();
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getAutor(), autor)){
                listaPorAutor.add(livro);
            }
        }
        if (listaPorAutor.isEmpty()){
            throw new LivroException(LivroException.BUSCAR);
        }
        return listaPorAutor;
    }

    @Override
    public Livro criar(Livro obj) {
        obj.setId(this.getProximoID());
        this.listLivros.add(obj);
        return obj;
    }

    @Override
    public void excluir(Livro obj) throws LivroException{
        boolean remocao = this.listLivros.remove(obj);
        if (!remocao){
            throw new LivroException(LivroException.EXCLUIR,obj);
        }
    }

    @Override
    public void excluirTodos() {
        this.listLivros = new ArrayList<>();
        this.proximoID=0;

    }

    @Override
    public Livro atualizar(Livro obj) throws LivroException{
        int index = this.listLivros.indexOf(obj);
        if (index == -1){
            throw new LivroException(LivroException.ATUALIZAR,obj);
        }
        this.listLivros.set(index, obj);
        return obj;
    }

    @Override
    public ArrayList<Livro> encontrarTodos() {
        return this.listLivros;
    }

    @Override
    public Livro encontrarPorID(int id) throws LivroException {
        for (Livro livro: listLivros){
            if (Objects.equals(livro.getId(), id)){
                return livro;
            }
        }
        throw new LivroException(LivroException.BUSCAR);
    }
}