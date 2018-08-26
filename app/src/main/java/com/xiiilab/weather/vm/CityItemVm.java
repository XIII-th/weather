package com.xiiilab.weather.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.persistance.CityEntity;

/**
 * Created by XIII-th on 26.08.2018
 */
public class CityItemVm extends RepositoryVm {

    private final MutableLiveData<CityEntity> mCityEntity;
    private final LiveData<String> mName;
    private final LiveData<CitySize> mSize;

    public CityItemVm() {
        mCityEntity = new MutableLiveData<>();
        mName = Transformations.map(mCityEntity, CityEntity::getName);
        mSize = Transformations.map(mCityEntity, CityEntity::getSize);
    }

    public void setCity(CityEntity city) {
        mCityEntity.setValue(city);
    }

    public LiveData<String> getName() {
        return mName;
    }

    public LiveData<CitySize> getSize() {
        return mSize;
    }

    public void onDeleteClicked() {
        getRepository().deleteCity(mCityEntity.getValue());
    }
}
