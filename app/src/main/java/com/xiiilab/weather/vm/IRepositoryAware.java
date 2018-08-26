package com.xiiilab.weather.vm;

import com.xiiilab.weather.persistance.Repository;

/**
 * Created by XIII-th on 24.08.2018
 */
public interface IRepositoryAware {
    void setRepository(Repository repository);
}
