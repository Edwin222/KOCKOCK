package com.example.youngju.kockockock.System;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sleep on 2017-08-15.
 */

public class RegionContainer extends ArrayList<Region> implements Serializable {

    public void TSPSort(){
        ArrayList<Integer> presentPath = new ArrayList<Integer>();
        presentPath.add(new Integer(0));

        //TSP Algorithm Application
        //while()
    }

    private int getWeight(int start, int end){
        if(start == end) {
            return -1;
        }

        APIGetter apiGetter = new APIGetter(APIGetter.SKPAPI_DISTANCE);

        apiGetter.addParam( this.get(0).getX() );
        apiGetter.addParam( this.get(0).getY() );
        apiGetter.addParam( this.get(1).getX() );
        apiGetter.addParam( this.get(1).getY() );

        try {
            apiGetter.start();
            apiGetter.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return (int) apiGetter.getResult();
    }

    private boolean isInPath(int target, ArrayList<Integer> path){
        return path.contains( new Integer(target) );
    }

    private int[][] initalizeMatrix(){
        int n = this.size();
        int[][] result = new int[n][n];

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                result[i][j] = getWeight(i, j);
            }
        }
        return result;
    }

    private int getBound(int[][] matrix, ArrayList<Integer> path){
        int n = path.size();
        int result = 0;

        //past weight addition
        for(int i=1; i < n; i++){
            result += matrix[ path.get(i-1) ][ path.get(i) ];
        }

        //initalize
        int last = path.get(n-1);
        n = this.size();

        for(int i=0;i<n;i++){
            if ( last != i && isInPath(i, path) ) continue;

            int min = Integer.MAX_VALUE;
            for(int j=0;j<n;j++){
                if(last == i) {
                    if(j == 0 || isInPath(j, path)) continue;
                }
                else {
                    if( isInPath(j, path) ) continue;
                }

                if(min > matrix[i][j]){
                    min = matrix[i][j];
                }
            }

            result += min;
        }

        return result;
    }
}
