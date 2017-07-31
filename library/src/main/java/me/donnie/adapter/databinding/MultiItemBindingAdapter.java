package me.donnie.adapter.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.databinding.delegate.ItemViewDelegate;
import me.donnie.adapter.databinding.delegate.ItemViewDelegateManager;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public abstract class MultiItemBindingAdapter<T, VH extends BaseViewHolder> extends BaseBindingAdapter<T, ViewDataBinding, VH> {

    protected ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemBindingAdapter(@Nullable ObservableArrayList<T> data) {
        super(data);
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) {
            return super.getItemViewType(position);
        }
        return mItemViewDelegateManager.getItemViewType(mData.get(position), position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutResId = itemViewDelegate.getItemViewLayoutId();
        BaseViewHolder holder = BaseViewHolder.createViewHolder(parent.getContext(), parent, layoutResId);
        setListener(parent, holder, viewType);
        return (VH)holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
        convert(binding, mData.get(position), position);
    }

    @Override
    protected void convert(ViewDataBinding binding, T t, int position) {
        mItemViewDelegateManager.convert(binding, t, position);
    }

    /*@Override
    protected void convert(B binding, T t, int position) {
        mItemViewDelegateManager.convert(binding, t, position);
    }*/

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public BaseBindingAdapter addItemViewDelegate(ItemViewDelegate itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public BaseBindingAdapter addItemViewDelegate(int viewType, ItemViewDelegate itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }
}
