package me.donnie.app.databinding;

import me.donnie.adapter.databinding.delegate.ItemViewDelegate;
import me.donnie.app.R;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public class ItemDbDefaultDelegate implements ItemViewDelegate<StringViewModel, ViewDbItemBinding> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.view_db_item;
    }

    @Override
    public boolean isForViewType(StringViewModel item, int position) {
        return !item.getTxt().equals("2") &&
                !item.getTxt().equals("3");
    }

    @Override
    public void convert(ViewDbItemBinding binding, StringViewModel stringViewModel, int position) {
        binding.setViewModel(stringViewModel);
        binding.executePendingBindings();
    }
}
