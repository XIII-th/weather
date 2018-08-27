package com.xiiilab.weather.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;
import com.xiiilab.weather.CitySize;
import com.xiiilab.weather.Month;
import com.xiiilab.weather.R;

/**
 * Created by XIII-th on 25.08.2018
 */
public class CommonBinding {

    @BindingAdapter("android:text")
    public static void getCitySizeLabel(TextView textView, CitySize citySize) {
        if (citySize == null)
            textView.setText(null);
        else {
            String[] types = textView.getResources().getStringArray(R.array.city_types);
            textView.setText(types[citySize.ordinal()]);
        }
    }

    @BindingAdapter("android:text")
    public static void getMonthLabel(TextView textView, Month month) {
        if (month == null)
            textView.setText(null);
        else {
            String[] types = textView.getResources().getStringArray(R.array.months);
            textView.setText(types[month.ordinal()]);
        }
    }

    @BindingAdapter("android:text")
    public static void setFloat(TextView textView, float f) {
        textView.setText(Float.toString(f));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getFloat(TextView textView) {
        return Float.parseFloat(textView.getText().toString());
    }
}
