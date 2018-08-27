package com.xiiilab.weather.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.xiiilab.weather.R;
import com.xiiilab.weather.databinding.ActivityEditBinding;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.WeatherVmFactory;
import com.xiiilab.weather.vm.edit.CityEditVm;

public class EditActivity extends AppCompatActivity {

    public static final String EDIT_CITY = "com.xiiilab.weather.EditActivity_EDIT_CITY";
    private CityEditVm mEditVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEditVm = ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(CityEditVm.class);

        if (!mEditVm.isInitiated()) {
            String cityId = getIntent() == null ? null : getIntent().getStringExtra(EDIT_CITY);
            if (cityId == null)
                mEditVm.setEntity(null);
            else {
                LiveData<CityEntity> liveData = Repository.getInstance().getCity(cityId);
                liveData.observe(this, mEditVm::setEntity);
            }
        }

        ActivityEditBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        binding.setLifecycleOwner(this);
        binding.setEditVm(mEditVm);
        setSupportActionBar(binding.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create menu with one option - save
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void onSaveClicked(MenuItem item) {
        mEditVm.save().observe(this, this::onSaveCompleted);
    }

    private void onSaveCompleted(Boolean success) {
        if (success)
            // changes successfully applied
            finish();
    }
}
