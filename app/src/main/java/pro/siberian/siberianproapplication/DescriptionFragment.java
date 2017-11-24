package pro.siberian.siberianproapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pro.siberian.siberianproapplication.adapters.ForecastAdapter;
import pro.siberian.siberianproapplication.data.City;
import pro.siberian.siberianproapplication.data.Weather;

/**
 * Created by Вараздат on 22.11.2017.
 */

public class DescriptionFragment extends Fragment {


    public static final String CITY_DESCRIPTION = "city_description";


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForecastAdapter mForecastAdapter;
    private City mCity;
    private DividerItemDecoration mDividerItemDecoration;
    private TextView mPrimaryText;
    private TextView mSubtext;
    private TextView mTemperature;
    private TextView mPressure;
    private TextView mHumidity;
    private ImageView mWeatehrIv;
    private ActionBar mActionBar;
    private Context mContext;


    public static DescriptionFragment getInstance(City city){
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CITY_DESCRIPTION, city);
        descriptionFragment.setArguments(bundle);
        return  descriptionFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_description, container, false);
        return view;
    }


    @Override
    public void onStop(){
        super.onStop();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
        toolbarSettings();
        recyclerViewSettings();
        setTextsToViews();
        setWeatherIcon();
    }


    protected void setTextsToViews(){
        String cityName = mCity.getCity();
        mPrimaryText.setText(cityName);
        String day = String.valueOf(mCity.getDay());
        String month = mCity.getMonth();
        mSubtext.setText(day + " " + month);
        String temp = String.valueOf(mCity.getWeather().getTemperature());
        String degreeChar = Weather.getDegreeCharAsString();
        mTemperature.setText(temp + degreeChar + "C");
        String pressure = String.valueOf( mCity.getWeather().getPressure());
        mPressure.setText(" " + pressure);
        String humidity = String.valueOf( mCity.getWeather().getHumidity());
        mHumidity.setText(" " + humidity + " %");
    }

    /**
     *Sets toolbar settings
     */
    protected void toolbarSettings(){
        String title = getString(R.string.description_title);
        mActionBar.setTitle(title);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }
    /**
     *Sets recyclerview settings
     */
    protected void recyclerViewSettings(){
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager)mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mForecastAdapter);
    }

    /**
     *Sets weather icon by icon field in City instance
     */
    protected void setWeatherIcon(){
        int weatherIconId = (mContext.getResources().getIdentifier("icon" +
                mCity.getWeather().getIcon(),"drawable",mContext.getPackageName()));
        Drawable icon = ContextCompat.getDrawable(mContext, weatherIconId);
        mWeatehrIv.setImageDrawable(icon);
    }



    /**
     *Initilizes fields of fragment
     */
    protected void initialization(View view){
        mRecyclerView = view.findViewById(R.id.description_rv);
        mLayoutManager = new LinearLayoutManager(getContext());
        mCity = (City)getArguments()
                .getSerializable(CITY_DESCRIPTION);
        mActionBar = ((WeatherActivity)getActivity()).getSupportActionBar();
        mForecastAdapter = new ForecastAdapter(mCity.getWeatherForecast(), getContext());
        mPrimaryText = view.findViewById(R.id.primary_text_description);
        mSubtext = view.findViewById(R.id.subtext_description);
        mTemperature = view.findViewById(R.id.temp_description);
        mPressure = view.findViewById(R.id.pressure_description);
        mHumidity = view.findViewById(R.id.humidity_description);
        mWeatehrIv = view.findViewById(R.id.weather_icon);
        mContext = getContext();
    }



}
