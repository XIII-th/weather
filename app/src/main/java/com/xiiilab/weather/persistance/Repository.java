package com.xiiilab.weather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Season;

/**
 * Created by XIII-th on 24.08.2018
 */
public class Repository {

    private static Repository INSTANCE;

    private final WeatherDatabase mDatabase;

    private Repository(Context context) {
        mDatabase = Room.databaseBuilder(context, WeatherDatabase.class, "weather.db").build();
    }

    public static void init(Context context) {
        INSTANCE = new Repository(context);
    }

    public static Repository getInstance() {
        if (INSTANCE == null)
            throw new IllegalStateException("Not initialised");
        return INSTANCE;
    }

    public LiveData<CitySize> getCitySize(String cityId) {
        return mDatabase.getCityDao().getSize(cityId);
    }

    public LiveData<float[]> getSeasonTemperature(String cityId, Season season) {
        return mDatabase.getMonthDao().getSeasonTemperatures(cityId, season.months);
    }

    public LiveData<String[]> getCitiesList() {
        return mDatabase.getCityDao().getNames();
    }
}
