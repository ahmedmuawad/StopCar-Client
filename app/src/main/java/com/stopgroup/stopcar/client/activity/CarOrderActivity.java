package com.stopgroup.stopcar.client.activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Fragment.DialogSendOrderFragment;
import com.stopgroup.stopcar.client.Helper.Closure;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Helper.HttpHelper;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Helper.OpenGps;
import com.stopgroup.stopcar.client.Helper.RouteHelper;
import com.stopgroup.stopcar.client.Helper.parser;
import com.stopgroup.stopcar.client.Modules.FareEstimate;
import com.stopgroup.stopcar.client.Modules.PlaceApi;
import com.stopgroup.stopcar.client.Modules.PromocodeResponse;
import com.stopgroup.stopcar.client.Modules.SendOrder;
import com.stopgroup.stopcar.client.Modules.Subcatogary;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.PrivateSubcatogaryadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
public class CarOrderActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "dd";
    private GoogleMap mMap;
    private Marker mMarcadorActual;
    private FusedLocationProviderClient mFusedLocationClient;
    private ImageView back;
    private TextView title;
    private TextView titlepayment;
    private TextView changepayment;
    private TextView fareEstimation;
    private LinearLayout fare;
    private LinearLayout Promo;
    private View Confirm;
    private LinearLayout from;
    private LinearLayout dotsLin;
    private LinearLayout detailsLin;
    private TextView locfrom, promocode_txv;
    private LinearLayout to;
    private TextView locto;
    private RecyclerView list;
    public static String payment = "";
    public String promocode = "", click = "", type = "";
    public static String from_lat = "", from_lon = "", to_lat = "", to_lon = "";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 456;
    Marker from_marker, to_marker;
    private ProgressBar progress;
    ImageView places1, places2;
    ArrayList<PlaceApi> results = new ArrayList<>();
    private ImageView gps;
    private Polyline polyLine;
    private String hours;
    private boolean isRent = false;
    private RouteHelper routeHelper = new RouteHelper();
    private LatLng fromLocation;
    private LatLng toLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentActivity.setInstance(this);
        setContentView(R.layout.activity_car_order);
        initView();
        onclick();
        OpenGps.checkgps(CarOrderActivity.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        from_lat = "";
        from_lon = "";
        to_lat = "";
        to_lon = "";
    }
    private void initView() {
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CarOrderActivity.this);
        places1 = findViewById(R.id.places1);
        places2 = findViewById(R.id.places2);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        titlepayment = findViewById(R.id.titlepayment);
        changepayment = findViewById(R.id.changepayment);
        fare = findViewById(R.id.fare);
        Promo = findViewById(R.id.Promo);
        Confirm = findViewById(R.id.Confirm);
        from = findViewById(R.id.from);
        dotsLin = findViewById(R.id.dotsLin);
        detailsLin = findViewById(R.id.detailsLin);
        locfrom = findViewById(R.id.locfrom);
        promocode_txv = findViewById(R.id.promocode_txv);
        to = findViewById(R.id.to);
        locto = findViewById(R.id.locto);
        fareEstimation = findViewById(R.id.fareEstimation);
        title.setText(getString(R.string.request_ride));
        titlepayment.setText(LoginSession.getlogindata(this).result.payment_method_text);
        progress = findViewById(R.id.progress);
        payment = LoginSession.getlogindata(this).result.payment_method_text;
        gps = findViewById(R.id.gps);
        list = findViewById(R.id.list);
        hours = getIntent().getStringExtra("hours");
        isRent = getIntent().getBooleanExtra("isRent", false);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
        catogaryadapter = new PrivateSubcatogaryadapter(this, categoriesBeans, from_lat, from_lon, to_lat, to_lon, fareEstimation);
        list.setAdapter(catogaryadapter);
        if (getIntent().hasExtra("SPECIAL_TYPE")) {
            if (getIntent().getExtras().getString("calculating_pricing").equals("tanks") ||
                    getIntent().getExtras().getString("calculating_pricing").equals("delivery")) {
                //    if (getIntent().getExtras().getInt("SPECIAL_TYPE") > 5) {
                from.setVisibility(View.GONE);
                dotsLin.setVisibility(View.GONE);
                //detailsLin.setVisibility(View.GONE);
            } else {
                from.setVisibility(View.VISIBLE);
                dotsLin.setVisibility(View.VISIBLE);
                //detailsLin.setVisibility(View.VISIBLE);
            }
//         dotsLin.setVisibility(View.GONE);
//         detailsLin.setVisibility(View.GONE);
        }
        if (getIntent().getExtras().getString("calculating_pricing").equals("tanks") ||
                getIntent().getExtras().getString("calculating_pricing").equals("delivery")) {
            from.setVisibility(View.GONE);
            dotsLin.setVisibility(View.GONE);
        } else {
            from.setVisibility(View.VISIBLE);
            dotsLin.setVisibility(View.VISIBLE);
        }
        if (getIntent().hasExtra("private")) {
            list.setVisibility(View.VISIBLE);
            getCatogriesForPrivateCar();
        }
        if (getIntent().hasExtra("type") && getIntent().getStringExtra("type").equals("delivery")) {
            from.setVisibility(View.GONE);
            dotsLin.setVisibility(View.GONE);
            type = "delivery";
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(CarOrderActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        to_lat = String.valueOf(location.getLatitude());
                        to_lon = String.valueOf(location.getLongitude());
                    }
                }
            });
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(CarOrderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mMap.clear();
                            from_lat = String.valueOf(location.getLatitude());
                            from_lon = String.valueOf(location.getLongitude());
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            fromLocation = latLng;
                            BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .icon(map_client)
                                    .title("my location");
                            from_marker = mMap.addMarker(options);
// move the camera zoom to the location
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                            Gdata.getAddress1(Double.parseDouble(from_lat), Double.parseDouble(from_lon), locfrom);
                            // Logic to handle location object
                        }
                    }
                });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (click == "1") {
                    Place place = PingPlacePicker.getPlace(data);
                    if (from_lon != "") {
                        from_marker.remove();
                        try {
                            polyLine.remove();
                        } catch (NullPointerException a) {
                            a.printStackTrace();
                        }
                    }
                    from_lat = String.valueOf(place.getLatLng().latitude);
                    from_lon = String.valueOf(place.getLatLng().longitude);
                    locfrom.setText(place.getName());
                    LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                    BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .icon(map_client)
                            .title("my location");
                    from_marker = mMap.addMarker(options);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    fromLocation = place.getLatLng();
                } else {
                    Place place = PingPlacePicker.getPlace(data);
                    to_lat = String.valueOf(place.getLatLng().latitude);
                    to_lon = String.valueOf(place.getLatLng().longitude);
                    locto.setText(place.getName());
                    LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                    BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .icon(map_client)
                            .title("my location");
                    if (to_marker != null) {
                        to_marker.remove();
                    }
                    to_marker = mMap.addMarker(options);
                    toLocation = place.getLatLng();
                    routeHelper.removeRoad();
                    routeHelper.drawRoad(fromLocation, toLocation, mMap);
                    try {
                        this.estimateFare(new Closure<FareEstimate>() {
                            @Override
                            public void run(FareEstimate args) {
                                super.run(args);
                                fareEstimation.setText(args.getResult().fare_estimation);
                            }
                        });
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                if (data != null) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    // : Handle the error.
                    Log.i(TAG, status.getStatusMessage());
                } else {
                    Toast.makeText(this, "عذرا حدث خطأ ما", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                payment = data.getStringExtra("type");
                Log.e("data", payment);
            }
        }
    }
    private void onclick() {
        places1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> titles = new ArrayList<>();
                for (PlaceApi place : results)
                    titles.add(place.name);
                Dialogs.chooseDialog(CarOrderActivity.this, getString(R.string.choose_place), titles, new Dialogs.DialogListener() {
                    @Override
                    public void onChoose(int pos) {
                        if (from_lon != "") {
                            from_marker.remove();
                        }
                        from_lat = results.get(pos).lat + "";
                        from_lon = results.get(pos).lng + "";
                        Gdata.getAddress1(Double.parseDouble(from_lat), Double.parseDouble(from_lon), locfrom);
                        LatLng latLng = new LatLng(Double.parseDouble(from_lat), Double.parseDouble(from_lon));
                        BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                        MarkerOptions options = new MarkerOptions()
                                .position(latLng)
                                .icon(map_client)
                                .title("my location");
                        from_marker = mMap.addMarker(options);
// move the camera zoom to the location
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    }
                });
            }
        });
        places2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> titles = new ArrayList<>();
                for (PlaceApi place : results)
                    titles.add(place.name);
                Dialogs.chooseDialog(CarOrderActivity.this, getString(R.string.choose_place), titles, new Dialogs.DialogListener() {
                    @Override
                    public void onChoose(int pos) {
                        if (to_lon != "") {
                            to_marker.remove();
                        }
                        to_lat = results.get(pos).lat + "";
                        to_lon = results.get(pos).lng + "";
                        Gdata.getAddress1(Double.parseDouble(to_lat), Double.parseDouble(to_lon), locto);
                        LatLng latLng = new LatLng(results.get(pos).lat, results.get(pos).lng);
                        BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                        MarkerOptions options = new MarkerOptions()
                                .position(latLng)
                                .icon(map_client)
                                .title("my location");
                        to_marker = mMap.addMarker(options);
// move the camera zoom to the location
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = "1";
                PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder()
                        .setAndroidApiKey("AIzaSyBYFoqMrU8982wmwFFyAj3IAPTaTKZkmaI")
                        .setMapsApiKey("AIzaSyBYFoqMrU8982wmwFFyAj3IAPTaTKZkmaI");
                try {
                    Intent placeIntent = builder.build(CarOrderActivity.this);
                    startActivityForResult(placeIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (Exception ex) {
                    Toast.makeText(CarOrderActivity.this, "Google Play services is not available...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = "2";
                PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder()
                        .setAndroidApiKey("AIzaSyBYFoqMrU8982wmwFFyAj3IAPTaTKZkmaI")
                        .setMapsApiKey("AIzaSyBYFoqMrU8982wmwFFyAj3IAPTaTKZkmaI");
                try {
                    Intent placeIntent = builder.build(CarOrderActivity.this);
                    startActivityForResult(placeIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (Exception ex) {
                    Toast.makeText(CarOrderActivity.this, "Google Play services is not available...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(CarOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_code);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = display.getWidth();
                dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText code = dialog.findViewById(R.id.code);
                TextView cancel = dialog.findViewById(R.id.cancel);
                TextView applycode = dialog.findViewById(R.id.applycode);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                applycode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (code.getText().toString().trim().equals("")) {
                            Dialogs.showToast(getString(R.string.enter_code_here), CarOrderActivity.this);
                        } else {
                            promocode = code.getText().toString().trim();
                            checkPromoCode(promocode, new SuccessLis() {
                                @Override
                                public void onSuccess() {
                                    dialog.dismiss();
                                    Dialogs.showToast("Success promocode", CarOrderActivity.this);
                                }
                                @Override
                                public void onError() {
                                    Dialogs.showToast("Wrong promocode", CarOrderActivity.this);
                                }
                            });
                        }
                    }
                });
                dialog.show();
            }
        });
        changepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UpdatepaymentActivity.class);
                i.putExtra("fareEstimation", fareEstimation.getText().toString());
                i.putExtra("paymentType", payment);
                startActivityForResult(i, 1);
            }
        });
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("SPECIAL_TYPE")) {
                    final Dialog dialog;
                    dialog = new Dialog(CarOrderActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_special_truck);
                    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    int width = display.getWidth();
                    dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    final EditText required = dialog.findViewById(R.id.required);
                    final EditText notes = dialog.findViewById(R.id.notes);
                    final CheckBox with_insurance = dialog.findViewById(R.id.with_insurance);

                    String categoryType = getIntent().getExtras().getString("calculating_pricing");
                    if(!categoryType.equals("truck_water")) {
                        with_insurance.setVisibility(View.GONE);
                    }

                    final ProgressBar progressBar = dialog.findViewById(R.id.progress);
                    View send = dialog.findViewById(R.id.send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (required.getText().toString().equals("") || notes.getText().toString().equals("")) {
                                Toast.makeText(CarOrderActivity.this, getString(R.string.plz_fill_data), Toast.LENGTH_SHORT).show();
                            } else if (getIntent().getExtras().getString("calculating_pricing").equals("truck_water") && from_lat.equals("")) {
                                //       } else if (getIntent().getExtras().getInt("SPECIAL_TYPE") == 5 && from_lat.equals("")) {
                                Toast.makeText(CarOrderActivity.this, R.string.select_loc, Toast.LENGTH_SHORT).show();
                            } else if (to_lat.equals("")) {
                                Toast.makeText(CarOrderActivity.this, R.string.select_loc, Toast.LENGTH_SHORT).show();
                            } else {
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("category_id", getIntent().getExtras().getInt("SPECIAL_TYPE"));
                                if (getIntent().getExtras().getString("calculating_pricing").equals("private_car") ||
                                        getIntent().getExtras().getString("calculating_pricing").equals("truck")) {
                                    //      if (getIntent().getExtras().getInt("SPECIAL_TYPE") < 6) {
                                    requestParams.put("from_lat", from_lat);
                                    requestParams.put("from_lng", from_lon);
                                }
                                requestParams.put("lat", from_lat);
                                requestParams.put("lng", from_lon);
                                requestParams.put("from_lat", from_lat);
                                requestParams.put("from_lng", from_lon);
                                requestParams.put("to_lat", to_lat);
                                requestParams.put("to_lng", to_lon);
//                                requestParams.put("sub_category_id", getIntent().getExtras().getInt("subcat"));
                                requestParams.put("sub_category_id", getIntent().getExtras().getInt("subcat"));
                                requestParams.put("trip_special_description", required.getText().toString());
                                requestParams.put("trip_special_note", notes.getText().toString());
                                if(with_insurance.isChecked()) {
                                    requestParams.put("with_insurance", 1);
                                }
                                makeCompanyOrder(requestParams, progressBar);
                                Log.e("requestParams", requestParams.toString());
                            }
                        }
                    });
                    dialog.show();
                } else {
                    boolean isDelivery = getIntent().getExtras().getString("type","no_type").equals("delivery");
                    if (getIntent().hasExtra("private") || isDelivery) {

                        if(categoriesBeans.size() > 0) {
                            boolean isOnlyOption =categoriesBeans.size()==1;

                            if (catogaryadapter.pos == -1 && isOnlyOption){
                                catogaryadapter.pos = 0;
                            }
                        } else {
                            catogaryadapter.pos = 0;
                        }


                        if (catogaryadapter.pos == -1 ) {
                            Dialogs.showToast(getString(R.string.select_car_type), CarOrderActivity.this);
                        } else {

                            if (SelectcarActivity.cat_id != 3) {
                                makeorder();
                            } else {
                                if (to_lat.equals("")) {
                                    Dialogs.showToast(getString(R.string.desi), CarOrderActivity.this);
                                } else {
                                    makeorder();
                                }
                            }
                        }
                    } else if (SelectcarActivity.cat_id != 3) {
                        makeorder();
                    } else {
                        if (to_lat.equals("")) {
                            Dialogs.showToast(getString(R.string.desi), CarOrderActivity.this);
                        } else {
                            makeorder();
                        }
                    }
                }
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Log.d("", "");
                mFusedLocationClient.getLastLocation().addOnSuccessListener(CarOrderActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            from_marker.remove();
                            from_lat = String.valueOf(location.getLatitude());
                            from_lon = String.valueOf(location.getLongitude());
                            Gdata.getAddress1(Double.parseDouble(from_lat), Double.parseDouble(from_lon), locfrom);
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .icon(map_client)
                                    .title("my location");
                            from_marker = mMap.addMarker(options);
// move the camera zoom to the location
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                            // Logic to handle location object
                        }
                    }
                });
            }
        });
    }
    private void makeorder() {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("from_lat", from_lat);
        requestParams.put("from_lng", from_lon);
        requestParams.put("payment_method", payment != null ? payment.toLowerCase() : "cash");
        requestParams.put("to_lat", to_lat);
        requestParams.put("to_lng", to_lon);
        requestParams.put("category_id", SelectcarActivity.cat_id);
        if (getIntent().hasExtra("subcat")) {
            requestParams.put("sub_category_id", getIntent().getIntExtra("subcat", 65655) + "");
        } else if (getIntent().hasExtra("private")) {
            Log.e("sub_id", categoriesBeans.get(catogaryadapter.pos).id + "");
            requestParams.put("sub_category_id", categoriesBeans.get(catogaryadapter.pos).id + "");
        } else if (to_lat.length() == 0 || to_lon.length() == 0) {
            Toast.makeText(this, "You should choose to location", Toast.LENGTH_LONG).show();
        }
        requestParams.put("promo_code", promocode);
        if (isRent) {
            requestParams.put("number_of_hours", hours);
            requestParams.put("type", "private");
            requestParams.put("category_id", 1);
        }
        if ((getIntent().hasExtra("calculating_pricing") && getIntent().getStringExtra("calculating_pricing").equals("delivery"))) {
            Dialogs.enterNotes(CarOrderActivity.this, new Dialogs.DialogSuccessListener() {
                @Override
                public void onSuccess(String notes) {
                    requestParams.put("trip_delivery_note", notes);
                    makeOrder(requestParams);
                }
            });
        } else {
            makeOrder(requestParams);
        }
        Log.e("test_send_order", requestParams + "");
    }
    void makeOrder(final RequestParams requestParams) {
        progress.setVisibility(View.VISIBLE);
        APIModel.postMethod(CarOrderActivity.this, "trips", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(CarOrderActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        makeorder();
                    }
                });
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                progress.setVisibility(View.GONE);
                Log.e("data22", responseString);
                Type dataType = new TypeToken<SendOrder>() {
                }.getType();
                SendOrder data = new Gson().fromJson(responseString, dataType);
                final Dialog dialog = new Dialog(CarOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_booking);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = display.getWidth();
                dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final TextView code = dialog.findViewById(R.id.code);
                TextView cancel = dialog.findViewById(R.id.cancel);
                TextView applycode = dialog.findViewById(R.id.applycode);
                try {
                    code.setText(String.format(getString(R.string.driver_will_pick_up_you_in_s_minutes), data.result.statistics.arriving_minutes));
                } catch (Exception e) {
                    code.setText(String.format(getString(R.string.driver_will_pick_up_you_in_s_minutes), "0"));
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        dialog.dismiss();
                    }
                });
                applycode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                        i.putExtra("data", responseString);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    interface SuccessLis {
        void onSuccess();
        void onError();
    }
    private void checkPromoCode(String code, final SuccessLis lis) {
        int categoryID = 0;
        if (getIntent().hasExtra("SPECIAL_TYPE")) {
            categoryID = getIntent().getExtras().getInt("SPECIAL_TYPE");
        } else {
            categoryID = SelectcarActivity.cat_id;
        }
        Dialogs.showLoadingDialog(this);
        APIModel.getMethod(CarOrderActivity.this, "promo/code?promo_code=" + code + "&category_id=" + categoryID, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Dialogs.dismissLoadingDialog();
                lis.onError();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Dialogs.dismissLoadingDialog();
                try {
                    Type dataType = new TypeToken<PromocodeResponse>() {
                    }.getType();
                    PromocodeResponse data = new Gson().fromJson(responseString, dataType);
                    promocode_txv.setText(data.result.discount + " %");
                    lis.onSuccess();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        titlepayment.setText(payment);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            // Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            final JSONObject json;
            try {
                json = new JSONObject(result);
                JSONArray routeArray = json.getJSONArray("routes");
                JSONObject routes = routeArray.getJSONObject(0);
                JSONArray newTempARr = routes.getJSONArray("legs");
                JSONObject newDisTimeOb = newTempARr.getJSONObject(0);
                JSONObject distOb = newDisTimeOb.getJSONObject("distance");
                JSONObject timeOb = newDisTimeOb.getJSONObject("duration");
                Log.i("Diatance :", distOb.getString("text"));
                Log.i("Time :", timeOb.getString("text"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ParserTask3 parserTask = new ParserTask3();
            parserTask.execute(result);
        }
    }
    private class ParserTask3 extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                parser parser = new parser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            Log.e("results", result + "");
            try {
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();
                    List<HashMap<String, String>> path = result.get(i);
                    Log.e("points", path + "");
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);
                        if (j == 0) {
                        } else if (j == 1) {
                        } else if (j > 1) {
                            double lat = Double.parseDouble(point.get("lat"));
                            double lng = Double.parseDouble(point.get("lng"));
                            LatLng position = new LatLng(lat, lng);
                            points.add(position);
                        }
                    }
                    lineOptions.addAll(points);
                    lineOptions.width(5);
                    lineOptions.color(Color.parseColor("#313d4d"));
                }
                try {
                    polyLine.remove();
                } catch (NullPointerException a) {
                    a.printStackTrace();
                }
                polyLine = mMap.addPolyline(lineOptions);
            } catch (NullPointerException e) {
            }
        }
    }
    private void estimateFare(final Closure<FareEstimate> closure) {
        HttpHelper.httpHelper.paramters.put("from_lat", from_lat);
        HttpHelper.httpHelper.paramters.put("from_lng", from_lon);
        HttpHelper.httpHelper.paramters.put("to_lat", to_lat);
        HttpHelper.httpHelper.paramters.put("to_lng", to_lon);
        HttpHelper.httpHelper.paramters.put("category_id", SelectcarActivity.cat_id);
        HttpHelper.httpHelper.paramters.put("sub_category_id", getIntent().getExtras().getInt("subcat"));
        Log.e("category_id", "category_id: " + SelectcarActivity.cat_id);
        Log.e("sub_category", "subCategory: " + getIntent().getExtras().getInt("subcat"));
        HttpHelper.httpHelper.get("trips/estimate", new Closure<String>() {
            @Override
            public void run(String args) {
                super.run(args);
                try {
                    Type dataType = new TypeToken<FareEstimate>() {
                    }.getType();
                    FareEstimate data = new Gson().fromJson(args, dataType);
                    closure.run(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void makeCompanyOrder(final RequestParams requestParams, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        APIModel.postMethod(CarOrderActivity.this, "trips/request/company", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("makeorder_fail", statusCode + "");
                APIModel.handleFailure(CarOrderActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        makeCompanyOrder(requestParams, progressBar);
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
    ArrayList<Subcatogary.ResultBean> categoriesBeans = new ArrayList<>();
    PrivateSubcatogaryadapter catogaryadapter;
    private void getCatogriesForPrivateCar() {
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(CarOrderActivity.this, "sub_categories/" + getIntent().getIntExtra("id", 6555), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("555555555", statusCode + "---");
                APIModel.handleFailure(CarOrderActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getCatogriesForPrivateCar();
                    }
                });
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("555555555", responseString + "---");
                Type dataType = new TypeToken<Subcatogary>() {
                }.getType();
                Subcatogary data = new Gson().fromJson(responseString, dataType);
                categoriesBeans.addAll(data.result);
                catogaryadapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }
        });
    }
}