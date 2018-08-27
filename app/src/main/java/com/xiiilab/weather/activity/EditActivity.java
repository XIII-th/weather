package com.xiiilab.weather.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.xiiilab.weather.R;
import com.xiiilab.weather.databinding.ActivityEditBinding;
import com.xiiilab.weather.list.MonthAdapter;
import com.xiiilab.weather.list.VmSupplier;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.persistance.Repository;
import com.xiiilab.weather.vm.edit.MonthEditVm;
import com.xiiilab.weather.vm.WeatherVmFactory;
import com.xiiilab.weather.vm.edit.CityEditVm;

public class EditActivity extends AppCompatActivity implements VmSupplier<MonthEditVm> {

    public static final String EDIT_CITY = "com.xiiilab.weather.EditActivity_EDIT_CITY";
    private CityEditVm mEditVm;
    private ActivityEditBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        mBinding.setLifecycleOwner(this);

        mEditVm = ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(CityEditVm.class);

        String cityId = getIntent() == null ? null : getIntent().getStringExtra(EDIT_CITY);
        if (!mEditVm.isInitiated()) {
            if (cityId == null) {
                setBindingEntity(null);
            } else {
                LiveData<CityEntity> liveData = Repository.getInstance().getCity(cityId);
                liveData.observe(this, this::setBindingEntity);
            }
        }

        setSupportActionBar(mBinding.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView monthList = findViewById(R.id.months_list);
        monthList.setAdapter(new MonthAdapter(cityId, this));
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

    @Override
    public MonthEditVm get(String key) {
        MonthEditVm monthEditVm = ViewModelProviders.of(this, WeatherVmFactory.getInstance()).get(key, MonthEditVm.class);
        mEditVm.addMonthVm(monthEditVm);
        return monthEditVm;
    }

    private void setBindingEntity(@Nullable CityEntity entity) {
        mEditVm.setEntity(entity);
        mBinding.setEditVm(mEditVm);
    }

    private void onSaveCompleted(Boolean success) {
        if (success)
            // changes successfully applied
            finish();
    }
}
