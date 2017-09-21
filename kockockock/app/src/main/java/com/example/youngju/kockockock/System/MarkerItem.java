package com.example.youngju.kockockock.System;

import com.example.youngju.kockockock.System.DataUnit.Region;

/**
 * Created by YoungJu on 2017-09-21.
 */

public class MarkerItem {
    private Region region;
    public MarkerItem(Region region) {
        this.region=region;
    }
    public void setRegion(Region region){ this.region=region;}
    public Region getRegion(){ return region; }
}
