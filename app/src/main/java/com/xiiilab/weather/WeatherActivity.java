package com.xiiilab.weather;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.xiiilab.weather.databinding.ActivityWeatherBinding;
import com.xiiilab.weather.vm.WeatherVm;
import com.xiiilab.weather.vm.WeatherVmFactory;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWeatherBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        setSupportActionBar(binding.toolbar);
        binding.setLifecycleOwner(this);
        WeatherVm vm = ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(WeatherVm.class);
        binding.setWeatherVm(vm);
    }

}
