package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stopgroup.stopcar.client.activity.EditlocActivity;
import com.stopgroup.stopcar.client.Modules.PlaceApi;
import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;


/**
 * Created by سيد on 04/06/2017.
 */
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {
    private ArrayList<PlaceApi> placeArrayList;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTxv, locationTxv;
        public ImageView edit_imv;

        public MyViewHolder(View view) {
            super(view);
            nameTxv = view.findViewById(R.id.name_txv);
            locationTxv = view.findViewById(R.id.location_txv);
            edit_imv = view.findViewById(R.id.edit_imv);
        }
    }


    public PlacesAdapter(Activity activity, ArrayList<PlaceApi> placeArrayList) {
        this.placeArrayList = placeArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_place, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.nameTxv.setText(placeArrayList.get(position).name);
        holder.locationTxv.setText(placeArrayList.get(position).location);
        holder.edit_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, EditlocActivity.class);
                i.putExtra("name", placeArrayList.get(position).name);
                i.putExtra("lat", String.valueOf(placeArrayList.get(position).lat));
                i.putExtra("lon", String.valueOf(placeArrayList.get(position).lng));
                i.putExtra("id", placeArrayList.get(position).id);
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }


}
