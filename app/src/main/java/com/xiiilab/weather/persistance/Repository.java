package com.xiiilab.weather.persistance;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Season;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by XIII-th on 24.08.2018
 */
public class Repository implements LifecycleObserver {

    private static Repository INSTANCE;

    private final WeatherDatabase mDatabase;
    private final ExecutorService mExecutor;

    private Repository(Context context) {
        mDatabase = Room.databaseBuilder(context, WeatherDatabase.class, "weather.db").build();
        mExecutor = Executors.newSingleThreadExecutor();
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

    public LiveData<List<CityEntity>> getCitiesList() {
        return mDatabase.getCityDao().getCities();
    }

    public void deleteCity(final CityEntity city) {
        mExecutor.execute(() -> mDatabase.getCityDao().delete(city));
    }

    public LiveData<CityEntity> getCity(String cityId) {
        return mDatabase.getCityDao().getCity(cityId);
    }

    public void saveCity(CityEntity city) {
        mDatabase.getCityDao().save(city);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void stopExecutorService() {
        mExecutor.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!mExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                mExecutor.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!mExecutor.awaitTermination(60, TimeUnit.SECONDS))
                    Log.e(getClass().getName(), "Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            mExecutor.shutdownNow();
        }
    }
}
