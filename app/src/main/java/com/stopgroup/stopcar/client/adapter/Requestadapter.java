package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.activity.HomeActivity;
import com.stopgroup.stopcar.client.activity.RequestActivity;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.Order;
import com.stopgroup.stopcar.client.R;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by سيد on 04/06/2017.
 */
public class Requestadapter extends RecyclerView.Adapter<Requestadapter.MyViewHolder> {
    Context context;
    private List<Order.ResultBean.RequestsBean> horizontalList;
    RequestActivity addressActivity = new RequestActivity();
    int id;
    public int pos = -1;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img;
        private TextView name;
        private TextView price;
        private TextView accept;

        public MyViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            accept = view.findViewById(R.id.accept);
        }
    }


    public Requestadapter(List<Order.ResultBean.RequestsBean> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_request, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).driver.first_name + " " + horizontalList.get(position).driver.last_name);
        holder.price.setText(horizontalList.get(position).price + " " + LoginSession.getlogindata(context).result.currency);
        try {
            Picasso.get().load(horizontalList.get(position).driver.image).into(holder.img);
        } catch (Exception e) {

        }
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptttripwithprice(horizontalList.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    private void acceptttripwithprice(final int price) {
        APIModel.getMethod(addressActivity, "trips/accept/price/" + Integer.valueOf(price), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(addressActivity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        acceptttripwithprice(price);
                    }
                });

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i = new Intent(context, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });
    }

}
