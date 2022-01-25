package com.stopgroup.stopcar.client.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.stopgroup.stopcar.client.activity.CarOrderActivity;
import com.stopgroup.stopcar.client.activity.SelectcarActivity;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.Subcatogary;
import com.stopgroup.stopcar.client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by سيد on 04/06/2017.
 */
public class PrivateSubcatogaryadapter extends RecyclerView.Adapter<PrivateSubcatogaryadapter.MyViewHolder> {
    CarOrderActivity activity;
    private List<Subcatogary.ResultBean> horizontalList;
    SelectcarActivity addressActivity = new SelectcarActivity();
    private ProgressDialog dialog;
    int id;
    public int pos = -1;

    TextView textView;

    String from_lat = "", from_lng = "", to_lat = "", to_lng = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView des;
        public ImageView check;
        public CircleImageView img;
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


    public PrivateSubcatogaryadapter(CarOrderActivity activity, List<Subcatogary.ResultBean> horizontalList, String from_lat, String from_lng, String to_lat, String to_lng, TextView textView) {
        this.horizontalList = horizontalList;
        this.from_lat = from_lat;
        this.from_lng = from_lng;
        this.to_lat = to_lat;
        this.to_lng = to_lng;
        this.textView = textView;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selectcar_priavte, parent, false);
//        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).name);
        holder.des.setText(horizontalList.get(position).description);
        Log.e("image", horizontalList.get(position).image);
        try {
            Picasso.get().load(horizontalList.get(position).image).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
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
                id = horizontalList.get(position).id;
                if (!CarOrderActivity.to_lat.equals("")) {
                    fareEstimate();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    private void fareEstimate() {

        APIModel.getMethod(activity, "trips/estimate?from_lat=" + CarOrderActivity.from_lat + "&from_lng=" + CarOrderActivity.from_lon + "&to_lat=" + CarOrderActivity.to_lat + "&to_lng=" + CarOrderActivity.to_lon + "&sub_category_id=" + id, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog = new ProgressDialog(activity);
                dialog.setMessage(activity.getString(R.string.calc_fare));
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        fareEstimate();
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("fare_estimate", responseString);
                try {
                    JSONObject mainObject = new JSONObject(responseString);
                    JSONObject uniObject = mainObject.getJSONObject("result");

                    textView.setText(uniObject.getString("fare_estimation") + "  " + LoginSession.getlogindata(activity).result.currency);
                } catch (JsonSyntaxException a) {
                    a.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
