package com.xiiilab.weather.vm;

import android.arch.lifecycle.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Season;
import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 24.08.2018
 */
public class WeatherVm extends RepositoryVm {

    // master
    private final MutableLiveData<String> mSelectedCity;
    private final MutableLiveData<Season> mSelectedSeason;
    private final MediatorLiveData<String[]> mCitiesNames;
    private final MediatorLiveData<float[]> mMeanTemperatureMediator;
    // detail
    private final LiveData<CitySize> mCitySize;
    private final LiveData<String> mMeanTemp;

    public WeatherVm() {
        mSelectedCity = new MutableLiveData<>();
        mSelectedSeason = new MutableLiveData<>();
        mCitiesNames = new MediatorLiveData<>();
        mCitySize = Transformations.switchMap(mSelectedCity, this::loadCitySize);
        // create mediator to get changes from city and season
        mMeanTemperatureMediator = new MediatorLiveData<>();
        mMeanTemperatureMediator.addSource(mSelectedCity, this::onCityChanged);
        mMeanTemperatureMediator.addSource(mSelectedSeason, this::onSeasonChanged);
        mMeanTemp = Transformations.map(mMeanTemperatureMediator, this::meanTemperature);
    }

    @Override
    public void setRepository(Repository repository) {
        super.setRepository(repository);
        mCitiesNames.addSource(getRepository().getCitiesList(), cityList -> {
            if (cityList == null)
                mCitiesNames.setValue(new String[0]);
            else {
                String[] names = new String[cityList.size()];
                for (int i = 0; i < names.length; i++)
                    names[i] = cityList.get(i).getName();
                mCitiesNames.setValue(names);
            }
        });
    }

    public LiveData<String[]> getCities() {
        return mCitiesNames;
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

    private void onCityChanged(String cityId) {
        Season season = mSelectedSeason.getValue();
        if (season != null)
            loadTemperature(cityId, season);
    }

    private void onSeasonChanged(Season season) {
        String cityId = mSelectedCity.getValue();
        if (cityId != null)
            loadTemperature(cityId, season);
    }

    private void loadTemperature(@NonNull String cityId, @NonNull Season season) {
        LiveData<float[]> seasonTemperature = getRepository().getSeasonTemperature(cityId, season);
        seasonTemperature.observeForever(new Observer<float[]>() {
            @Override
            public void onChanged(@Nullable float[] temp) {
                seasonTemperature.removeObserver(this);
                mMeanTemperatureMediator.setValue(temp);
            }
        });
    }

    private String meanTemperature(float[] temperature) {
        // TODO: apply strategy
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
