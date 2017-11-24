package pro.siberian.siberianproapplication.data;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Вараздат on 17.11.2017.
 */

public class Weather implements Serializable{

    @SerializedName("temp")
    @ColumnInfo(name = "temp")
    private double mTemperature;
    @SerializedName("temp_max")
    @ColumnInfo(name = "temp_max")
    private int mMaxTemperature;
    @SerializedName("temp_min")
    @ColumnInfo(name = "temp_min")
    private int mMinTemperature;
    @SerializedName("pressure")
    @ColumnInfo(name = "pressure")
    private int mPressure;
    @SerializedName("humidity")
    @ColumnInfo(name = "humidity")
    private int mHumidity;
    @ColumnInfo(name = "icon")
    private String mIcon;




    public Weather(double temperature, int maxTemperature, int minTemperature, int pressure,
                   int humidity) {
        mTemperature = temperature;
        mMaxTemperature = maxTemperature;
        mMinTemperature = minTemperature;
        mPressure = pressure;
        mHumidity = humidity;
    }

    public static String getDegreeCharAsString(){
        int unicode = 0x00b0;
        char degree = Character.toChars(unicode)[0];
        return String.valueOf(degree);
    }

    public double getTemperature() {
        return mTemperature;
    }

    public int getMaxTemperature() {
        return mMaxTemperature;
    }

    public int getMinTemperature() {
        return mMinTemperature;
    }

    public int getPressure() {
        return mPressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
}
