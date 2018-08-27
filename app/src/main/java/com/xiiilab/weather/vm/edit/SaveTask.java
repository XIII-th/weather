package com.xiiilab.weather.vm.edit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 27.08.2018
 */
class SaveTask extends AsyncTask<CheckFunction, Void, Boolean> {

    private final MutableLiveData<Boolean> mResult;
    private final Repository mRepository;
    private final CityEntity mCityEntity;

    public SaveTask(Repository repository, CityEntity cityEntity) {
        mRepository = repository;
        mCityEntity = cityEntity;
        mResult = new MutableLiveData<>();
    }

    public LiveData<Boolean> getResult() {
        return mResult;
    }

    @Override
    protected Boolean doInBackground(CheckFunction... checkFunctions) {
        boolean result = true;
        for (CheckFunction function : checkFunctions)
            result &= function.check(mCityEntity);
        if (result)
            mRepository.saveCity(mCityEntity);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mResult.setValue(result);
    }
}
