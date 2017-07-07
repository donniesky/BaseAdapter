package me.donnie.adapter.wrapper;

/**
 * @author donnieSky
 * @created_at 2017/7/7.
 * @description
 */

public interface ILoadMoreView {

    void onLoading();

    void onError();

    void onCompleted(boolean hasMore);

    void onClickLoad();

}
