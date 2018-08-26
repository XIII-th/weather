package com.xiiilab.weather.list;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import com.xiiilab.weather.vm.CityItemVm;

/**
 * Created by XIII-th on 26.08.2018
 */
class CityItemViewHolder extends RecyclerView.ViewHolder {

    public final CityItemVm vm;

    public CityItemViewHolder(ViewDataBinding binding, CityItemVm cityItemVm) {
        super(binding.getRoot());
        vm = cityItemVm;
    }
}
