package pro.siberian.siberianproapplication.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pro.siberian.siberianproapplication.data.City;
import pro.siberian.siberianproapplication.data.Weather;
import pro.siberian.siberianproapplication.data.WeatherForecast;

/**
 * Created by Вараздат on 24.11.2017.
 */

public class JsonHelper {

    public static final String NAME = "name";
    public static final String ICON = "icon";
    public static final String WEATHER = "weather";
    public static final String LIST = "list";
    public static final String DATE = "dt_txt";
    public static final String MAIN = "main";
    public static final String MESSAGE = "message";




    public static City getCity(final String weathersJson, final String forecastJson){
        String cityName = getCityName(weathersJson);
        Weather weather = getWeather(weathersJson);
        WeatherForecast forecast = getForecast(forecastJson);
        City city = new City(cityName, weather, forecast);
        return city;
    }


    private static String getCityName(String weathersJson){
        JsonElement jsonElement = new JsonParser().parse(weathersJson);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String cityName = jsonObject.get(NAME).toString().replaceAll("\"","");
        return cityName;
    }


    public static boolean isMessage(String weathersJson){
        JsonElement element = new JsonParser().parse(weathersJson);
        JsonElement messageEl = element.getAsJsonObject().get(MESSAGE);
        return messageEl == null;
    }



    private static String getIcon(String weathersJson){
        JsonElement element = new JsonParser().parse(weathersJson);
        JsonObject object = element.getAsJsonObject();
        JsonObject jsonObject = object.getAsJsonArray(WEATHER).get(0).getAsJsonObject();
        String icon = jsonObject.get(ICON).toString().replaceAll("\"","");
        return icon;
    }

    private static Weather getWeather(String weathersJson){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Weather.class, new WeatherDeserializer())
                .create();
        Weather weather = gson.fromJson(weathersJson ,Weather.class);
        String icon = getIcon(weathersJson);
        weather.setIcon(icon);
        return weather;
    }


    private static JsonArray getListArray(String forecastJson){
        JsonElement jsonElement = new JsonParser().parse(forecastJson);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(LIST);
        return jsonArray;
    }

    private static WeatherForecast getForecast(String forecastJson){
        List<Weather> forecast = new ArrayList<>();
        List<String> days = new ArrayList<>();
        List<String> months = new ArrayList<>();
        List<String> times = new ArrayList<>();

        JsonArray array = getListArray(forecastJson);
        for(JsonElement element : array){
            Weather weather = getWeatherFromMain(element);
            String dateAndTime = getDateFromDtTxt(element);
            String time  = getTime(dateAndTime);
            String month = getMonth(dateAndTime);
            String day = getDay(dateAndTime);
            String icon = getIconFromWeather(element);
            weather.setIcon(icon);
            forecast.add(weather);
            times.add(time);
            months.add(month);
            days.add(day);
        }
        WeatherForecast weatherForecast = new WeatherForecast(forecast, days, months, times);
        return weatherForecast;
    }






    private static String getDateFromDtTxt(JsonElement element){
        String date = element.getAsJsonObject().get(DATE).toString().replaceAll("\"","");
        return date;
    }

    private static Weather getWeatherFromMain(JsonElement element){
        JsonObject object = element.getAsJsonObject().getAsJsonObject(MAIN);
        Weather weather = new GsonBuilder().create().fromJson(object,Weather.class);
        return weather;
    }


    private static String getIconFromWeather(JsonElement element){
        JsonObject object = element.getAsJsonObject();
        JsonObject jsonObject = object.getAsJsonArray(WEATHER).get(0).getAsJsonObject();
        String icon = jsonObject.get(ICON).toString().replaceAll("\"","");
        return icon;
    }



    private static String getMonth(String dateAndTime){
        String fullTime = dateAndTime.split(" ")[0];
        int intMonth  = Integer.parseInt(fullTime.substring(5,7));
        String month = new SimpleDateFormat().getDateFormatSymbols().getMonths()[intMonth-1];
        Log.d("MyTag", "month = " + month);
        return  month;
    }

    private static String getDay(String dateAndTime){
        String fullTime = dateAndTime.split(" ")[0];
        String day = fullTime.substring(8,10);
        Log.d("MyTag", "day = " + day);
        return day;

    }


    private static String getTime(String dateAndTime){
        String fullTime = dateAndTime.split(" ")[1];
        String time = fullTime.substring(0,5);
        Log.d("MyTag", "time = " + time);
        return time;
    }



}
