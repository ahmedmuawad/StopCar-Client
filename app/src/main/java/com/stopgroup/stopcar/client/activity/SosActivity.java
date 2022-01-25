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
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Modules.Getcontact;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Contact1adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SosActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private RecyclerView list;
    private ProgressBar progress;
    Contact1adapter catogaryadapter;
    ArrayList<Getcontact.ResultBean> categoriesBeans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initView();
        onclick();
        make_sos();

    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        list = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        title.setText(getString(R.string.sos));
        LinearLayoutManager layoutManage = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(layoutManage);
        catogaryadapter = new Contact1adapter(categoriesBeans);
        list.setAdapter(catogaryadapter);
    }
    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void make_sos() {
        APIModel.getMethod(SosActivity.this, "client_contacts", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(SosActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        make_sos();
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Type dataType = new TypeToken<Getcontact>() {
                    }.getType();
                    Getcontact data = new Gson().fromJson(responseString, dataType);
                    categoriesBeans.addAll(data.result);
                    catogaryadapter.notifyDataSetChanged();
                } catch (Exception e) {

                }

            }
        });
    }
}
