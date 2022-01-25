package com.stopgroup.stopcar.client.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stopgroup.stopcar.client.Helper.Closure;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Helper.HttpHelper;
import com.stopgroup.stopcar.client.Modules.EmergencyContacts;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.EmergencyAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmergencyContactsActivity extends AppCompatActivity   {

    private ImageView back;
    private ImageView more;
    private TextView title;
    private View add;

    LinearLayout mainLinear ;
    RecyclerView list ;
    ArrayList<EmergencyContacts.ResultContactsModel> contacts = new ArrayList<>();
    EmergencyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentActivity.setInstance(this);
        setContentView(R.layout.activity_emergency_contacts);
        initView();
        onclick();
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.fetchContacts(new Closure<EmergencyContacts>() {
            @Override
            public void run(EmergencyContacts args) {
                super.run(args);
                if (args.getResult().size() > 0) {
                    mainLinear.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    contacts.clear();
                    contacts.addAll(args.getResult());
                    adapter.notifyDataSetChanged();
                }else{
                    mainLinear.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        add = findViewById(R.id.add);
        more = findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        mainLinear=  findViewById(R.id.mainLinear);
        list =  findViewById(R.id.list);


        title.setText(getString(R.string.emergency_contacts));



    }
    private void onclick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContactActivity.class);
                startActivity(i);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.pos > -1){
                    updateContact();
                }
            }
        });
    }

    private void setup(){
        LinearLayoutManager layoutManage = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(layoutManage);
        adapter = new EmergencyAdapter(contacts);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void fetchContacts(final Closure<EmergencyContacts> closure){
        HttpHelper.httpHelper.get("client_contacts", new Closure<String>() {
            @Override
            public void run(String args) {
                super.run(args);
                Type dataType = new TypeToken<EmergencyContacts>() {
                }.getType();
                EmergencyContacts data =  new Gson().fromJson(args, dataType);
                closure.run(data);
            }
        });
    }

    public void updateContact() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(R.string.alert);
        alertDialog.setPositiveButton(getString(R.string.make_default), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                defaultContact();
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton(getString(R.string.delete),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteContact();
                dialog.dismiss();
            }
        });
        alertDialog.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void deleteContact(){
        try{
            HttpHelper.httpHelper.delete("client_contacts/" + contacts.get(adapter.pos).getId(), new Closure<String>() {
                @Override
                public void run(String args) {
                    super.run(args);
                    Log.e("response", "run: "+ args );

                }
            });
            contacts.remove(adapter.pos);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void defaultContact(){
        try{
            HttpHelper.httpHelper.put("client_contacts/default/" + contacts.get(adapter.pos).getId(), new Closure<String>() {
                @Override
                public void run(String args) {
                    super.run(args);
                    Log.e("response", "run: "+ args );
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
