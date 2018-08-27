package com.xiiilab.weather.vm.edit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.xiiilab.weather.Month;
import com.xiiilab.weather.R;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.IRepositoryAware;

/**
 * Created by XIII-th on 27.08.2018
 */
public class MonthEditVm extends AndroidViewModel implements IRepositoryAware {

    private final MutableLiveData<String> mError;

    private Repository mRepository;
    private Month mMonth;
    private float mTemperature;

    public MonthEditVm(@NonNull Application application) {
        super(application);
        mError = new MutableLiveData<>();
    }

    @Override
    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    public LiveData<String> getError() {
        return mError;
    }

    public Month getMonth() {
        return mMonth;
    }

    public void setMonth(String cityId, Month month) {
        mMonth = month;
        // TODO: 27.08.2018 implement more robust solution for two way data binding with liveData temperature
        LiveData<Float> monthTemperature = mRepository.getMonthTemperature(cityId, mMonth);
        monthTemperature.observeForever(new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float temperature) {
                monthTemperature.removeObserver(this);
                mTemperature = temperature == null ? 0F : temperature;
            }
        });

    }

    public void setTemperature(float temperature) {
        mTemperature = temperature;
    }

    public float getTemperature() {
        return mTemperature;
    }

    @WorkerThread
    public boolean validate() {
        if (getTemperature() > getApplication().getResources().getInteger(R.integer.max_temperature)) {
            mError.postValue(getApplication().getString(R.string.unexpected_high_temperature));
            return false;
        }
        if (getTemperature() < getApplication().getResources().getInteger(R.integer.min_temperature)) {
            mError.postValue(getApplication().getString(R.string.unexpected_low_temperature));
            return false;
        }
        mError.postValue(null);
        return true;
    }
}
