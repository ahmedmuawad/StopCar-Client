package com.stopgroup.stopcar.client.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.R;

import cz.msebera.android.httpclient.Header;

public class ChangepaymentActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private View paypal;
    private TextView txtpaypal;
    private ImageView imgpaypal;
    private View visa;
    private TextView txtvisa;
    private ImageView imgvisa;
    private View cash;
    private TextView txtcash;
    private ImageView imgcash;
    private View next;
    String type = "";
    int trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        trip_id = getIntent().getIntExtra("trip_id", 0);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        paypal = findViewById(R.id.paypal);
        txtpaypal = findViewById(R.id.txtpaypal);
        imgpaypal = findViewById(R.id.imgpaypal);
        visa = findViewById(R.id.visa);
        txtvisa = findViewById(R.id.txtvisa);
        imgvisa = findViewById(R.id.imgvisa);
        cash = findViewById(R.id.cash);
        txtcash = findViewById(R.id.txtcash);
        imgcash = findViewById(R.id.imgcash);
        next = findViewById(R.id.next);
        title.setText(getString(R.string.addpayment));
        if (!getIntent().getStringExtra("payment").equals("")) {
            title.setText(getString(R.string.payment));
            if (getIntent().getStringExtra("payment").equals("paypal")) {
                type = "paypal";
                txtpaypal.setTextColor(Color.parseColor("#353E46"));
                imgpaypal.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
                imgvisa.setVisibility(View.GONE);
            } else if (getIntent().getStringExtra("payment").equals("cash")) {
                type = "cash";
                txtcash.setTextColor(Color.parseColor("#353E46"));
                imgcash.setVisibility(View.VISIBLE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
                txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
                imgvisa.setVisibility(View.GONE);
            } else if (getIntent().getStringExtra("payment").equals("credit")) {
                type = "credit";
                txtvisa.setTextColor(Color.parseColor("#353E46"));
                imgvisa.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
            }
        }
    }

    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("")) {
                    Dialogs.showToast(getString(R.string.select_payment_mode), ChangepaymentActivity.this);
                } else {
                    register();
                }
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "paypal";
                txtpaypal.setTextColor(Color.parseColor("#353E46"));
                imgpaypal.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
                imgvisa.setVisibility(View.GONE);
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "cash";
                txtcash.setTextColor(Color.parseColor("#353E46"));
                imgcash.setVisibility(View.VISIBLE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
                txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
                imgvisa.setVisibility(View.GONE);
            }
        });
        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "credit";
                txtvisa.setTextColor(Color.parseColor("#353E46"));
                imgvisa.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
            }
        });
    }

    private void register() {
        APIModel.getMethod(ChangepaymentActivity.this, "trips/change/payment/ " + trip_id + "?payment_method=" + type, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(ChangepaymentActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        register();
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                finish();
            }
        });
    }
}
