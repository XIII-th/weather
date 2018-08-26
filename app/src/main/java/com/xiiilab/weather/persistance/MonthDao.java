package com.xiiilab.weather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.xiiilab.weather.Month;

/**
 * Created by XIII-th on 26.08.2018
 */
@Dao
public interface MonthDao {

    @Query("SELECT temperature FROM months WHERE city = :cityId AND month IN (:months)")
    LiveData<float[]> getSeasonTemperatures(String cityId, Month... months);
}
