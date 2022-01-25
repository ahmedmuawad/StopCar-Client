package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stopgroup.stopcar.client.activity.CompanyDetailsActivity;
import com.stopgroup.stopcar.client.Helper.Gdata;
import com.stopgroup.stopcar.client.Modules.Companies;
import com.stopgroup.stopcar.client.R;

import java.util.List;


/**
 * Created by سيد on 04/06/2017.
 */
public class CompanyCatogaryAdapter extends RecyclerView.Adapter<CompanyCatogaryAdapter.MyViewHolder> {
    Context context;
    List<Companies.ResultBean.SubCategoriesBean> horizontalList ;
    int id;
    public int pos = -1;

    public CompanyCatogaryAdapter(CompanyDetailsActivity companyDetailsActivity, List<Companies.ResultBean.SubCategoriesBean> subCategories) {
    this.context = companyDetailsActivity ;
        this.horizontalList = subCategories;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView check;
        public LinearLayout view_;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            check = view.findViewById(R.id.check);
            view_ = view.findViewById(R.id.view_);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selectcar2, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).name);

        if (pos == position) {
            holder.check.setVisibility(View.VISIBLE);
            holder.view_.setBackgroundColor(Color.parseColor("#E1E3E4"));


        } else {
            holder.view_.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.check.setVisibility(View.GONE);
        }

        holder.view_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                Gdata.sub_category_id = horizontalList.get(position).id +"" ;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }


}
