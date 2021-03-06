package com.stopgroup.stopcar.client.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Modules.ConfigResponse;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Adapter_Viewpager;
import com.vistrav.ask.Ask;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

public class AdActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView title;
    private TextView desc;
    private View login;
    private View register;
    private ProgressBar pb;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
        getAds();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        pb = findViewById(R.id.pb);
        back = findViewById(R.id.back);

        Ask.on(this)
                .forPermissions(Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.CALL_PHONE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                      //  , Manifest.permission.SEND_SMS
                        , Manifest.permission.CAMERA
                        , Manifest.permission.READ_CONTACTS)
                .withRationales("Call permission need for call",
                        "In order to save file you will need to grant storage permission"
                        , "In order to Read file you will need to grant storage permission"
                        , "allow access location"
                        , "allow access GET ACCOUNTS"
                        , "allow access location"
                        , "allow access network"
                ) //optional
                .go();
        title.setText(R.string.add_payment);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdActivity.this, RegActivity.class);
                startActivity(i);
            }
        });

    }


    void getAds() {
        APIModel.getMethod(AdActivity.this, "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(AdActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getAds();
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {
                    Type dataType = new TypeToken<ConfigResponse>() {
                    }.getType();
                    ConfigResponse data = new Gson().fromJson(responseString, dataType);

                    desc.setText(data.result.config.intro);
                    Adapter_Viewpager adp = new Adapter_Viewpager(AdActivity.this, data.result.sliders);
                    viewPager.setAdapter(adp);


                } catch (JsonSyntaxException a) {
                    a.printStackTrace();
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pb.setVisibility(View.GONE);
            }
        });
    }
}
