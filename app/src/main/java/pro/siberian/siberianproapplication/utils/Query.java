package pro.siberian.siberianproapplication.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pro.siberian.siberianproapplication.data.City;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class Query {

    public static URL getUrl(String city, String requestType){
        UrlBuilder urlBuilder = new UrlBuilder();
        URL url = urlBuilder.requestType(requestType)
                .city(city)
                .mode(UrlRequestParams.JSON_MODE)
                .units(UrlRequestParams.METRIC_UNITS)
                .api(UrlRequestParams.API_KEY)
                .build();
        return url;
    }

    public static List<URL> getUrls(List<City> cities, String requestType){
        List<URL> urls = new ArrayList<>();
        for(City city : cities){
            String cityName = city.getCity();
            URL url = Query.getUrl(cityName, requestType);
            urls.add(url);
        }
        return urls;
    }


    public static List<URL> getUrls(String[] cities, String requestType){
        List<URL> urls = new ArrayList<>();
        for(String cityName : cities){
            URL url = Query.getUrl(cityName, requestType);
            urls.add(url);
        }
        return urls;
    }

    public static List<String> query(List<URL> urls){
        List<String> jsons = new ArrayList<>();
        for(URL url : urls){
            InputStream inputStream = Query.getJsonInputStream(url);
            String json = Query.convertStreamToString(inputStream);
            jsons.add(json);
        }
        return jsons;
    }

    public static List<City> getUpdatedCities(List<String> weatherJsons, List<String> forecsatJsons){
        List<City> cities = new ArrayList<>();
        for(int i = 0; i < weatherJsons.size() ;i++){
            City city = JsonHelper.getCity(weatherJsons.get(i), forecsatJsons.get(i));
            cities.add(city);
        }
        return cities;
    }


    public static String convertStreamToString(InputStream inputStream){
        if(inputStream == null){
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        try {
            while( (currentLine = bufferedReader.readLine()) != null ){
                stringBuilder.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static InputStream getJsonInputStream(URL url){
        InputStream inputStream = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
