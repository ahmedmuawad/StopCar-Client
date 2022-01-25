package com.stopgroup.stopcar.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.stopgroup.stopcar.client.Helper.LoginSession.loginFile;

public class EditActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private Spinner spin;
    private EditText phone;
    private EditText password;
    private View next;
    private ProgressBar progress;
    private ArrayList<Settings.ResultBean.CountriesBean> countries = new ArrayList<>();
    int country_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        onclick();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        spin = findViewById(R.id.spin);
        phone = findViewById(R.id.phone);
        next = findViewById(R.id.next);
        progress = findViewById(R.id.progress);
        title.setText(getString(R.string.edit_profile));
        getcountry();
        firstname.setText(LoginSession.getlogindata(EditActivity.this).result.first_name);
        lastname.setText(LoginSession.getlogindata(EditActivity.this).result.last_name);
        phone.setText(LoginSession.getlogindata(EditActivity.this).result.mobile);
        email.setText(LoginSession.getlogindata(EditActivity.this).result.email);

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
                }
                if (lastname.getText().toString().equals("")) {
                    lastname.setError(getString(R.string.required));
                }
                if (phone.getText().toString().equals("")) {
                    phone.setError(getString(R.string.required));
                }
                if (email.getText().toString().equals("")) {
                    email.setError(getString(R.string.required));
                }
                if (phone.getText().toString().length() != 9) {
                    phone.setError(getString(R.string.ph));
                }
                if (!Gdata.emailValidator(email.getText().toString().trim())) {
                    email.setError(getString(R.string.emailnotcorrect));
                }
                if (phone.getText().toString().length() == 9 && Gdata.emailValidator(email.getText().toString().trim()) && !firstname.getText().toString().trim().equals("") && !lastname.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !phone.getText().toString().trim().equals("")) {
                    register();
                }
            }
        });
    }

    private void register() {
        progress.setVisibility(View.VISIBLE);
        RequestParams requestParams = new RequestParams();
        requestParams.put("email", email.getText().toString());
        requestParams.put("first_name", firstname.getText().toString());
        requestParams.put("last_name", lastname.getText().toString());
        requestParams.put("mobile", phone.getText().toString());
        requestParams.put("country_id", country_id + "");
        APIModel.putMethod(EditActivity.this, "client/update", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(EditActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        register();
                    }
                });

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("fdd", "vghvgh");
                try {
                    JSONObject jo = new JSONObject(responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    jsonObject.put("token_type", LoginSession.getlogindata(EditActivity.this).token_type);
                    jsonObject.put("access_token", LoginSession.getlogindata(EditActivity.this).access_token);
                    jsonObject.put("refresh_token", LoginSession.getlogindata(EditActivity.this).refresh_token);
                    jsonObject.put("statusCode", LoginSession.getlogindata(EditActivity.this).statusCode);
                    jsonObject.put("statusText", LoginSession.getlogindata(EditActivity.this).statusText);
                    loginFile = getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginFile.edit();
                    editor.putString("json", jsonObject.toString());
                    editor.apply();
                    LoginSession.setdat(EditActivity.this);
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getcountry() {
        APIModel.getMethod(EditActivity.this, "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(EditActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
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
                ArrayAdapter<Settings.ResultBean.CountriesBean> adapter = new ArrayAdapter<Settings.ResultBean.CountriesBean>(EditActivity.this, android.R.layout.simple_spinner_dropdown_item, countries) {

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
                for (int i = 0 ; i<countries.size() ; i++){
                    if (countries.get(i).id == LoginSession.getlogindata(EditActivity.this).result.country_id){
                        spin.setSelection(i);
                        break;
                    }
                }
                progress.setVisibility(View.GONE);
            }
        });
    }
}
