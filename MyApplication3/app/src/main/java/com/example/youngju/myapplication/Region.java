package com.example.youngju.myapplication;

import java.io.Serializable;

/**
 * Created by HyunMin on 2017-08-09.
 */

public class Region implements Serializable{
    //Type Constant List
    public final static int STATION = 1001;
    public final static int FESTIVAL = 1002;
    public final static int ATTRACTION = 1003;
    public final static int RESTAURANT = 1004;
    public final static int FACILITY = 1005;

    //Choice Constant List
    public final static int NOTSELECTED = 2000;
    public final static int SELECTED = 2001;
    public final static int BEGINPOINT = 2002;
    public final static int ENDPOINT = 2003;

    //Member Variable List
    private int type;
    private boolean rec;
    private int choice;

    public Region(int t, boolean r, int c){
        this.type = t;
        this.rec = r;
        this.choice = c;
    }

    public int getType(){
        return type;
    }

    public boolean isRecommended(){
        return rec;
    }

    public int getChoice(){
        return choice;
    }
}
