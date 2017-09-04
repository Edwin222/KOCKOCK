package com.example.youngju.kockockock.System;

import java.io.Serializable;
import java.util.Date;


public class TravelInfo implements Cloneable, Serializable {
    private Date startTime;
    private Date endTime;
    private String city;
    private String local;

    public TravelInfo(Date st, Date et, String c, String l){
        setStartTime(st);
        setEndTime(et);
        setCity(c);
        setLocal(l);
    }

    @Override
    protected TravelInfo clone() throws CloneNotSupportedException {

        TravelInfo data = (TravelInfo)super.clone();
        data.setStartTime((Date) startTime.clone());
        data.setEndTime((Date) endTime.clone());
        data.setCity(city.toString());
        data.setLocal(local.toString());
        return data;

    }

    //Accesor
    public Date getStartTime(){
        return startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public String getCity(){
        return city;
    }

    public String getLocal(){
        return local;
    }

    //Mutator
    private void setStartTime(Date st){
        startTime = st;
    }

    private void setEndTime(Date et){
        endTime = et;
    }

    private void setCity(String c){
        city = c;
    }

    private void setLocal(String l){
        local = l;
    }

}
