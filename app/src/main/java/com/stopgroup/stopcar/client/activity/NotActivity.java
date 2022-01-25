package com.stopgroup.stopcar.client.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.PaginatorData;
import com.stopgroup.stopcar.client.Modules.Not;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.NotAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NotActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private RecyclerView list;
    private ProgressBar progress;
    NotAdapter resultsAdapter;
    ArrayList<Not.ResultBean> results = new ArrayList<>();
    PaginatorData paginatorData = new PaginatorData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not);
        initView();
        getcatogries();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        list = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        title.setText(getString(R.string.notifications));
        LinearLayoutManager layoutManage = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(layoutManage);
        resultsAdapter = new NotAdapter(this,results);
        list.setAdapter(resultsAdapter);
    }
    private void getcatogries() {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(NotActivity.this, "client/notifications", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(NotActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getcatogries();
                    }
                });

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("gg",responseString);
                Type dataType = new TypeToken<Not>() {
                }.getType();
                Not data = new Gson().fromJson(responseString, dataType);
                results.addAll(data.result);
                resultsAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }
        });
    }
    private void onclick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
