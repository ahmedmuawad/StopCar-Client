package com.stopgroup.stopcar.client.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.stopgroup.stopcar.client.Helper.LoginSession.loginFile;

public class SettingsActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private TextView changepass;
    private TextView emgency;
    Dialog dialog;
    private TextView lang1;
String lang = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        changepass = findViewById(R.id.changepass);
        emgency = findViewById(R.id.emgency);
        title.setText(getString(R.string.settings));
        lang1 = findViewById(R.id.lang);
    }

    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        emgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EmergencyContactsActivity.class);
                startActivity(i);
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_changepassword);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = display.getWidth();
                dialog.getWindow().setLayout((width * 17 / 20), LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText code = dialog.findViewById(R.id.code);
                final EditText oldPassword = dialog.findViewById(R.id.old_password);
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
                        if (code.getText().toString().trim().equals("") || oldPassword.getText().toString().trim().equals("")) {
                            code.setError(getString(R.string.required));
                            oldPassword.setError(getString(R.string.required));
                        } else {
                            register(code.getText().toString(),oldPassword.getText().toString());
                        }

                    }
                });
                dialog.show();
            }
        });
        lang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLanguageDialog();
            }
        });
    }

    private void register(final String pass,final String oldPassword) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("password", pass);
        requestParams.put("old_password", oldPassword);
        APIModel.putMethod(SettingsActivity.this, "client/update", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(SettingsActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        register(pass,oldPassword);
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("fdd", "vghvgh");
                try {
                    JSONObject jo = new JSONObject(responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    jsonObject.put("token_type", LoginSession.getlogindata(SettingsActivity.this).token_type);
                    jsonObject.put("access_token", LoginSession.getlogindata(SettingsActivity.this).access_token);
                    jsonObject.put("refresh_token", LoginSession.getlogindata(SettingsActivity.this).refresh_token);
                    jsonObject.put("statusCode", LoginSession.getlogindata(SettingsActivity.this).statusCode);
                    jsonObject.put("statusText", LoginSession.getlogindata(SettingsActivity.this).statusText);
                    loginFile = getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginFile.edit();
                    editor.putString("json", jsonObject.toString());
                    editor.apply();
                    LoginSession.setdat(SettingsActivity.this);
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void openLanguageDialog() {
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lang_dialog);
        dialog.setCancelable(true);
        View okBtn = dialog.findViewById(R.id.ok_button);
        View cancel = dialog.findViewById(R.id.cancel_button);

        final RadioButton arabic = dialog.findViewById(R.id.radio_arabic);
        RadioButton english = dialog.findViewById(R.id.radio_english);


        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang = "ar";
            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang = "en";
            }
        });

        if (Locale.getDefault().getLanguage().equals("en")) {
            arabic.setGravity(Gravity.LEFT | Gravity.CENTER);
        } else if (Locale.getDefault().getLanguage().equals("ar")) {

        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lang.equals("")) {
                    Toast.makeText(SettingsActivity.this, R.string.plz_select_lang, Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                    Locale locale = new Locale(lang);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    SettingsActivity.this.getResources().updateConfiguration(config, null);
                    prefs.edit().putString("lang", lang).apply();

                    Intent i = new Intent(SettingsActivity.this, HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    SettingsActivity.this.finish();
                    dialog.dismiss();
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
