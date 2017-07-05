package me.donnie.app;


import me.donnie.adapter.BaseAdapter;
import me.donnie.adapter.BaseViewHolder;

/**
 * @author donnieSky
 * @created_at 2017/7/5.
 * @description
 */

public class TestAdapter extends BaseAdapter<String, BaseViewHolder> {

    public TestAdapter() {
        super(R.layout.list_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.txt, s);
    }
}
