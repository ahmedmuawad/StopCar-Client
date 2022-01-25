package com.stopgroup.stopcar.client.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.RateTripModel;
import com.stopgroup.stopcar.client.R;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

public class ReviewTripActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private TextView price;
    private TextView locfrom;
    private TextView locto;
    private RatingBar rateBar;
    private EditText comment;
    private View needHelpBtn;
    private View rateBtn;
    private LinearLayout lin;
    private ProgressBar progress;
    RateTripModel data;
    private TextView tripType;
    private TextView tripID;
    private TextView tripdate;
    private TextView nashmiTax;
    private TextView nashmiWallet;
    private TextView billTax;
    private TextView tripfare;
    private TextView tripDistance;
    private Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_trip);
        res = getResources();
        initView();
        current_order();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        locfrom = findViewById(R.id.locfrom);
        locto = findViewById(R.id.locto);
        rateBar = findViewById(R.id.rate_bar);
        comment = findViewById(R.id.comment);
        needHelpBtn = findViewById(R.id.need_help_btn);
        rateBtn = findViewById(R.id.rate_btn);
        title.setText(getString(R.string.review_your_trip));
        lin = findViewById(R.id.lin);
        progress = findViewById(R.id.progress);

//        try{
//            String json = getIntent().getStringExtra("json");
//            Type dataType = new TypeToken<Order>() {
//            }.getType();
//            data = new Gson().fromJson(json, dataType);
//            Log.e("555555_test", json+"--" );
//
//            locfrom.setText(data.result.from_location + "");
//            locto.setText(data.result.to_location + "");
//            price.setText(data.result.total_price + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
//            progress.setVisibility(View.GONE);
//            lin.setVisibility(View.VISIBLE);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        tripType = findViewById(R.id.tripType);
        tripdate = findViewById(R.id.tripdate);
        tripID = findViewById(R.id.tripID);
        nashmiTax = findViewById(R.id.nashmiTax);
        nashmiWallet = findViewById(R.id.nashmiWallet);
        billTax = findViewById(R.id.billTax);
        tripfare = findViewById(R.id.tripfare);
        tripDistance = findViewById(R.id.trip_distance);
    }

    private void current_order() {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(ReviewTripActivity.this, "trips/client/current", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(ReviewTripActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        current_order();
                    }
                });
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("5555_tess", responseString + "");
                Type dataType = new TypeToken<RateTripModel>() {
                }.getType();
                data = new Gson().fromJson(responseString, dataType);
                tripType.setText(data.rate_last_trip.type + "");
                tripdate.setText(data.rate_last_trip.created_at + "");
                tripID.setText(data.rate_last_trip.id+"");
                tripDistance.setText(data.rate_last_trip.distance+" "+res.getString(R.string.km));
                Double total =  (data.rate_last_trip.total_price + data.rate_last_trip.wallet)  - data.rate_last_trip.discount ;
                //tripfare.setText(data.rate_last_trip.total_price + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                tripfare.setText(total + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);

                //nashmiTax.setText(data.rate_last_trip.total_price - (data.rate_last_trip.wallet + data.rate_last_trip.discount ) + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                nashmiTax.setText(data.rate_last_trip.total_price + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                try {
                    nashmiWallet.setText(data.rate_last_trip.wallet + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                }catch (Exception a) {a.printStackTrace();}

                billTax.setText(data.rate_last_trip.discount  + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                locfrom.setText(data.rate_last_trip.from_location + "");
                locto.setText(data.rate_last_trip.to_location + "");
                if( data.rate_last_trip.discount < data.rate_last_trip.total_price )
                     price.setText(total + " " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                else price.setText("0 " + LoginSession.getlogindata(ReviewTripActivity.this).result.currency);
                progress.setVisibility(View.GONE);
                lin.setVisibility(View.VISIBLE);
            }
        });
    }

    private void make_rate() {
        if (rateBar.getRating() == 0) {
            Dialogs.showToast(getString(R.string.complete_rate_data), ReviewTripActivity.this);
        } else {
            ratetrip();
        }
    }

    private void ratetrip() {
        APIModel.getMethod(ReviewTripActivity.this, String.format("client/trip/rate/%s?rate=%s&comment=%s", data.rate_last_trip.id, rateBar.getRating(), comment.getText().toString()), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(ReviewTripActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        ratetrip();
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_rate();
            }
        });
        needHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SosActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
