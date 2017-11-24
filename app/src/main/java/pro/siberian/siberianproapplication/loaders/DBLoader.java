package pro.siberian.siberianproapplication.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import pro.siberian.siberianproapplication.data.CitiesDataBase;
import pro.siberian.siberianproapplication.data.City;

/**
 * Created by Вараздат on 18.11.2017.
 */

public class DBLoader extends AsyncTaskLoader<List<City>> {

    private Context mContext;

    public DBLoader(Context context, Bundle bundle) {
        super(context);
        mContext = context;
    }


    @Override
    public List<City> loadInBackground() {
        CitiesDataBase db = CitiesDataBase.getCitiesDatabase(mContext);
        List<City> cities = (ArrayList<City>) db.userDao().getCities();
        return  cities;
    }
}
