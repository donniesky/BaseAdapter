package me.donnie.adapter.databinding.delegate;

import android.databinding.ViewDataBinding;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public interface ItemViewDelegate<T, B extends ViewDataBinding> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(B binding, T t, int position);

}
