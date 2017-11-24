package pro.siberian.siberianproapplication.data;

import android.arch.persistence.room.ColumnInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class WeatherForecast implements Serializable {

    @ColumnInfo(name = "forecast")
    private List<Weather> mForecast = new ArrayList<>();
    @ColumnInfo(name = "days")
    private List<String> mDays = new ArrayList<>();
    @ColumnInfo(name = "months")
    private List<String> mMonths = new ArrayList<>();
    @ColumnInfo(name = "times")
    private List<String> mTimes = new ArrayList<>();


    public WeatherForecast(List<Weather> forecast, List<String> days, List<String> months, List<String> times ) {
        mForecast = forecast;
        mDays = days;
        mMonths = months;
        mTimes = times;
    }

    public List<Weather> getForecast() {
        return mForecast;
    }

    public List<String> getDays() {
        return mDays;
    }

    public void setDays(List<String> day) {
        mDays = day;
    }

    public List<String> getMonths() {
        return mMonths;
    }

    public void setMonths(List<String> month) {
        mMonths = month;
    }

    public List<String> getTimes() {
        return mTimes;
    }
}
