package org.example.dao.bibliotecario;

import org.example.model.Bibliotecario;
import org.example.utils.FileMethods;
import org.example.excecoes.BibliotecarioException;
import org.example.model.Bibliotecario;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class BibliotecarioDAOFile implements BibliotecarioDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "bibliotecario";

    public BibliotecarioDAOFile(){
        arquivo = FileMethods.createFile(NAMEFILE);
    }

    @Override
    public void excluirTodos() {
        FileMethods.apagarConteudoArquivo(arquivo);
    }

    @Override
    public ArrayList<Bibliotecario> encontrarTodos() {
        return FileMethods.consultarArquivoList(arquivo);
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
        int cont =-1;
        for (Bibliotecario b : bibliotecario){
            cont++;
        }
        return (cont+1);
    }
}
