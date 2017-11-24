package pro.siberian.siberianproapplication.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import pro.siberian.siberianproapplication.R;
import pro.siberian.siberianproapplication.data.City;

/**
 * Created by Вараздат on 14.11.2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{


    private List<City> mCities;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public CardsAdapter(List<City> cities, OnItemClickListener listener){
        mCities = cities;
        mOnItemClickListener = listener;
        mContext = ((Fragment)mOnItemClickListener).getContext();
    }


    public interface OnItemClickListener{
        void onItemClick(City city);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cityTV;
        public TextView temperatyreTV;
        public ImageView iconImV;

        public ViewHolder(View itemView){
            super(itemView);
            cityTV = itemView.findViewById(R.id.list_item_city);
            temperatyreTV = itemView.findViewById(R.id.list_item_temperature);
            iconImV = itemView.findViewById(R.id.list_item_icon);
        }
    }


    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element,
                parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CardsAdapter.ViewHolder holder, final int position) {
        holder.cityTV.setText(mCities.get(position).getCity());
        holder.temperatyreTV.setText(String.valueOf(mCities.get(position).getWeather().getTemperature()));
        Drawable icon = getDrawable(position);
        holder.iconImV.setImageDrawable(icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(mCities.get(position));
            }
        });
    }


    private int getDrawableId(int position){
        int id = mContext.getResources().getIdentifier(
                ForecastAdapter.ICON + mCities.get(position).getWeather().getIcon(),
                ForecastAdapter.DRAWABLE_RESOURCE, mContext.getPackageName());
        return id;
    }


    private Drawable getDrawable(int position){
        int iconId = getDrawableId(position);
        return ContextCompat.getDrawable(mContext, iconId);
    }


    public void setCities(List<City> cities) {
        mCities = cities;
    }

    public void addCity(City city){
        mCities.add(city);
        Collections.sort(mCities);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
