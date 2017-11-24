package pro.siberian.siberianproapplication.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import pro.siberian.siberianproapplication.utils.StringArrayConverter;
import pro.siberian.siberianproapplication.utils.WeatherListConverter;

/**
 * Created by Вараздат on 18.11.2017.
 */


@TypeConverters({WeatherListConverter.class, StringArrayConverter.class})
@Database(entities = {City.class}, version = 1)
public abstract class CitiesDataBase extends RoomDatabase {

    private static CitiesDataBase INSTANCE;

    public abstract DBDao userDao();

    public static CitiesDataBase getCitiesDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), CitiesDataBase.class, "user-database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }
}

