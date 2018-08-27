package com.xiiilab.weather.vm.edit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.R;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.MonthEntity;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.IRepositoryAware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIII-th on 27.08.2018
 */
public class CityEditVm extends AndroidViewModel implements IRepositoryAware {

    private final MutableLiveData<String> mNameError;
    private final List<MonthEditVm> mMonthEditVmList;

    private Repository mRepository;
    private CityEntity mCityEntity;
    private SaveTask mSaveTask;

    public CityEditVm(Application application) {
        super(application);
        mNameError = new MutableLiveData<>();
        mMonthEditVmList = new ArrayList<>();
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

    public LiveData<String> getNameError() {
        return mNameError;
    }

    public void setSize(int index) {
        mCityEntity.setSize(CitySize.values()[index]);
    }

    public int getSize() {
        return mCityEntity.getSize().ordinal();
    }

    public void addMonthVm(MonthEditVm monthEditVm) {
        mMonthEditVmList.add(monthEditVm);
    }

    public LiveData<Boolean> save() {
        mSaveTask = new SaveTask(this::onSave);
        LiveData<Boolean> result = mSaveTask.getResult();
        result.observeForever(ignored -> mSaveTask = null);
        Check[] checkList = new Check[mMonthEditVmList.size() + 1];
        checkList[0] = this::checkName;
        for (int i = 1; i < checkList.length; i++)
            checkList[i] = mMonthEditVmList.get(i - 1)::validate;
        mSaveTask.execute(checkList);
        return result;
    }

    @WorkerThread
    private boolean checkName() {
        mNameError.postValue(null);
        if (getName().isEmpty()) {
            mNameError.postValue(getApplication().getString(R.string.name_must_be_not_empty));
            return false;
        }
        return true;
    }

    @WorkerThread
    private void onSave() {
        mRepository.saveCity(mCityEntity);
        for (MonthEditVm monthEditVm : mMonthEditVmList) {
            MonthEntity monthEntity = new MonthEntity(
                    monthEditVm.getMonth(), mCityEntity.getName(), monthEditVm.getTemperature());
            mRepository.saveMonth(monthEntity);
        }
    }
}
