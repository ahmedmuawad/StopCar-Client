package com.stopgroup.stopcar.client.activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Closure;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Helper.HttpHelper;
import com.stopgroup.stopcar.client.Modules.Order;
import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.Choose1Adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
public class BookingConfirmationActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    private TextView locfrom;
    private TextView locto;
    private View cancel_button;
    private LinearLayout lin;
    private ProgressBar progress;
    int trip_id;
    String responseString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentActivity.setInstance(this);
        setContentView(R.layout.activity_booking_confirmation);
        trip_id = getIntent().getIntExtra("trip_id", 0);
        responseString = getIntent().getStringExtra("data");
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Type dataType = new TypeToken<Order>() {
        }.getType();
        Order data = new Gson().fromJson(responseString, dataType);
        trip_id = data.result.id;
        locto.setText(data.result.to_location);
        locfrom.setText(data.result.from_location);
        lin.setVisibility(View.VISIBLE);
        //current_order();
    }
    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        locfrom = findViewById(R.id.locfrom);
        locto = findViewById(R.id.locto);
        title.setText(getString(R.string.booking_confirmation));
        cancel_button = findViewById(R.id.cancel);
        lin = findViewById(R.id.lin);
        progress = findViewById(R.id.progress);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void canceltrip(int id) {
        HttpHelper.httpHelper.paramters.put("cancel_reason_id", id);
        Log.d("cancel_reason_id", id + "");
        Log.d("trip_id", trip_id + "");
        HttpHelper.httpHelper.get("trips/cancel/" + trip_id, new Closure<String>() {
            @Override
            public void run(String args) {
                super.run(args);
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }
    private void current_order() {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(BookingConfirmationActivity.this, "trips/client/current", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(BookingConfirmationActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        current_order();
                    }
                });
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Type dataType = new TypeToken<Order>() {
                    }.getType();
                    Order data = new Gson().fromJson(responseString, dataType);
                    locto.setText(data.result.to_location);
                    locfrom.setText(data.result.from_location);
                } catch (Exception e) {
                }
                progress.setVisibility(View.GONE);
                lin.setVisibility(View.VISIBLE);
            }
        });
    }
    public void cancel(View view) {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(BookingConfirmationActivity.this, "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(View.GONE);
                APIModel.handleFailure(BookingConfirmationActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        cancel(null);
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.setVisibility(View.GONE);
                Type dataType = new TypeToken<Settings>() {
                }.getType();
                Settings data = new Gson().fromJson(responseString, dataType);
                Dialog dialog = new Dialog(BookingConfirmationActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_choose);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = display.getWidth();
                dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                RecyclerView choose_recycler = dialog.findViewById(R.id.choose_recycler);


                dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                LinearLayoutManager layoutManage = new LinearLayoutManager(getApplicationContext());
                choose_recycler.setLayoutManager(layoutManage);
                Choose1Adapter chooseAdapter = new Choose1Adapter(BookingConfirmationActivity.this, new ArrayList<>(data.result.cancel_reasons));
                choose_recycler.setAdapter(chooseAdapter);
                dialog.findViewById(R.id.applycode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        canceltrip(chooseAdapter.id);
                    }
                });
                dialog.show();
            }
        });
    }
}
