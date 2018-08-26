package com.xiiilab.weather.persistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by XIII-th on 26.08.2018
 */
@Database(entities = {CityEntity.class, MonthEntity.class}, version = 1, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract CityDao getCityDao();

    public abstract MonthDao getMonthDao();
}
