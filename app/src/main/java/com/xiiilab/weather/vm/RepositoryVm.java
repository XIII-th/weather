package com.xiiilab.weather.vm;

import android.arch.lifecycle.ViewModel;
import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 27.08.2018
 */
class RepositoryVm extends ViewModel implements IRepositoryAware {

    private Repository mRepository;

    @Override
    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    protected Repository getRepository() {
        return mRepository;
    }
}
