package pro.siberian.siberianproapplication.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pro.siberian.siberianproapplication.R;
import pro.siberian.siberianproapplication.data.Weather;
import pro.siberian.siberianproapplication.data.WeatherForecast;

/**
 * Created by Вараздат on 23.11.2017.
 */



public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>{

    public static final String DRAWABLE_RESOURCE = "drawable";
    public static final String ICON = "icon";

    private WeatherForecast mWeatherForecast;
    private Context mContext;

    public ForecastAdapter(WeatherForecast forecast, Context context){
        mWeatherForecast = forecast;
        mContext = context;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView dayOfWeekTV;
        public TextView temperatyreTV;
        public ImageView iconImV;

        public ViewHolder(View itemView){
            super(itemView);
            dayOfWeekTV = itemView.findViewById(R.id.day_of_week);
            temperatyreTV = itemView.findViewById(R.id.forecast_temp);
            iconImV = itemView.findViewById(R.id.forecast_icon);
        }
    }


    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_list_element,
                parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, final int position) {
        String date = getDate(position);
        holder.dayOfWeekTV.setText(date);
        String temp = getTemperature(position);
        holder.temperatyreTV.setText(temp);
        Drawable icon = getDrawable(position);
        holder.iconImV.setImageDrawable(icon);
    }


    private String getDate(int position){
        String month = mWeatherForecast.getMonths().get(position);
        String day = String.valueOf(mWeatherForecast.getDays().get(position));
        String time = String.valueOf(mWeatherForecast.getTimes().get(position));
        return day + " " + month + " " + time;
    }


    private String getTemperature(int position){
        String temp = String.valueOf(mWeatherForecast.getForecast().get(position).getMinTemperature())
                + "/"  + String.valueOf(mWeatherForecast.getForecast().get(position).getMaxTemperature())
                + " " + Weather.getDegreeCharAsString() + "C";
        return temp;
    }


    private int getDrawableId(int position){
        int id = mContext.getResources().getIdentifier(
                ICON + mWeatherForecast.getForecast().get(position).getIcon(),
                DRAWABLE_RESOURCE, mContext.getPackageName());
        return id;
    }


    private Drawable getDrawable(int position){
        int iconId = getDrawableId(position);
        return ContextCompat.getDrawable(mContext, iconId);
    }


    @Override
    public int getItemCount() {
        return mWeatherForecast.getForecast().size()-1;
    }
}

