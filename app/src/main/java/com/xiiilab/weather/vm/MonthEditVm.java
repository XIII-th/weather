package com.xiiilab.weather.vm;

import android.arch.lifecycle.ViewModel;
import com.xiiilab.weather.Month;

/**
 * Created by XIII-th on 27.08.2018
 */
public class MonthEditVm extends ViewModel {

    private Month mMonth;
    private float mTemperature;

    public Month getMonth() {
        return mMonth;
    }

    public void setMonth(Month month) {
        mMonth = month;
    }

    public void setTemperature(float temperature) {
        mTemperature = temperature;
    }

    public float getTemperature() {
        return mTemperature;
    }
}
