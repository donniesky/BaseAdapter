package me.donnie.app.databinding;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.databinding.MultiItemBindingAdapter;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public class TextMultiDbAdapter extends MultiItemBindingAdapter<StringViewModel, BaseViewHolder> {

    public TextMultiDbAdapter() {
        super(null);

        addItemViewDelegate(new ItemDbDefaultDelegate());
        addItemViewDelegate(new ItemDb1Delegate());
        addItemViewDelegate(new ItemDb2Delegate());
    }
}
