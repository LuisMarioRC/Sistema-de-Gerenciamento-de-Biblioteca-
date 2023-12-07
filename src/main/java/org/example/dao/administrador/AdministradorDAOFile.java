package org.example.dao.administrador;

import org.example.excecoes.AdministradorException;
import org.example.model.Administrador;
import org.example.utils.FileMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public abstract class AdministradorDAOFile implements AdministradorDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "administrador";

    public AdministradorDAOFile(){
        arquivo = FileMethods.createFile(NAMEFILE);
    }

    @Override
    public Administrador criar(Administrador obj) {
        ArrayList<Administrador> administrador = encontrarTodos() ;
        obj.setNumeroDeIdentificacao(this.getProximoID(administrador));
        administrador.add(obj);
        FileMethods.sobreescreverArquivo(arquivo, administrador);
        return obj;
    }

    @Override
    public void excluir(Administrador obj) throws AdministradorException {
        ArrayList<Administrador> administrador = encontrarTodos() ;
        boolean remocao =administrador.remove(obj);
        if (!remocao){
            throw new AdministradorException(AdministradorException.EXCLUIR);
        }
        FileMethods.sobreescreverArquivo(arquivo, administrador);
    }

    @Override
    public void excluirTodos() {
        FileMethods.apagarConteudoArquivo(arquivo);
    }


    @Override
    public Administrador atualizar(Administrador obj) throws AdministradorException{
        ArrayList<Administrador> administrador = encontrarTodos() ;
        int index = administrador.indexOf(obj);
        if (index == -1){
            throw new AdministradorException(AdministradorException.ATUALIZAR);
        }
        administrador.set(index, obj);
        FileMethods.sobreescreverArquivo(arquivo, administrador);
        return obj;
    }

    @Override
    public ArrayList<Administrador> encontrarTodos() {
        return FileMethods.consultarArquivoList(arquivo);
    }

    @Override
    public Administrador encontrarPorID(int id) throws AdministradorException {
        ArrayList<Administrador> listaDeAdministrador = encontrarTodos() ;
        for (Administrador administrador: listaDeAdministrador){
            if (Objects.equals(administrador.getNumeroDeIdentificacao(), id)){
                return administrador;
            }
        }
        throw new AdministradorException(AdministradorException.BUSCAR);
    }

    private int getProximoID(ArrayList<Administrador> administrador){
        int cont =-1;
        for (Administrador a : administrador){
            cont++;
        }
        return (cont+1);
    }
}
