package com.xiiilab.weather.vm.edit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

/**
 * Created by XIII-th on 27.08.2018
 */
class SaveTask extends AsyncTask<Check, Void, Boolean> {

    private final MutableLiveData<Boolean> mResult;
    private final Runnable mOnSave;

    public SaveTask(Runnable onSave) {
        mOnSave = onSave;
        mResult = new MutableLiveData<>();
    }

    public LiveData<Boolean> getResult() {
        return mResult;
    }

    @Override
    protected Boolean doInBackground(Check... checks) {
        boolean result = true;
        for (Check function : checks)
            result &= function.check();
        if (result)
            mOnSave.run();
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mResult.setValue(result);
    }
}
