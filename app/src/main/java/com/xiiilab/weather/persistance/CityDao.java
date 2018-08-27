package com.xiiilab.weather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.xiiilab.weather.CitySize;

import java.util.List;

/**
 * Created by XIII-th on 26.08.2018
 */
@Dao
public interface CityDao {

    @Query("SELECT size FROM cities WHERE name = :cityId")
    LiveData<CitySize> getSize(String cityId);

    @Query("SELECT * FROM cities")
    LiveData<List<CityEntity>> getCities();

    @Delete
    void delete(CityEntity city);

    @Query("SELECT * FROM cities WHERE name = :cityId")
    LiveData<CityEntity> getCity(String cityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(CityEntity city);
}
