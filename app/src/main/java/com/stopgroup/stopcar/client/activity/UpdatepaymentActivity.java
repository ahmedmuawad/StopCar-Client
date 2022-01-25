package com.stopgroup.stopcar.client.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.R;

public class UpdatepaymentActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private TextView money;
    private TextView fare;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepayment);
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
        money =  findViewById(R.id.money);
        title.setText(getString(R.string.addpayment));

        String paymentType = getIntent().getStringExtra("paymentType");
        if (paymentType != null) {
            withOutUserPayment(paymentType);
        }else {
            withUserPayment();
        }

        try {
            String estimation = getIntent().getStringExtra("fareEstimation");
            int estimationInt = Integer.parseInt(estimation);
            estimationInt = estimationInt+15;
            estimation = estimation +" - " + estimationInt +" "+LoginSession.getlogindata(this).result.currency;
            money.setText(estimation);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void withUserPayment(){
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
    private void withOutUserPayment(String payment){
        if (payment.equals("paypal")) {
            type = "paypal";
            txtpaypal.setTextColor(Color.parseColor("#353E46"));
            imgpaypal.setVisibility(View.VISIBLE);
            txtcash.setTextColor(Color.parseColor("#E1E3E4"));
            imgcash.setVisibility(View.GONE);
            txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
            imgvisa.setVisibility(View.GONE);
        } else if (payment.equals("cash")) {
            type = "cash";
            txtcash.setTextColor(Color.parseColor("#353E46"));
            imgcash.setVisibility(View.VISIBLE);
            txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
            imgpaypal.setVisibility(View.GONE);
            txtvisa.setTextColor(Color.parseColor("#E1E3E4"));
            imgvisa.setVisibility(View.GONE);
        } else if (payment.equals("credit")) {
            type = "credit";
            txtvisa.setTextColor(Color.parseColor("#353E46"));
            imgvisa.setVisibility(View.VISIBLE);
            txtcash.setTextColor(Color.parseColor("#E1E3E4"));
            imgcash.setVisibility(View.GONE);
            txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
            imgpaypal.setVisibility(View.GONE);
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
                onBackPressed();
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarOrderActivity.payment = "paypal";
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
                CarOrderActivity.payment = "cash";
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
                CarOrderActivity.payment = "credit";
                txtvisa.setTextColor(Color.parseColor("#353E46"));
                imgvisa.setVisibility(View.VISIBLE);
                txtcash.setTextColor(Color.parseColor("#E1E3E4"));
                imgcash.setVisibility(View.GONE);
                txtpaypal.setTextColor(Color.parseColor("#E1E3E4"));
                imgpaypal.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
