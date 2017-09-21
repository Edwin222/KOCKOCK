package com.example.youngju.kockockock.System.DataUnit;


import com.example.youngju.kockockock.System.DataContainer.RegionContainer;

import java.io.Serializable;

public class Path implements Serializable {
    private String pathName;
    private TravelInfo travelinfo;

    private RegionContainer regionList;

    public Path(String name) {
        setName(name);
    }

    public RegionContainer getList(){
        return regionList;
    }

    public TravelInfo getTravelInfo() { return travelinfo; }

    public void setTravelInfo(TravelInfo t){

        try {
            travelinfo = t.clone();

        } catch(CloneNotSupportedException e){
            e.printStackTrace();
        }

    }

    public String getName(){
        return pathName;
    }

    public void setName(String n){
        pathName = n;
    }

    @Override
    public String toString(){
        return "Path:"+pathName+"  city:"+travelinfo.getMetro().getName() +"  local:"+travelinfo.getCity().getName();
    }
}
