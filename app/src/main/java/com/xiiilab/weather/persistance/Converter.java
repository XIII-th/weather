package com.xiiilab.weather.persistance;

import android.arch.persistence.room.TypeConverter;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Month;

/**
 * Created by XIII-th on 26.08.2018
 */
public class Converter {

    @TypeConverter
    public static String fromCitySize(CitySize size) {
        return size.name();
    }

    @TypeConverter
    public static CitySize toCitySize(String name) {
        return CitySize.valueOf(name);
    }

    @TypeConverter
    public static String fromMonth(Month month) {
        return month.name();
    }

    @TypeConverter
    public static Month toMonth(String name) {
        return Month.valueOf(name);
    }
}
