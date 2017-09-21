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

    private String latitude; //경도, 세로줄
    private String longitude; //위도, 가로줄
    private String content;
    private String name;

    public Region(String name, String content, int type, boolean r, int cho, String latitude, String longitude){
        this.name = name;
        this.content = content;
        this.type = type;
        this.rec = r;
        this.choice = cho;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName(){ return name; }

    public String getContent() { return content; }

    public String getLatitude(){ return latitude; }

    public String getLongitude() { return longitude; }

    public int getType(){
        return type;
    }

    public boolean isRecommended(){
        return rec;
    }

    public int getChosenStatus() {
        return choice;
    }
}
