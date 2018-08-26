package com.xiiilab.weather.persistance;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import com.xiiilab.weather.CitySize;

/**
 * Created by XIII-th on 26.08.2018
 */
@Entity(tableName = "cities", primaryKeys = "name")
public class CityEntity {

    @NonNull
    private String name;
    @NonNull
    private CitySize size;

    public CityEntity(@NonNull String name, @NonNull CitySize size) {
        this.name = name;
        this.size = size;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public CitySize getSize() {
        return size;
    }

    public void setSize(@NonNull CitySize size) {
        this.size = size;
    }
}
