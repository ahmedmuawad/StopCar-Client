package com.stopgroup.stopcar.client.Helper;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stopgroup.stopcar.client.Api.APIModel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class HttpHelper {
    public AsyncHttpClient client = new AsyncHttpClient();
    public static HttpHelper httpHelper = new HttpHelper();
    public Map<String,Object> paramters = new HashMap<String , Object>();
    private boolean apiRunning = false;

    public void get(String api,final Closure<String> closure){
        if(apiRunning){
            return;
        }

        client.setConnectTimeout(999999);
        try {
            client.addHeader("Authorization", "Bearer " + LoginSession.getlogindata(CurrentActivity.getInstance()).access_token);
        }catch (Exception e){

        }
        client.addHeader("version","v1");
        client.addHeader("lang", Locale.getDefault().getLanguage());


        String method = this.initGet(api);
        client.get(APIModel.BASE_URL+method ,new TextHttpResponseHandler(){
            @Override
            public void onStart() {
                apiRunning = true;
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                apiRunning = false;
                HttpHelper.httpHelper.paramters.clear();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(CurrentActivity.getInstance(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        return;
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                closure.run(responseString);
            }
        });

    }
    public void post(String api,final Closure<String> closure){
        if(apiRunning){
            return;
        }

        client.setConnectTimeout(999999);
        try {
            client.addHeader("Authorization", "Bearer " + LoginSession.getlogindata(CurrentActivity.getInstance()).access_token);
        }catch (Exception e){

        }
        client.addHeader("version","v1");
        client.addHeader("lang", Locale.getDefault().getLanguage());

//        client.addHeader("token", FirebaseInstanceId.getInstance().getToken());
        RequestParams params = new RequestParams();
        for (Map.Entry<String, Object> entry : paramters.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }

        client.post(APIModel.BASE_URL+api,params ,new TextHttpResponseHandler(){
            @Override
            public void onStart() {
                apiRunning = true;
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                apiRunning = false;
                HttpHelper.httpHelper.paramters.clear();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                APIModel.handleFailure(CurrentActivity.getInstance(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        return;
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                closure.run(responseString);
            }
        });

    }
    public void put(String api,final Closure<String> closure){
        if(apiRunning){
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(999999);
        try {
            client.addHeader("Authorization", "Bearer " + LoginSession.getlogindata(CurrentActivity.getInstance()).access_token);
        }catch (Exception e){

        }
        client.addHeader("version","v1");
        client.addHeader("lang", Locale.getDefault().getLanguage());

        RequestParams params = new RequestParams();
        for (Map.Entry<String, Object> entry : paramters.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }


        client.put(APIModel.BASE_URL+api,params,new TextHttpResponseHandler(){
            @Override
            public void onStart() {
                apiRunning = true;
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                apiRunning = false;
                HttpHelper.httpHelper.paramters.clear();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                HttpHelper.httpHelper.paramters.clear();
                APIModel.handleFailure(CurrentActivity.getInstance(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        return;
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                closure.run(responseString);
            }
        });

    }
    public void delete(String api,final Closure<String> closure){
        if(apiRunning){
            return;
        }

        client.setConnectTimeout(999999);
        try {
            client.addHeader("Authorization", "Bearer " + LoginSession.getlogindata(CurrentActivity.getInstance()).access_token);
        }catch (Exception e){

        }
        client.addHeader("version","v1");
        client.addHeader("lang", Locale.getDefault().getLanguage());

        client.delete(APIModel.BASE_URL+api,new TextHttpResponseHandler(){
            @Override
            public void onStart() {
                apiRunning = true;
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                apiRunning = false;
                HttpHelper.httpHelper.paramters.clear();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                apiRunning = false;
                APIModel.handleFailure(CurrentActivity.getInstance(), statusCode, responseString, new APIModel.RefreshTokenListener() {
                    @Override
                    public void onRefresh() {
                        return;
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                apiRunning = false;
                closure.run(responseString);
            }
        });

    }

    private String initGet(String method){

        String genericUrl = method;
        int counter = 0;

        if(paramters.size() > 0){
            for(Map.Entry<String, Object> entry : paramters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                // do what you have to do here
                // In your case, another loop.

                if(counter == 0){
                    genericUrl = genericUrl+"?"+key+"="+value;
                }else{
                    genericUrl = genericUrl+"&"+key+"="+value;
                }
                counter += 1;

            }

        }

        return genericUrl;


    }





}
