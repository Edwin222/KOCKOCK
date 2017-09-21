package com.example.youngju.kockockock.System.OperationStructure;

import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-10.
 */

public class HeapElement  {
    private int bound;
    ArrayList<Integer> path;

    public HeapElement(){
        bound = 0;
        path = null;
    }
    public HeapElement(int b, ArrayList<Integer> path){
        bound = b;
        this.path = path;
    }

    public ArrayList<Integer> getPath(){
    	return path;
    }
    
    //Accessor
    public int getTotalWeight(int[][] matrix){
        int result = 0;
        int n = path.size();

        for(int i=1; i<n; i++) {
            result += matrix[path.get(i - 1)][path.get(i)];
        }

        return result;
    }

    public int getBound(){
        return bound;
    }

}
