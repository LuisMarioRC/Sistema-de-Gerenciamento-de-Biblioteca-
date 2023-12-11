package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe {FileMethods} fornece métodos utilitários para manipulação de arquivos, incluindo criação,
 * consulta, sobrescrita e exclusão de arquivos.
 * @author Gabriel Henry
 * @author Luis Mario
 * @see java.io
 * @see java.util.ArrayList
 * @see java.util.HashMap
 * @see java.util.Map
 */
public abstract class FileMethods{
    /**
     * O diretório padrão onde os arquivos serão armazenados.
     */
    public static final File DIRECTORY = new File("cache");
    /**
     * A extensão padrão para os arquivos manipulados por esta classe.
     */
    public static final String EXTENSAO = ".mh";

    /**
     * Cria um novo arquivo no diretório especificado, se o diretório não existir, ele será criado.
     *
     * @param nomearquivo O nome desejado para o arquivo (sem a extensão).
     * @return Um objeto {@code File} representando o arquivo recém-criado ou existente, ou {@code null} em caso de erro.
     *
     * @throws SecurityException Se ocorrer uma violação de segurança ao criar o diretório ou o arquivo.
     */
    public static File createFile(String nomearquivo) {
        if (!DIRECTORY.exists())
            DIRECTORY.mkdir();
        File arquivo = new File(DIRECTORY.getName() + "/" + nomearquivo + EXTENSAO);
        if (!arquivo.exists()){
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return arquivo;
    }

    /**
     * Consulta um arquivo e retorna uma lista de objetos serializáveis.
     *
     * @param file O arquivo a ser consultado.
     * @param <T> O tipo dos objetos na lista.
     * @return Uma lista de objetos serializáveis contidos no arquivo, ou uma lista vazia em caso de erro.
     */
    public static <T extends Serializable> ArrayList<T> consultarArquivoList(File file) {
        ArrayList<T> list;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            list = (ArrayList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            list = new ArrayList<>();
        }

        return list;
    }

    /**
     * Consulta um arquivo e retorna um mapa de objetos serializáveis associados a chaves inteiras.
     *
     * @param file O arquivo a ser consultado.
     * @param <V> O tipo dos objetos no mapa.
     * @return Um mapa de objetos serializáveis associados a chaves inteiras contidos no arquivo, ou um mapa vazio em caso de erro.
     */
    public static <V extends Serializable> Map<Integer, V> consultarArquivoMap(File file){
        Map<Integer, V> map;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            map = (Map<Integer, V>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * Sobrescreve o conteúdo de um arquivo com um novo objeto serializável.
     *
     * @param arquivo O arquivo a ser sobrescrito.
     * @param objeto O objeto a ser escrito no arquivo.
     * @param <T> O tipo do objeto a ser escrito.
     * @return {@code true} se a operação for bem-sucedida, {@code false} em caso de erro.
     */
    public static <T extends Serializable> boolean sobreescreverArquivo(File arquivo, Object objeto){
        if (arquivo == null || objeto == null) {
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(objeto);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Apaga o conteúdo de um arquivo, mantendo o arquivo vazio.
     *
     * @param arquivo O arquivo cujo conteúdo será apagado.
     * @throws RuntimeException Se ocorrer um erro durante a operação de exclusão do conteúdo.
     */
    public static void apagarConteudoArquivo(File arquivo) {
        try {
            new FileOutputStream(arquivo).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
