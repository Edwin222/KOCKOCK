package com.example.youngju.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HyunMin on 2017-08-09.
 */

public class Path implements Serializable{
    private ArrayList<Region> pathList;
    private TravelInfo travelInfo;
    private ArrayList<String> PathDetailList;

    public Path(TravelInfo t){
        travelInfo = new TravelInfo(t);
    }

    public Path(Path p){

    }

    public void addRegion(Region r){
        pathList.add(r);
    }

    public void deleteRegion(Region r){
        pathList.remove(r);
    }

    @Override
    public String toString() {
        return "";
    }
}
