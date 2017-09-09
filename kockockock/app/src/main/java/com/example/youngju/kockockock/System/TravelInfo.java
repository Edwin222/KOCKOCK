package com.example.youngju.kockockock.System;

import java.io.Serializable;
import java.util.Date;


public class TravelInfo implements Cloneable, Serializable {
    private Date startTime;
    private String city;
    private String local;

    public TravelInfo(){}

    public TravelInfo(Date st,  String c, String l){
        setStartTime(st);
        setCity(c);
        setLocal(l);
    }

    @Override
    protected TravelInfo clone() throws CloneNotSupportedException {

        TravelInfo data = (TravelInfo)super.clone();
        data.setStartTime((Date) startTime.clone());
        data.setCity(city.toString());
        data.setLocal(local.toString());
        return data;

    }

    //Accesor
    public Date getStartTime(){
        return startTime;
    }

    public String getCity(){
        return city;
    }

    public String getLocal(){
        return local;
    }

    //Mutator
    public void setStartTime(Date st){
        startTime = st;
    }

    public void setCity(String c){
        city = c;
    }

    public void setLocal(String l){
        local = l;
    }

}
