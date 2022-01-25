package com.stopgroup.stopcar.client.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stopgroup.stopcar.client.activity.AdActivity;
import com.stopgroup.stopcar.client.Modules.Login;
import com.stopgroup.stopcar.client.R;


import java.lang.reflect.Type;


/**
 * Created by pc on 01/06/2017.
 */

public class LoginSession {
    public static String token = "";
    public static String refresh_token = "";

    public static Long expired;
    public static boolean isLogin;
    public static SharedPreferences loginFile;
    public static int id;
    public static String email;
    public static String mobile;
    public static String fname;
    public static String lastname;
    public static String image;
    public static String county_id;
    public static String code;
    public static String currency;

    public static void setdata(Activity activity) {

        loginFile = activity.getSharedPreferences("LOGIN_FILE", 0);
        String x = loginFile.getString("json", "");
        Log.e("data", x);
        if (!x.equals("")) {
            Type dataType = new TypeToken<Login>() {
            }.getType();

            Login data = new Gson().fromJson(x, dataType);
            email = data.result.email;
            mobile = data.result.mobile;
            fname = data.result.first_name;
            lastname = data.result.last_name;
            image = data.result.image;
            county_id = String.valueOf(data.result.country_id);
            code = data.result.country_code;
            currency = data.result.currency;
            token = data.access_token;
            refresh_token = data.refresh_token;
            expired = data.expires_in;
            isLogin = true;
        } else {
            isLogin = false;
        }
    }

    public static Login getlogindata(Activity activity) {
        loginFile = activity.getSharedPreferences("LOGIN_FILE", 0);
        String x = loginFile.getString("json", "");
        Login data = null;
        Log.e("data", x);
        if (!x.equals("")) {
            isLogin = true;
            Type dataType = new TypeToken<Login>() {
            }.getType();
            data = new Gson().fromJson(x, dataType);
        } else {
            isLogin = false;
        }
        return data;
    }

    public static Login getlogindata(Context activity) {
        loginFile = activity.getSharedPreferences("LOGIN_FILE", 0);
        String x = loginFile.getString("json", "");
        Login data = null;
        Log.e("data", x);
        if (!x.equals("")) {
            isLogin = true;
            Type dataType = new TypeToken<Login>() {
            }.getType();
            data = new Gson().fromJson(x, dataType);
        } else {
            isLogin = false;
        }
        return data;
    }

    public static void setdat(Context activity) {

        loginFile = activity.getSharedPreferences("LOGIN_FILE", 0);
        String x = loginFile.getString("json", "");
        Log.e("data", x);
        if (!x.equals("")) {
            Type dataType = new TypeToken<Login>() {
            }.getType();
            Login data = new Gson().fromJson(x, dataType);
            email = data.result.email;
            mobile = data.result.mobile;
            fname = data.result.first_name;
            lastname = data.result.last_name;
            image = data.result.image;
            county_id = String.valueOf(data.result.country_id);
            code = data.result.country_code;
            currency = data.result.currency;
            token = data.access_token;
            refresh_token = data.refresh_token;
            expired = data.expires_in;
            isLogin = true;
        } else {
            isLogin = false;
        }
    }

    public static void clearData(Activity activity) {
        if (activity!= null){
            loginFile = activity.getSharedPreferences("LOGIN_FILE", 0);

            loginFile.edit().clear().apply();

            Intent i = new Intent(activity, AdActivity.class);
            activity.startActivity(i);
            activity.finish();

        }

    }

    public static void AddToSharedPreferences(Activity activity, String Name, String USERNAME, String phone, String Email, String IMAGE, String password, String lat, String lon, String token, String expired) {
        loginFile = activity.getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginFile.edit();
        editor.putString("NAME", Name);
        editor.putString("USERNAME", USERNAME);
        editor.putString("PHONE", phone);
        editor.putString("EMAIL", Email);
        editor.putString("IMAGE", IMAGE);
        editor.putString("password", password);
        editor.putBoolean("LOGIN", true);
        editor.putString("lat", lat);
        editor.putString("lon", lon);
        editor.putString("token", token);
        editor.putString("expired", expired);

        editor.apply();

    }

    public static void AddTotokenSharedPreferences(Activity activity, String token, String expired) {
        loginFile = activity.getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginFile.edit();
        editor.putString("token", token);
        editor.putString("expired", expired);

        editor.apply();

    }

    public static void AddTotokenSharedpassword(Activity activity, String password) {
        loginFile = activity.getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginFile.edit();
        editor.putString("password", password);

        editor.apply();

    }

    public static void makecall(final Activity activity, final String phone) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(
                activity);
        alert.setMessage(R.string.plz_login_first);
        alert.setPositiveButton(R.string.call,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        activity.startActivity(intent);
                        dialog.dismiss();
                    }
                });
//        alert.setNegativeButton(R.string.sms,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null)));
//
//                        dialog.dismiss();
//
//                    }
//                });
        alert.show();
    }

    private static void initLoginSharedPreference(Context context) {
        loginFile = context.getSharedPreferences("loginFile", Context.MODE_PRIVATE);
    }

//    public static void setSocialMediaData(Activity activity, SocialMediaModel data) {
//        initLoginSharedPreference(activity);
//        SharedPreferences.Editor editor = loginFile.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(data);
//        editor.putString(SOCIAL_MEDIA_DATA_KEY, json);
//        editor.apply();
//    }
//
//    public static SocialMediaModel getSocialMediaData(Activity activity) {
//        initLoginSharedPreference(activity);
//        Gson gson = new Gson();
//        String json = loginFile.getString(SOCIAL_MEDIA_DATA_KEY, "");
//        SocialMediaModel socialMediaModel = gson.fromJson(json, SocialMediaModel.class);
//
//        if (socialMediaModel != null)
//            return socialMediaModel;
//
//        return new SocialMediaModel();
//    }
}