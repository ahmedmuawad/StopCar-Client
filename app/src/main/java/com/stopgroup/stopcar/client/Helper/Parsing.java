package com.stopgroup.stopcar.client.Helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Parsing<T> {
    public T model(String json){
        Type dataType = new TypeToken<T>() {
        }.getType();
        T data =  new Gson().fromJson(json, dataType);
        return data;
    }
}
