package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stopgroup.stopcar.client.activity.ContactActivity;
import com.stopgroup.stopcar.client.Modules.Contact;
import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by سيد on 04/06/2017.
 */
public class Contactadapter extends RecyclerView.Adapter<Contactadapter.MyViewHolder> {
    Context context;
    private List<Contact> horizontalList;
    ContactActivity addressActivity = new ContactActivity();
    int id;
    public int pos = -1;

    public List<Integer> selected = new ArrayList<>();

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


    public Contactadapter(List<Contact> horizontalList) {
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
        holder.name.setText(horizontalList.get(position).name);
        holder.phone.setText(horizontalList.get(position).phone );
        if(horizontalList.get(position).image != null){
            holder.image.setImageURI(horizontalList.get(position).image);
        }else{
            holder.image.setImageResource(R.drawable.man);
        }
        holder.image.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) holder.image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        horizontalList.get(position).bitMap = bitmap;

        boolean removed = false;
        for (int i = 0; i < selected.size(); i++) {
            if(selected.get(i) == position){
                removed = true;
                holder.check.setChecked(true);
                break;
            }
        }
        if (!removed){
            holder.check.setChecked(false);
        }


        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position ;
                boolean removed = false;
                for (int i = 0; i < selected.size(); i++) {
                    if(selected.get(i) == position){
                        selected.remove(i);
                        removed = true;
                        break;
                    }
                }
                if (!removed){
                    selected.add(position);
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
