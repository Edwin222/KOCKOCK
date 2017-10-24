package com.example.youngju.kockockock.System.DataUnit;

/**
 * Created by Edwin on 2017-09-22.
 */

public class WeatherInfo {
    private String max_temperature;
    private String min_temperature;
    private String skyCode;
    private String sky;

    public WeatherInfo(String maxt, String mint, String code, String s){
        max_temperature = maxt;
        min_temperature = mint;
        sky = s;
        skyCode = code;
    }

    public String getMaxT(){
        return max_temperature + "℃";
    }

    public String getMinT(){
        return min_temperature + "℃";
    }

    public String getSkyCode(){
        return skyCode;
    }

    public String getSky(){
        return sky;
    }

    @Override
    public String toString(){
        return getSky() + "/" + getSkyCode() + "/" + getMaxT() + "/" + getMinT();
    }

}
