package me.donnie.app;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.delegate.ItemViewDelegate;

/**
 * @author donnieSky
 * @created_at 2017/7/5.
 * @description
 */

public class Item1Delegate implements ItemViewDelegate<String> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.view_item_1;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return item.equals("2");
    }

    @Override
    public void convert(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.txt1, s);
    }
}
