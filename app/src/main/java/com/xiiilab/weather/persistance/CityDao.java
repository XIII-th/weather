package com.xiiilab.weather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.xiiilab.weather.CitySize;

import java.util.List;

/**
 * Created by XIII-th on 26.08.2018
 */
@Dao
public interface CityDao {

    @Query("SELECT size FROM cities WHERE name = :cityId")
    LiveData<CitySize> getSize(String cityId);

    @Query("SELECT name FROM cities")
    LiveData<String[]> getNames();

    @Query("SELECT * FROM cities")
    LiveData<List<CityEntity>> getCities();
}
