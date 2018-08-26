package com.xiiilab.weather.vm;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 24.08.2018
 */
public class WeatherVmFactory implements ViewModelProvider.Factory {

    private static WeatherVmFactory INSTANCE;

    private final Application mApplication;
    private final Repository mRepository;

    private WeatherVmFactory(Application application, Repository repository) {
        mApplication = application;
        mRepository = repository;
    }

    @MainThread
    public static void init(@NonNull Application app, @NonNull Repository db) {
        INSTANCE = new WeatherVmFactory(app, db);
    }

    public static WeatherVmFactory getInstance() {
        if (INSTANCE == null)
            throw new IllegalStateException("Not initialised");
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication);
        T vm = factory.create(modelClass);
        if (vm instanceof IRepositoryAware)
            ((IRepositoryAware)vm).setRepository(mRepository);
        return vm;
    }
}
