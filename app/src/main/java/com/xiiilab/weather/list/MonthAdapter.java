package com.xiiilab.weather.list;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xiiilab.weather.Month;
import com.xiiilab.weather.R;
import com.xiiilab.weather.databinding.MonthEditItemBinding;
import com.xiiilab.weather.vm.edit.MonthEditVm;

/**
 * Created by XIII-th on 27.08.2018
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthEditViewHolder> {

    private final String mCityId;
    private final VmSupplier<MonthEditVm> mVmSupplier;

    public MonthAdapter(String cityId, VmSupplier<MonthEditVm> vmSupplier) {
        mCityId = cityId;
        mVmSupplier = vmSupplier;
    }

    @NonNull
    @Override
    public MonthEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MonthEditItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.month_edit_item, parent, false);
        binding.setLifecycleOwner(mVmSupplier);
        return new MonthEditViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthEditViewHolder holder, int position) {
        Month month = Month.values()[position];
        MonthEditVm monthEditVm = mVmSupplier.get(month.name());
        monthEditVm.setMonth(mCityId, month);
        holder.binding.setMonthEditVm(monthEditVm);
    }

    @Override
    public int getItemCount() {
        return Month.values().length;
    }
}
