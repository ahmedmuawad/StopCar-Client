package com.stopgroup.stopcar.client.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stopgroup.stopcar.client.R;

import java.util.ArrayList;

/**
 * Created by kemo on 7/30/2018.
 */

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

    public ClickListener clickListener;
    private Activity activity;
    private ArrayList<String> choicesArrayList;
    public int id = -1;

    public ChooseAdapter(Activity activity, ArrayList<String> choicesArrayList) {
        this.activity = activity;
        this.choicesArrayList = choicesArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.row_choose, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text.setText(choicesArrayList.get(position));

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(position);
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
        private TextView check;

        public ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text_view);
            check = itemView.findViewById(R.id.check);

        }
    }

}
