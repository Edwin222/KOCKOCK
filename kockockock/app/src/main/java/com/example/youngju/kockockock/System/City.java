package com.example.youngju.kockockock.System;

/**
 * Created by sleep on 2017-08-22.
 */

public class City {
    private int areaCode;
    private String name;

    public City(int ac, String n){
        areaCode = ac;
        name = n;
    }

    public int getCode(){
        return areaCode;
    }

    public String getName(){
        return name;
    }
}
