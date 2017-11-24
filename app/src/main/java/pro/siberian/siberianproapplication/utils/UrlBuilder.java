package pro.siberian.siberianproapplication.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Вараздат on 18.11.2017.
 */

public class UrlBuilder {

    private String mRequestType;
    private String mUnits;
    private String mMode;
    private String mAPI;
    private String mCity;


    public UrlBuilder requestType(String requestType){
        mRequestType = requestType;
        return this;
    }

    public UrlBuilder units(String units){
        mUnits = units;
        return this;
    }

    public UrlBuilder mode(String mode){
        mMode = mode;
        return this;
    }

    public UrlBuilder api(String api){
        mAPI = api;
        return this;
    }

    public UrlBuilder city(String city){
        mCity = city.replaceAll(" ", "+").replaceAll("\"" , "");
        return this;
    }

    public URL build(){
        String urlString;
        URL url = null;
        urlString = UrlRequestParams.BASE_URL + mRequestType + "?q=" + mCity + "&units=" + mUnits
        + "&type=" + UrlRequestParams.ACCURATE_REQUEST + "&mode=" + mMode + "&APPID=" + mAPI;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }




}
