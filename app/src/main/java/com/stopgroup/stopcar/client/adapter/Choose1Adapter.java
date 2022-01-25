package com.stopgroup.stopcar.client.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stopgroup.stopcar.client.Modules.Settings;
import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;

/**
 * Created by kemo on 7/30/2018.
 */

public class Choose1Adapter extends RecyclerView.Adapter<Choose1Adapter.ViewHolder> {

    public ClickListener clickListener;
    private Activity activity;
    private ArrayList<Settings.ResultBean.CancelReasonsBean> choicesArrayList;
    public int id = -1;

    public Choose1Adapter(Activity activity, ArrayList<Settings.ResultBean.CancelReasonsBean> choicesArrayList) {
        this.activity = activity;
        this.choicesArrayList = choicesArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.item_dialog_list, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text.setText(choicesArrayList.get(position).name);
        if (id == choicesArrayList.get(position).id) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.GONE);
        }
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = choicesArrayList.get(position).id;
                notifyDataSetChanged();
            }
        });

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return choicesArrayList.size();
    }


    public interface ClickListener {
        void onClick(int pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private View check;

        public ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text_view);
            check = itemView.findViewById(R.id.check);

        }
    }

}
