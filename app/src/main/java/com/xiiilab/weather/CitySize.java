package com.xiiilab.weather;

import android.support.annotation.StringRes;

/**
 * Created by XIII-th on 24.08.2018
 */
public enum CitySize {
    SMALL(R.string.small),
    MIDDLE(R.string.middle),
    BIG(R.string.big);

    @StringRes
    public final int labelId;

    CitySize(@StringRes int labelId) {
        this.labelId = labelId;
    }
}
