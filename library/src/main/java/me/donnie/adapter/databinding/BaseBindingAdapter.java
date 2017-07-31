package me.donnie.adapter.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import me.donnie.adapter.BaseViewHolder;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public abstract class BaseBindingAdapter<T, B extends ViewDataBinding, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected ObservableArrayList<T> mData;
    protected ListChangedCallback itemsChangeCallback;
    protected int mLayoutResId;

    protected OnItemClickListener mOnItemClickListener;

    @NonNull
    public ObservableArrayList<T> getData() {
        return mData;
    }

    public void setData(@Nullable ObservableArrayList<T> data) {
        mData = data == null ? new ObservableArrayList<T>() : data;
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

    public BaseBindingAdapter(@Nullable ObservableArrayList<T> data) {
        this(0, data);
    }

    public BaseBindingAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    public BaseBindingAdapter(@LayoutRes int layoutResId, @Nullable ObservableArrayList<T> data) {
        this.mData = data == null ? new ObservableArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
        this.itemsChangeCallback = new ListChangedCallback();
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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mData.addOnListChangedCallback(itemsChangeCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mData.removeOnListChangedCallback(itemsChangeCallback);
    }

    protected void resetItems(ObservableArrayList<T> newItems) {
        this.mData = newItems;
    }

    protected void onChanged(ObservableArrayList<T> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(ObservableArrayList<T> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(ObservableArrayList<T> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeMoved(ObservableArrayList<T> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeRemoved(ObservableArrayList<T> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    private class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<T>> {

        @Override
        public void onChanged(ObservableArrayList<T> sender) {
            BaseBindingAdapter.this.onChanged(sender);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<T> sender, int positionStart, int itemCount) {
            BaseBindingAdapter.this.onItemRangeChanged(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<T> sender, int positionStart, int itemCount) {
            BaseBindingAdapter.this.onItemRangeInserted(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<T> sender, int fromPosition, int toPosition, int itemCount) {
            BaseBindingAdapter.this.onItemRangeMoved(sender);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<T> sender, int positionStart, int itemCount) {
            BaseBindingAdapter.this.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, BaseViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
