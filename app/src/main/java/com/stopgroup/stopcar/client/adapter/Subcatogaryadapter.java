package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.activity.SelectcarActivity;
import com.stopgroup.stopcar.client.Modules.Subcatogary;
import com.stopgroup.stopcar.client.R;

import java.util.List;


/**
 * Created by سيد on 04/06/2017.
 */
public class Subcatogaryadapter extends RecyclerView.Adapter<Subcatogaryadapter.MyViewHolder> {
    Context context;
    private List<Subcatogary.ResultBean> horizontalList;
    SelectcarActivity addressActivity = new SelectcarActivity();
    int id;
    public int pos = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView des;
        public ImageView check;
        public ImageView img;
        public View card;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            des = view.findViewById(R.id.des);
            check = view.findViewById(R.id.check);
            card = view.findViewById(R.id.card);
            img = view.findViewById(R.id.img);
        }
    }


    public Subcatogaryadapter(List<Subcatogary.ResultBean> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selectcar, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).name);
        holder.des.setText(horizontalList.get(position).description);
        Log.e("image",horizontalList.get(position).image);
        try {
            Picasso.get().load(horizontalList.get(position).image).into(holder.img);
        }catch (Exception e){

        }
        if (pos == position) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.GONE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }


}
