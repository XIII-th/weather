package com.xiiilab.weather.list;

import android.support.v7.widget.RecyclerView;
import com.xiiilab.weather.databinding.MonthEditItemBinding;

/**
 * Created by XIII-th on 27.08.2018
 */
public class MonthEditViewHolder extends RecyclerView.ViewHolder {

    public final MonthEditItemBinding binding;

    public MonthEditViewHolder(MonthEditItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
