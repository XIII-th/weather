package com.xiiilab.weather.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by XIII-th on 25.08.2018
 */
public class CitySpinnerBinding {

    @BindingAdapter("city")
    @SuppressWarnings("unchecked")
    public static void setCity(Spinner spinner, String cityId) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(cityId);
        if (position != -1)
            spinner.setSelection(position, false);
    }

    @InverseBindingAdapter(attribute = "city")
    public static String getCity(Spinner spinner) {
        return (String) spinner.getSelectedItem();
    }

    @BindingAdapter("cityAttrChanged")
    public static void setInverseListener(Spinner spinner, final InverseBindingListener listener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO: empty method
            }
        });
    }
}
