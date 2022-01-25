package com.stopgroup.stopcar.client.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Helper.PaginatorData;
import com.stopgroup.stopcar.client.Modules.Companies;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.CompaniesAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CompaniesActivity extends AppCompatActivity {


    public static boolean runningResult = false;
    RecyclerView resultsRecycler;
    CompaniesAdapter resultsAdapter;
    ArrayList<Companies.ResultBean> results = new ArrayList<>();
    PaginatorData paginatorData = new PaginatorData();
    TextView titleTxv;
    ImageView backImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);


        initView();
        listeners();
    }

    public void initView() {

        resultsRecycler = findViewById(R.id.results_recycler);
        resultsAdapter = new CompaniesAdapter(this, results);
        resultsRecycler.setHasFixedSize(true);
        resultsRecycler.setAdapter(resultsAdapter);
        paginatorData.progressRel = findViewById(R.id.progrsData_layout);
        backImg = findViewById(R.id.back);
        titleTxv = findViewById(R.id.title);
        titleTxv.setText(Gdata.title);
    }

    public void listeners() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final LinearLayoutManager linearLayoutManager = new GridLayoutManager(this , 2);

         resultsRecycler.setLayoutManager(linearLayoutManager);

        resultsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                if (!paginatorData.loading && !paginatorData.empty) {
                    paginatorData.loading = true;
                    if (lastVisibleItemPosition >= totalItemCount || visibleItemCount >= totalItemCount ) {
                        Log.e("test_paginate", paginatorData.empty+"" );
                        getResults(CompaniesActivity.this, paginatorData);
                    }
                }

            }
        });


        getResults(CompaniesActivity.this, paginatorData);
    }
int page = 1 ;

    public void getResults(final Activity activity, final PaginatorData paginator) {

        paginator.progressRel.setVisibility(View.VISIBLE);

        APIModel.getMethod(activity, "driver/companies/category/"+  Gdata.category_id + "?page="+ page, new TextHttpResponseHandler() {
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
                paginator.loading = false;
                Log.e("getResultscompanies" , responseString) ;
                Type dataType = new TypeToken<Companies>() {}.getType();
                Companies response = new Gson().fromJson(responseString, dataType);
                results.addAll(response.result);
                paginator.progressRel.setVisibility(View.GONE);
                resultsAdapter.notifyDataSetChanged();
                page ++ ;
            }
        });
    }
    }

