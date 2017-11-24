package pro.siberian.siberianproapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pro.siberian.siberianproapplication.data.City;

public class WeatherActivity extends AppCompatActivity implements CitiesFragment.FragmentListener{


    public static final String CITIES_FRAGMENT_TAG = "cities_fragment";
    public static final String DESCRIPTION_FRAGMENT_TAG = "description_fragment";


    private Intent mServiceIntent;
    private WeatherService mWeatherService;
    private Context ctx;
    public Context getCtx() {
        return ctx;
    }
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initialization();
        toolbarSettings();
        if (!isMyServiceRunning(mWeatherService.getClass())) {
            startService(mServiceIntent);
        }
        startFragment(CITIES_FRAGMENT_TAG, null);
    }



    /**
     *Sets toolbar settings
     */
    protected void toolbarSettings(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     *Initilizes fields of activity
     */
    protected void initialization(){
        mToolbar = findViewById(R.id.toolbar);
        ctx = this;
        mWeatherService= new WeatherService(getCtx());
        mServiceIntent = new Intent(getCtx(), mWeatherService.getClass());
    }


    /**
     *Checks whether service (that updates weather) is running
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //stopping service in order to start it in broadcast receiver and thus gets infinite service
        stopService(mServiceIntent);
    }

    /**
     *Starts one of the fragments depending on TAG
     */
    protected void startFragment(String fragmentTAG,Bundle bundle){
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(fragmentTAG){
            case DESCRIPTION_FRAGMENT_TAG:
                City city = (City)bundle.getSerializable("city");
                if(fragmentManager.findFragmentByTag(DESCRIPTION_FRAGMENT_TAG) == null){
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, DescriptionFragment.getInstance(city)
                                    , DESCRIPTION_FRAGMENT_TAG)
                            .addToBackStack("TAG")
                            .commit();
                }
                break;
            case CITIES_FRAGMENT_TAG:
                if(fragmentManager.findFragmentByTag(CITIES_FRAGMENT_TAG) == null){
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, new CitiesFragment(), CITIES_FRAGMENT_TAG)
                            .commit();
                }
                break;
        }
    }

    /**
     *Opens description fragment
     */
    @Override
    public void openDescription(City city) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("city",city);
        startFragment(DESCRIPTION_FRAGMENT_TAG, bundle);
    }


}
