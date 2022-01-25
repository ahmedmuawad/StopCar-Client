package com.stopgroup.stopcar.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Catogaryadapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SelectcarActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    private RecyclerView list;
    private View continu;
    Catogaryadapter catogaryadapter;
    ArrayList<Settings.ResultBean.CategoriesBean> categoriesBeans = new ArrayList<>();
    private ProgressBar progress;
    public static int cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_selectcar);
         setContentView(R.layout.activity_selectcar);
        initView();
        getcatogries();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        list = findViewById(R.id.list);
        continu = findViewById(R.id.continu);
        title.setText(getString(R.string.select_car_type));
        GridLayoutManager layoutManage = new GridLayoutManager(this,2);
        list.setLayoutManager(layoutManage);
        list.setHasFixedSize(true);
        catogaryadapter = new Catogaryadapter(categoriesBeans);
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
                    Dialogs.showToast(getString(R.string.select_car_type), SelectcarActivity.this);
                } else {
                    cat_id = categoriesBeans.get(catogaryadapter.pos).id;


                    if (categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("private_car")) {
                        Intent i = new Intent(getApplicationContext(), CarOrderActivity.class);
                        i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                        i.putExtra("private", "private");
                        i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                        startActivity(i);
                    } else if (categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("tok_tok")) {
                        Intent i = new Intent(getApplicationContext(), CarOrderActivity.class);
                        i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                        i.putExtra("private", "private");
                        i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                        startActivity(i);
                    } else if (categoriesBeans.get(catogaryadapter.pos).has_sub &&
                            (categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("truck_water") || categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("truck")
                                    || categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("tanks"))) {
                        Intent i = new Intent(getApplicationContext(), SubcarActivity.class);
                        i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                        i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                        startActivity(i);
                    } else if (categoriesBeans.get(catogaryadapter.pos).has_sub && !categoriesBeans.get(catogaryadapter.pos).auto_accept &&
                            categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("companies")) {
                        Intent i = new Intent(getApplicationContext(), CompaniesActivity.class);
                        Gdata.category_id = categoriesBeans.get(catogaryadapter.pos).id + "";
                        Gdata.title = categoriesBeans.get(catogaryadapter.pos).description + "";
                        i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                        startActivity(i);
                    } else if (categoriesBeans.get(catogaryadapter.pos).has_sub && !categoriesBeans.get(catogaryadapter.pos).auto_accept &&
                            categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("companies")) {
                        Intent i = new Intent(getApplicationContext(), SendOrderActivity.class);
                        Gdata.category_id = categoriesBeans.get(catogaryadapter.pos).id + "";
                        Gdata.title = categoriesBeans.get(catogaryadapter.pos).description + "";
                        i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                        startActivity(i);
                    } else if (categoriesBeans.get(catogaryadapter.pos).calculating_pricing.equals("delivery")) {
                        if(categoriesBeans.get(catogaryadapter.pos).has_sub) {
                            Intent i = new Intent(getApplicationContext(), CarOrderActivity.class);
                            i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                            i.putExtra("private", "private");
                            i.putExtra("type", "delivery");
                            i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), CarOrderActivity.class);
                            i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                            i.putExtra("type", "delivery");
                            i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                            startActivity(i);
                        }

                    } else {
                        Intent i = new Intent(getApplicationContext(), CarOrderActivity.class);
                        i.putExtra("id", categoriesBeans.get(catogaryadapter.pos).id);
                        i.putExtra("type", "normal");
                        i.putExtra("calculating_pricing", categoriesBeans.get(catogaryadapter.pos).calculating_pricing);
                        startActivity(i);
                    }

                }
            }
        });
    }

    private void getcatogries() {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(SelectcarActivity.this, "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(SelectcarActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getcatogries();
                    }
                });

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("getcatogries", responseString);
                Log.e("555_lang", Locale.getDefault().getLanguage() + "");
                try {
                    Type dataType = new TypeToken<Settings>() {
                    }.getType();
                    Settings data = new Gson().fromJson(responseString, dataType);
                    categoriesBeans.addAll(data.result.categories);
                    catogaryadapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
