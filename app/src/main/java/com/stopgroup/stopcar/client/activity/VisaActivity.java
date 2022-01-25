package com.stopgroup.stopcar.client.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.R;
import com.vinaygaba.creditcardview.CreditCardView;

import cz.msebera.android.httpclient.Header;

public class VisaActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private CreditCardView card1;
    private View next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        card1 = findViewById(R.id.card1);

        title.setText(getString(R.string.add_payment));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next = findViewById(R.id.next);
    }

    private void onclick() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("payment_method", "credit");
        requestParams.put("full_name", card1.getCardName());
        requestParams.put("card_number", card1.getCardNumber());
        requestParams.put("expire", card1.getExpiryDate());
        requestParams.put("cvv", "234");
        requestParams.put("device_token", "dd");
        APIModel.putMethod(VisaActivity.this, "client/update", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(VisaActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        register();
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i = new Intent(VisaActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}
