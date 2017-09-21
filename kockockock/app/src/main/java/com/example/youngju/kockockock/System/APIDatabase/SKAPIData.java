package com.example.youngju.kockockock.System.APIDatabase;

import com.example.youngju.kockockock.System.DataUnit.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import java.io.IOException;

/**
 * Created by Edwin on 2017-09-09.
 */

//날씨 정보

public class SKAPIData {

    private Document doc;

    private SKAPIData(){
    }

    private static class LazyIdiom {
        public static SKAPIData INSTANCE = new SKAPIData();
    }

    public static SKAPIData getInstance(){
        return LazyIdiom.INSTANCE;
    }

    public int getTotalDistance(String startX, String startY, String endX, String endY){
        doc = getXMLData("tmap/route", "requCoordType=WGS84GEO", "endX="+endX, "endY="+endY, "startX="+startX, "startY"+startY, "resCoordType=WGS84GEO");

        return Integer.parseInt(doc.getElementsByTag("tmap:totalDistance").get(0).text());
    }

    public WeatherInfo[] getWeeklyWeather(String lat, String lon){
        WeatherInfo[] result = new WeatherInfo[7];
        JSONObject obj;

        //1~2 Day weather
        doc = getJsonData("weather/forecast/3days", "lat="+lat, "lon="+lon);

        try {
            obj = new JSONObject(doc.text());
            JSONObject skyObject = obj.getJSONObject("weather").getJSONObject("forecast3days").getJSONObject("fcst3hour").getJSONObject("sky");
            JSONArray tempArray = obj.getJSONObject("weather").getJSONObject("forecast3days").getJSONObject("fcstdaily").getJSONArray("temperature");

            result[0] = new WeatherInfo(tempArray.getString(0), tempArray.getString(1), skyObject.getString("code4hour"), skyObject.getString("name4hour"));
            result[1] = new WeatherInfo(tempArray.getString(2), tempArray.getString(3), skyObject.getString("code22hour"), skyObject.getString("name22hour"));
            result[2] = new WeatherInfo(tempArray.getString(4), tempArray.getString(5), skyObject.getString("code43hour"), skyObject.getString("name43hour"));

        } catch(Exception e){
            e.printStackTrace();
        }

        //3~7 Day Weather
        doc = getJsonData("weather/forecast/6days", "lat="+lat, "lon="+lon);

        try {
            obj = new JSONObject(doc.text());
            JSONObject skyObject = obj.getJSONObject("weather").getJSONObject("forecast6days").getJSONObject("sky");
            JSONObject tempObject = obj.getJSONObject("weather").getJSONObject("forecast6days").getJSONObject("temperature");

            for(int i=3; i<7; i++){
                result[i] = new WeatherInfo(tempObject.getString("tmax"+(i+1)+"day"), tempObject.getString("tmin"+(i+1)+"day"),
                        skyObject.getString("pmCode"+(i+1)+"day"), tempObject.getString("pmName"+(i+1)+"day"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public synchronized Document getJsonData(String serviceName, String... parameters){
        //Make real URL from parameters
        String connectionURL = "https://apis.skplanetx.com/" + serviceName + "?version=1&appKey=" + getAPIKey();
        for(String s : parameters){
            connectionURL += "&" + s;
        }

        Document doc = new Document(""); //initialize

        try {
            doc = Jsoup.connect(connectionURL).get();
        } catch(IOException e){
            e.printStackTrace();
        }

        return doc;
    }

    private synchronized Document getXMLData(String serviceName,  String... parameters){

        //Make real URL from parameters
        String connectionURL =  "https://apis.skplanetx.com/" + serviceName + "?version=1&appKey=" + getAPIKey();
        for(String s : parameters) {
            connectionURL += "&" + s;
        }

        Document doc = new Document(""); //initialize

        try {
            //get XML file from URL
            doc = Jsoup.connect(connectionURL).parser(Parser.xmlParser()).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    private String getAPIKey(){
        return "8f0a1f7d-4f48-31e6-94ef-3856d7cb83fe";
    }
}
