package com.example.youngju.kockockock.System.DataUnit;

import com.example.youngju.kockockock.System.APIDatabase.APIGetter;

import java.io.Serializable;

public class Region implements Serializable {
    //Type Constant List
    public final static int STATION = 200; // 역
    public final static int FESTIVAL = 15; // 행사
    public final static int ATTRACTION = 12; // 관광지
    public final static int RESTAURANT = 39; // 식당
    public final static int FACILITY = 201; // 편의시설

    //Choice Constant List
    public final static int NOTSELECTED = 2000; //선택안됨
    public final static int SELECTED = 2001; //선택됨
    public final static int BEGINPOINT = 2002; //시작점
    public final static int ENDPOINT = 2003; //끝점

    //Member Variable List
    private int type;
    private boolean rec;
    private int choice;

    private String ID;
    private String latitude; //위도, 가로줄
    private String longitude; //경도, 세로줄
    private String content;
    private String name;

    public Region(String ID, String name, int type, String latitude, String longitude){
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.content = null;
        this.rec = false;
        this.choice = Region.NOTSELECTED;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getContent(){
        if(content == null){
            APIGetter apiGetter = new APIGetter(APIGetter.TOURAPI_OVERVIEW);
            apiGetter.addParam(ID);

            try {
                apiGetter.start();
                apiGetter.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            content = (String) apiGetter.getResult();
        }

        return content;
    }

    public String getName(){ return name; }

    public String getID(){ return ID; }

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

    public void setChoice(int choice){ this.choice=choice; }

}
