package com.xiiilab.weather.vm;

import android.support.annotation.Nullable;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.persistance.CityEntity;

/**
 * Created by XIII-th on 27.08.2018
 */
public class CityEditVm extends RepositoryVm {

    private CityEntity mCityEntity;

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
        mCityEntity.setName(name);
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
}
