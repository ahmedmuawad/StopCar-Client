package com.stopgroup.stopcar.client.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.R;
import com.stopgroup.stopcar.client.adapter.CompanyCatogaryAdapter;

public class CompanyDetailsActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title;
    private ImageView image;
    private TextView name;
    private RecyclerView list;
    private View continuation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        list = findViewById(R.id.list);
        continuation = findViewById(R.id.continuation);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title.setText(Gdata.CompaniesResultBean.company_name);
        name.setText(Gdata.CompaniesResultBean.company_name);
        try {
            Picasso.get().load(Gdata.CompaniesResultBean.company_img).into(image);
        } catch (Exception e) {

        }
        Gdata.sub_category_id = "";
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        CompanyCatogaryAdapter resultsAdapter = new CompanyCatogaryAdapter(this, Gdata.CompaniesResultBean.subCategories);
        list.setHasFixedSize(true);
        list.setAdapter(resultsAdapter);
        continuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Gdata.sub_category_id.equals("")) {
                } else {
                    Intent i = new Intent(CompanyDetailsActivity.this, SendOrderActivity.class);
                    startActivity(i);
                }

            }
        });

    }


}
