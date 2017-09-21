package com.example.youngju.kockockock.System;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Edwin on 2017-09-09.
 */

public class APIGetter extends Thread {
    public final static int TOURAPI_METRO_N = 1;
    public final static int TOURAPI_CITY_N = 2;
    public final static int TOURAPI_METRO_CODE = 3;
    public final static int SKPAPI_DISTANCE = 4;

    private int mode_dataType;
    private Object result;
    private ArrayList<Object> parameter;

    public APIGetter(int dataType){
        mode_dataType = dataType;
        parameter = new ArrayList<Object>();
    }

    public void addParam(Object parm){
        parameter.add(parm);
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
        }
    }
}