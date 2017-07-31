package me.donnie.adapter.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public class BaseBindHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final B binding;

    public BaseBindHolder(@NonNull Context context,
                          @NonNull ViewGroup parent,
                          @LayoutRes int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId, parent, false));
        this.binding = DataBindingUtil.bind(itemView);
    }
}
