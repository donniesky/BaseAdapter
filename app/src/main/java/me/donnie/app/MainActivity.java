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

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    private Test2Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Test2Adapter();
        rv.setAdapter(adapter);
        adapter.setData(getdatas());

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                Toast.makeText(MainActivity.this, "点击"+ position, Toast.LENGTH_SHORT).show();
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
}
