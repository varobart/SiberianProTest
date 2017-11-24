package pro.siberian.siberianproapplication.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pro.siberian.siberianproapplication.data.Weather;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class WeatherListConverter {


    public final static String SEPARATOR = "__,__";

    @TypeConverter
    public static String convertListToString(List<Weather> forecast) {
        Weather[] mForecastArray = new Weather[forecast.size()];
        for (int i = 0; i <= forecast.size()-1; i++) {
            mForecastArray[i] = forecast.get(i);
        }
        String str = "";
        Gson gson = new Gson();
        for (int i = 0; i < mForecastArray.length; i++) {
            String jsonString = gson.toJson(mForecastArray[i]);
            str = str + jsonString;
            if (i < mForecastArray.length - 1) {
                str = str + SEPARATOR;
            }
        }
        return str;
    }

    @TypeConverter
    public static List<Weather> convertStringToList(String mForecastString) {
        String[] mForecastArray = mForecastString.split(SEPARATOR);
        List<Weather> mForecasts = new ArrayList<>();
        Gson gson = new Gson();
        for (int i=0;i<mForecastArray.length-1;i++){
            mForecasts.add(gson.fromJson(mForecastArray[i] , Weather.class));
        }
        return mForecasts;
    }

}
