package com.xiiilab.weather.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.xiiilab.weather.R;
import com.xiiilab.weather.list.CityListAdapter;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.CityItemVm;
import com.xiiilab.weather.vm.WeatherVmFactory;

public class CityListActivity extends AppCompatActivity implements CityListAdapter.VmSupplier {

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
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EditActivity.EDIT_CITY, cityName);
        startActivity(intent);
    }

    public void onCityAddButtonClicked(View view) {
        startActivity(new Intent(this, EditActivity.class));
    }

    @Override
    public CityItemVm get(String key) {
        return ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(key, CityItemVm.class);
    }
}
