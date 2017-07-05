package me.donnie.app;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.delegate.ItemViewDelegate;

/**
 * @author donnieSky
 * @created_at 2017/7/5.
 * @description
 */

public class ItemDefaultDelegate implements ItemViewDelegate<String> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_item;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return !item.equals("1") && !item.equals("2") && !item.equals("3");
    }

    @Override
    public void convert(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.txt, s);
    }
}
