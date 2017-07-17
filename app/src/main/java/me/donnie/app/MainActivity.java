package me.donnie.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.donnie.adapter.BaseAdapter;
import me.donnie.adapter.BaseViewHolder;
import me.donnie.adapter.wrapper.Walle;

public class MainActivity extends AppCompatActivity implements Walle.OnLoadMoreListener {

    private RecyclerView rv;

    private Test2Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Test2Adapter();

        Walle walle = Walle.newBuilder()
                /*.enableHeader(true)
                .headerRes(R.layout.view_header)
                .enableFooter(true)
                .footerRes(R.layout.view_footer)*/
                .enableLoadMore(true)
                .loadmoreRes(R.layout.view_load_more)
                .addLoadMoreListener(this)
                .wrapperAdapter(adapter).build();
        rv.setAdapter(walle.getWrapperAdapter());

        adapter.setData(getdatas());

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                Toast.makeText(MainActivity.this, "点击"+(position - 1), Toast.LENGTH_SHORT).show();
            }
        });
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
