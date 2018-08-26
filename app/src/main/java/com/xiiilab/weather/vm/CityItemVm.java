package com.xiiilab.weather.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 26.08.2018
 */
public class CityItemVm extends ViewModel implements IRepositoryAware {

    private final MutableLiveData<CityEntity> mCityEntity;
    private final LiveData<String> mName;
    private final LiveData<CitySize> mSize;

    private Repository mRepository;

    public CityItemVm() {
        mCityEntity = new MutableLiveData<>();
        mName = Transformations.map(mCityEntity, CityEntity::getName);
        mSize = Transformations.map(mCityEntity, CityEntity::getSize);
    }

    @Override
    public void setRepository(Repository repository) {
        mRepository = repository;
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

    }
}
