package pro.siberian.siberianproapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pro.siberian.siberianproapplication.adapters.CardsAdapter;
import pro.siberian.siberianproapplication.data.CitiesDataBase;
import pro.siberian.siberianproapplication.data.City;
import pro.siberian.siberianproapplication.loaders.DBLoader;
import pro.siberian.siberianproapplication.loaders.DefaultCitiesLoader;
import pro.siberian.siberianproapplication.loaders.NewCityLoader;
import pro.siberian.siberianproapplication.utils.DialogHelper;
import pro.siberian.siberianproapplication.utils.InternetManager;

/**
 * Created by Вараздат on 14.11.2017.
 */

public class CitiesFragment extends Fragment implements NewCityDialogFragment.NewCityDialogFragmentListener,
        View.OnClickListener, CardsAdapter.OnItemClickListener{

    public static final int LOADER_DEFAULT_CITIES_ID = 1;
    public static final int LOADER_DB__ID = 2;
    public static final int LOADER_NEW_CITY__ID = 3;
    public static final String NEW_CITY = "new_city";



    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardsAdapter mCardsAdapter;
    private CitiesDataBase db;
    private FloatingActionButton mFAB;
    private DialogFragment mDialogFragment;
    private List<City> mCities = new ArrayList<>();
    private DividerItemDecoration mDividerItemDecoration;
    private FragmentListener mFragmentListener;
    private ActionBar mActionBar;
    private boolean isConnected;
    private boolean isDBEmpty;





    public interface FragmentListener{
        void openDescription(City city);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_cities, container, false);
        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
        recyclerViewSettings();
        fabSettings();
        toolbarSettings();
        displayData();
    }

    /**
     *Displays data on recyclerview if DB is not empty otherwise load default list of cities and
     * then displays it
     */
    protected void displayData(){
        if(isConnected && isDBEmpty){
            putDefaultCitiesToDB();
        } else{
            loadCitiesFromDB();
        }
    }

    /**
     *Sets toolbar settings
     */
    protected void toolbarSettings(){
        String title = getString(R.string.list_title);
        mActionBar.setTitle(title);
    }

    /**
     *Sets recyclerview settings
     */
    protected void recyclerViewSettings(){
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager)mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCardsAdapter);
    }

    /**
     *Sets recyclerview settings
     */
    protected void fabSettings(){
        mFAB.setOnClickListener(this);
    }


    protected void loadCitiesFromDB(){
        getLoaderManager().initLoader(LOADER_DB__ID, null, DBLoaderCallback).forceLoad();

    }


    protected void putDefaultCitiesToDB(){
        getLoaderManager().initLoader(LOADER_DEFAULT_CITIES_ID, null, defaultCitiesLoaderCallback).forceLoad();
    }

    /**
     *Opens clicked city in description fragment
     */
    @Override
    public void onItemClick(City city) {
            mFragmentListener.openDescription(city);
        }

    /**
     *Initilizes fields of fragment
     */
    protected void initialization(View view){
        db  = CitiesDataBase.getCitiesDatabase(getContext());
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mCardsAdapter = new CardsAdapter(mCities, this);
        mFAB = view.findViewById(R.id.FAB);
        mFragmentListener = (FragmentListener) getActivity();
        isConnected = InternetManager.isInternetConn(getContext());
        isDBEmpty = db.userDao().getCities().isEmpty();
        mActionBar = ((WeatherActivity)getActivity()).getSupportActionBar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((WeatherActivity)getActivity()).getSupportActionBar()
            .getThemedContext());
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
                | MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setActionView(searchView);
        searchView.setOnQueryTextListener(searchListener);
    }


    SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            List<City> searchList = new ArrayList<>();
            for(City city : mCities){
                if(city.getCity().toLowerCase().startsWith(newText.toLowerCase())){
                    searchList.add(city);
                }
            }
            mCardsAdapter.setCities(searchList);
            mCardsAdapter.notifyDataSetChanged();
            return false;
        }
    };




    /**
     *Callback for loading new city
     */
    private LoaderManager.LoaderCallbacks<City> newCityLoaderCallback = new LoaderManager.LoaderCallbacks<City>() {
        @Override
        public Loader<City> onCreateLoader(int id, Bundle args) {
            Loader<City> loader = null;
            if (id == LOADER_NEW_CITY__ID) {
                loader = new NewCityLoader(getContext(), args);
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<City> loader, City city) {
            if(city != null) {
                mCardsAdapter.addCity(city);
                mCardsAdapter.notifyDataSetChanged();
            } else{
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle(DialogHelper.WARNING)
                        .setPositiveButton(DialogHelper.OK,
                            new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                                }
                            })
                        .setMessage(DialogHelper.CITY_ALREADY_IN)
                        .create();
                alertDialog.show();
            }
        }

        @Override
        public void onLoaderReset(Loader<City> loader) {
        }
    };

    /**
     *Callback for loading cities list from DB
     */
    private LoaderManager.LoaderCallbacks<List<City>> DBLoaderCallback = new LoaderManager.LoaderCallbacks<List<City>>() {
        @Override
        public Loader<List<City>> onCreateLoader(int id, Bundle args) {
            Loader<List<City>> loader = null;
            if (id == LOADER_DB__ID) {
                loader = new DBLoader(getContext(), args);
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<City>> loader, List<City> cities) {
            mCities = cities;
            Collections.sort(mCities);
            mCardsAdapter.setCities(mCities);
            mCardsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<City>> loader) {
        }
    };

    /**
     *Callback for loading of the list of default cities if DB is empty
     */
    private LoaderManager.LoaderCallbacks<List<City>> defaultCitiesLoaderCallback = new LoaderManager.LoaderCallbacks<List<City>>() {
        @Override
        public Loader<List<City>> onCreateLoader(int id, Bundle args) {
            Loader<List<City>> loader = null;
            if (id == LOADER_DEFAULT_CITIES_ID) {
                loader = new DefaultCitiesLoader(getContext(), args);
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<City>> loader, List<City> cities) {
            mCities = cities;
            Collections.sort(mCities);
            mCardsAdapter.setCities(mCities);
            mCardsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<City>> loader) {

        }
    };


    /**
     *Adds new city by FAB clicking and afterwards input
     */
    @Override
    public void onClick(View v) {
        if(isConnected) {
            mDialogFragment = new NewCityDialogFragment();
            mDialogFragment.setTargetFragment(this, 0);
            mDialogFragment.show(getFragmentManager(), "TAG");
        } else{
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle(DialogHelper.WARNING)
                    .setPositiveButton(DialogHelper.OK,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .setMessage(DialogHelper.NO_CONNECTION)
                    .create();
            alertDialog.show();
        }
    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        String cityName = ((NewCityDialogFragment)mDialogFragment).getCity();
        Bundle bundle = new Bundle();
        bundle.putString(NEW_CITY, cityName);
        getLoaderManager().restartLoader(LOADER_NEW_CITY__ID, bundle, newCityLoaderCallback).forceLoad();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
        dialogFragment.getDialog().cancel();
    }
}
