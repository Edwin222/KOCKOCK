package com.example.youngju.kockockock.System;

import java.io.Serializable;

public class Region implements Serializable {
    //Type Constant List
    public final static int STATION = 1001; // 역
    public final static int FESTIVAL = 1002; // 행사
    public final static int ATTRACTION = 1003; // 관광지
    public final static int RESTAURANT = 1004; // 식당
    public final static int FACILITY = 1005; // 편의시설

    //Choice Constant List
    public final static int NOTSELECTED = 2000; //선택안됨
    public final static int SELECTED = 2001; //선택됨
    public final static int BEGINPOINT = 2002; //시작점
    public final static int ENDPOINT = 2003; //끝점

    //Member Variable List
    private int type;
    private boolean rec;
    private int choice;

    private String pos_X;
    private String pos_Y;

    public Region(int type, boolean r, int cho, String x, String y){
        this.type = type;
        this.rec = r;
        this.choice = cho;
        pos_X = x;
        pos_Y = y;
    }

    public String getX(){ return pos_X; }

    public String getY() { return pos_Y; }

    public int getType(){
        return type;
    }

    public boolean isRecommended(){
        return rec;
    }

    public int getChosenStatus(){
        return choice;
    }
}
