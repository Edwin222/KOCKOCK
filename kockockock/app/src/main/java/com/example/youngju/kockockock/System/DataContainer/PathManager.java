package com.example.youngju.kockockock.System.DataContainer;

import android.os.Environment;
import android.util.Log;

import com.example.youngju.kockockock.System.DataUnit.Path;

import java.io.File;
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

    public synchronized void saveData(){

        try {

            File dir = new File("/sdcard/KOCKOCK");
            if(!dir.exists()){
                Log.d("SIBAL", "DIRSIBAL");
                Log.d("SIBAL", String.valueOf(dir.mkdirs()));
            }
            Log.d("SIBAL", String.valueOf(dir.exists()) + dir.getAbsolutePath());

            File file = new File("/sdcard/KOCKOCK/data.bin");
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(this);

            fout.close();
            out.close();
        } catch(Exception e){
            Log.d("Kock", "savedate:Exception");
            e.printStackTrace();
        }

    }

    public void loadData(){

        try {
            FileInputStream fin = new FileInputStream("/sdcard/KOCKOCK/data.bin");
            ObjectInputStream in = new ObjectInputStream(fin);

            PathManager pathManager = (PathManager) in.readObject();
            Log.d("SIBAL", Environment.getRootDirectory().getAbsolutePath());

            for(int i=0;i<pathManager.size();i++){
                Log.d("SIBAL", Environment.getRootDirectory().getAbsolutePath());
                add(pathManager.get(i));
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
