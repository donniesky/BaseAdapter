package me.donnie.app.databinding;

import me.donnie.adapter.databinding.delegate.ItemViewDelegate;
import me.donnie.app.R;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public class ItemDb2Delegate implements ItemViewDelegate<StringViewModel, me.donnie.app.databinding.ViewDbItem2Binding> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.view_db_item_2;
    }

    @Override
    public boolean isForViewType(StringViewModel item, int position) {
        return item.getTxt().equals("3");
    }

    @Override
    public void convert(me.donnie.app.databinding.ViewDbItem2Binding binding, StringViewModel stringViewModel, int position) {
        binding.setViewModel(stringViewModel);
        binding.executePendingBindings();
    }
}
