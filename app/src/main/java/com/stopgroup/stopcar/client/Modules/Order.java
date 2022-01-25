package com.stopgroup.stopcar.client.Modules;

import java.util.ArrayList;
import java.util.List;

public class Order {


    /**
     * result : {"id":26,"from_lat":31.528625,"from_lng":31.029095,"to_lat":null,"to_lng":null,"from_location":null,"to_location":null,"payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"0","status":1,"status_text":"accepted","trip_price":0,"commission":20,"tax":0,"total_price":20,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":null,"created_at":"2018-07-25 01:48:00","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145678","country_id":1,"country_code":"020","currency":"USD","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"/public/uploads/no_avatar.jpg","car_license_img":"/public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"BMW","model_id":1,"model_name":"www","color_id":1,"color_name":"black","color":"#00f600"},"online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":0,"total_credit":0,"category_id":1,"category_name":"سيارة ملاكي"}},"category_id":1,"category_name":"سيارة ملاكي"}
     * statusCode : 200
     * statusText : OK
     */

    public ResultBean result;
    public int statusCode;
    public String statusText;
    /**
     * id : 31
     * price : 20
     * accept : 1
     * driver : {"first_name":"z","last_name":"z","email":"a@a.com","mobile":"453219687","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg"}
     */

    public int id;
    public int price;
    public int accept;
    public ResultBean.DriverBeanX driver;

    public static class ResultBean {
        /**
         * id : 26
         * from_lat : 31.528625
         * from_lng : 31.029095
         * to_lat : null
         * to_lng : null
         * from_location : null
         * to_location : null
         * payment_method : 1
         * payment_method_text : cash
         * distance : 0
         * time_estimation : 0 Min
         * fare_estimation : 0
         * status : 1
         * status_text : accepted
         * trip_price : 0
         * commission : 20
         * tax : 0
         * total_price : 20
         * driver_rate : null
         * client_rate : null
         * driver_comment : null
         * client_comment : null
         * end_date : null
         * created_at : 2018-07-25 01:48:00
         * driver : {"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145678","country_id":1,"country_code":"020","currency":"USD","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"/public/uploads/no_avatar.jpg","car_license_img":"/public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"BMW","model_id":1,"model_name":"www","color_id":1,"color_name":"black","color":"#00f600"},"online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":0,"total_credit":0,"category_id":1,"category_name":"سيارة ملاكي"}}
         * category_id : 1
         * category_name : سيارة ملاكي
         */

        public int id;
        public double from_lat;
        public double from_lng;
        public String to_lat;
        public String to_lng;
        public String from_location;
        public String to_location;
        public int payment_method;
        public boolean payment_paid;
        public String payment_method_text;
        public double distance;
        public String time_estimation;
        public String fare_estimation;
        public int status;
        public String status_text;
        public double trip_price;
        public double commission;
        public double tax;
        public int total_price;
        public String driver_rate;
        public String client_rate;
        public String driver_comment;
        public String client_comment;
        public String end_date;
        public String created_at;
        public DriverBeanX driver;
        public int category_id;
        public String category_name;
        public String type;
        public List<RequestsBean> requests = new ArrayList<>();
        public StatisticsBeanX statistics;
        public static class StatisticsBeanX {
            /**
             * arriving_minutes : 0 Min
             * arriving_distance : 0 km
             * arriving_time : 03:17 am
             */

            public String arriving_minutes;
            public String arriving_distance;
            public String arriving_time;
        }
        public static class RequestsBean {
            /**
             * id : 31
             * price : 20
             * accept : 1
             * driver : {"first_name":"z","last_name":"z","email":"a@a.com","mobile":"453219687","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg"}
             */

            public int id;
            public int price;
            public int accept;
            public Driverz driver ;
            public static class Driverz {
                /**
                 * first_name : mohamed
                 * last_name : y
                 * email : y@y.com
                 * mobile : 230145678
                 * country_id : 1
                 * country_code : 020
                 * currency : USD
                 * image : https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg
                 * activation_code : 0
                 * active : 1
                 * block : 0
                 * driver : {"id":26,"driving_license_img":"/public/uploads/no_avatar.jpg","car_license_img":"/public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"BMW","model_id":1,"model_name":"www","color_id":1,"color_name":"black","color":"#00f600"},"online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":0,"total_credit":0,"category_id":1,"category_name":"سيارة ملاكي"}
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
            }
        }
        public static class DriverBeanX {
            /**
             * first_name : mohamed
             * last_name : y
             * email : y@y.com
             * mobile : 230145678
             * country_id : 1
             * country_code : 020
             * currency : USD
             * image : https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg
             * activation_code : 0
             * active : 1
             * block : 0
             * driver : {"id":26,"driving_license_img":"/public/uploads/no_avatar.jpg","car_license_img":"/public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"BMW","model_id":1,"model_name":"www","color_id":1,"color_name":"black","color":"#00f600"},"online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":0,"total_credit":0,"category_id":1,"category_name":"سيارة ملاكي"}
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
            public DriverBean driver;

            public static class DriverBean {
                /**
                 * id : 26
                 * driving_license_img : /public/uploads/no_avatar.jpg
                 * car_license_img : /public/uploads/no_avatar.jpg
                 * car : {"id":12,"year":2018,"brand_id":1,"brand_name":"BMW","model_id":1,"model_name":"www","color_id":1,"color_name":"black","color":"#00f600"}
                 * online : false
                 * lat : 31.196973333333336
                 * lng : 30.009393333333332
                 * his_credit : 0
                 * admin_credit : 0
                 * total_credit : 0
                 * category_id : 1
                 * category_name : سيارة ملاكي
                 */

                public int id;
                public String driving_license_img;
                public String rate;
                public String car_license_img;
                public CarBean car;
                public boolean online;
                public Double lat=0.0;
                public Double lng=0.0;
                public int his_credit;
                public int admin_credit;
                public int total_credit;
                public int category_id;
                public String category_name;

                public static class CarBean {
                    /**
                     * id : 12
                     * year : 2018
                     * brand_id : 1
                     * brand_name : BMW
                     * model_id : 1
                     * model_name : www
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
                    public String plate_number;
                }
            }
        }
    }

}
