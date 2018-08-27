package com.xiiilab.weather.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.xiiilab.weather.R;
import com.xiiilab.weather.TemperatureRepresentation;
import com.xiiilab.weather.databinding.ActivityWeatherBinding;
import com.xiiilab.weather.vm.WeatherVm;
import com.xiiilab.weather.vm.WeatherVmFactory;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding mBinding;
    private WeatherVm mWeatherVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        setSupportActionBar(mBinding.toolbar);
        mBinding.setLifecycleOwner(this);
        mWeatherVm = ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(WeatherVm.class);
        mWeatherVm.setTemperatureRepresentation(TemperatureRepresentation.CELSIUS);
        mBinding.setWeatherVm(mWeatherVm);

        mWeatherVm.getMeanTemp().observe(this, this::onMeanTemperatureChanged);
    }

    public void onCityListButtonClicked(View view) {
        startActivity(new Intent(this, CityListActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    public void onRepresentationChanged(MenuItem item) {
        TemperatureRepresentation representation;
        switch (item.getItemId()) {
            case R.id.action_celsius:
                representation = TemperatureRepresentation.CELSIUS;
                break;
            case R.id.action_fahrenheit:
                representation = TemperatureRepresentation.FAHRENHEIT;
                break;
            case R.id.action_kelvin:
                representation = TemperatureRepresentation.KELVIN;
                break;
            default:
                throw new IllegalStateException("Unexpected menu item " + item.getTitle());
        }

        mWeatherVm.setTemperatureRepresentation(representation);
    }

    private void onMeanTemperatureChanged(String meanTemperature) {
        String message = getString(R.string.mean_temperature_is, meanTemperature);
        Log.d("MEAN_TEMPERATURE", message);
        Snackbar.make(mBinding.coordinator, message, Snackbar.LENGTH_SHORT).show();
    }
}
