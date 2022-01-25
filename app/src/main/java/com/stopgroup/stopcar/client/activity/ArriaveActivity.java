package com.stopgroup.stopcar.client.activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.activity.chat_libs.PermissionsActivity;
public class ArriaveActivity extends PermissionsActivity {
    ArriaveFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_arriave2);
        fragment = new ArriaveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("trip_id" , getIntent().getIntExtra("trip_id" ,0));
        fragment.setArguments(bundle);
        fragment.setFinishListener(new ArriaveFragment.FinishListener() {
            @Override
            public void onFinish(boolean tripDone) {
                finish();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        fragment.onBackPressed();

    }
}