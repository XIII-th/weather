package com.xiiilab.weather.vm;

import android.arch.lifecycle.*;
import android.support.annotation.Nullable;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Season;
import com.xiiilab.weather.TemperatureRepresentation;
import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 24.08.2018
 */
public class WeatherVm extends RepositoryVm {

    // master
    private final MutableLiveData<String> mSelectedCity;
    private final MutableLiveData<Season> mSelectedSeason;
    private final MediatorLiveData<String[]> mCitiesNames;
    private final MutableLiveData<TemperatureRepresentation> mTemperatureRepresentation;
    // detail
    private final LiveData<CitySize> mCitySize;
    private final MediatorLiveData<String> mMeanTemp;

    public WeatherVm() {
        mSelectedCity = new MutableLiveData<>();
        mSelectedSeason = new MutableLiveData<>();
        mCitiesNames = new MediatorLiveData<>();
        mTemperatureRepresentation = new MutableLiveData<>();
        mCitySize = Transformations.switchMap(mSelectedCity, this::loadCitySize);
        // create mediator to get changes from city and season
        mMeanTemp = new MediatorLiveData<>();
        mMeanTemp.addSource(mSelectedCity, this::onCityChanged);
        mMeanTemp.addSource(mSelectedSeason, this::onSeasonChanged);
        mMeanTemp.addSource(mTemperatureRepresentation, this::onRepresentationChanged);
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

    public void setTemperatureRepresentation(TemperatureRepresentation temperatureRepresentation) {
        mTemperatureRepresentation.setValue(temperatureRepresentation);
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
        loadTemperature(cityId, mSelectedSeason.getValue());
    }

    private void onSeasonChanged(Season season) {
        loadTemperature(mSelectedCity.getValue(), season);
    }

    private void onRepresentationChanged(TemperatureRepresentation ignored) {
        loadTemperature(mSelectedCity.getValue(), mSelectedSeason.getValue());
    }

    private void loadTemperature(@Nullable String cityId, @Nullable Season season) {
        if (cityId == null || season == null)
            return;
        LiveData<float[]> seasonTemperature = getRepository().getSeasonTemperature(cityId, season);
        seasonTemperature.observeForever(new Observer<float[]>() {
            @Override
            public void onChanged(@Nullable float[] temp) {
                seasonTemperature.removeObserver(this);
                mMeanTemp.setValue(meanTemperature(temp));
            }
        });
    }

    private String meanTemperature(@Nullable float[] temperature) {
        if (temperature == null || temperature.length == 0)
            return null;
        float sum = 0F;
        for (float temp : temperature)
            sum += temp;
        sum /= temperature.length;
        TemperatureRepresentation representation = mTemperatureRepresentation.getValue();
        return representation == null ? String.valueOf(sum) : representation.toString(sum / temperature.length);
    }
}
