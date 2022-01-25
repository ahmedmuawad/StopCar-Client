package com.stopgroup.stopcar.client.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Helper.SocialContact;
import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.stopgroup.stopcar.client.Helper.LoginSession.loginFile;
public class RegActivity extends AppCompatActivity  {
    private TextView title;

    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private EditText password;
    private View next;
    private ImageView back;
    private Spinner spin;
    public ProgressBar progress;
    TextView policyText;
    AppCompatCheckBox policyCheck;
    private ArrayList<Settings.ResultBean.CountriesBean> countries = new ArrayList<>();
    int country_id;
    String countryCode = "" ;
    private String TAG = "printKeyHash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        onclick();

        printHashKey();

    }
    private void initView() {
        title = findViewById(R.id.title);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        next = findViewById(R.id.next);
        title.setText(getString(R.string.reg));
        back = findViewById(R.id.back);
        spin = findViewById(R.id.spin);
        if (Locale.getDefault().getLanguage().equals("ar")) {
            firstname.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lastname.setGravity(Gravity.CENTER | Gravity.RIGHT);
            email.setGravity(Gravity.CENTER | Gravity.RIGHT);
            phone.setGravity(Gravity.CENTER | Gravity.RIGHT);
            password.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }
        progress = findViewById(R.id.progress);
        getcountry();
        policyText = findViewById(R.id.textPolicy);
        policyCheck = findViewById(R.id.checkBox);
        policyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });
    }

    private void onclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstname.getText().toString().equals("")) {
                    firstname.setError(getString(R.string.required));
                    return;
                }
                if (lastname.getText().toString().equals("")) {
                    lastname.setError(getString(R.string.required));
                    return;
                }


                if (email.getText().toString().equals("")) {
                    email.setError(getString(R.string.required));
                    return;
                }
                if (!Gdata.emailValidator(email.getText().toString().trim())) {
                    email.setError(getString(R.string.emailnotcorrect));
                    return;
                }

                if (password.getText().toString().trim().equals("")) {
                    password.setError(getString(R.string.required));
                    return;
                }


                String mobileStr =  phone.getText().toString();


                if (mobileStr.equals("")) {
                    phone.setError(getString(R.string.required));
                    return;
                }
                if (mobileStr.length() < 9) {
                    phone.setError("تاكد من ادخال هاتف صحيح");
                    return;
                }
                if (countryCode.equals("02") || countryCode.equals("+02") || countryCode.equals("+2")) {
                    if (mobileStr.startsWith( "+966")||mobileStr.startsWith("966")||mobileStr.startsWith( "05") || mobileStr.startsWith( "5")) {

                        Toast.makeText(RegActivity.this,  "يبدوا ان الهاتف الذى ادخلته تابع للسعوديه وليس لمصر", Toast.LENGTH_SHORT).show();


                        return;
                    }

                }



                if (!policyCheck.isChecked()) {
                    Toast.makeText(RegActivity.this, R.string.must_accept_policy, Toast.LENGTH_SHORT).show();
                    return;
                }
                register();
            }
        });

    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }



    private void register() {
        progress.setVisibility(View.VISIBLE);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                RequestParams requestParams = new RequestParams();
                requestParams.put("email", email.getText().toString());
                requestParams.put("first_name", firstname.getText().toString());
                requestParams.put("last_name", lastname.getText().toString());
                requestParams.put("password", password.getText().toString());
                requestParams.put("mobile", phone.getText().toString());
                requestParams.put("country_id", country_id + "");
                requestParams.put("device_type", "1");
                requestParams.put("device_token", instanceIdResult.getToken());
                APIModel.postMethod(RegActivity.this, "client/register", requestParams, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        APIModel.handleFailure(RegActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                register();
                            }
                        });
                        progress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        loginFile = getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginFile.edit();
                        editor.putString("json", responseString);
                        editor.apply();
                        LoginSession.setdata(RegActivity.this);
                        Intent i = new Intent(RegActivity.this, VerifyMobileActivity.class);
                        i.putExtra("pass", password.getText().toString());
                        startActivity(i);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void getcountry() {
        APIModel.getMethod(RegActivity.this, "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(RegActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getcountry();
                    }
                });
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<Settings>() {
                }.getType();
                Settings data = new Gson().fromJson(responseString, dataType);
                countries.addAll(data.result.countries);
                ArrayAdapter<Settings.ResultBean.CountriesBean> adapter = new ArrayAdapter<Settings.ResultBean.CountriesBean>(RegActivity.this, android.R.layout.simple_spinner_dropdown_item, countries) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = inflater.inflate(R.layout.item_code, null, false);
                        TextView txtTitle = convertView.findViewById(R.id.code);
                        ImageView imageView = convertView.findViewById(R.id.img);
                        if (countries.size() > 0) {
                            try {
                                txtTitle.setText(countries.get(position).code);
                                try {
                                    Picasso.get().load(countries.get(position).image).into(imageView);
                                } catch (Exception e) {
                                }
                                country_id = countries.get(position).id;
                                countryCode = countries.get(position).code;
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                        return convertView;
                    }
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = inflater.inflate(R.layout.item_code1, null, false);
                        TextView txtTitle = convertView.findViewById(R.id.code);
                        ImageView imageView = convertView.findViewById(R.id.img);
                        if (countries.size() > 0) {
                            try {
                                txtTitle.setText(countries.get(position).code);
                                try {
                                    Picasso.get().load(countries.get(position).image).into(imageView);
                                } catch (Exception e) {
                                }
                                country_id = countries.get(position).id;
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                        return convertView;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
        });
    }

}
