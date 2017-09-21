package com.example.youngju.kockockock.System.DataContainer;

import com.example.youngju.kockockock.System.DataUnit.City;

import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-22.
 */

public class CityList extends ArrayList<City> {

    public String[] getNameArray(){
        String[] result = new String[this.size()];

        for(int i=0;i<result.length;i++){
            result[i] = this.get(i).getName();
        }

        return result;
    }
}
