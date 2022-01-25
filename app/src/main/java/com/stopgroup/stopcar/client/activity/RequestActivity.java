package com.stopgroup.stopcar.client.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stopgroup.stopcar.client.Modules.Order;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Requestadapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private RecyclerView list;
    private ProgressBar progress;
    Requestadapter catogaryadapter;
    ArrayList<Order.ResultBean.RequestsBean> categoriesBeans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        list = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        title.setText(getString(R.string.requests));
        LinearLayoutManager layoutManage = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(layoutManage);
        catogaryadapter = new Requestadapter(categoriesBeans);
        list.setAdapter(catogaryadapter);
        Type dataType = new TypeToken<Order>() {
        }.getType();
        Order data = new Gson().fromJson(getIntent().getStringExtra("data"), dataType);
        categoriesBeans.addAll(data.result.requests);
    }
    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
