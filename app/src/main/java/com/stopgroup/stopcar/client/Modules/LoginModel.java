package com.stopgroup.stopcar.client.Modules;

import java.util.List;

/**
 * Created by Tarek on 7/16/18.
 */

public class LoginModel {

    /**
     * token_type : Bearer
     * expires_in : 129599999999
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImI1NjcxM2EzM2U5MTVhNDExOTEzZDkwOTM3YzI2OTdmMmE5MjhiNDFmZWQwZTMzMDZiYjQ5MmU1YTJiZjExMDNiODEwNmM0YzM2ODMyMWY1In0.eyJhdWQiOiIyIiwianRpIjoiYjU2NzEzYTMzZTkxNWE0MTE5MTNkOTA5MzdjMjY5N2YyYTkyOGI0MWZlZDBlMzMwNmJiNDkyZTVhMmJmMTEwM2I4MTA2YzRjMzY4MzIxZjUiLCJpYXQiOjE1MzE3NjYxMDYsIm5iZiI6MTUzMTc2NjEwNiwiZXhwIjoxMzExMzE3NjYxMDUsInN1YiI6IjEzIiwic2NvcGVzIjpbXX0.qJiOJFrU-X3gtT7RIER5sr6KrpVE5YStZ7NlKMJf-BN85aJJIPlmz2Z7nRr9xgR6uTWlcR7kWm7NIaG3HAQJaSGi5k7Eg_2FkP8k_7D-tyCzGGBHJIEVsJbyR5BNnzIPNxNbdcKT5x_jMDP8GNmm73DPO1pBVdJyC-AI6mlxEH6kZlQsgbR9wXqY1EPZ4fzfNxqu9hcScFRpEYKLTlThYdXlsO63H7PUhDnmjkUpumkxx8i1U7SXzjvI9xmZX1cxyApayC2Z2odqNKKp6V1CtJ7fGLa83KM7iaCkKRvDs964OjEbXex7kGesN-rtVoAKod7iOzPmO5l0RJAu928Wq5njkA908RmBIuyop-QWUxpRAzl3bM4VWY5E20jqMkOzxkSAHdjfzzjTmELbj3qkooiO_bx0eTUUhwlLn0XExzyeZu3u3AE6UP50Nx56am9X4QIpCy1jLQUswVzZuvoyzsmn8ME0nr3B7nXy7Xo2p1PVybHqLVMFx_pPaviGJREsjeKnZrt1AL4UYIzUp9iuJfUlwCLkfMOzKhv_5NTJXDkuORv3Eul_Wpc0npLCaDfwASbZ54cXZbRfpjG25qufapw1Nda3HSXn3BVY0BS2rwy6o6jxYgsl0SL0SZF95xSPvSOv_PynsZFC1tvBCqYzGajC1QlB0gDMxOID90H23Ec
     * refresh_token : def502004b5c50c0bdf37ae6f40fcb12eca9754e0a8f8e033d89de62d7417ed3cb478b769e53442decc2a9ed814d0821e4fb5c8d601c776a59656d558eed07a6ccc140ce41dee660187ee7d98530050b8e661c6b27ea9a7b76dc4e7b74d473b458cfea4a3949ee29da2863d5373bfa8e6a066098c02b0e15c9bbfd69d2cefa855f415f5fb3f26facb37ba3d6bd93389c01060747b46d15f1b18edacfcce28d661b62a71f233c3e9b397d07d139a14be12c3f553abc4193b9a74a4285c6d88666d64f9cedacc72fb856b68b7cf0d279a5781406d3d1041508ca7c8491d93d7167a3925a28874d8d6123c7fc15297ce592f2218dab0abb99e722057fbad25998bee6e56e96fb0f7542a30b8538c5d92c698d90bb65c59491495a7f47ad7b3e9dc07eb0baf23dfa8fe690e34adb37327a3e9c3d5f94184e80c7cc7af29e24130a56372a2806054c208c35dea0ff4c9e76a36b8849b7a22a8bd8e1f6cc74a4257d5de3
     * result : {"first_name":"mohamed","last_name":"abdu","email":"mohamedabduissa6@gmail.com","mobile":"1140156285","country_id":1,"country_code":"020","currency":"USD","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","client_credit":0,"payment_method":"2","payment_method_text":"credit","places":[{"id":2,"name":"home","lat":32,"lng":32,"location":""}]}
     * statusCode : 200
     * statusText : OK
     */

    public String token_type;
    public long expires_in;
    public String access_token;
    public String refresh_token;
    public ResultBean result;
    public int statusCode;
    public String statusText;

    public static class ResultBean {
        /**
         * first_name : mohamed
         * last_name : abdu
         * email : mohamedabduissa6@gmail.com
         * mobile : 1140156285
         * country_id : 1
         * country_code : 020
         * currency : USD
         * image : https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg
         * activation_code : 0
         * active : 1
         * block : 0
         * client_credit : 0
         * payment_method : 2
         * payment_method_text : credit
         * places : [{"id":2,"name":"home","lat":32,"lng":32,"location":""}]
         */

        public String first_name;
        public String last_name;
        public String email;
        public String mobile;
        public int country_id;
        public String country_code;
        public String currency;
        public String image;
        public int activation_code;
        public String active;
        public String block;
        public int client_credit;
        public String payment_method;
        public String payment_method_text;
        public List<PlacesBean> places;

        public static class PlacesBean {
            /**
             * id : 2
             * name : home
             * lat : 32
             * lng : 32
             * location :
             */
            public int id;
            public String name;
            public int lat;
            public int lng;
            public String location;
        }
    }
}
