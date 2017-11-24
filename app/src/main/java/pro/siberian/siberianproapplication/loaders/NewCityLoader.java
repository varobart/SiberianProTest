package pro.siberian.siberianproapplication.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.net.URL;
import java.util.List;

import pro.siberian.siberianproapplication.CitiesFragment;
import pro.siberian.siberianproapplication.data.CitiesDataBase;
import pro.siberian.siberianproapplication.data.City;
import pro.siberian.siberianproapplication.utils.Query;
import pro.siberian.siberianproapplication.utils.UrlRequestParams;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class NewCityLoader extends AsyncTaskLoader<City> {

    private Context mContext;
    private String mCityName;

    public NewCityLoader(Context context, Bundle bundle) {
        super(context);
        mContext = context;
        mCityName = bundle.getString(CitiesFragment.NEW_CITY);
    }


    @Override
    public City loadInBackground() {
        if( !isCityInDB(mCityName)) {
            List<URL> weatherUrl = Query.getUrls(new String[]{mCityName}, UrlRequestParams.CURRENT_WEATHER);
            List<String> weatherJson = Query.query(weatherUrl);
            if(weatherJson.get(0) == null){
                return null;
            }
            List<URL> forecastUrl = Query.getUrls(new String[]{mCityName}, UrlRequestParams.WEATHER_FORECAST);
            List<String> forecastJson = Query.query(forecastUrl);
            final List<City> updatedList = Query.getUpdatedCities(weatherJson, forecastJson);
            insertToDB(updatedList);
            return updatedList.get(0);
        }
        return null;
    }


    protected void insertToDB(List<City> cities){
        CitiesDataBase db = CitiesDataBase.getCitiesDatabase(mContext);
        db.userDao().insertCities(cities);
    }


    private boolean isCityInDB(String cityName){
        List<City> cities;
        CitiesDataBase db = CitiesDataBase.getCitiesDatabase(mContext);
        cities = db.userDao().getCities();
        for(City city : cities){
            if(city.getCity().toLowerCase().equals(cityName.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}