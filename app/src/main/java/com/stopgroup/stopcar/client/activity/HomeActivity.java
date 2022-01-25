package com.stopgroup.stopcar.client.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Fragment.HomeFragment;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.History;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.activity.chat_libs.PermissionsActivity;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.stopgroup.stopcar.client.Fragment.HomeFragment.REQUEST_LOCATION;
public class HomeActivity extends PermissionsActivity {
    //    public static final String TAG_HOME = "home";
//    public static final String TAG_History = "history";
//    public static final String TAG_NOTIFICATIONS = "notifications";
//    public static final String TAG_SETTINGS = "settings";
//    public static final String TAG_HELP = "help";
    private DrawerLayout drawer;
    private TextView titleTxv;
    private FrameLayout frame;
    private NavigationView navView;
    View headerview;
    TextView name;
    TextView email;
    CircleImageView image;
    FrameLayout view_container;
    private FragmentManager fragmentManager;
    public String selectedFragmentTag = "";
    public static int navItemIndex = 0;
    public static String[] activityTitles;
    private DrawerLayout drawerLayout;
    private ImageView icMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        onclick();
        UpdateTrip();


    }
    int Refresh_Time=2000;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
//code
            timerHandler.postDelayed(this, Refresh_Time);
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable,Refresh_Time);
    }
    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }
    public void UpdateTrip(){
        APIModel.getMethod(this, "client/current/trips", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<History>() {
                }.getType();
                History response = new Gson().fromJson(responseString, dataType);
                int id = 0;
                if (response.result.size() > 0) {
                    if (response.result.get(0).status != 2) {
                        id = response.result.get(0).id;
                    }
                    for (History.ResultBean trip : response.result) {
                        if (trip.category_id == 2) {
                            if (response.result.get(0).status != 2) {
                                if(trip.id>id){
                                    id = trip.id;
                                }

                            }
                        }
                    }

                }
                if (id != 0) {
                    inHome = false;
                    fragment = new ArriaveFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("trip_id", id);
                    bundle.putBoolean("hide_action", true);
                    fragment.setArguments(bundle);
                    fragment.setFinishListener(new ArriaveFragment.FinishListener() {
                        @Override
                        public void onFinish(boolean tripDone) {
                            inHome = true;
                            if (tripDone) {
                                if(!inHome){
                                    inHome = true;
                                    goHome();
                                }

                            } else {
                                finish();

                            }
                        }
                    });
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }
    boolean inHome = true;
    ArriaveFragment fragment;
    private void initView() {
        drawer = findViewById(R.id.drawer_layout);
        titleTxv = findViewById(R.id.title_txv);
        frame = findViewById(R.id.frame);
        navView = findViewById(R.id.nav_view);
        headerview = navView.getHeaderView(0);
        view_container = headerview.findViewById(R.id.view_container);
        name = headerview.findViewById(R.id.user_name_txv);
        email = headerview.findViewById(R.id.user_email_txv);
        image = headerview.findViewById(R.id.user_image_imv);
        String firstname = LoginSession.getlogindata(this).result.first_name;
        String lastname = LoginSession.getlogindata(this).result.last_name == null ? "" : LoginSession.getlogindata(this).result.last_name;
        name.setText(firstname + " " + lastname);
        Picasso.get().load(LoginSession.getlogindata(this).result.image).into(image);
        icMenu = findViewById(R.id.ic_menu);
        setUpNavigationView();
        if (getIntent().hasExtra("trip_id")) {
            Intent i = new Intent(this, ArriaveActivity.class);
            i.putExtra("trip_id", Integer.parseInt(getIntent().getStringExtra("trip_id")));
            getIntent().removeExtra("trip_id");
            startActivity(i);
        }
        goHome();
    }
    public void goHome() {
        inHome = true;
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    private void onclick() {
        view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
                closeDrawer();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
    }
    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    public void listeners() {
    }
    @Override
    public void onBackPressed() {
        if (!inHome) {
         fragment.onBackPressed();
            return;
        }
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            fragmentManager.popBackStack();
            super.onBackPressed();
        }
    }
    private void setUpNavigationView() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        break;
                    case R.id.new_trip:
                        startActivity(new Intent(HomeActivity.this, SelectcarActivity.class));
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 3;
                        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(i);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_payment:
                        navItemIndex = 4;
                        Intent ii = new Intent(getApplicationContext(), PaymentActivity.class);
                        startActivity(ii);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_help:
                        navItemIndex = 5;
                        Intent iii = new Intent(getApplicationContext(), HelpActivity.class);
                        startActivity(iii);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_current_trips:
                        navItemIndex = 6;
                        startActivity(new Intent(getApplicationContext(), CurrentTripsActivity.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_history:
                        navItemIndex = 7;
                        Intent iiii = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(iiii);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 8;
                        Intent a = new Intent(getApplicationContext(), NotActivity.class);
                        startActivity(a);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contact_us:
                        navItemIndex = 9;
                        Intent iiiiii = new Intent(getApplicationContext(), ContactUsActivity.class);
                        startActivity(iiiiii);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 10;
                        drawer.closeDrawer(GravityCompat.START);
                        logout();
                        break;
                    default:
                        navItemIndex = 0;
                }
                menuItem.setChecked(true);

                /*if (menuItem.isCheckable()) {
                    if (menuItem.isChecked()) {
                        menuItem.setChecked(false);
                    } else {
                        menuItem.setChecked(true);
                    }


                    // loadHomeFragment();
                }*/
                return true;
            }
        });
        icMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }
    private void logout() {
        APIModel.getMethod(HomeActivity.this, "logout", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LoginSession.clearData(HomeActivity.this);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LoginSession.clearData(HomeActivity.this);
            }
        });
    }
    //callback method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                                overridePendingTransition(0, 0);
                                startActivity(i);
                                overridePendingTransition(0, 0);
                            }
                        }, 1000);
                        // All required changes were successfully made
//
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
//                        Toast.makeText(getActivity(), "Gps Canceled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
//        finish();
    }
}
