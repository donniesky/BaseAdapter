package me.donnie.app;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.databinding.BaseBindingAdapter;
import me.donnie.adapter.wrapper.Walle;
import me.donnie.app.databinding.StringViewModel;
import me.donnie.app.databinding.TextMultiDbAdapter;

public class MainActivity extends AppCompatActivity implements Walle.OnLoadMoreListener {

    private RecyclerView rv;

    //private Test2Adapter adapter;

    //private TestBindingAdapter mAdapter;

    private TextMultiDbAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //adapter = new Test2Adapter();
        mAdapter = new TextMultiDbAdapter();

        Walle walle = Walle.newBuilder()
                .enableHeader(true)
                .headerRes(R.layout.view_header)
                .enableFooter(true)
                .footerRes(R.layout.view_footer)
                .enableLoadMore(true)
                .loadmoreRes(R.layout.view_load_more)
                .addLoadMoreListener(this)
                .wrapperAdapter(mAdapter).build();
        rv.setAdapter(walle.getWrapperAdapter());
        //rv.setAdapter(mAdapter);

        mAdapter.setData(getViewModels());

        mAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                Toast.makeText(MainActivity.this, "点击"+(position - 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ObservableArrayList<StringViewModel> getViewModels() {
        ObservableArrayList<StringViewModel> models = new ObservableArrayList<>();
        for (int i = 0; i < 30; i++) {
            String txt = "";
            if (i == 2) {
                txt = ""+2;
            } else if (i == 3) {
                txt = ""+3;
            } else {
                txt = "This is Test -- "+i;
            }
            models.add(new StringViewModel(txt));
        }
        return models;
    }

    private List<String> getdatas() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30 ; i++) {
            String s = "";
            if (i == 2) {
                s = ""+2;
            } else if (i == 3) {
                s = ""+3;
            } else {
                s = "This is Test -- "+i;
            }
            data.add(s);
        }
        return data;
    }

    @Override
    public void onLoadMore(int currentPage, int totalItemCount) {
        //adapter.addData(getdatas());
        Toast.makeText(MainActivity.this,
                "currentPage = "+currentPage + " " + "totalItemCount = "+totalItemCount,
                Toast.LENGTH_SHORT).show();
    }
}
