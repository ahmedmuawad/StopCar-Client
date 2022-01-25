package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stopgroup.stopcar.client.activity.ContactActivity;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.Getcontact;
import com.stopgroup.stopcar.client.R;

import java.util.List;


/**
 * Created by سيد on 04/06/2017.
 */
public class Contact1adapter extends RecyclerView.Adapter<Contact1adapter.MyViewHolder> {
    Context context;
    private List<Getcontact.ResultBean> horizontalList;
    ContactActivity addressActivity = new ContactActivity();
    int id;
    public int pos = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private CheckBox check;
        private LinearLayout lin;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            lin = view.findViewById(R.id.lin);
            check = view.findViewById(R.id.check);
        }
    }


    public Contact1adapter(List<Getcontact.ResultBean> horizontalList) {
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
        holder.phone.setText(horizontalList.get(position).mobile );
        holder.check.setVisibility(View.GONE);
       holder.lin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               LoginSession.makecall((Activity) context,horizontalList.get(position).mobile);
           }
       });

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }



}
