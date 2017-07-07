package me.donnie.adapter.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @author donnieSky
 * @created_at 2017/7/7.
 * @description
 */

public class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final int LINEAR = 1;
    private static final int GRID = 2;
    private static final int STAGGERED = 3;

    private int mLayoutManagerType;

    private int visibleThreshold = 0;

    private int currentPage = 0;

    private int currentTotalCount = 0;

    private boolean loading = true;

    private Walle.OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(Walle.OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public int getVisibleThreshold() {
        return visibleThreshold;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0 && mOnLoadMoreListener == null) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (mLayoutManagerType == 0) {
            if (layoutManager instanceof GridLayoutManager) {
                mLayoutManagerType = GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                mLayoutManagerType = LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                mLayoutManagerType = STAGGERED;
            }
        }

        int lastVisibleItemPosition;
        switch (mLayoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case STAGGERED:
                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
                break;
            default:
                lastVisibleItemPosition = 0;
                break;
        }

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (loading && totalItemCount > currentTotalCount) {
            loading = false;
            currentTotalCount = totalItemCount;
        }

        if (!loading && (visibleItemCount > 0 && (lastVisibleItemPosition) >= totalItemCount - 1 - visibleThreshold)) {
            currentPage++;
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMore(currentPage, totalItemCount);
            }
            loading = true;
        }
    }

    public void reset() {
        this.currentPage = 0;
        this.currentTotalCount = 0;
        this.loading = true;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }
}
