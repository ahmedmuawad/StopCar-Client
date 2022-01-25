package com.stopgroup.stopcar.client.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.R;

import cz.msebera.android.httpclient.Header;

public class EditlocActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private ImageView back;
    private TextView title;
    private View Confirm;
    private TextView loc;
    private ProgressBar progress;
    String lat = "", lon = "";
    private GoogleMap mMap;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 456;
    Marker from_marker;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editloc);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        Confirm = findViewById(R.id.Confirm);
        loc = findViewById(R.id.loc);
        progress = findViewById(R.id.progress);
        title.setText(getString(R.string.edit_location));
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");

        name = findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("name"));
        Gdata.getAddress1(Double.parseDouble(lat), Double.parseDouble(lon), loc);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(EditlocActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setOnMapClickListener(this);
        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(map_client)
                .title("my location");

        from_marker = mMap.addMarker(options);
// move the camera zoom to the location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));


    }

    @Override
    public void onMapClick(LatLng latLng) {
        from_marker.remove();
        lat = String.valueOf(latLng.latitude);
        lon = String.valueOf(latLng.longitude);
        Gdata.getAddress1(Double.parseDouble(lat), Double.parseDouble(lon), loc);
        BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(map_client)
                .title("my location");
        from_marker = mMap.addMarker(options);
// move the camera zoom to the location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (lat != "") {
                    from_marker.remove();
                }
                lat = String.valueOf(place.getLatLng().latitude);
                lon = String.valueOf(place.getLatLng().longitude);
                Gdata.getAddress1(Double.parseDouble(lat), Double.parseDouble(lon), loc);
                LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .icon(map_client)
                        .title("my location");
                from_marker = mMap.addMarker(options);
// move the camera zoom to the location
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // : Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
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
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(EditlocActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // : Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // : Handle the error.
                }
            }
        });


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lat.equals("")) {
                    Dialogs.showToast(getString(R.string.desi), EditlocActivity.this);
                } else {
                    makeorder();
                }

            }
        });
    }

    private void makeorder() {
        progress.setVisibility(View.VISIBLE);
        RequestParams requestParams = new RequestParams();
        requestParams.put("lat", lat);
        requestParams.put("lng", lon);
        requestParams.put("name", name.getText().toString());

        APIModel.putMethod(EditlocActivity.this, "client_places/" + getIntent().getIntExtra("id", 556455), requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(EditlocActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        makeorder();
                    }
                });

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.setVisibility(View.GONE);
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}
