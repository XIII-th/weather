package com.xiiilab.weather.list;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;

/**
 * Created by XIII-th on 27.08.2018
 */
public interface VmSupplier<T extends ViewModel> extends LifecycleOwner {
    T get(String key);
}
