package com.example.youngju.kockockock.System;

import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-10.
 */

public class HeapElement extends ArrayList<Integer> {
    private int bound;

    public HeapElement(){
        bound = 0;
    }
    public HeapElement(int b, ArrayList<Integer> path){
        super(path);
        bound = b;
    }

    //Accessor
    public int getTotalWeight(int[][] matrix){
        int result=0;
        int n = this.size();

        for(int i=1; i<n; i++) {
            result += matrix[this.get(i - 1)][this.get(i)];
        }

        return result + this.get( this.size() );
    }

    public int getBound(){
        return bound;
    }

}
