package me.donnie.adapter.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.donnie.adapter.BaseViewHolder;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public abstract class BaseBindingAdapter<T, B extends ViewDataBinding, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mData;
    protected int mLayoutResId;

    protected OnItemClickListener mOnItemClickListener;

    @NonNull
    public List<T> getData() {
        return mData;
    }

    public void setData(@Nullable List<T> data) {
        mData = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        mData.addAll(position, newData);
        notifyItemRangeInserted(position, newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(@NonNull Collection<? extends T> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position < mData.size()) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    public BaseBindingAdapter(@Nullable List<T> data) {
        this(0, data);
    }

    public BaseBindingAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    public BaseBindingAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = BaseViewHolder.createViewHolder(parent.getContext(), parent, mLayoutResId);
        setListener(parent, holder, viewType);
        return (VH) holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        B binding = DataBindingUtil.bind(holder.itemView);
        convert(binding, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected abstract void convert(B binding, T t, int position);

    protected void setListener(final ViewGroup parent, final BaseViewHolder holder, int viewType) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, holder, position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, BaseViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
