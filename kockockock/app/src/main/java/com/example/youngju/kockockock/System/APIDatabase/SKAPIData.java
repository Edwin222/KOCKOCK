package com.example.youngju.kockockock.System.APIDatabase;

import android.util.Log;

import com.example.youngju.kockockock.System.DataUnit.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Edwin on 2017-09-09.
 */

//날씨 정보

public class SKAPIData {

    private JSONObject doc;

    private SKAPIData(){
    }

    private static class LazyIdiom {
        public static SKAPIData INSTANCE = new SKAPIData();
    }

    public static SKAPIData getInstance(){
        return LazyIdiom.INSTANCE;
    }

    public int getTotalDistance(String startX, String startY, String endX, String endY){

        Log.d("SKP", ""+startX+","+startY+"//"+endX+","+endY);
        JSONObject obj = getJsonData("tmap/routes", "endX="+endX, "endY="+endY, "reqCoordType=WGS84GEO", "startX="+startX, "startY="+startY);
        String result="";
        if(obj == null){
            return 0;
        }

        try {
            result = obj.getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("totalDistance");
        } catch (Exception e){
            e.printStackTrace();
        }

        return Integer.parseInt(result);
    }

    public WeatherInfo[] getWeeklyWeather(String lat, String lon){
        WeatherInfo[] result = new WeatherInfo[7];

        //1~2 Day weather
        JSONObject obj = getJsonData("weather/forecast/3days", "lat="+lat, "lon="+lon);

        try {
            JSONObject skyObject = obj.getJSONObject("weather").getJSONArray("forecast3days").getJSONObject(0).getJSONObject("fcst3hour").getJSONObject("sky");
            JSONObject tempObject = obj.getJSONObject("weather").getJSONArray("forecast3days").getJSONObject(0).getJSONObject("fcstdaily").getJSONObject("temperature");

            result[0] = new WeatherInfo(tempObject.getString("tmax1day"), tempObject.getString("tmin1day"), skyObject.getString("code4hour"), skyObject.getString("name4hour"));
            Log.d("WEATHER", result[0].toString());
            result[1] = new WeatherInfo(tempObject.getString("tmax2day"), tempObject.getString("tmin2day"), skyObject.getString("code22hour"), skyObject.getString("name22hour"));
            Log.d("WEATHER", result[1].toString());
            result[2] = new WeatherInfo(tempObject.getString("tmax3day"), tempObject.getString("tmin3day"), skyObject.getString("code43hour"), skyObject.getString("name43hour"));
            Log.d("WEATHER", result[2].toString());

        } catch(Exception e){
            e.printStackTrace();
        }

        //3~7 Day Weather
        obj = getJsonData("weather/forecast/6days", "lat="+lat, "lon="+lon);

        try {
            JSONObject skyObject = obj.getJSONObject("weather").getJSONArray("forecast6days").getJSONObject(0).getJSONObject("sky");
            JSONObject tempObject = obj.getJSONObject("weather").getJSONArray("forecast6days").getJSONObject(0).getJSONObject("temperature");

            for(int i=3; i<7; i++){
                result[i] = new WeatherInfo(tempObject.getString("tmax"+(i+1)+"day"), tempObject.getString("tmin"+(i+1)+"day"),
                        skyObject.getString("pmCode"+(i+1)+"day"), skyObject.getString("pmName"+(i+1)+"day"));
                Log.d("WEATHER", result[i].toString());
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public synchronized JSONObject getJsonData(String serviceName, String... parameters){
        //Make real URL from parameters
        String connectionURL = "http://apis.skplanetx.com/" + serviceName + "?version=1&appKey=" + getAPIKey();
        for(String s : parameters){
            connectionURL += "&" + s;
        }

        JSONObject result = null;

        try {
            URL url = new URL(connectionURL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));

            String jsonContent = "";
            String cat = null;
            while( (cat = reader.readLine()) != null){
                jsonContent += cat;
            }

            result = new JSONObject(jsonContent);

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

    private String getAPIKey(){
        return "8f0a1f7d-4f48-31e6-94ef-3856d7cb83fe";
    }
}
