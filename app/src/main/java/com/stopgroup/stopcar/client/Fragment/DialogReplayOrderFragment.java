package com.stopgroup.stopcar.client.Fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.activity.HomeActivity;
import com.stopgroup.stopcar.client.Api.APIModel;
import com.stopgroup.stopcar.client.Helper.LoginSession;
import com.stopgroup.stopcar.client.Modules.Order;
import com.stopgroup.stopcar.client.R;

import cz.msebera.android.httpclient.Header;


public class DialogReplayOrderFragment extends DialogFragment {
  public   DialogReplayOrderFragment(){

    }

    View view;
    Order.ResultBean.RequestsBean requestsBean;
    private TextView ReplyFrom;
    private TextView price;
    private TextView ok;
    private TextView cancel;
    private ProgressBar progressBar;

    @SuppressLint("ValidFragment")
    public DialogReplayOrderFragment(Order.ResultBean.RequestsBean requestsBean) {
        this.requestsBean = requestsBean;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog_replay_order, container, false);
        initView(view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }

    private void initView(View view) {
        ReplyFrom = view.findViewById(R.id.ReplyFrom);
        price = view.findViewById(R.id.price);
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);
        progressBar = view.findViewById(R.id.progress);
        ReplyFrom.setText(getString(R.string.Reply_from) +" " + requestsBean.driver.first_name );
        price.setText(  requestsBean.price +" "+ LoginSession.getlogindata(getActivity()).result.currency);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reject();
            }
        });
    }

    private void accept () {
        APIModel.getMethod(getActivity(), "trips/accept/price/" + requestsBean.id, new TextHttpResponseHandler() {



            @Override
            public void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(getActivity(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
//                        Toast.makeText(HomeActivity.this, "55555_", Toast.LENGTH_SHORT).show();
                        accept();
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i = new Intent(getActivity(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
               getActivity(). finish();

            }
        });
    }

    private void reject () {
        APIModel.getMethod(getActivity(), "trips/reject/request/" + requestsBean.id, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                APIModel.handleFailure(getActivity(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
//                        Toast.makeText(HomeActivity.this, "55555_", Toast.LENGTH_SHORT).show();
                        accept();
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i = new Intent(getActivity(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
               getActivity(). finish();

            }
        });
    }

}
