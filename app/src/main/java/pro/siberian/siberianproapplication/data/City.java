package pro.siberian.siberianproapplication.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Вараздат on 18.11.2017.
 */



@Entity(tableName = "Cities")
public class City implements Comparable, Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "city")
    private String mCity;
    @Embedded
    private Weather mWeather;
    @Embedded
    private WeatherForecast mWeatherForecast;
    @ColumnInfo(name = "day")
    private int mDay;
    @ColumnInfo(name = "month")
    private String mMonth;




    public City(String city, Weather weather, WeatherForecast weatherForecast) {
        mCity = city;
        mWeather = weather;
        mWeatherForecast = weatherForecast;
        mMonth = getCurMonth();
        mDay = getCurDay();
    }


    private String getCurMonth(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
        String month = simpleDateFormat.format(calendar.getTime());
        return  month;
    }


    private int getCurDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return mCity.compareTo(((City)o).getCity());
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return mCity;
    }

    public Weather getWeather() {
        return mWeather;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeatherForecast getWeatherForecast() {
        return mWeatherForecast;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }
}
