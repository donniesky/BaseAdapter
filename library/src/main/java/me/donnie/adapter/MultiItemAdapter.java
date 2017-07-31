package me.donnie.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.List;

import me.donnie.adapter.delegate.ItemViewDelegate;
import me.donnie.adapter.delegate.ItemViewDelegateManager;

/**
 * @author donnieSky
 * @created_at 2017/7/5.
 * @description
 */

public abstract class MultiItemAdapter<T, VH extends BaseViewHolder> extends BaseAdapter<T, VH> {

    protected ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemAdapter(@Nullable List data) {
        super(data);
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
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
        convert(holder, mData.get(position), position);
    }

    @Override
    protected void convert(VH holder, T t, int position) {
        mItemViewDelegateManager.convert(holder, t, position);
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public BaseAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public BaseAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }
}
