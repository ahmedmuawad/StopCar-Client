package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.Helper.CurrentActivity;
import com.stopgroup.stopcar.client.Modules.EmergencyContacts;
import com.stopgroup.stopcar.client.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by سيد on 04/06/2017.
 */
public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.MyViewHolder> {
    Context context;
    private List<EmergencyContacts.ResultContactsModel> horizontalList;
    int id;
    public int pos = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private CheckBox check;
        private CircleImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            check = view.findViewById(R.id.check);
            image =  view.findViewById(R.id.contactImage);
        }
    }


    public EmergencyAdapter(List<EmergencyContacts.ResultContactsModel> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).getName());
        holder.phone.setText(horizontalList.get(position).getMobile() );
        Picasso.get().load(horizontalList.get(position).getImage()).into(holder.image);

        if (pos == position){
            holder.check.setChecked(true);
        }else {
            holder.check.setChecked(false);
        }
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == pos){
                    pos = -1;
                }else{
                    pos = position ;
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }



}
