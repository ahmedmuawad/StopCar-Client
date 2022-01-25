package com.stopgroup.stopcar.client.Modules;

/**
 * Created by tarek on 13/08/18.
 */

public class Rate {

    /**
     * rate_last_trip : {"id":78,"from_lat":30.0059584,"from_lng":31.1783764,"to_lat":null,"to_lng":null,"from_location":"طريق كفر طهرمس، كفر طهرمس / ب، قسم بولاق الدكرور، الجيزة، مصر","to_location":"عاصم أحمد حموده، أولى الهرم، العمرانية، الجيزة، مصر","payment_method":1,"payment_method_text":"cash","distance":1,"time_estimation":"4 Min","fare_estimation":"16","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":2,"commission":10,"tax":4,"discount":0,"total_price":26,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":"2018-08-13 00:37:00","created_at":"2018-08-13 00:35:30","type":"normal","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"30.0045744","lng":"31.1787268","his_credit":-30,"admin_credit":2056,"total_credit":2172,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":3,"today_earned":1480,"online_hours":0}}},"category_id":1,"category_name":"سيارة خصوصي"}
     * result : {}
     * statusCode : 200
     * statusText : OK
     */

    public RateLastTripBean rate_last_trip;
    public ResultBean result;
    public int statusCode;
    public String statusText;

    public static class RateLastTripBean {
        /**
         * id : 78
         * from_lat : 30.0059584
         * from_lng : 31.1783764
         * to_lat : null
         * to_lng : null
         * from_location : طريق كفر طهرمس، كفر طهرمس / ب، قسم بولاق الدكرور، الجيزة، مصر
         * to_location : عاصم أحمد حموده، أولى الهرم، العمرانية، الجيزة، مصر
         * payment_method : 1
         * payment_method_text : cash
         * distance : 1
         * time_estimation : 4 Min
         * fare_estimation : 16
         * status : 7
         * status_text : تم الانتهاء من الرحلة
         * trip_price : 2
         * commission : 10
         * tax : 4
         * discount : 0
         * total_price : 26
         * driver_rate : null
         * client_rate : null
         * driver_comment : null
         * client_comment : null
         * end_date : 2018-08-13 00:37:00
         * created_at : 2018-08-13 00:35:30
         * type : normal
         * driver : {"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"30.0045744","lng":"31.1787268","his_credit":-30,"admin_credit":2056,"total_credit":2172,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":3,"today_earned":1480,"online_hours":0}}}
         * category_id : 1
         * category_name : سيارة خصوصي
         */

        public int id;
        public double from_lat;
        public double from_lng;
        public String to_lat;
        public String to_lng;
        public String from_location;
        public String to_location;
        public int payment_method;
        public String total_price;

        public static class DriverBeanX {
            public static class DriverBean {
                public static class CarBean {
                    /**
                     * id : 12
                     * year : 2018
                     * brand_id : 1
                     * brand_name : بي إم دبليو
                     * model_id : 1
                     * model_name : 218i
                     * color_id : 1
                     * color_name : black
                     * color : #00f600
                     */

                    public int id;
                    public int year;
                    public int brand_id;
                    public String brand_name;
                    public int model_id;
                    public String model_name;
                    public int color_id;
                    public String color_name;
                    public String color;
                }

                public static class StatisticsBean {
                }
            }
        }
    }

    public static class ResultBean {
    }
}
