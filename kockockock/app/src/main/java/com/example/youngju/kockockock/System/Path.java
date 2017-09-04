package com.example.youngju.kockockock.System;


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

    public void setTravlelInfo(TravelInfo t){

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
}
