package com.example.youngju.kockockock.System;

import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-22.
 */

public class RegionManager extends ArrayList<Region> {

    public RegionManager(int metroCode, int cityCode){
        APIGetter apiGetter = new APIGetter(APIGetter.TOURAPI_GET_REGION);

        apiGetter.addParam(metroCode, cityCode);

        try {
            apiGetter.start();
            apiGetter.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        ArrayList<Region> result = (ArrayList<Region>) apiGetter.getResult();
        for(Region r : result){
            this.add(r);
        }
    }

    public ArrayList<Region> getRegionByType(int type){
        ArrayList<Region> result = new ArrayList<Region>();

        for(Region r : this){
            if(r.getType() == type){
                result.add(r);
            }
        }

        return result;
    }

    public ArrayList<Region> getRecommend(){
        ArrayList<Region> result = new ArrayList<Region>();

        for(Region r : this){
            if(r.isRecommended()){
                result.add(r);
            }
        }

        return result;
    }
}
