package com.stopgroup.stopcar.client.Fragment;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Dialogs;
import com.stopgroup.stopcar.client.Modules.HomeEvent;
import com.stopgroup.stopcar.client.Modules.Order;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.activity.BookingConfirmationActivity;
import com.stopgroup.stopcar.client.activity.CarOrderActivity;
import com.stopgroup.stopcar.client.activity.SelectcarActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Timer timer = new Timer();
    private static final String TAG = "dd";
    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationClient;
    String lat = "", lon = "";
    private LatLng currentLocation = new LatLng(0, 0);
    View view;
    private View card;
    private View rentCard;
    private TextView requestBtn;
    private TextView rentBtn;
    private boolean makeRequest;
    Handler handler = new Handler(Looper.getMainLooper());
    int count = 0;
    Order data;
    private String hours;
    private boolean isRent = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        onclick();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e(TAG, "gps enabled");
        } else {
            mEnableGps();
        }
        return view;
    }
    private void initView() {
        card = view.findViewById(R.id.card);
        requestBtn = view.findViewById(R.id.requestBtn);
        rentBtn = view.findViewById(R.id.rentBtn);
        rentCard = view.findViewById(R.id.cardRent);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        card.setVisibility(View.GONE);
        rentCard.setVisibility(View.GONE);
        makeRequest = false;
    }
    public void controlBtns(boolean event) {
        card.setVisibility(View.VISIBLE);
        //  if (event) {
//            rentCard.setVisibility(View.VISIBLE);
        requestBtn.setText(getString(R.string.make_request));
        makeRequest = true;
        //  } else {
//            rentCard.setVisibility(View.GONE);
//            requestBtn.setText(getString(R.string.cancel));
//            makeRequest = false;
        //  }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.clear();
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.e("test_tekrar", "test_tekraar");
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mMap.clear();
                            lat = String.valueOf(location.getLatitude());
                            lon = String.valueOf(location.getLongitude());
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            currentLocation = latLng;
                            BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pincolored);
                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .icon(map_client)
                                    .title("my location");
                            mMap.addMarker(options);
