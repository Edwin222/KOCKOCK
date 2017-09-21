package com.example.youngju.kockockock.System.DataUnit;

import java.io.Serializable;
import java.util.Date;


public class TravelInfo implements Cloneable, Serializable {
    private Date startTime;
    private City metro;
    private City city;

    public TravelInfo(){}

    public TravelInfo(Date st, City m, City c){
        setStartTime(st);
        setMetro(new City(m));
        setCity(new City(c));
    }

    @Override
    protected TravelInfo clone() throws CloneNotSupportedException {

        TravelInfo data = (TravelInfo)super.clone();
        data.setStartTime((Date) startTime.clone());
        data.setMetro(new City(metro));
        data.setCity(new City(city));
        return data;

    }

    //Accesor
    public Date getStartTime(){
        return startTime;
    }

    public City getMetro() { return metro; }

    public City getCity() { return city; }

    //Mutator
    public void setStartTime(Date st){
        startTime = st;
    }

    public void setMetro(City m){ metro = m; }

    public void setCity(City c){ city = c; }
}
