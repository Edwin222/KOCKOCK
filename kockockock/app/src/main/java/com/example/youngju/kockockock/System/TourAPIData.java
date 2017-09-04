package com.example.youngju.kockockock.System;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by sleep on 2017-08-19.
 */

public class TourAPIData {
    private final String OS = "AND";
    private final String APPNAME = "KOCKOCK";
    private Document doc;

    private TourAPIData(){
    }

    private static class LazyIdiom {
        public static TourAPIData INSTANCE = new TourAPIData();
    }

    public static TourAPIData getInstance() {
        return LazyIdiom.INSTANCE;
    }

    public City[] getMetro(){
        new Thread() {
            @Override
            public void run(){
                doc = getXMLData("areaCode", 1);
            }
        };

        City[] result = makeCityResult( doc.select("body").get(0).select("item") );

        return result;
    }

    public String[] getMetroName(){
        City[] cityList = getMetro();
        String[] result = new String[cityList.length];

        for(int i=0;i<cityList.length;i++){
            result[i] = cityList[i].getName();
        }

        return result;
    }

    public City[] getCity(final int areaCode){
        new Thread() {
            @Override
            public void run(){
                doc = getXMLData("areaCode", 1, "&areaCode="+String.valueOf(areaCode));
            }
        };

        City[] result = makeCityResult( doc.select("body").get(0).select("item") );

        return result;
    }

    public String[] getCityName(final int areaCode){
        City[] cityList = getCity(areaCode);
        String[] result = new String[cityList.length];

        for(int i=0;i<cityList.length;i++){
            result[i] = cityList[i].getName();
        }

        return result;
    }

    public int getMetroCode(int position){
        City[] metroList = getMetro();

        return metroList[position].getCode();

    }

    private City[] makeCityResult(Elements cityList){
        City[] result = new City[cityList.size()];

        for(int i=0; i < cityList.size(); i++){
            int code = Integer.parseInt(cityList.get(i).select("code").get(0).text());
            String name = cityList.get(i).select("name").get(0).text();

            result[i] = new City(code, name);
        }

        return result;
    }


    private synchronized Document getXMLData(String serviceName, int page, String... parameters){

        //Make real URL from parameters
        String connectionURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + serviceName + "?ServiceKey="+ getServiceKey() + "&pageNo=" + String.valueOf(page) +"&MobileOS=" + OS + "&MobileApp=" + APPNAME;

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

    private String getServiceKey(){
        return "ftiu7oSNDPVL1tqSYAzl%2BR4UYUiCuLsBDB61NBMu6n%2FDCJYOgrmKCSs4cVSwiYrdc5QIs%2FLKA4iqXU3%2FUx1I5w%3D%3D";
    }
}
