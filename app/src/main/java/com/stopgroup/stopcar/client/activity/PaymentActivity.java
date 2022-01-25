package com.stopgroup.stopcar.client.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Helper.IntentClass;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.R;

import cz.msebera.android.httpclient.Header;

public class PaymentActivity extends AppCompatActivity {

    private View back;
    private TextView title;
    private View paypal;
    private TextView txtpaypal;
    private View imgpaypal;
    private View visa;
    private TextView txtvisa;
    private View imgvisa;
    private View cash;
    private TextView txtcash;
    private View imgcash;
    private View next;
    String type = "";
    public boolean canBack = false;
    @Override
    public void onBackPressed() {
        if(canBack){
            super.onBackPressed();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // todo
        if (getIntent().getExtras()!=null){
            canBack = getIntent().getExtras()   .getBoolean("canBack", true);
        }

        setContentView(R.layout.activity_payment);
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
        if (!LoginSession.getlogindata(PaymentActivity.this).result.payment_method_text.equals("")) {
            title.setText(getString(R.string.payment));
            if (LoginSession.getlogindata(this).result.payment_method_text.equals("paypal")) {
                type = "paypal";
                txtpaypal.setTextColor(Color.parseColor("#353E46"));
                imgpaypal.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
                imgvisa.setVisibility(View.GONE);
            } else if (LoginSession.getlogindata(this).result.payment_method_text.equals("cash")) {
                type = "cash";
                txtcash.setTextColor(Color.parseColor("#353E46"));
                imgcash.setVisibility(View.VISIBLE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
                txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
                imgvisa.setVisibility(View.GONE);
            } else if (LoginSession.getlogindata(this).result.payment_method_text.equals("credit")) {
                type = "credit";
                txtvisa.setTextColor(Color.parseColor("#353E46"));
                imgvisa.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
            }
        }
        // todo
        if(!canBack){
            back.setVisibility(View.GONE);
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
                    Dialogs.showToast(getString(R.string.select_payment_mode), PaymentActivity.this);
                } else {
                    if (!type.equals("credit")) {
                        register();
                    } else {
                        Intent i = new Intent(PaymentActivity.this, VisaActivity.class);
                        startActivity(i);
                    }
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
                IntentClass.goToActivity(PaymentActivity.this, BankAccountActivity.class, null);
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
        RequestParams requestParams = new RequestParams();
        requestParams.put("payment_method", type);
        APIModel.putMethod(PaymentActivity.this, "client/update", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(PaymentActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        register();
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i = new Intent(PaymentActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}
