package com.example.youngju.kockockock.System.APIDatabase;

import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;
import com.example.youngju.kockockock.System.OperationStructure.GeoTrans;
import com.example.youngju.kockockock.System.OperationStructure.GeoTransPoint;
import com.google.android.gms.maps.model.LatLng;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public static NaverSearchAPI getInstance(){
        return LazyIdiom.INSTANCE;
    }

    private LatLng getLatLng(int mapx, int mapy) {
        GeoTransPoint temp = new GeoTransPoint(mapx, mapy);
        temp = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, temp);
        Double lat = temp.getY() * 1E6;
        Double lng = temp.getX() * 1E6;
        LatLng result = new LatLng(lat, lng);

        return result;
    }

    private ArrayList<Region> makeRegionList(String query, String content, int num){
        ArrayList<Region> result = new ArrayList<Region>();

        doc = getXMLData(query);
        Elements list = doc.getElementsByTag("item");

        for(int i=0;i<num;i++){
            Element cursor = list.get(0);
            String name;
            int mapx, mapy;

            name = cursor.getElementsByTag("title").text();
            mapx = Integer.parseInt(cursor.getElementsByTag("mapx").text());
            mapy = Integer.parseInt(cursor.getElementsByTag("mapy").text());

            LatLng geoPoint = getLatLng(mapx, mapy);

            Region newRegion = new Region("", name, Region.T_STATION, String.valueOf(geoPoint.latitude), String.valueOf(geoPoint.longitude));
            newRegion.setContent(content);
            result.add(newRegion);
        }

        return result;
    }

    private ArrayList<Region> makeRecRegionList(String query, String content, int num){
        ArrayList<Region> result = makeRegionList(query, content, num);

        for(Region r : result){
            r.setRec(true);
        }

        return result;
    }

    private ArrayList<Region> getSubway(String query, int num){
        query += " 지하철역";

        return makeRegionList(query, "지하철역", num);
    }

    private ArrayList<Region> getStation(String query, int num){
        query += " 기차역";

        return makeRegionList(query, "기차역", num);
    }

    private ArrayList<Region> getTerminal(String query, int num){
        query += " 정류장";

        return makeRegionList(query, "정류장", num);
    }

    private ArrayList<Region> getAttraction(String query, int num){
        query += " 여행지";

        return makeRecRegionList(query, "추천 여행지", num);
    }

    private ArrayList<Region> getFood(String query, int num){
        query += " 맛집";

        return makeRecRegionList(query, "추천 맛집", num);
    }

    private ArrayList<Region> getToilet(String query, int num){
        query += " 공중화장실";

        return makeRegionList(query, "공중 화장실", num);
    }

    private ArrayList<Region> getCStore(String query, int num){
        query += " 편의점";

        return makeRegionList(query, "편의점", num);
    }

    private ArrayList<Region> getInfoCenter(String query, int num){
        query += " 관광정보센터";

        return makeRegionList(query, "관광 정보 센터", num);
    }

    public ArrayList<Region> getRegionBySearch(TravelInfo info){
        final int NUM_STATION = 2;
        final int NUM_ATTRACTION = 1;
        final int NUM_FOOD = 3;
        final int NUM_TOILET = 1;
        final int NUM_CONVEN = 2;
        final int NUM_INFOCENTER = 1;

        String query = info.getMetro().getName() + " " + info.getCity().getName();
        ArrayList<Region> result = new ArrayList<Region>();

        result.addAll(getStation(query, NUM_STATION));
        result.addAll(getSubway(query, NUM_STATION));
        result.addAll(getTerminal(query, NUM_STATION));
        result.addAll(getAttraction(query, NUM_ATTRACTION));
        result.addAll(getFood(query, NUM_FOOD));
        result.addAll(getToilet(query, NUM_TOILET));
        result.addAll(getCStore(query, NUM_CONVEN));
        result.addAll(getInfoCenter(query, NUM_INFOCENTER));

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

    private Document getXMLData(String keyword){
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
