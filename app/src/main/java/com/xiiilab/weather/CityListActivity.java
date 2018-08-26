package com.xiiilab.weather;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.xiiilab.weather.list.CityListAdapter;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.CityItemVm;
import com.xiiilab.weather.vm.WeatherVmFactory;

public class CityListActivity extends AppCompatActivity implements CityListAdapter.VmSupplier {

    private byte mVmCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView list = findViewById(R.id.city_list);
        list.setAdapter(new CityListAdapter(this, Repository.getInstance().getCitiesList()));
    }

    public void onCityItemClicked(View view) {
        String cityName = (String) view.getTag();
        throw new UnsupportedOperationException("Not implemented yet " + cityName);
    }

    public void onCityAddButtonClicked(View view) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public CityItemVm get() {
        return ViewModelProviders.
                of(this, WeatherVmFactory.getInstance()).
                get(String.valueOf(mVmCounter++), CityItemVm.class);
    }
}
