package com.xiiilab.weather.list;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xiiilab.weather.R;
import com.xiiilab.weather.databinding.ListItemBinding;
import com.xiiilab.weather.persistance.CityEntity;
import com.xiiilab.weather.vm.CityItemVm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIII-th on 26.08.2018
 */
public class CityListAdapter extends RecyclerView.Adapter<CityItemViewHolder> {

    private final VmSupplier mVmSupplier;
    private final List<CityEntity> mCityEntityList;

    public CityListAdapter(VmSupplier vmSupplier, LiveData<List<CityEntity>> cities) {
        mVmSupplier = vmSupplier;
        mCityEntityList = new ArrayList<>();
        cities.observe(vmSupplier, this::update);
    }

    @NonNull
    @Override
    public CityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
        binding.setLifecycleOwner(mVmSupplier);
        return new CityItemViewHolder(binding, mVmSupplier.get());
    }

    @Override
    public void onBindViewHolder(@NonNull CityItemViewHolder holder, int position) {
        synchronized (mCityEntityList) {
            holder.vm.setCity(mCityEntityList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        synchronized (mCityEntityList) {
            return mCityEntityList.size();
        }
    }

    public interface VmSupplier extends LifecycleOwner {
        CityItemVm get();
    }

    private void update(List<CityEntity> cityEntities) {
        synchronized (mCityEntityList) {
            mCityEntityList.clear();
            mCityEntityList.addAll(cityEntities);
            // TODO: notify about certain item change
            notifyDataSetChanged();
        }
    }
}
