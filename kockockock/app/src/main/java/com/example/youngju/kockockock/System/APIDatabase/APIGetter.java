package com.example.youngju.kockockock.System.APIDatabase;

import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-09.
 */

public class APIGetter extends Thread {
    public final static int TOURAPI_METRO_N = 1;
    public final static int TOURAPI_CITY_N = 2;
    public final static int TOURAPI_METRO_CODE = 3;
    public final static int SKPAPI_DISTANCE = 4;
    public final static int TOURAPI_CITY_CODE = 5;
    public final static int TOURAPI_GET_REGION = 6;
    public final static int TOURAPI_OVERVIEW = 7;
    public final static int TOURAPI_METRO = 8;
    public final static int TOURAPI_CITY =9;
    public final static int SKPAPI_WEATHER = 10;

    private int mode_dataType;
    private Object result;
    private ArrayList<Object> parameter;

    public APIGetter(int dataType){
        mode_dataType = dataType;
        parameter = new ArrayList<Object>();
    }

    public void addParam(Object... parm){
        for(Object o : parm){
            parameter.add(o);
        }
    }

    public void resetParam(){
        parameter.removeAll(null);
    }

    public Object getResult(){
        return result;
    }

    @Override
    public void run(){
        switch(mode_dataType){
            case TOURAPI_CITY_N:
                result = TourAPIData.getInstance().getCityName((int) parameter.get(0));
                break;
            case TOURAPI_METRO_N:
                result = TourAPIData.getInstance().getMetroName();
                break;
            case TOURAPI_METRO_CODE:
                result = TourAPIData.getInstance().getMetroCode((int) parameter.get(0));
                break;
            case SKPAPI_DISTANCE:
                result = SKAPIData.getInstance().getTotalDistance((String) parameter.get(0), (String) parameter.get(1), (String) parameter.get(2) ,(String) parameter.get(3));
                break;
            case TOURAPI_CITY_CODE:
                result = TourAPIData.getInstance().getCityCode((int) parameter.get(0), (int) parameter.get(1));
                break;
            case TOURAPI_GET_REGION:
                result = TourAPIData.getInstance().getRegionByCity((int) parameter.get(0), (int) parameter.get(1));
                break;
            case TOURAPI_OVERVIEW:
                result = TourAPIData.getInstance().getOverview((String) parameter.get(0));
                break;
            case TOURAPI_METRO:
                result = TourAPIData.getInstance().getMetro();
                break;
            case TOURAPI_CITY:
                result = TourAPIData.getInstance().getCity((int) parameter.get(0));
                break;
            case SKPAPI_WEATHER:
                result = SKAPIData.getInstance().getWeeklyWeather( (String) parameter.get(0), (String) parameter.get(1) );
                break;
        }
    }
}
