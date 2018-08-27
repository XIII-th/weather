package com.xiiilab.weather.list;

import android.support.v7.widget.RecyclerView;
import com.xiiilab.weather.databinding.ListItemBinding;

/**
 * Created by XIII-th on 26.08.2018
 */
class CityItemViewHolder extends RecyclerView.ViewHolder {

    public final ListItemBinding binding;

    public CityItemViewHolder(ListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
