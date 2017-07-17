package me.donnie.adapter.wrapper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.donnie.adapter.R;

/**
 * @author donnieSky
 * @created_at 2017/7/7.
 * @description
 */

public class LoadMoreView extends FrameLayout implements ILoadMoreView {

    private ProgressBar mProgressBar;
    private TextView msg;
    private View root;

    public LoadMoreView(@NonNull Context context, @LayoutRes int loadMoreRes) {
        super(context);

        LayoutInflater.from(context).inflate(loadMoreRes, this, true);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        msg = (TextView) findViewById(R.id.msg);
        root = findViewById(R.id.root);
    }

    @Override
    public void onLoading() {
        mProgressBar.setVisibility(VISIBLE);
        msg.setText(getContext().getString(R.string.loading));
    }

    @Override
    public void onError() {
        mProgressBar.setVisibility(GONE);
        msg.setVisibility(VISIBLE);
        msg.setText(getContext().getString(R.string.error_msg));
    }

    @Override
    public void onCompleted(boolean hasMore) {
        mProgressBar.setVisibility(GONE);
        msg.setVisibility(VISIBLE);
        if (hasMore) {
            msg.setText(getContext().getString(R.string.load_complete));
        } else {
            msg.setText(getContext().getString(R.string.no_more_msg));
        }

    }

    @Override
    public void onClickLoad() {
        mProgressBar.setVisibility(GONE);
        msg.setVisibility(VISIBLE);
        msg.setText(getContext().getString(R.string.click_to_load));
    }
}
