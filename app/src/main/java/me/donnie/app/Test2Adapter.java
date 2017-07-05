package me.donnie.app;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.MultiItemAdapter;

/**
 * @author donnieSky
 * @created_at 2017/7/5.
 * @description
 */

public class Test2Adapter extends MultiItemAdapter<String, BaseViewHolder> {
    public Test2Adapter() {
        super(null);

        addItemViewDelegate(new ItemDefaultDelegate());
        addItemViewDelegate(new Item1Delegate());
        addItemViewDelegate(new Item2Delegate());
    }
}
