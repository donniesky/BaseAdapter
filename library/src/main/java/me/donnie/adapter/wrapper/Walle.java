package me.donnie.adapter.wrapper;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.donnie.adapter.BaseViewHolder;

/**
 * @author donnieSky
 * @created_at 2017/7/6.
 * @description
 */

public class Walle {

    @LayoutRes
    private int headerRes;

    @LayoutRes
    private int footerRes;

    @LayoutRes
    private int loadMoreRes;

    private boolean enableHeader;

    private boolean enableFooter;

    private boolean enableLoadMore;

    private WrapperAdapter wrapperAdapter;

    private OnRecyclerViewScrollListener mOnRecyclerViewScrollListener;

    private OnHeaderClickListener mOnHeaderClickListener;

    private Walle(Builder builder) {
        setHeaderRes(builder.headerRes);
        setFooterRes(builder.footerRes);
        setLoadMoreRes(builder.loadMoreRes);
        setEnableHeader(builder.enableHeader);
        setEnableFooter(builder.enableFooter);
        setEnableLoadMore(builder.enableLoadMore);
        setWrapperAdapter(builder.baseAdapter);
        setVisibleThreshold(builder.visibleThreshold);
        setOnLoadMoreListener(builder.onLoadMoreListener);
        setOnHeaderClickListener(builder.onHeaderClickListener);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void setHeaderRes(@LayoutRes int headerRes) {
        this.headerRes = headerRes;
    }

    public void setFooterRes(@LayoutRes int footerRes) {
        this.footerRes = footerRes;
    }

    public void setLoadMoreRes(@LayoutRes int loadMoreRes) {
        this.loadMoreRes = loadMoreRes;
    }

    public void setEnableHeader(boolean enableHeader) {
        this.enableHeader = enableHeader;
    }

    public void setEnableFooter(boolean enableFooter) {
        this.enableFooter = enableFooter;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
        if (enableLoadMore) {
            this.mOnRecyclerViewScrollListener = new OnRecyclerViewScrollListener();
        }
    }

    public void setVisibleThreshold(int visibleThreshold) {
        if (this.mOnRecyclerViewScrollListener != null) {
            this.mOnRecyclerViewScrollListener.setVisibleThreshold(visibleThreshold);
        }
    }

    public void setWrapperAdapter(RecyclerView.Adapter<BaseViewHolder> baseAdapter) {
        this.wrapperAdapter = new WrapperAdapter(baseAdapter);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if (this.mOnRecyclerViewScrollListener != null) {
            this.mOnRecyclerViewScrollListener.setOnLoadMoreListener(onLoadMoreListener);
        }
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        mOnHeaderClickListener = onHeaderClickListener;
    }

    public WrapperAdapter getWrapperAdapter() {
        return wrapperAdapter;
    }

    public void addTo(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(wrapperAdapter);
    }

    private class WrapperAdapter extends RecyclerView.Adapter<BaseViewHolder> {

        private static final int VIEW_TYPE_HEADER = -111;
        private static final int VIEW_TYPE_FOOTER = -222;
        private static final int VIEW_TYPE_LOAD_MORE = -333;

        private boolean isLoadMore = false;

        private LoadMoreView mLoadMoreView;

        public void loadComplete() {
            this.isLoadMore = false;
        }

        private final RecyclerView.Adapter<BaseViewHolder> baseAdapter;

        private WrapperAdapter(RecyclerView.Adapter<BaseViewHolder> baseAdapter) {
            assert baseAdapter != null;
            this.baseAdapter = baseAdapter;
            this.baseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    notifyItemMoved(fromPosition, toPosition);
                }
            });
        }

        private boolean isHeader(int position) {
            return enableHeader && (headerRes > 0) && position == 0 && baseAdapter.getItemCount() > 0;
        }

        private boolean isFooter(int position) {
            return enableFooter && (footerRes > 0) && position == getItemCount() - ((enableLoadMore && (loadMoreRes > 0)) ? 1 : 0) - 1 && baseAdapter.getItemCount() > 0;
        }

        private boolean isLoadMore(int position) {
            return enableLoadMore && (loadMoreRes > 0) && position == getItemCount() - 1 && baseAdapter.getItemCount() > 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return VIEW_TYPE_HEADER;
            } else if (isFooter(position)) {
                return VIEW_TYPE_FOOTER;
            } else if (isLoadMore(position)) {
                return VIEW_TYPE_LOAD_MORE;
            } else {
                return baseAdapter.getItemViewType((enableHeader && headerRes > 0) ? position - 1 : position);
            }
        }

        @Override
        public long getItemId(int position) {
            if (isHeader(position) || isFooter(position) || isLoadMore(position)) {
                return RecyclerView.NO_ID;
            } else {
                return baseAdapter.getItemId((enableHeader && headerRes > 0) ? position - 1 : position);
            }
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            baseAdapter.setHasStableIds(hasStableIds);
        }

        @Override
        public void onViewRecycled(BaseViewHolder holder) {
            baseAdapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(BaseViewHolder holder) {
            return baseAdapter.onFailedToRecycleView(holder);
        }

        @Override
        public void onViewAttachedToWindow(BaseViewHolder holder) {
            baseAdapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(BaseViewHolder holder) {
            baseAdapter.onViewDetachedFromWindow(holder);
            int viewType = holder.getItemViewType();
            if (viewType == VIEW_TYPE_HEADER || viewType == VIEW_TYPE_FOOTER || viewType == VIEW_TYPE_LOAD_MORE) {
                setFullSpan(holder);
            }
        }

        private void setFullSpan(RecyclerView.ViewHolder holder) {
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            baseAdapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            baseAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            baseAdapter.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = (GridLayoutManager) manager;
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = getItemViewType(position);
                        if (type == VIEW_TYPE_HEADER || type == VIEW_TYPE_FOOTER) {
                            return gridManager.getSpanCount();
                        }
                        return 1;
                    }
                });
            }

            if (mOnRecyclerViewScrollListener != null) {
                recyclerView.removeOnScrollListener(mOnRecyclerViewScrollListener);
                recyclerView.addOnScrollListener(mOnRecyclerViewScrollListener);
            }
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            baseAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_HEADER) {
                return BaseViewHolder.createViewHolder(parent.getContext(), parent, headerRes);
            } else if (viewType == VIEW_TYPE_FOOTER) {
                return BaseViewHolder.createViewHolder(parent.getContext(), parent, footerRes);
            } else if (viewType == VIEW_TYPE_LOAD_MORE) {
                if (mLoadMoreView == null) {
                    mLoadMoreView = new LoadMoreView(parent.getContext(), loadMoreRes);
                }
                return BaseViewHolder.createViewHolder(mLoadMoreView);
            } else {
                return baseAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            if (isHeader(position)) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnHeaderClickListener != null) {
                            mOnHeaderClickListener.onClick(view);
                        }
                    }
                });
            }
            if (!isHeader(position) && !isFooter(position) && !isLoadMore(position)) {
                baseAdapter.onBindViewHolder(holder, (enableHeader && headerRes > 0) ? position - 1 : position);
            }
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
            if (!isHeader(position) && !isFooter(position) && !isLoadMore(position)) {
                baseAdapter.onBindViewHolder(holder, (enableHeader && headerRes > 0) ? position - 1 : position, payloads);
            }
        }

        @Override
        public int getItemCount() {
            return baseAdapter.getItemCount()
                    + (baseAdapter.getItemCount() > 0 ? (
                    ((headerRes > 0 && enableHeader) ? 1 : 0)
                            + ((footerRes > 0 && enableFooter) ? 1 : 0)
                            + ((loadMoreRes > 0 && enableLoadMore) ? 1 : 0)
                    ) : 0);
        }


    }

    public static final class Builder {
        @LayoutRes
        private int headerRes;

        @LayoutRes
        private int footerRes;

        @LayoutRes
        private int loadMoreRes;

        private int visibleThreshold;

        private boolean enableHeader;
        private boolean enableFooter;
        private boolean enableLoadMore;
        private RecyclerView.Adapter<BaseViewHolder> baseAdapter;
        private OnLoadMoreListener onLoadMoreListener;
        private OnHeaderClickListener onHeaderClickListener;

        private Builder() {
        }

        public Builder headerRes(@LayoutRes int headerRes) {
            this.headerRes = headerRes;
            return this;
        }

        public Builder footerRes(@LayoutRes int footerRes) {
            this.footerRes = footerRes;
            return this;
        }

        public Builder loadmoreRes(@LayoutRes int loadMoreRes) {
            this.loadMoreRes = loadMoreRes;
            return this;
        }

        public Builder enableHeader(boolean enableHeader) {
            this.enableHeader = enableHeader;
            return this;
        }

        public Builder enableFooter(boolean enableFooter) {
            this.enableFooter = enableFooter;
            return this;
        }

        public Builder enableLoadMore(boolean enableLoadMore) {
            this.enableLoadMore = enableLoadMore;
            return this;
        }

        public Builder visiblieThreshold(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
            return this;
        }

        public Builder wrapperAdapter(RecyclerView.Adapter<BaseViewHolder> baseAdapter) {
            this.baseAdapter = baseAdapter;
            return this;
        }

        public Builder addLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
            return this;
        }

        public Builder addHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
            this.onHeaderClickListener = onHeaderClickListener;
            return this;
        }

        public Walle build() {
            return new Walle(this);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int currentPage, int totalItemCount);
    }

    public interface OnHeaderClickListener {
        void onClick(View view);
    }

}
