package com.stopgroup.stopcar.client.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Helper.PaginatorData;
import com.stopgroup.stopcar.client.Helper.camera.Camera;
import com.stopgroup.stopcar.client.Helper.camera.ImageConverter;
import com.stopgroup.stopcar.client.Modules.PlaceApi;
import com.stopgroup.stopcar.client.Modules.PlacesResponse;
import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.PlacesAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.stopgroup.stopcar.client.Helper.LoginSession.loginFile;
import static com.stopgroup.stopcar.client.Helper.camera.Camera.compressImage;
import static com.stopgroup.stopcar.client.Helper.camera.Camera.getRealPathFromURI;
import static com.stopgroup.stopcar.client.Helper.camera.Camera.myBitmap;
import static com.stopgroup.stopcar.client.Helper.camera.Camera.selectedImagePath;
import static com.stopgroup.stopcar.client.Helper.camera.Camera.sourceFile;
public class ProfileActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    private Spinner spin;
    private RecyclerView placesRecycler;
    private CircleImageView image;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private ArrayList<Settings.ResultBean.CountriesBean> countries = new ArrayList<>();
    private ArrayList<PlaceApi> placeArrayList = new ArrayList<>();
    int country_id;
    PaginatorData paginatorData = new PaginatorData();
    PlacesAdapter placesAdapter;
    private RelativeLayout progrsMoreDataLayout;
    private RelativeLayout progrsDataLayout;
    String image1 = "", image2 = "", check;
    private FancyButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        onclick();
    }
    private void initView() {
        Camera.activity = ProfileActivity.this;
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        spin = findViewById(R.id.spin);
        spin.setEnabled(false);
        placesRecycler = findViewById(R.id.places_recycler);
        image = findViewById(R.id.image);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        title.setText(getString(R.string.edit_profile));
        Picasso.get().load(LoginSession.getlogindata(ProfileActivity.this).result.image).into(image);
        firstname.setText(LoginSession.getlogindata(ProfileActivity.this).result.first_name);
        lastname.setText(LoginSession.getlogindata(ProfileActivity.this).result.last_name);
        email.setText(LoginSession.getlogindata(ProfileActivity.this).result.email);
        phone.setText(LoginSession.getlogindata(ProfileActivity.this).result.mobile);
        placesRecycler = findViewById(R.id.places_recycler);
        paginatorData.progressRel = findViewById(R.id.progrsData_layout);
        placesAdapter = new PlacesAdapter(this, placeArrayList);
        placesRecycler.setHasFixedSize(true);
        placesRecycler.setAdapter(placesAdapter);
        getcountry();
        getPlaces();
        progrsMoreDataLayout = findViewById(R.id.progrsMoreData_layout);
        progrsDataLayout = findViewById(R.id.progrsData_layout);
        add = findViewById(R.id.add);
    }
    private void getcountry() {
        APIModel.getMethod(ProfileActivity.this, "configs", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(ProfileActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getcountry();
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<Settings>() {
                }.getType();
                Settings data = new Gson().fromJson(responseString, dataType);
                countries.addAll(data.result.countries);
                ArrayAdapter<Settings.ResultBean.CountriesBean> adapter = new ArrayAdapter<Settings.ResultBean.CountriesBean>(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, countries) {
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
                for (int i = 0; i < countries.size(); i++) {
                    if (countries.get(i).id == LoginSession.getlogindata(ProfileActivity.this).result.country_id) {
                        spin.setSelection(i);
                        break;
                    }
                }
            }
        });
    }
    private void getPlaces() {
        paginatorData.progressRel.setVisibility(View.VISIBLE);
        APIModel.getMethod(ProfileActivity.this, "client_places", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(ProfileActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        getPlaces();
                    }
                });
                paginatorData.progressRel.setVisibility(View.GONE);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type dataType = new TypeToken<PlacesResponse>() {
                }.getType();
                PlacesResponse data = new Gson().fromJson(responseString, dataType);
                placeArrayList.addAll(data.result);
                placesAdapter.notifyDataSetChanged();
                paginatorData.progressRel.setVisibility(View.GONE);
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
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.cameraOperation();
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        placesRecycler.setLayoutManager(linearLayoutManager);
        placesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (!paginatorData.loading && !paginatorData.empty) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 10) {
                        getPlaces();
                    }
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddlocActivity.class);
                startActivity(i);
            }
        });
    }
    public void save() {
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
        if (!Gdata.emailValidator(email.getText().toString().trim())) {
            email.setError(getString(R.string.emailnotcorrect));
        }
        if (Gdata.emailValidator(email.getText().toString().trim()) && !firstname.getText().toString().trim().equals("") && !lastname.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !phone.getText().toString().trim().equals("")) {
            register();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//-----------------------------------------------------------------------------------------------------------------------------------
                myBitmap = (Bitmap) data.getExtras().get("data");
                Uri tempUri = Camera.getImageUri(ProfileActivity.this, myBitmap);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                selectedImagePath = String.valueOf(finalFile);
                sourceFile = new File(compressImage(selectedImagePath));
//                myBitmap = BitmapFactory.decodeFile(sourceFile.getAbsolutePath());
                image1 = ImageConverter.convert(myBitmap);
                image.setImageBitmap(myBitmap);
                save();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(ProfileActivity.this,
                        R.string.camera_closed, Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(ProfileActivity.this,
                        R.string.failed_to_open_camera, Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == Camera.PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(ProfileActivity.this,
                        " Failed to select picture", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            Uri selectedImageUri = data.getData();
            try {
                myBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Setting the Bitmap to ImageView
            selectedImagePath = Camera.getPath(selectedImageUri);
            sourceFile = new File((selectedImagePath));
        }
        try {
            image1 = ImageConverter.convert(myBitmap);
            image.setImageBitmap(myBitmap);
        } catch (OutOfMemoryError a) {
            a.printStackTrace();
        } catch (NullPointerException a) {
            a.printStackTrace();
        } catch (RuntimeException a) {
            a.printStackTrace();
        }
    }
    private void register() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("email", email.getText().toString());
        requestParams.put("first_name", firstname.getText().toString());
        requestParams.put("last_name", lastname.getText().toString());
        requestParams.put("mobile", phone.getText().toString());
        requestParams.put("country_id", country_id + "");
        requestParams.put("image", image1);
        APIModel.putMethod(ProfileActivity.this, "client/update", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(ProfileActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        register();
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("fdd", "vghvgh");
                try {
                    JSONObject jo = new JSONObject(responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    jsonObject.put("token_type", LoginSession.getlogindata(ProfileActivity.this).token_type);
                    jsonObject.put("access_token", LoginSession.getlogindata(ProfileActivity.this).access_token);
                    jsonObject.put("refresh_token", LoginSession.getlogindata(ProfileActivity.this).refresh_token);
                    jsonObject.put("statusCode", LoginSession.getlogindata(ProfileActivity.this).statusCode);
                    jsonObject.put("statusText", LoginSession.getlogindata(ProfileActivity.this).statusText);
                    loginFile = getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginFile.edit();
                    editor.putString("json", jsonObject.toString());
                    editor.apply();
                    LoginSession.setdat(ProfileActivity.this);
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    Toast.makeText(ProfileActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
