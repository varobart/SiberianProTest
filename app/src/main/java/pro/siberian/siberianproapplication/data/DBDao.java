package pro.siberian.siberianproapplication.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Вараздат on 18.11.2017.
 */

@Dao
public interface DBDao {

    @Query("UPDATE Cities SET temp = :temperature  WHERE id = :id")
    void updateCity(long id, double temperature);

    @Query("SELECT * FROM Cities")
    List<City> getCities();

    @Insert
    void insertCity(City city);

    @Insert
    void insertCities(List<City> cities);



    @Query("DELETE  FROM Cities")
    void deleteCities();


}
