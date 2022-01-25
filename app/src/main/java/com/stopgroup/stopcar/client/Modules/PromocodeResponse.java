package com.stopgroup.stopcar.client.Modules;

public class PromocodeResponse {


    public ResultModel result;
    public int statusCode;
    public String statusText;

    public static class ResultModel {
        public int id;
        public String code;
        public int discount;
        public int number_of_usage;
        public String expire;
    }
}
