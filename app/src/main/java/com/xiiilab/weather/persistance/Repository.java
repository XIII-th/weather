package com.xiiilab.weather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Season;

/**
 * Created by XIII-th on 24.08.2018
 */
public class Repository {
    public LiveData<CitySize> getCitySize(String cityId) {
        return new MutableLiveData<>();
    }

    public LiveData<float[]> getSeasonTemperature(String cityId, Season season) {
        return new MutableLiveData<>();
    }

    public LiveData<String[]> getCitiesList() {
        String[] cities = {"Moscow", "New York"};
        MutableLiveData<String[]> liveData = new MutableLiveData<>();
        liveData.setValue(cities);
        return liveData;
    }
}
