package com.xiiilab.weather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.xiiilab.weather.Month;

/**
 * Created by XIII-th on 26.08.2018
 */
@Dao
public interface MonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MonthEntity month);

    @Query("SELECT temperature FROM months WHERE city = :cityId AND month IN (:months)")
    LiveData<float[]> getSeasonTemperatures(String cityId, Month... months);

    @Query("SELECT temperature FROM months WHERE city = :cityId AND month = :month")
    LiveData<Float> getTemperature(String cityId, Month month);
}
