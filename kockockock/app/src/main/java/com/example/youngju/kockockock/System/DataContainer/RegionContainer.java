package com.example.youngju.kockockock.System.DataContainer;

import android.util.Log;

import com.example.youngju.kockockock.System.APIDatabase.APIGetter;
import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.OperationStructure.HeapElement;
import com.example.youngju.kockockock.System.OperationStructure.MinHeap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sleep on 2017-08-15.
 */

public class RegionContainer extends ArrayList<Region> implements Serializable {


    public void setRegionSequence(){
        int n = this.size();
        int minWeight = Integer.MAX_VALUE;
        HeapElement minElement = null;
        int[][] graphMatrix = initalizeMatrix();
        ArrayList<Integer> presentPath = new ArrayList<Integer>();
        MinHeap heap = new MinHeap();

        System.out.println("TESTTESTSETETSETSETSETSETETSETSETEST");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(graphMatrix[i][j]+" ");
            }
            System.out.println();
        }

        presentPath.add(0);
        heap.add(new HeapElement(getBound(graphMatrix, presentPath), presentPath));

        //TSP Algorithm Application
        while(!heap.isEmpty()){
            HeapElement cursor = heap.popHeap();
            Log.d("TSPTEST", "BOUND="+cursor.getBound());
            if(cursor.getBound() >= minWeight){
                continue;
            }

            if(cursor.getPath().size() == n-2){ // make path
                cursor.getPath().add(n-1);
                for(int i=0;i<n;i++){
                    if( !isInPath(i, cursor.getPath()) ){
                        cursor.getPath().add(n-3, i);
                        break;
                    }
                }

                int tweight = cursor.getTotalWeight(graphMatrix);
                Log.d("TSPTEST", "tweight="+tweight);
                if( tweight < minWeight){
                    minWeight = tweight;
                    minElement = cursor;
                }
                continue;
            }

            for(int i=0;i<n-1;i++){
                presentPath = new ArrayList<Integer>(cursor.getPath());

                if(!isInPath(i, presentPath)){
                    presentPath.add(i);
                    heap.addHeap(new HeapElement(getBound(graphMatrix, presentPath), presentPath));
                }
            }
        }

        System.out.println("TEXTSETSTSETESTSTESTESTESTESTESTSETESTSET");
        for(int i=0;i< minElement.getPath().size(); i++){
            System.out.print(minElement.getPath().get(i)+" ");
        }
        System.out.println();

        refeshSequence(minElement.getPath());
    }

    private void refeshSequence(ArrayList<Integer> path){
        ArrayList<Region> newSequence = new ArrayList<Region>();

        int length = path.size();
        for(int i=0;i<length;i++){
            newSequence.add( this.get( path.get(i) ));
        }

        this.clear();
        for(Region r : newSequence){
            this.add(r);
        }
    }
    private int getWeight(int start, int end){
        if(start == end) {
            return -1;
        }

        APIGetter apiGetter = new APIGetter(APIGetter.SKPAPI_DISTANCE);

        apiGetter.addParam( this.get(start).getLongitude(), this.get(start).getLatitude());
        apiGetter.addParam( this.get(end).getLongitude(), this.get(end).getLatitude());

        try {
            apiGetter.start();
            apiGetter.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return (int) apiGetter.getResult();
    }

    private boolean isInPath(int target, ArrayList<Integer> path){
        return path.contains( target );
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
                    if(j == 0 || isInPath(j, path) || matrix[i][j] < 0) continue;
                }
                else {
                    if( isInPath(j, path) || matrix[i][j] < 0) continue;
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
