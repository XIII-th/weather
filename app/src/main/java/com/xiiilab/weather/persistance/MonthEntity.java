package com.xiiilab.weather.persistance;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;
import com.xiiilab.weather.Month;

/**
 * Created by XIII-th on 26.08.2018
 */
@Entity(tableName = "months",
        foreignKeys = @ForeignKey(entity = CityEntity.class,
                parentColumns = "name", childColumns = "city", onDelete = ForeignKey.CASCADE),
        primaryKeys = {"month", "city"},
        indices = @Index(value = {"city"}))
public class MonthEntity {

    @NonNull
    private Month month;
    @NonNull
    private String city;
    private float temperature;

    public MonthEntity(@NonNull Month month, @NonNull String city, float temperature) {
        this.month = month;
        this.city = city;
        this.temperature = temperature;
    }

    @NonNull
    public Month getMonth() {
        return month;
    }

    public void setMonth(@NonNull Month month) {
        this.month = month;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