//// move the camera zoom to the location
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
//                            if (count==0) {
//                            }
                            // Logic to handle location object
                        }
                    }
                });
    }
    public void timer() {
        try {
            if (timer == null) {
                timer = new Timer();
            }
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        current_order(lat, lon);
                        }
                    });
                }
            }, 0, 10000);//put here time 1000 milliseconds=1 second
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
        timer();
    }
    private void onclick() {
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (makeRequest) {
                    Intent i = new Intent(getActivity(), SelectcarActivity.class);
                    startActivity(i);
                    handler.removeCallbacksAndMessages(null);
                } else {
                    try {
                        Intent i = new Intent(getActivity(), BookingConfirmationActivity.class);
                        i.putExtra("trip_id", data.result.id);
                        getActivity().startActivity(i);
                        getActivity().finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_rent);
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = display.getWidth();
                dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText hours = dialog.findViewById(R.id.hours);
                TextView cancel = dialog.findViewById(R.id.cancel);
                TextView apply = dialog.findViewById(R.id.apply);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hours.getText().toString().trim().equals("")) {
                            Dialogs.showToast(getString(R.string.enter_code_here), getActivity());
                        } else {
                            HomeFragment.this.hours = hours.getText().toString().trim();
                            HomeFragment.this.isRent = true;
                            dialog.dismiss();
                            Intent i = new Intent(getActivity(), CarOrderActivity.class);
                            i.putExtra("id", 1);
                            i.putExtra("type", "private");
                            i.putExtra("hours", HomeFragment.this.hours);
                            i.putExtra("isRent", HomeFragment.this.isRent);
                            startActivity(i);
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    private void current_order(final String lat, final String lng) {
        APIModel.getMethod(getActivity(), "trips/client/current?lat=" + lat + "&lng=" + lng, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("5555_fail", "");
                APIModel.handleFailure(getActivity(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        current_order(lat, lng);
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Log.e("test_1", "50505050505");
                    Type dataType = new TypeToken<Order>() {
                    }.getType();
                    data = new Gson().fromJson(responseString, dataType);

                    if (data.result == null || data.result.id == 0) {
                        controlBtns(true);
                    } else {
                        controlBtns(false);
                    }
                    if (!data.result.type.equals("special")) {
                        if (data.result.status == 1 || data.result.status == 4 || data.result.status == 5 || data.result.status == 6) {
//                            Intent i = new Intent(getActivity(), ArriaveActivity.class);
//                            i.putExtra("data", responseString);
//                            startActivity(i);
                        } else if (data.result.status == 0) {
                            EventBus.getDefault().post(new HomeEvent(true));
//                            Intent i = new Intent(getApplicationContext(), BookingConfirmationActivity.class);
//                            i.putExtra("data", responseString);
//                            startActivity(i);
//                            finish();
                        }
                    } else if (data.result.type.equals("special") && data.result.status != 0) {
                        if (data.result.status == 1 || data.result.status == 4 || data.result.status == 5 || data.result.status == 6) {
//                            Intent i = new Intent(getActivity(), ArriaveActivity.class);
//                            i.putExtra("data", responseString);
//                            startActivity(i);
                        }
                    } else {
                        if (data.result.requests.size() > 0) {
//                            Intent i = new Intent(getApplicationContext(), RequestActivity.class);
//                            i.putExtra("data", responseString);
//                            startActivity(i);
                            DialogReplayOrderFragment dialogReplayOrderFragment = new DialogReplayOrderFragment(data.result.requests.get(0));
                            dialogReplayOrderFragment.show(getActivity().getFragmentManager(), "");
                        } else {
//                            Intent iiii = new Intent(getActivity(), HistoryActivity.class);
//                            startActivity(iiii);
                        }
                    }
                    JSONObject jo = new JSONObject(responseString);
                    JSONArray jsonArray = jo.getJSONArray("drivers");
                    mMap.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        LatLng latLng = new LatLng(j.getDouble("lat"), j.getDouble("lng"));
                        BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.carr);
                        MarkerOptions options = new MarkerOptions()
                                .position(latLng)
                                .icon(map_client)
                                .title("");
                        mMap.addMarker(options);
// move the camera zoom to the location
                    }
                    BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pincolored);
                    MarkerOptions options = new MarkerOptions()
                            .position(currentLocation)
                            .icon(map_client)
                            .title("my location");
                    mMap.addMarker(options);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.open))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        stopRepeatingTask();
        super.onStop();
        stopRepeatingTask();
        Log.e("dd", "cdcd");
    }
    @Override
    public void onPause() {
        stopRepeatingTask();
        super.onPause();
        stopRepeatingTask();
        Log.e("dd", "cdcd");
        timer.cancel();
        timer = null;
    }
    @Override
    public void onDestroy() {
        stopRepeatingTask();
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    void stopRepeatingTask() {
        handler.removeMessages(0);
        handler.removeCallbacksAndMessages(null);
    }
    public static final int REQUEST_LOCATION = 001;
    public static GoogleApiClient googleApiClient;
    LocationManager locationManager;
    LocationRequest locationRequest;
    LocationSettingsRequest.Builder locationSettingsRequest;
    PendingResult<LocationSettingsResult> pendingResult;
    public void mEnableGps() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        mLocationSetting();
    }
    public void mLocationSetting() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1 * 1000);
        locationRequest.setFastestInterval(1 * 1000);
        locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        mResult();
    }
    public void mResult() {
        pendingResult = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSettingsRequest.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
    //callback method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Log.e("5555555555", "onActivityResult: ");
                        Toast.makeText(getActivity(), "Gps enabled", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(this).attach(this).commit();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(getActivity(), "Gps Canceled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
//   Toast.makeText(getActivity(),"onnconnected_5555",Toast.LENGTH_SHORT);
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
