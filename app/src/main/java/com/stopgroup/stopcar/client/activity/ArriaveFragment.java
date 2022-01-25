package com.stopgroup.stopcar.client.activity;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dewarder.holdinglibrary.HoldingButtonLayout;
import com.dewarder.holdinglibrary.HoldingButtonLayoutListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Closure;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Helper.HttpHelper;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Helper.RouteHelper;
import com.stopgroup.stopcar.client.Modules.EmergencyContacts;
import com.stopgroup.stopcar.client.Modules.Order;
import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.activity.chat_libs.AudioRecorder;
import com.stopgroup.stopcar.client.activity.chat_libs.FilesUploader;
import com.stopgroup.stopcar.client.activity.chat_libs.Message;
import com.stopgroup.stopcar.client.activity.chat_libs.PermissionsActivity;
import com.stopgroup.stopcar.client.adapter.Choose1Adapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.stopgroup.stopcar.client.activity.chat_libs.Libs.getCurrentTime;
public class ArriaveFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "dd";
    private GoogleMap mMap;
    private Marker mMarcadorActual;
    private FusedLocationProviderClient mFusedLocationClient;
    private ImageView back;
    private TextView title;
    private LinearLayout lin1;
    private CircleImageView img;
    private TextView name;
    private RatingBar rate;
    private TextView num;
    private TextView model, platenum;
    private TextView distance;
    private TextView time;
    private TextView status;
    private ImageView call;
    private TextView line;
    private LinearLayout lin;
    private TextView loc1;
    private TextView titlepayment;
    private TextView changepayment;
    private View Confirm;
    private LinearLayout sos;
    private TextView locfrom;
    private TextView locto;
    Order data = new Order();
    private boolean firstCall = true;
    String phone = "", lat = "", lon = "";
    Marker marker, marker1;
    private int mInterval = 15000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    Handler handler = new Handler(Looper.getMainLooper());
    int x = 0;
    Dialog dialog;
    ArrayList<Settings.ResultBean.CancelReasonsBean> cancelReasonsBean = new ArrayList<>();
    Choose1Adapter chooseAdapter;
    private View card;
    private ProgressBar progress;
    private LinearLayout lin2;
    int count = 0;
    private boolean canBack = false;
    private boolean checkReview = false;
    private Timer timer = new Timer();
    private Double currentLat = 0.0;
    private Double currentLng = 0.0;
    private RouteHelper routeHelper = new RouteHelper();
    private ImageView more;
    private View pay;
    int trip_id;
    View mainView;
    public <T extends View> T findViewById(int id) {
        return mainView.findViewById(id);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_arriave, container, false);
        CurrentActivity.setInstance(getActivity());
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        trip_id = getArguments().getInt("trip_id", 0);
        if (getArguments().getBoolean("hide_action", false)) {
            findViewById(R.id.actionBar).setVisibility(View.GONE);
        }
        initView();
        onclick();
        return mainView;
    }
    @Override
    public void onResume() {
        super.onResume();
        timer();
    }
    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        lin1 = findViewById(R.id.lin1);
        img = findViewById(R.id.img);
        name = findViewById(R.id.name);
        rate = findViewById(R.id.rate);
        num = findViewById(R.id.num);
        distance = findViewById(R.id.distance);
        time = findViewById(R.id.time);
        platenum = findViewById(R.id.platenum);
        model = findViewById(R.id.model);
        status = findViewById(R.id.status);
        call = findViewById(R.id.call);
        line = findViewById(R.id.line);
        lin = findViewById(R.id.lin);
        loc1 = findViewById(R.id.loc1);
        titlepayment = findViewById(R.id.titlepayment);
        changepayment = findViewById(R.id.changepayment);
        Confirm = findViewById(R.id.Confirm);
        sos = findViewById(R.id.sos);
        locfrom = findViewById(R.id.locfrom);
        locto = findViewById(R.id.locto);
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        title.setText(getString(R.string.pickuparriave));
        card = findViewById(R.id.card);
        progress = findViewById(R.id.progress);
        lin2 = findViewById(R.id.lin2);
        more = findViewById(R.id.more);
        pay = findViewById(R.id.pay);
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
                            getCategories();
                        }
                    });
                }
            }, 0, 10000);//put here time 1000 milliseconds=1 second
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Settings settings;
    private void getCategories() {
        if (settings != null) {
            current_order();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        APIModel.getMethod(getActivity(), "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(getActivity(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getCategories();
                    }
                });
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Type dataType = new TypeToken<Settings>() {
                    }.getType();
                    settings = new Gson().fromJson(responseString, dataType);
                    current_order();
                    progress.setVisibility(View.GONE);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void current_order() {
        APIModel.getMethod(getActivity(), "trips/client/current?trip_id=" + trip_id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(getActivity(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        current_order();
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Order order = new Order();
                    Log.e("current_order", responseString);
                    Type dataType = new TypeToken<Order>() {
                    }.getType();
                    if (firstCall) {
                        data = new Gson().fromJson(responseString, dataType);
                        firstCall = false;
                    } else {
                        order = new Gson().fromJson(responseString, dataType);
                        checkReview = true;
                    }
                    if ((data.result == null || data.result.id == 0)) {
                        canBack = true;
                        return;
                    }
                    if (data.result.status == 6 && data.result.payment_method == 2 && data.result.payment_paid == false) {
                        pay.setVisibility(View.VISIBLE);
                    } else if (data.result.status == 6 && data.result.payment_method == 2 && data.result.payment_paid == true) {
                        pay.setVisibility(View.GONE);
                    } else {
                        //&& (order.result == null || order.result.id == 0)
                        if (data.result.status == 7 && checkReview) {
                            Intent i = new Intent(getActivity(), ReviewTripActivity.class);
                            i.putExtra("data", responseString);
                            String json = new Gson().toJson(data);
                            i.putExtra("json", json);
                            startActivity(i);
                            finishListener.onFinish(true);
                            return;
                        } else if (order.result != null && order.result.id != 0) {
                            data = order;
                        }
                    }
                    phone = data.result.driver.mobile;
                    locfrom.setText(data.result.from_location);
                    locto.setText(data.result.to_location);
                    name.setText(data.result.driver.first_name + " " + data.result.driver.last_name);
                    status.setText(data.result.status_text);
                    try {
                        rate.setRating(Float.parseFloat(data.result.driver.driver.rate));
                        num.setText("(" + Math.round(Float.parseFloat(data.result.driver.driver.rate))+ ")");
                    } catch (Exception e) {
                        rate.setRating(0);
                        num.setText("(0)");
                    }
                    if (data.result.category_id != 3) {
                        try {
                            model.setText(data.result.driver.driver.car.brand_name + " - " + data.result.driver.driver.car.model_name + " - " + data.result.driver.driver.car.color_name);
                        } catch (Exception e) {
                            model.setText("");
                        }
                    } else {
                        model.setVisibility(View.GONE);
                    }
                    if (data.result.driver.driver.car != null)
                        platenum.setText(data.result.driver.driver.car.plate_number);
                    distance.setText(getString(R.string.estimate_distance) + " : " + data.result.distance + " " + getString(R.string.km));
                    time.setText(getString(R.string.estimate_time) + " : " + data.result.time_estimation);
                    findViewById(R.id.estimation_layout).setVisibility(!TextUtils.isEmpty(data.result.to_location) ? View.VISIBLE : View.GONE);
                    Picasso.get().load(data.result.driver.image).into(img);
                    titlepayment.setText(data.result.payment_method_text);
                    loc1.setText(data.result.from_location);
                    if (data.result.status == 4) {
                        Confirm.setVisibility(View.GONE);
                        loc1.setText(data.result.to_location);
                    } else if (data.result.status == 5) {
                        call.setVisibility(View.GONE);
                        loc1.setText(data.result.to_location);
                        marker.remove();
                    }
                    if (!lat.equals("")) {
                        //marker1.remove();
                    }
                    if (data.result.driver.driver.lat != null) {
                        LatLng latLng = new LatLng(data.result.driver.driver.lat, data.result.driver.driver.lng);
                        String imageUrl = null;
                        for (Settings.ResultBean.CategoriesBean Categorise : settings.result.categories) {
                            if (Categorise.id == data.result.category_id) {
                                imageUrl = Categorise.img;
                            }
                        }
                        if (map_client != null) {
                            startMap(latLng, map_client);
                        } else {
                            if (imageUrl == null) {
                                map_client = BitmapDescriptorFactory.fromResource(R.drawable.carr);
                                startMap(latLng, map_client);
                            } else {
                                Picasso.get().load(imageUrl).resize(150, 150).centerInside().into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        BitmapDescriptor map_client = BitmapDescriptorFactory.fromBitmap(bitmap);
                                        startMap(latLng, map_client);
                                    }
                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                    }
                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }
                                });
                            }
                        }
                    }
                    if (count == 0) {
                    }
                    lin2.setVisibility(View.VISIBLE);
                    card.setVisibility(View.VISIBLE);
                    String myUid = "user_" + trip_id;
                    String myName = "Me";
                    String myImage = "https://www.klrealty.com.au/wp-content/uploads/2018/11/user-image-.png";
                    String hisUid = "driver_" + trip_id;
                    String hisName = data.result.driver.first_name + " " + data.result.driver.last_name;
                    String hisImage = data.result.driver.image;
                    startChat(myUid, myName, myImage, hisUid, hisName, hisImage);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    BitmapDescriptor map_client;
    public void startMap(LatLng latLng, BitmapDescriptor map_client) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .zIndex(2)
                .icon(map_client);
        if (data.result.status < 4) {
            options.title(data.result.statistics.arriving_minutes);
            try {
                if (data.result.type.equals("special")) {
                    LatLng from = new LatLng(data.result.driver.driver.lat, data.result.driver.driver.lng);
                    LatLng to = new LatLng(Double.parseDouble(data.result.to_lat), Double.parseDouble(data.result.to_lng));
                    routeHelper.drawRoad(from, to, mMap);
                } else {
                    if (data.result.from_lat >= 0) {
                        LatLng from = new LatLng(currentLat, currentLng);
                        LatLng to = new LatLng(data.result.driver.driver.lat, data.result.driver.driver.lng);
                        routeHelper.drawRoad(from, to, mMap);
                    } else {
                        LatLng from = new LatLng(data.result.driver.driver.lat, data.result.driver.driver.lng);
                        LatLng to = new LatLng(Double.parseDouble(data.result.to_lat), Double.parseDouble(data.result.to_lng));
                        routeHelper.drawRoad(from, to, mMap);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.result.status < 5) {
            try {
                routeHelper.removeRoad();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.result.status < 6) {
            try {
                LatLng from = new LatLng(data.result.driver.driver.lat, data.result.driver.driver.lng);
                LatLng to = new LatLng(Double.parseDouble(data.result.to_lat), Double.parseDouble(data.result.to_lng));
                routeHelper.drawRoad(from, to, mMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            marker1.setPosition(latLng);
        } catch (Exception e) {
            marker1 = mMap.addMarker(options);
        }
        // move the camera zoom to the location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        lat = String.valueOf(data.result.driver.driver.lat);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // : Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mMap.clear();
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            BitmapDescriptor map_client = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .icon(map_client)
                                    .zIndex(1)
                                    .title("my location");
                            marker = mMap.addMarker(options);
                            currentLat = latLng.latitude;
                            currentLng = latLng.longitude;
// move the camera zoom to the location
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                            // Logic to handle location object
                        }
                    }
                });
    }
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
            } finally {
//                getdescussion();
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    void startRepeatingTask() {
        mStatusChecker.run();
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
    public void onBackPressed() {
        if (trip_id == 0) {
            try {
                if (!TextUtils.isEmpty(data.result.type) && data.result.type.equals("special")) {
                    Intent history = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(history);
                    return;
                } else
                    finishListener.onFinish(false);
            } catch (Exception e) {
                finishListener.onFinish(false);
            }
        } else
            finishListener.onFinish(false);
    }
    public interface FinishListener {
        public void onFinish(boolean tripDone);
    }
    FinishListener finishListener;
    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }
    private void checkPaymentOnlineCountry() {

     /*
        String code = LoginSession.code;
        if (code != null && (code.contains("02") || code.contains("+2"))) {
            Intent i = new Intent(getActivity(), FawryActivity.class);
            i.putExtra("price", (data.result.total_price));
            i.putExtra("requestID", data.result.id + "");
            startActivity(i);
        } else {
            Intent i = new Intent(getActivity(), WebviewActivity.class);
            i.putExtra("id", data.result.id);
            startActivity(i);
        }
      */

        Toast.makeText(getActivity(), "لم يتم إعداد منصة الدفع بعد", Toast.LENGTH_SHORT).show();

    }
    private void onclick() {
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPaymentOnlineCountry();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        changepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangepaymentActivity.class);
                i.putExtra("trip_id", data.result.id);
                i.putExtra("payment", data.result.payment_method_text);
                startActivity(i);
            }
        });
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x == 0) {
                    lin.setVisibility(View.VISIBLE);
                    x = 1;
                } else {
                    lin.setVisibility(View.GONE);
                    x = 0;
                }
            }
        });
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callingSos();
            }
        });
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_choose);
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = display.getWidth();
                dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                RecyclerView choose_recycler = dialog.findViewById(R.id.choose_recycler);
                TextView cancel = dialog.findViewById(R.id.cancel);
                TextView applycode = dialog.findViewById(R.id.applycode);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                LinearLayoutManager layoutManage = new LinearLayoutManager(getActivity());
                choose_recycler.setLayoutManager(layoutManage);
                chooseAdapter = new Choose1Adapter(getActivity(), cancelReasonsBean);
                choose_recycler.setAdapter(chooseAdapter);
                getreason();
                applycode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        canceltrip();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        locto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditdestinationActivity.class);
                try {
                    i.putExtra("lat", data.result.to_lat);
                    i.putExtra("lon", data.result.to_lng);
                } catch (Exception e) {
                }
                startActivity(i);
            }
        });
    }
    private void getreason() {
        APIModel.getMethod(getActivity(), "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(getActivity(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getreason();
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<Settings>() {
                }.getType();
                Settings data = new Gson().fromJson(responseString, dataType);
                cancelReasonsBean.addAll(data.result.cancel_reasons);
                chooseAdapter.notifyDataSetChanged();
            }
        });
    }
    private void canceltrip() {
        HttpHelper.httpHelper.paramters.put("cancel_reason_id", chooseAdapter.id);
        HttpHelper.httpHelper.get("trips/cancel/" + data.result.id, new Closure<String>() {
            @Override
            public void run(String args) {
                super.run(args);
                finishListener.onFinish(true);
                dialog.dismiss();
            }
        });
    }
    private void callingSos() {
        this.sos(new Closure<EmergencyContacts>() {
            @Override
            public void run(EmergencyContacts args) {
                super.run(args);
                if (args.getResult().size() > 0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            }
        });
    }
    private void sos(final Closure<EmergencyContacts> closure) {
        HttpHelper.httpHelper.paramters.put("default_contact", 1);
        HttpHelper.httpHelper.get("client_contacts", new Closure<String>() {
            @Override
            public void run(String args) {
                super.run(args);
                Type dataType = new TypeToken<EmergencyContacts>() {
                }.getType();
                EmergencyContacts data = new Gson().fromJson(args, dataType);
                closure.run(data);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            new FilesUploader(getActivity(), data.getData(), "images", ".jpg", new FilesUploader.OnImageUploadListener() {
                @Override
                public void OnImageUploaded(String file) {
                    sendMessage("image", file);
                    Toast.makeText(getActivity(), "تم الارسال بنجاح", Toast.LENGTH_LONG).show();
                }
                @Override
                public void OnError() {
                    Toast.makeText(getActivity(), "عذرا حدث خطأ ما", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private RecyclerView mChatRecycler;
    String myUid, myImage, hisImage, hisUid, myName, hisName;
    EditText input;
    ImageView mSend;
    public void sendMessage(String kind, String content) {
        String time = getCurrentTime();
        String seen = "UNSEEN";
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("kind", kind);
        data.put("content", content);
        data.put("seen", seen);
        data.put("time", time);
        HashMap<String, String> me = (HashMap) data.clone();
        me.put("who", "me");
        HashMap<String, String> him = (HashMap) data.clone();
        him.put("who", "him");
        String msg_id = FirebaseDatabase.getInstance().getReference().push().getKey();
        FirebaseDatabase.getInstance().getReference().child("Chats").child(hisUid).child(myUid).child(msg_id).setValue(him);
        FirebaseDatabase.getInstance().getReference().child("Chats").child(myUid).child(hisUid).child(msg_id).setValue(me);
    }
    AudioRecorder audioRecorder;
    boolean isViceExpand = false;
    boolean chatStarted = false;
    public void startChat(String myUid, String myName, String myImage, String hisUid, String hisName, String hisImage) {
        if (chatStarted) {
            return;
        }
        chatStarted = true;
        this.myUid = myUid;
        this.myName = myName;
        this.myImage = myImage;
        this.hisUid = hisUid;
        this.hisName = hisName;
        this.hisImage = hisName;
        HoldingButtonLayout voice_message = findViewById(R.id.voice_message);
        voice_message.addListener(new HoldingButtonLayoutListener() {
            @Override
            public void onBeforeExpand() {
                isViceExpand = true;
                ((PermissionsActivity) getActivity()).requestAppPermissions(new String[]{Manifest.permission.RECORD_AUDIO, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, new PermissionsActivity.OnPermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted() {
                        File tempDir = ContextCompat.getDataDir(getActivity());
                        if (tempDir != null && isViceExpand) {
                            String temp = tempDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".mp3";
                            audioRecorder = new AudioRecorder(temp);
                            audioRecorder.start();
                            Toast.makeText(getActivity(), "قم بالإزاحة للتراجع عن الارسال", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onExpand() {
            }
            @Override
            public void onBeforeCollapse() {
            }
            @Override
            public void onCollapse(boolean cancel) {
                isViceExpand = false;
                if (audioRecorder != null) {
                    audioRecorder.stop();
                    File voice = new File(audioRecorder.path);
                    if (cancel) {
                        voice.delete();
                        Toast.makeText(getActivity(), "تمت ازالة الرسالة بنجاح", Toast.LENGTH_SHORT).show();
                    } else {
                        new FilesUploader(getActivity(), Uri.fromFile(voice), "voice", ".mp3", new FilesUploader.OnImageUploadListener() {
                            @Override
                            public void OnImageUploaded(String file) {
                                sendMessage("voice", file);
                                voice.delete();
                            }
                            @Override
                            public void OnError() {
                                Toast.makeText(getActivity(), "عذرا , تاكد من الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                                voice.delete();
                            }
                        });
                    }
                    audioRecorder = null;
                }
            }
            @Override
            public void onOffsetChanged(float v, boolean b) {
            }
        });
        input = findViewById(R.id.inputET);
        mSend = findViewById(R.id.send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                String msg = input.getText().toString();
                if (msg.isEmpty()) {
                    return;
                }
                input.setText("");
                input.setHint("Type something......");
                sendMessage("text", msg);
            }
        });
        findViewById(R.id.send_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), 1);
            }
        });
        mChatRecycler = findViewById(R.id.chat_recyclerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mChatRecycler.setLayoutManager(layoutManager);
        mChatRecycler.setHasFixedSize(true);
        mChatRecycler.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mChatRecycler.scrollToPosition(positionStart);
                }
            }
        });
        mChatRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mChatRecycler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mChatRecycler.smoothScrollToPosition(mChatRecycler.getAdapter().getItemCount() - 1);
                            } catch (Exception e) {
                            }
                        }
                    }, 100);
                }
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Chats").child(myUid).child(hisUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final boolean firstTime = messages.isEmpty();
                messages.clear();
                for (DataSnapshot msg : dataSnapshot.getChildren()) {
                    Message message = msg.getValue(Message.class);
                    if (message != null && message.who != null) {
                        message.id = msg.getKey();
                        messages.add(message);
                    }
                }
                adapter.notifyDataSetChanged();
                mChatRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (firstTime) {
                                mChatRecycler.scrollToPosition(mChatRecycler.getAdapter().getItemCount() - 1);
                            } else {
                                mChatRecycler.smoothScrollToPosition(mChatRecycler.getAdapter().getItemCount() - 1);
                            }
                        } catch (Exception e) {
                        }
                    }
                }, 100);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", "error");
            }
        });
    }
    ArrayList<Message> messages = new ArrayList<>();
    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layout = 0;
            switch (viewType) {
                case 0:
                    layout = R.layout.item_message_received;
                    break;
                case 1:
                    layout = R.layout.item_message_sent;
                    break;
                case 2:
                    layout = R.layout.item_message_call;
                    break;
            }
            return new ChatViewHolder(getLayoutInflater().inflate(layout, parent, false));
        }
        @Override
        public int getItemViewType(int position) {
            if (messages.get(position).isCall()) {
                return 2;
            }
            if (messages.get(position).who.equals("me")) {
                return 1;
            } else {
                return 0;
            }
        }
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ChatViewHolder) holder).LoadMessage(getActivity(), messages.get(position), myImage, hisImage);
        }
        @Override
        public int getItemCount() {
            return messages.size();
        }
    };
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        View view;
        CircleImageView imageView;
        public ChatViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.single_image);
        }
        private void setMessage(final Activity context, Message message) {
            TextView text = view.findViewById(R.id.text);
            ImageView image = view.findViewById(R.id.image);
            VoicePlayerView playerView = view.findViewById(R.id.voicePlayerView);
            if (message.kind == null) {
                message.kind = "unknown";
            }
            switch (message.kind) {
                case "text":
                    text.setText(message.content);
                    text.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                    playerView.setVisibility(View.GONE);
                    break;
                case "video_call":
                    text.setText("مكالمة فيديو منتهية");
                    break;
                case "voice_call":
                    text.setText("مكالمة صوتية منتهية");
                    break;
                case "image":
                    image.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);
                    playerView.setVisibility(View.GONE);
                    Picasso.get().load(message.content).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(image);
                    break;
                case "voice":
                    image.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    playerView.setVisibility(View.VISIBLE);
                    File target = new File(new File(Environment.getExternalStorageDirectory(), "jampo"), message.id + ".mp3");
                    if (target.exists()) {
                        playerView.setAudio(target.getAbsolutePath());
                    } else {
                        playerView.setAudio("none");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    saveUrl(target, message.content);
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            playerView.setAudio(target.getAbsolutePath());
                                        }
                                    });
                                } catch (IOException e) {
                                    target.delete();
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    break;
                default:
                    text.setText("يجب تحديث التطبيق لمشاهدة الرسالة");
                    text.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                    playerView.setVisibility(View.GONE);
                    break;
            }
            View.OnLongClickListener showTime = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, message.time, Toast.LENGTH_SHORT).show();
                    return false;
                }
            };
            text.setOnLongClickListener(showTime);
            if (image != null)
                image.setOnLongClickListener(showTime);
        }
        private void setWho(String who, final String myImage, final String hisImage) {
            if (imageView != null) {
                final String image = who.equals("me") ? myImage : hisImage;
                Picasso.get().load(image).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
            }
        }
        private void setWho(String who) {
            if (imageView != null) {
                Picasso.get().load(who.equals("me") ? R.drawable.people : R.drawable.placeholder).into(imageView);
            }
        }
        public void LoadMessage(Activity context, Message message, String myImage, String hisImage) {
            setMessage(context, message);
            setWho(message.who, myImage, hisImage);
        }
        public void LoadSystemMessage(Activity context, Message message) {
            setMessage(context, message);
            setWho(message.who);
        }
        public void saveUrl(final File filename, final String urlString)
                throws IOException {
            filename.getParentFile().mkdirs();
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {
                in = new BufferedInputStream(new URL(urlString).openStream());
                fout = new FileOutputStream(filename);
                final byte[] data = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
        }
    }
}
