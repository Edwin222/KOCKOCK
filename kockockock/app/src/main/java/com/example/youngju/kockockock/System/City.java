package com.example.youngju.kockockock.System;

import java.io.Serializable;

/**
 * Created by sleep on 2017-08-22.
 */

public class City implements Serializable{
    private int areaCode;
    private String name;

    public City(int ac, String n){
        areaCode = ac;
        name = n;
    }

    public City(City c){
        areaCode = c.getCode();
        name = c.getName();
    }

    public int getCode(){
        return areaCode;
    }

    public String getName(){
        return name;
    }
}
