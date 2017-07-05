package me.donnie.adapter.delegate;

import me.donnie.adapter.BaseViewHolder;

/**
 * @author donnieSky
 * @created_at 2017/7/5.
 * @description
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(BaseViewHolder holder, T t, int position);

}
