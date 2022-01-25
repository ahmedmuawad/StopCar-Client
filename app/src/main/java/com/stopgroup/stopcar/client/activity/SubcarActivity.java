package com.stopgroup.stopcar.client.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Modules.Subcatogary;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Subcatogaryadapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SubcarActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private RecyclerView list;
    private View continu;
    private ProgressBar progress;
    Subcatogaryadapter catogaryadapter;
    ArrayList<Subcatogary.ResultBean> categoriesBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcar);
        initView();
        onclick();
        getcatogries();
    }

    private void initView() {

        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        list = findViewById(R.id.list);
        continu = findViewById(R.id.continu);
        progress = findViewById(R.id.progress);
        title.setText(getString(R.string.select_truck_type));
        GridLayoutManager layoutManage = new GridLayoutManager(this,2);
        list.setLayoutManager(layoutManage);
        list.setHasFixedSize(true);
        catogaryadapter = new Subcatogaryadapter(categoriesBeans);
        list.setAdapter(catogaryadapter);
        progress = findViewById(R.id.progress);
    }

    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catogaryadapter.pos == -1) {
                    Dialogs.showToast(getString(R.string.select_car_type), SubcarActivity.this);
                } else {
                    if (categoriesBeans.get(catogaryadapter.pos).childs.size() > 0) {
                        String data = new Gson().toJson(categoriesBeans.get(catogaryadapter.pos).childs);
                        Intent i = new Intent(getApplicationContext(), SubwaterActivity.class);
                        i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                        i.putExtra("calculating_pricing", getIntent().getStringExtra("calculating_pricing"));
                        i.putExtra("SPECIAL_TYPE", getIntent().getExtras().getInt("id"));
                        i.putExtra("array", data);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), CarOrderActivity.class);
                        i.putExtra("subcat", categoriesBeans.get(catogaryadapter.pos).id);
                        i.putExtra("type", "normal");
                        i.putExtra("calculating_pricing", getIntent().getStringExtra("calculating_pricing"));
                        if (getIntent().getStringExtra("calculating_pricing").equals("companies")
                                || getIntent().getStringExtra("calculating_pricing").equals("truck_water")
                                || getIntent().getStringExtra("calculating_pricing").equals("tanks")) {
                            i.putExtra("SPECIAL_TYPE", getIntent().getExtras().getInt("id"));
                        }
                        startActivity(i);
                    }
                }
            }
        });
    }

    private void getcatogries() {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(SubcarActivity.this, "sub_categories/" + getIntent().getIntExtra("id", 6555), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(SubcarActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getcatogries();
                    }
                });

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<Subcatogary>() {
                }.getType();
                Subcatogary data = new Gson().fromJson(responseString, dataType);
                categoriesBeans.addAll(data.result);
                catogaryadapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }
        });
    }
}
