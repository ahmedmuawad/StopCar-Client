package com.stopgroup.stopcar.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Fragment.DialogSendOrderFragment;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.R;

import cz.msebera.android.httpclient.Header;


public class SendOrderActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private ImageView image;
    private TextView from;
    private TextView to;
    private EditText typeOfGoods;
    private EditText notes;
    private View send;
    private ProgressBar progressBar;
    private EditText reassemble;
    String sFrom, sTo;
    String  from_lng, to_lng, company_id, sub_category_id, trip_special_description, trip_special_reassemble, trip_special_note, cancel_reason_id;
   String from_lat = "" ;
   String to_lat = "" ;


    private static final int PLACE_PICKER_REQUEST_FROM = 1;
    private static final int PLACE_PICKER_REQUEST_To = 2;
//    static  LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
//            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    static  LatLngBounds BOUNDS_MOUNTAIN_VIEW ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);
        initView();

        double radiusDegrees = 1.0;
        LatLng center = new LatLng(24.774265,46.738586) ;
        LatLng northEast = new LatLng(center.latitude + radiusDegrees, center.longitude + radiusDegrees);
        LatLng southWest = new LatLng(center.latitude - radiusDegrees, center.longitude - radiusDegrees);
        BOUNDS_MOUNTAIN_VIEW = LatLngBounds.builder()
                .include(northEast)
                .include(southWest)
                .build();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        image = findViewById(R.id.image);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        typeOfGoods = findViewById(R.id.type_of_goods);
        notes = findViewById(R.id.notes);
        send = findViewById(R.id.send);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        reassemble = findViewById(R.id.reassemble);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(Gdata.CompaniesResultBean == null || Gdata.CompaniesResultBean.company_name.equals("")){
            title.setText("");
            reassemble.setVisibility(View.VISIBLE);
            Log.e("555_ttttt", company_id+"");
        }else{
            title.setText(Gdata.CompaniesResultBean.company_name);
            reassemble.setVisibility(View.GONE);
            Log.e("555_ttttt_2222", company_id+"" );
        }



        if(Gdata.category_id .equals("4")){
            reassemble.setVisibility(View.GONE);
        }
else {
            reassemble.setVisibility(View.VISIBLE);

        }
        try {
            Picasso.get().load(Gdata.CompaniesResultBean.company_img).into(image);
        } catch (Exception e) {
        }
            onClick();
        }

        private void onClick() {

            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(SendOrderActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST_FROM);


                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(SendOrderActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST_To);


                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String categoryID = Gdata.category_id;
                if( Gdata.CompaniesResultBean != null &&  Gdata.CompaniesResultBean.id > 0){
                    company_id = Gdata.CompaniesResultBean.id + "";
                }else{
                    company_id = "";
                }
                sub_category_id = Gdata.sub_category_id + "";
                 trip_special_description = typeOfGoods.getText().toString() + "";
                trip_special_note = notes.getText().toString() + "";

                if (from_lat.equals("") || to_lat.equals("")
                        || trip_special_description.equals("")  || trip_special_note.equals("")) {


                    Toast.makeText(SendOrderActivity.this, getString(R.string.filedComplete), Toast.LENGTH_SHORT).show();

                } else {

                    RequestParams requestParams = new RequestParams();
                    if(categoryID.equals("3")){
                        requestParams.put("company_id", company_id);
                        requestParams.put("trip_special_reassemble", reassemble.getText().toString());
                    }
                    requestParams.put("category_id", categoryID);
                    requestParams.put("from_lat", from_lat);
                    requestParams.put("from_lng", from_lng);
                    requestParams.put("to_lat", to_lat);
                    requestParams.put("to_lng", to_lng);
                    requestParams.put("sub_category_id", sub_category_id);
                    requestParams.put("trip_special_description", trip_special_description);
                    requestParams.put("trip_special_reassemble", trip_special_reassemble);
                    requestParams.put("trip_special_note", trip_special_note);
                    requestParams.put("cancel_reason_id", cancel_reason_id);
                    makeorder(requestParams);
                    Log.e("requestParams" , requestParams.toString()) ;
                }

            }
        });
    }

    void makeorder(final RequestParams requestParams) {
        progressBar.setVisibility(View.VISIBLE);
        APIModel.postMethod(SendOrderActivity.this, "trips/request/company", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(SendOrderActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        makeorder(requestParams);
                    }
                });

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progressBar.setVisibility(View.GONE);

                Log.e("makeorderx", responseString);
                DialogSendOrderFragment dialogSendOrderFragment = new DialogSendOrderFragment();
                dialogSendOrderFragment.show(getFragmentManager(), "");
            }
        });
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST_FROM
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();

            if (attributions == null) {
                attributions = "";
            }

            sFrom =  address.toString() ;
            from.setText(sFrom) ;
            from_lat = place.getLatLng().latitude + "";
            from_lng = place.getLatLng().longitude + "";
        } else if (requestCode == PLACE_PICKER_REQUEST_To
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();

            String attributions = (String) place.getAttributions();

            sTo =  address.toString() ;
            to.setText(sTo) ;

            to_lat = place.getLatLng().latitude + "";
            to_lng = place.getLatLng().longitude + "";


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        progressBar.setVisibility(View.GONE);

    }


}
