package com.xiiilab.weather.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Season;

/**
 * Created by XIII-th on 24.08.2018
 */
public class WeatherVm extends RepositoryVm {

    // master
    private final MutableLiveData<String> mSelectedCity;
    private final MutableLiveData<Season> mSelectedSeason;
    // detail
    private final LiveData<CitySize> mCitySize;
    private final LiveData<String> mMeanTemp;

    public WeatherVm() {
        mSelectedCity = new MutableLiveData<>();
        mSelectedSeason = new MutableLiveData<>();
        mCitySize = Transformations.switchMap(mSelectedCity, this::loadCitySize);
        // create mediator to get changes from city and season
        MediatorLiveData<float[]> mediator = new MediatorLiveData<>();
        mediator.addSource(mSelectedCity, this::onCityChanged);
        mediator.addSource(mSelectedSeason, this::onSeasonChanged);
        mMeanTemp = Transformations.map(mediator, this::meanTemperature);
    }

    public LiveData<String[]> getCities() {
        return getRepository().getCitiesNames();
    }

    public void setSelectedCity(String cityId) {
        mSelectedCity.setValue(cityId);
    }

    public String getSelectedCity() {
        return mSelectedCity.getValue();
    }

    public void setSelectedSeason(int order) {
        mSelectedSeason.setValue(Season.values()[order]);
    }

    public int getSelectedSeason() {
        Season season = mSelectedSeason.getValue();
        return season == null ? 0 : season.ordinal();
    }

    public LiveData<String> getMeanTemp() {
        return mMeanTemp;
    }

    public LiveData<CitySize> getCitySize() {
        return mCitySize;
    }

    private LiveData<CitySize> loadCitySize(String cityId) {
        return getRepository().getCitySize(cityId);
    }

    private LiveData<float[]> onCityChanged(String cityId) {
        return getRepository().getSeasonTemperature(cityId, mSelectedSeason.getValue());
    }

    private LiveData<float[]> onSeasonChanged(Season season) {
        return getRepository().getSeasonTemperature(mSelectedCity.getValue(), season);
    }

    private String meanTemperature(float[] temperature) {
        // TODO: apply strategy
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
