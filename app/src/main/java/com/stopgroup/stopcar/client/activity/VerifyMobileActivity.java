package com.stopgroup.stopcar.client.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.R;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
public class VerifyMobileActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    private TextView text;
    private EditText number;
    private View btnSubmit;

    private TextView btnResendCode;


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        LoginSession.setdata(VerifyMobileActivity.this);

        FirebaseAuth.getInstance().setLanguageCode("ar");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("برجاء الانتظار");
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        number = findViewById(R.id.number);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnResendCode = findViewById(R.id.btnResendCode);
        title.setText(getString(R.string.verifyphone));
        text.setText(getString(R.string.dont_worry_just_enter_your_email_or_phone_below_and_we_will_send_you_the_password_reset_instructions) + " " + LoginSession.code + LoginSession.mobile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSendCode();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().equals("")) {
                    number.setError(getString(R.string.required));
                } else {
                    active();
                }
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(VerifyMobileActivity.this, "تم التفعيل بشكل تلقائى", Toast.LENGTH_SHORT).show();
                activeInApi();
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();

                Toast.makeText(VerifyMobileActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                progressDialog.dismiss();

                VerifyMobileActivity.this.verificationId = verificationId;
                Toast.makeText(VerifyMobileActivity.this, "تم ارسال الرمز لهاتفك بنجاح", Toast.LENGTH_SHORT).show();
            }
        };
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(      getFormattedPhone(),       60,TimeUnit.SECONDS, this, mCallbacks);



    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String verificationId = "";

    public void active() {
        progressDialog.show();
        if (verificationId.isEmpty()) {
            Toast.makeText(this, "برجاء انتظار استلام الرمز اولا", Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, number.getText().toString());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            activeInApi();
                        } else {
                            progressDialog.dismiss();
                           try {
                               if (task.getException().getLocalizedMessage().contains("The sms code has expired")){
                                   reSendCode();
                                   Toast.makeText(VerifyMobileActivity.this, "لقد انتهت مدة الرمز , تم ارسال رقم جديد لك", Toast.LENGTH_SHORT).show();

                               }else {
                                   Toast.makeText(VerifyMobileActivity.this, "عذرا رقم تحقق خاطئ", Toast.LENGTH_SHORT).show();

                               }
                           }catch (Exception e){
                               Toast.makeText(VerifyMobileActivity.this, "عذرا رقم تحقق خاطئ", Toast.LENGTH_SHORT).show();

                           }

                       }
                    }
                });
    }

    public String getFormattedPhone() {
        String final_phone = "";
        String countryCode = LoginSession.code;
        if (countryCode.equals("02") || countryCode.equals("+02") || countryCode.equals("+2")) {
            if (LoginSession.mobile.startsWith("+20")) {
                final_phone = LoginSession.mobile;
            }
            if (LoginSession.mobile.startsWith("20")) {
                final_phone = "+" + LoginSession.mobile;
            }
            if (LoginSession.mobile.startsWith("0")) {
                final_phone = "+2" + LoginSession.mobile;
            }
        } else if (countryCode.equals("218") || countryCode.equals("00218") || countryCode.equals("+218")) {

            if (LoginSession.mobile.startsWith( "+218")) {
                final_phone = LoginSession.mobile;
            }else if (LoginSession.mobile.startsWith("218")) {
                final_phone = "+" + LoginSession.mobile;
            }
            else if (LoginSession.mobile.startsWith( "0")) {
                final_phone = "+218" + LoginSession.mobile.substring(1);
            }else{
                final_phone = "+218" + LoginSession.mobile;
            }

        }else if (countryCode.equals("966") || countryCode.equals("00966") || countryCode.equals("+966")) {

            if (LoginSession.mobile.startsWith( "+966")) {
                final_phone = LoginSession.mobile;
            }
            if (LoginSession.mobile.startsWith("966")) {
                final_phone = "+" + LoginSession.mobile;
            }
            if (LoginSession.mobile.startsWith( "0")) {
                final_phone = "+966" + LoginSession.mobile.replaceFirst("05", "5");
            }

        }
        if (final_phone.isEmpty()){
            final_phone = "+2" +  LoginSession.mobile;
        }
        return final_phone;
    }



    public void reSendCode() {

        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(      getFormattedPhone(),       60,TimeUnit.SECONDS, this, mCallbacks);


    }

    private void activeInApi() {
        APIModel.getMethod(VerifyMobileActivity.this, "activate/" + LoginSession.mobile, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                APIModel.handleFailure(VerifyMobileActivity.this, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        activeInApi();
                    }
                });
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progressDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                // todo
                Intent i = new Intent(VerifyMobileActivity.this, PaymentActivity.class).putExtra("canBack", false);
                startActivity(i);
            }
        });
    }
}
