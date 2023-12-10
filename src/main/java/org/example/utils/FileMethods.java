package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class FileMethods{
    public static final File DIRECTORY = new File("cache");
    public static final String EXTENSAO = ".mh";

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


    public static <T extends Serializable> ArrayList<T> consultarArquivoList(File file) {
        ArrayList<T> list;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            list = (ArrayList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            list = new ArrayList<>();
        }

        return list;
    }

    public static <V extends Serializable> Map<Integer, V> consultarArquivoMap(File file){
        Map<Integer, V> map;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            map = (Map<Integer, V>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            map = new HashMap<>();
        }
        return map;
    }

    public static <T extends Serializable> boolean sobreescreverArquivo(File arquivo, ArrayList<T> list){
        if (arquivo == null || list == null) {
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static <T extends Serializable> boolean sobreescreverArquivoMap(File arquivo, Map<Integer,T> map){
        if (arquivo == null || map == null) {
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(map);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void apagarConteudoArquivo(File arquivo) {
        try {
            new FileOutputStream(arquivo).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
