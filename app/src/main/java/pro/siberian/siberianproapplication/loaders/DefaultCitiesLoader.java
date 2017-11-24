package pro.siberian.siberianproapplication.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.net.URL;
import java.util.List;

import pro.siberian.siberianproapplication.data.CitiesDataBase;
import pro.siberian.siberianproapplication.data.City;
import pro.siberian.siberianproapplication.utils.DefaultCities;
import pro.siberian.siberianproapplication.utils.Query;
import pro.siberian.siberianproapplication.utils.UrlRequestParams;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class DefaultCitiesLoader extends AsyncTaskLoader<List<City>> {

    public DefaultCitiesLoader(Context context, Bundle bundle) {
        super(context);
    }

    @Override
    public List<City> loadInBackground() {
        String[] defaultCities = DefaultCities.DEFAULT_CITIES;
        List<URL> weatherUrls = Query.getUrls(defaultCities, UrlRequestParams.CURRENT_WEATHER);
        List<String> weatherJsons = Query.query(weatherUrls);
        List<URL> forecastUrls = Query.getUrls(defaultCities, UrlRequestParams.WEATHER_FORECAST);
        List<String> forecastJsons = Query.query(forecastUrls);
        final List<City> updatedList = Query.getUpdatedCities(weatherJsons, forecastJsons);
        insertToDB(updatedList);
        return  updatedList;
    }

    protected void insertToDB(List<City> cities){
        CitiesDataBase db  = CitiesDataBase.getCitiesDatabase(getContext().getApplicationContext());
        db.userDao().insertCities(cities);
    }
}
