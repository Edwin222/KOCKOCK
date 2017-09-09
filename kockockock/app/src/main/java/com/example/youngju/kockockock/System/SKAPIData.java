package com.example.youngju.kockockock.System;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;

/**
 * Created by Edwin on 2017-09-09.
 */

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
        doc = getXMLData("route", "requCoordType=WGS84GEO", "endX="+endX, "endY="+endY, "startX="+startX, "startY"+startY, "resCoordType=WGS84GEO");

        return Integer.parseInt(doc.getElementsByTag("tmap:totalDistance").get(0).text());
    }

    private synchronized Document getXMLData(String serviceName,  String... parameters){

        //Make real URL from parameters
        String connectionURL =  "https://apis.skplanetx.com/tmap/" + serviceName + "?version=1&appKey=" + getAPIKey();
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
