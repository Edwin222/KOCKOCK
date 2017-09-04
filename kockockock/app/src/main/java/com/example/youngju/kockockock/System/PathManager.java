package com.example.youngju.kockockock.System;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class PathManager extends ArrayList<Path> implements Serializable {

    private static class LazyIdiom {
        public static PathManager INSTANCE = new PathManager();
    }

    public static PathManager getInstance(){
        return LazyIdiom.INSTANCE;
    }

    private PathManager(){
        super();
        loadData();
    }

    public ArrayList<String> getPathList(){
        ArrayList<String> result = new ArrayList<String>();

        for(int i=0;i<this.size();i++){
            result.add(this.get(i).getName());
        }

        return result;
    }

    public void saveData(){

        try {
            FileOutputStream fout = new FileOutputStream("data.bin");
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(this);

            fout.close();
            out.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void loadData(){

        try {
            FileInputStream fin = new FileInputStream("data.bin");
            ObjectInputStream in = new ObjectInputStream(fin);

            PathManager pathManager = (PathManager) in.readObject();

            for(int i=0;i<this.size();i++){
                add(pathManager.get(0));
            }

            fin.close();
            in.close();

        } catch(FileNotFoundException fe){
            return;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
