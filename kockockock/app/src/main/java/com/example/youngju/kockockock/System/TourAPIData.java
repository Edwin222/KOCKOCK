package com.example.youngju.kockockock.System;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sleep on 2017-08-19.
 */

// 필요한 지역 정보?

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

    public CityList getMetro(){
        doc = getXMLData("areaCode", 1);
        CityList result = makeCityResult( doc.select("body").get(0).select("item") );

        return result;
    }

    public String[] getMetroName(){
        doc = getXMLData("areaCode", 1);
        String[] result = makeCityNameResult( doc.select("body").get(0).select("item") );

        return result;
    }

    public CityList getCity(final int areaCode){
        doc = getXMLData("areaCode", 1, "&areaCode="+String.valueOf(areaCode));

        CityList result = makeCityResult( doc.select("body").get(0).select("item") );

        return result;
    }

    public String[] getCityName(final int areaCode){
        doc = getXMLData("areaCode", 1, "&areaCode="+String.valueOf(areaCode));

        String[] result = makeCityNameResult( doc.select("body").get(0).select("item") );

        return result;
    }

    public int getMetroCode(int position){
        CityList metroList = getMetro();

        return metroList.get(position).getCode();

    }

    public String getOverview(String ID){

        doc = getXMLData("detailCommon", 1, "contentId="+ID, "overviewYN=Y");

        return doc.select("overview").text();
    }

    public ArrayList<Region> getRegionByCity(int metroCode, int cityCode){

        doc = getXMLData("areaBasedList", 1, "areaCode="+metroCode, "sigunguCode="+cityCode, "arrange=B");

        ArrayList<Region> result = new ArrayList<Region>();

        Elements regionList = doc.select("body").get(0).select("item");
        int itemNum = (regionList.size() > 10) ? 10 : regionList.size();
        for(int i=0;i<itemNum;i++){
            Element target = regionList.get(i);

            String ID = target.select("contentid").text();
            int type = Integer.parseInt(target.select("contenttypeid").text());
            String name = target.select("title").text();
            String latitude = target.select("mapy").text();
            String longitude = target.select("mapx").text();

            result.add(new Region(ID, name, type, latitude, longitude));
        }

        return result;
    }

    public int getCityCode(final int areaCode, int position){
        CityList cityList = getCity(areaCode);

        return cityList.get(position).getCode();
    }

    private CityList makeCityResult(Elements cityList){
        CityList result = new CityList();

        for(int i=0; i < cityList.size(); i++){
            int code = Integer.parseInt(cityList.get(i).select("code").get(0).text());
            String name = cityList.get(i).select("name").get(0).text();

            result.add(new City(code, name));
        }

        return result;
    }

    private String[] makeCityNameResult(Elements cityList){
        String[] result = new String[cityList.size()];

        for(int i=0; i<cityList.size();i++){
            result[i] = cityList.get(i).select("name").get(0).text();
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
