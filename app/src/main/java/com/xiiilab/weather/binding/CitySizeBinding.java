package com.xiiilab.weather.binding;

import android.databinding.BindingAdapter;
import android.widget.TextView;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.R;

/**
 * Created by XIII-th on 25.08.2018
 */
public class CitySizeBinding {

    @BindingAdapter("android:text")
    public static void getCitySizeLabel(TextView textView, CitySize citySize) {
        textView.setText(citySize == null ? R.string.loading : citySize.labelId);
    }
}
