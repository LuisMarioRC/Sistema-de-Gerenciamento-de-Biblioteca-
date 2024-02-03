package com.example.dao.bibliotecario;

import com.example.model.Bibliotecario;
import com.example.utils.FileMethods;
import com.example.excecoes.BibliotecarioException;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see FileMethods
 * @see BibliotecarioException
 * @see Bibliotecario
 * @see java.util.ArrayList
 * @see java.util.Objects
 * @see java.io.File
 */
public class BibliotecarioDAOFile implements BibliotecarioDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "bibliotecario";

    public BibliotecarioDAOFile(){
        arquivo = FileMethods.criarArquivo(NAMEFILE);
    }

    @Override
    public void excluirTodos() {
        FileMethods.apagarConteudoArquivo(arquivo);
    }

    @Override
    public ArrayList<Bibliotecario> encontrarTodos() {
        return FileMethods.lerArquivoList(arquivo);
    }

    @Override
    public Bibliotecario criar(Bibliotecario obj) {
        ArrayList<Bibliotecario> bibliotecario = encontrarTodos() ;
        obj.setNumeroDeIdentificacao(this.getProximoID(bibliotecario));
        bibliotecario.add(obj);
        FileMethods.sobreescreverArquivo(arquivo, bibliotecario);
        return obj;
    }

    @Override
    public void excluir(Bibliotecario obj) throws BibliotecarioException {
        ArrayList<Bibliotecario> bibliotecario = encontrarTodos() ;
        boolean remocao = bibliotecario.remove(obj);
        if (!remocao){
            throw new BibliotecarioException(BibliotecarioException.EXCLUIR);
        }
        FileMethods.sobreescreverArquivo(arquivo, bibliotecario);

    }

    @Override
    public Bibliotecario atualizar(Bibliotecario obj) throws BibliotecarioException{
        ArrayList<Bibliotecario> bibliotecario = encontrarTodos() ;
        int index= bibliotecario.indexOf(obj);
        if (index == -1){
            throw new BibliotecarioException(BibliotecarioException.BUSCAR);
        }
        bibliotecario.set(index,obj);
        FileMethods.sobreescreverArquivo(arquivo, bibliotecario);
        return obj;
    }

    @Override
    public Bibliotecario encontrarPorID(int id) throws BibliotecarioException {
        ArrayList<Bibliotecario> bibliotecario = encontrarTodos() ;
        for (Bibliotecario listaDeBibliotecario: bibliotecario){
            if (Objects.equals(listaDeBibliotecario.getNumeroDeIdentificacao(), id)){
                return listaDeBibliotecario;
            }
        }
        throw new BibliotecarioException(BibliotecarioException.BUSCAR);
    }

    private int getProximoID(ArrayList<Bibliotecario> bibliotecario){
        return(bibliotecario.size());
    }
}
