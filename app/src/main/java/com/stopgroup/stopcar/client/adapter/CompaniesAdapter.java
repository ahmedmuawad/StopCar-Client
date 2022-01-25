package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.activity.CompanyDetailsActivity;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Modules.Companies;
import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;


/**
 * Created by سيد on 04/06/2017.
 */
public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.MyViewHolder> {
    private ArrayList<Companies.ResultBean> historyArrayList;
    Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout view_;
        private ImageView image;
        private TextView name;

        public MyViewHolder(View view) {
            super(view);
            view_ = view.findViewById(R.id.view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
        }
    }


    public CompaniesAdapter(Activity activity, ArrayList<Companies.ResultBean> historyArrayList) {
        this.historyArrayList = historyArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_companys, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(historyArrayList.get(position).company_name);
        try {
            Picasso.get().load(historyArrayList.get(position).company_img).into(holder.image);
        } catch (Exception e) {

        }
        holder.view_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (historyArrayList.get(position).subCategories.size()>0) {
                    Intent i = new Intent(activity, CompanyDetailsActivity.class);
                    Gdata.CompaniesResultBean = historyArrayList.get(position);
                    activity.startActivity(i);
                }
                else
                    Toast.makeText(activity, activity.getString(R.string.noVicals), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }


}
