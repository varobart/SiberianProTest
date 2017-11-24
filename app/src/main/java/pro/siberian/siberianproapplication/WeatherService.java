package pro.siberian.siberianproapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pro.siberian.siberianproapplication.data.CitiesDataBase;
import pro.siberian.siberianproapplication.data.City;
import pro.siberian.siberianproapplication.utils.Query;
import pro.siberian.siberianproapplication.utils.UrlRequestParams;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class WeatherService extends Service {

    public final static int UPDATE_INTERVAL = 1_000_000;


    private Timer timer;
    private TimerTask timerTask;

    public WeatherService(Context applicationContext) {
        super();
    }

    public WeatherService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //sends intent to br to start service after stopping => infinite service
        Intent broadcastIntent = new Intent(WeatherReceiver.SERVICE_RESTART_ACTION);
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }


    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL); //
    }

    /**
     * it sets the timer to update weather in background
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                updateWeather();
            }
        };
    }


    public List<City> getCitiesFromDB(){
        CitiesDataBase db  = CitiesDataBase.getCitiesDatabase(getApplicationContext());
        return (ArrayList<City>) db.userDao().getCities();
    }

    public void updateDB(List<City> updateList){
        CitiesDataBase db  = CitiesDataBase.getCitiesDatabase(getApplicationContext());
        db.userDao().deleteCities();
        db.userDao().insertCities(updateList);
    }

    public void updateWeather(){
        List<City> cities = getCitiesFromDB();
        List<URL> weatherUrls = Query.getUrls(cities, UrlRequestParams.CURRENT_WEATHER);
        List<String> weatherJsons = Query.query(weatherUrls);
        List<URL> forecastUrls = Query.getUrls(cities, UrlRequestParams.WEATHER_FORECAST);
        List<String> forecastJsons = Query.query(forecastUrls);
        List<City> updatedList = Query.getUpdatedCities(weatherJsons, forecastJsons);
        updateDB(updatedList);
    }


    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
