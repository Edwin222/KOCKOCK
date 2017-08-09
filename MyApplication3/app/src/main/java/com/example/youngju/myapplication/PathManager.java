package com.example.youngju.myapplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by HyunMin on 2017-08-09.
 */

public class PathManager {
    private ArrayList<Path> pathList;

    private static class LazyHolder{
        public static final  PathManager INSTANCE = new PathManager();
    }

    private PathManager(){
        this.pathList = new ArrayList<Path>();
    }

    public static PathManager getInstance(){
        return LazyHolder.INSTANCE;
    }

    public void saveData(){

        try {
            FileOutputStream fout = new FileOutputStream("Data.bin");
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(this.pathList);

            out.close();
            fout.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void loadData(){
        try {
            FileInputStream fin = new FileInputStream("Data.bin");
            ObjectInputStream in = new ObjectInputStream(fin);

            this.pathList = (ArrayList<Path>) in.readObject();

            in.close();
            fin.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addPath(Path p){
        this.pathList.add(p);
    }

    public void deletePath(Path p){
        this.pathList.remove(p);
    }

    public void getPath(){

    }
}
