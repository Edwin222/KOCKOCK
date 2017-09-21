package com.example.youngju.kockockock.System.OperationStructure;

import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-10.
 */

public class MinHeap extends ArrayList<HeapElement> {

    public MinHeap(){
        super();
        this.add(new HeapElement());
    }
    
    public boolean isEmpty(){
    	if(this.size() == 1){
    		return true;
    	}
    	
    	return false;
    }

    public void addHeap(HeapElement e){
        int cursor = this.size();
        this.add(e);

        while(cursor/2 >= 1){
            HeapElement parent = this.get(cursor/2);
            if(parent.getBound() > e.getBound()){
                this.set(cursor, parent);
                cursor /= 2;
            }
            else break;
        }

        this.set(cursor, e);
    }

    public HeapElement popHeap(){
    	if(isEmpty()){
    		return null;
    	}
    	
        HeapElement result = this.get(1);
        this.set(1, this.get(this.size()-1));
        this.remove(this.size()-1);

        if(isEmpty()){
        	return result;
        }
        
        int cursor = 1;
        HeapElement currentE = this.get(cursor);
        while(cursor*2 < this.size()-1){

            int targetCursor = 0;
            HeapElement lesserE;
            if( cursor*2 + 1 >= this.size()-1 || this.get(cursor*2).getBound() < this.get(cursor*2 + 1).getBound()){
                lesserE = this.get(cursor*2);
                targetCursor = cursor*2;
            }
            else {
                lesserE = this.get(cursor*2+1);
                targetCursor = cursor*2+1;
            }

            if(lesserE.getBound() < currentE.getBound() ){
                this.set(cursor, lesserE);
                cursor = targetCursor;
            }
            else {
                this.set(cursor, currentE);
                break;
            }
        }

        return result;
    }


}
