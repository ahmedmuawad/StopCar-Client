package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.activity.ArriaveActivity;
import com.stopgroup.stopcar.client.activity.ArriaveFragment;
import com.stopgroup.stopcar.client.Helper.Closure;
import com.stopgroup.stopcar.client.Helper.HttpHelper;
import com.stopgroup.stopcar.client.Modules.History;
import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;


/**
 * Created by سيد on 04/06/2017.
 */
public class CurrentTripsAdapter extends RecyclerView.Adapter<CurrentTripsAdapter.MyViewHolder> {
    private ArrayList<History.ResultBean> historyArrayList;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView costTxv, nameTxv, locationTxv, statusTxv, dateTxv;
        public TextView categoryType;
        public ImageView imageImv;
        public View received;
        public View card_view;

        public MyViewHolder(View view) {
            super(view);
            nameTxv = view.findViewById(R.id.name_txv);
            locationTxv = view.findViewById(R.id.location_txv);
            costTxv = view.findViewById(R.id.cost_txv);
            statusTxv = view.findViewById(R.id.status_txv);
            dateTxv = view.findViewById(R.id.date_txv);
            imageImv = view.findViewById(R.id.image_imv);
            received = view.findViewById(R.id.received);
            card_view = view.findViewById(R.id.card_view);
            categoryType = view.findViewById(R.id.city_txv);
            //categoryType.setVisibility();
        }
    }


    public CurrentTripsAdapter(Activity activity, ArrayList<History.ResultBean> historyArrayList) {
        this.historyArrayList = historyArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (historyArrayList.get(position).driver != null) {
            holder.nameTxv.setText(historyArrayList.get(position).driver.first_name);
        }

        holder.statusTxv.setText(historyArrayList.get(position).status_text);
        holder.costTxv.setText(historyArrayList.get(position).total_price + "");
        holder.locationTxv.setText(historyArrayList.get(position).to_location);
        holder.dateTxv.setText(historyArrayList.get(position).created_at);
        try {
            holder.categoryType.setText(historyArrayList.get(position).sub_category_name);

        } catch (Exception e) {
            holder.categoryType.setText(historyArrayList.get(position).category_name);

        }
        try {
            Picasso.get().load(historyArrayList.get(position).driver.image);
        } catch (Exception a) {
            a.printStackTrace();
        }

        if (historyArrayList.get(position).status == 2 ) {
            holder.statusTxv.setTextColor(Color.parseColor("#DC0000"));
            holder.received.setVisibility(View.GONE);
        } else if (historyArrayList.get(position).status < 7 && historyArrayList.get(position).status != 2 && historyArrayList.get(position).status > 0) {
            holder.statusTxv.setTextColor(Color.parseColor("#35a73e"));
            String categoryPrice = historyArrayList.get(position).category_calculating_pricing;
            if(categoryPrice.equals("tanks") || categoryPrice.equals("truck_water") || categoryPrice.equals("companies")) {
                holder.received.setVisibility(View.VISIBLE);
            } else {
                holder.received.setVisibility(View.GONE);
            }
            if(historyArrayList.get(position).status == 6) {
                holder.received.setVisibility(View.GONE);
            }
            holder.received.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpHelper.httpHelper.get("trips/request/company/finish/" + historyArrayList.get(position).id, new Closure<String>() {
                        @Override
                        public void run(String args) {
                            super.run(args);
                            Log.e("response", "run: " + args);
                            historyArrayList.get(position).status = 7;
                            notifyDataSetChanged();
                        }
                    });

                }
            });

        } else {
            holder.received.setVisibility(View.GONE);
            holder.statusTxv.setTextColor(Color.parseColor("#35a73e"));
        }
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ArriaveActivity.class);
                i.putExtra("trip_id", historyArrayList.get(position).id);
                activity.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }


}
