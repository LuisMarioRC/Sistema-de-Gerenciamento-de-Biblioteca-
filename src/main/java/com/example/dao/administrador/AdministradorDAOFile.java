package com.example.dao.administrador;

import com.example.excecoes.AdministradorException;
import com.example.model.Administrador;
import com.example.utils.FileMethods;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe que é responsável por fazer o acesso dos dados da classe Empréstimos
 * @author Luis Mario
 * @author Gabril Henry
 * @see AdministradorException
 * @see Administrador
 * @see java.util.ArrayList
 * @see java.util.Objects
 * @see FileMethods
 * @see java.io.File
 */
public class AdministradorDAOFile implements AdministradorDAOInterface {
    File arquivo;
    private static final String NAMEFILE= "administrador";

    public AdministradorDAOFile(){
        arquivo = FileMethods.criarArquivo(NAMEFILE);
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
        return FileMethods.lerArquivoList(arquivo);
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
        return(administrador.size());
    }
}
