package com.xiiilab.weather;

import android.app.Application;
import android.arch.lifecycle.ProcessLifecycleOwner;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.WeatherVmFactory;

/**
 * Created by XIII-th on 24.08.2018
 */
public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Repository.init(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(Repository.getInstance());
        WeatherVmFactory.init(this, Repository.getInstance());
    }
}
