package com.xiiilab.weather.vm.edit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.R;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.IRepositoryAware;

/**
 * Created by XIII-th on 27.08.2018
 */
public class CityEditVm extends AndroidViewModel implements IRepositoryAware {

    private final MutableLiveData<String> mNameError;

    private Repository mRepository;
    private CityEntity mCityEntity;
    private SaveTask mSaveTask;

    public CityEditVm(Application application) {
        super(application);
        mNameError = new MutableLiveData<>();
    }

    @Override
    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    public void setEntity(@Nullable CityEntity entity) {
        if (entity == null)
            mCityEntity = new CityEntity("", CitySize.SMALL);
        else
            mCityEntity = entity;
    }

    public boolean isInitiated() {
        return mCityEntity != null;
    }

    public void setName(String name) {
        mCityEntity.setName(name.trim());
    }

    public String getName() {
        return mCityEntity.getName();
    }

    public void setSize(int index) {
        mCityEntity.setSize(CitySize.values()[index]);
    }

    public int getSize() {
        return mCityEntity.getSize().ordinal();
    }

    public LiveData<Boolean> save() {
        mSaveTask = new SaveTask(mRepository, mCityEntity);
        LiveData<Boolean> result = mSaveTask.getResult();
        result.observeForever(ignored -> mSaveTask = null);
        mSaveTask.execute(this::checkName);
        return result;
    }

    private boolean checkName(CityEntity entity) {
        mNameError.postValue(null);
        if (entity.getName().isEmpty()) {
            mNameError.postValue(getApplication().getString(R.string.name_must_be_not_empty));
            return false;
        }
        return true;
    }
}
