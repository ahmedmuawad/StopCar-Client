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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Helper.SocialContact;
import com.stopgroup.stopcar.client.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.stopgroup.stopcar.client.Helper.LoginSession.loginFile;

public class LoginActivity extends AppCompatActivity implements SocialContact {


    private TextView title;

    private EditText username;
    private EditText password;
    private View btnLogin;
    private TextView forgetpassBtn;
    private static ProgressBar progress;
    private ImageView back;

    private String TAG ="printKeyHash";
    public static String social_email;
    public static String social_id;
    public static String social_name;
    public static LoginActivity loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        initView();
        printKeyHash();
        printHashKey(this);

    }


    @Override
    public void account(String id, String email, String name) {
        social_email = email ;
        social_id = id ;
        social_name = name ;
        loginSocial();
    }
    private void printKeyHash() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.stopgroup.nashme.nashmeuser", PackageManager.GET_SIGNATURES);


            for (Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public  void printHashKey(Context pContext) {
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


    void login() {

        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, R.string.plz_fill_data, Toast.LENGTH_SHORT).show();
        } else {
            RequestParams arg = new RequestParams();
            arg.put("username", username.getText().toString());
            arg.put("password", password.getText().toString());
            arg.put("device_type", "1");
            String token =  FirebaseInstanceId.getInstance().getToken();
            if (token == null){
                arg.put("device_token", "");
            }else{
                arg.put("device_token", token);
            }
            APIModel.postMethod(this, "client/login", arg, new TextHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    APIModel.handleFailure(LoginActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                        @Override
                        public void onRefresh() {
                            login();
                        }
                    });
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    loginFile = getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginFile.edit();
                    editor.putString("json", responseString);
                    editor.apply();
                    LoginSession.setdata(LoginActivity.this);
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();

                }
            });

        }
    }


    public void loginSocial() {


        RequestParams arg = new RequestParams();
        arg.put("social_id", social_id);
        arg.put("email", social_email);
        arg.put("first_name", social_name);
        arg.put("device_token", FirebaseInstanceId.getInstance().getToken());
        APIModel.postMethod(loginActivity, "client/register/social", arg, new TextHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    APIModel.handleFailure(loginActivity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                        @Override
                        public void onRefresh() {
                            loginSocial();

                        }
                    });
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    loginFile = loginActivity.getSharedPreferences("LOGIN_FILE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginFile.edit();
                    editor.putString("json", responseString);
                    editor.apply();
                    LoginSession.setdata(loginActivity);
                    Intent i = new Intent(loginActivity, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    loginActivity.   startActivity(i);
                    loginActivity.  finish();

                }
            });


    }

    TextView     policyText ;
    AppCompatCheckBox policyCheck ;
    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        forgetpassBtn = findViewById(R.id.forgetpassBtn);
        policyText = findViewById(R.id.textPolicy);
        policyCheck = findViewById(R.id.checkBox);

        policyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this , HelpActivity.class) ;
                startActivity(i);
            }
        });

        if (Locale.getDefault().getLanguage().equals("ar")) {
            username.setGravity(Gravity.CENTER | Gravity.RIGHT);
            password.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }
        progress = findViewById(R.id.progress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!policyCheck.isChecked()){
//                    Toast.makeText(LoginActivity.this, R.string.must_accept_policy, Toast.LENGTH_SHORT).show();
//                }
//                else {
                    login();
//                }
            }
        });




        forgetpassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgetPassActivity.class);
                startActivity(intent);
            }
        });
    }



    public void firebaseWithGoogle(GoogleSignInAccount googleSignInAccount) {
        social_id = googleSignInAccount.getId();
        social_email = googleSignInAccount.getEmail();
        social_name = googleSignInAccount.getDisplayName();
        loginSocial();
        //AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

//        firebaseAurh.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    social_id = task.getResult().getUser().getUid();
//                    social_email = task.getResult().getUser().getEmail();
//                    social_name = task.getResult().getUser().getDisplayName();
//                    loginSocial();
//                }
//            }
//        });

    }





}
