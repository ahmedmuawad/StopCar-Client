package com.stopgroup.stopcar.client.activity;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Helper.PaginatorData;
import com.stopgroup.stopcar.client.Modules.History;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.HistoryAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView resultsRecycler;
    HistoryAdapter resultsAdapter;
    ArrayList<History.ResultBean> results = new ArrayList<>();

    PaginatorData paginatorData = new PaginatorData();

    TextView titleTxv;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentActivity.setInstance(this);
        setContentView(R.layout.activity_history);

        initView();
        listeners();
    }

    public void initView() {

        resultsRecycler = findViewById(R.id.results_recycler);
        resultsAdapter = new HistoryAdapter(this, results);
        resultsRecycler.setHasFixedSize(true);
        resultsRecycler.setAdapter(resultsAdapter);

        paginatorData.progressRel = findViewById(R.id.progrsData_layout);
        backImg = findViewById(R.id.back);
        titleTxv = findViewById(R.id.title);

        titleTxv.setText(R.string.history);
    }

    public void listeners() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        resultsRecycler.setLayoutManager(linearLayoutManager);
        getResults(HistoryActivity.this, paginatorData);
    }

    public void getResults(final Activity activity, final PaginatorData paginator) {

        paginator.progressRel.setVisibility(View.VISIBLE);

        APIModel.getMethod(activity, "client/history", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getResults(activity, paginator);
                    }
                });
                paginator.progressRel.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<History>() {
                }.getType();
                History response = new Gson().fromJson(responseString, dataType);
                results.addAll(response.result);
                paginator.progressRel.setVisibility(View.GONE);
                resultsAdapter.notifyDataSetChanged();
            }
        });
    }
}

