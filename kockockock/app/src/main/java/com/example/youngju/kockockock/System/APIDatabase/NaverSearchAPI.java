package com.example.youngju.kockockock.System.APIDatabase;

import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Edwin on 2017-09-22.
 */

public class NaverSearchAPI {

    private Document doc;

    private NaverSearchAPI(){
    }

    static private class LazyIdiom {
        public static NaverSearchAPI INSTANCE = new NaverSearchAPI();
    }

    public NaverSearchAPI getInstance(){
        return LazyIdiom.INSTANCE;
    }
/*
    private ArrayList<Region>

    public ArrayList<Region> getSubway(String query){
    }

    public ArrayList<Region> getStation(String query){
    }

    public ArrayList<Region> getTerminal(String query){

    }

    public ArrayList<Region> getRegionBySearch_nonMetro(TravelInfo info){
        final int NUM_STATION = 2;
        final int NUM_ATTRACTION = 1;
        final int NUM_FOOD = 3;
        final int NUM_TOILET = 1;
        final int NUM_CONVEN = 2;
        final int NUM_INFOCENTER = 1;

        String query = info.getMetro().getName() + info.getCity().getName();
        ArrayList<Region> result = new ArrayList<Region>();


        return result;
    }

    public ArrayList<Region> getRegionBySearch_Metro(TravelInfo info){
        final int NUM_STATION = 2;
        final int NUM_ATTRACTION = 1;
        final int NUM_FOOD = 3;
        final int NUM_TOILET = 1;
        final int NUM_CONVEN = 2;
        final int NUM_INFOCENTER = 1;

        String query = info.getMetro().getName();

        ArrayList<Region> result = new ArrayList<Region>();


        return result;
    }

    private boolean isMetropolis(String name){
        if(name.equals("대구")){
            return true;
        }
        else if(name.equals("인천")){
            return true;
        }
        else if(name.equals("서울")){
            return true;
        }
        else if(name.equals("광주")){
            return true;
        }
        else if(name.equals("부산")){
            return true;
        }
        else if(name.equals("대전")){
            return true;
        }
        else if(name.equals("울산")){
            return true;
        }

        return false;
    }
*/
    public Document getXMLData(String keyword){
        String connectionURL = "https://openapi.naver.com/v1/search/local.xml?query=";

        try {
            String query = URLEncoder.encode(keyword, "UTF-8");
            connectionURL += query + "&display=10&sort=random";

            Connection connection = Jsoup.connect(connectionURL);
            connection.header("X-Naver-Client-Id", getClientID());
            connection.header("X-Naver-Client-Secret", getClientSecret());

            doc = connection.get();

        } catch(Exception e){
            e.printStackTrace();
        }

        return doc;
    }

    private String getClientID(){
        return "hC0JexgRLwDeRWCwj1zb";
    }

    private String getClientSecret(){
        return "vMxtwOSODJ";
    }
}
