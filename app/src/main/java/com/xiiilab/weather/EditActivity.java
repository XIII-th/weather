package com.xiiilab.weather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.xiiilab.weather.databinding.ActivityEditBinding;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.CityEditVm;
import com.xiiilab.weather.vm.WeatherVmFactory;

public class EditActivity extends AppCompatActivity {

    public static final String EDIT_CITY = "com.xiiilab.weather.EditActivity_EDIT_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CityEditVm editVm = ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(CityEditVm.class);
        Intent intent = getIntent();
        if (!editVm.isInitiated()) {
            if (intent == null)
                editVm.setEntity(null);
            else {
                LiveData<CityEntity> liveData = Repository.getInstance().getCity(intent.getStringExtra(EDIT_CITY));
                liveData.observe(this, editVm::setEntity);
            }
        }

        ActivityEditBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        binding.setLifecycleOwner(this);
        binding.setEditVm(editVm);
        setSupportActionBar(binding.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

    }

}
