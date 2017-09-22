package com.example.youngju.kockockock.System.APIDatabase;

import org.jsoup.nodes.Document;

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
    public Document getXMLData(String keyword){
        String connectionURL = "https://openapi.naver.com/v1/search/local.xml?query=";

        try {
            String query = URLEncoder.encode(keyword, "UTF-8");
            connectionURL += query + "&display=10&sort=random";

            Connection connection = Jsoup.connect(connectionURL);
            connection.header()

        } catch(Exception e){
            e.printStackTrace();
        }
    }
*/
    private String getClientID(){
        return "hC0JexgRLwDeRWCwj1zb";
    }

    private String getClientSecret(){
        return "vMxtwOSODJ";
    }
}
