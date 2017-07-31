package me.donnie.app.databinding;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.databinding.BaseBindingAdapter;
import me.donnie.app.R;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public class TestBindingAdapter extends BaseBindingAdapter<StringViewModel, ViewDbItemBinding, BaseViewHolder> {

    public TestBindingAdapter() {
        super(R.layout.view_db_item);
    }

    /*@Override
    protected void convert(BaseBindHolder<ViewDbItemBinding> holder, StringViewModel model, int position) {
        StringViewModel viewModel = getItem(position);
        ViewDbItemBinding itemBinding = holder.binding;
        itemBinding.setViewModel(viewModel);
        itemBinding.executePendingBindings();
    }*/

    @Override
    protected void convert(ViewDbItemBinding binding, StringViewModel stringViewModel, int position) {
        binding.setViewModel(stringViewModel);
        binding.executePendingBindings();
    }
}
