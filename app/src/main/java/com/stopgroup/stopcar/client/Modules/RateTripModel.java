package com.stopgroup.stopcar.client.Modules;

/**
 * Created by Tarek on 11/22/18.
 */

public class RateTripModel {


    /**
     * rate_last_trip : {"id":497,"from_lat":29.9741783,"from_lng":31.2433933,"to_lat":null,"to_lng":null,"from_location":"أحمد عبد الباقي، العيسوية، البساتين، محافظة القاهرة\u202c، مصر","to_location":"أحمد عبد الباقي، العيسوية، البساتين، محافظة القاهرة\u202c، مصر","payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"4","status":7,"status_text":"Collected","trip_price":4,"commission":0,"tax":0,"country_tax":0,"discount":0,"total_price":4,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":"2018-11-22 04:12:40","start_date":"2018-11-22 04:12:39","created_at":"2018-11-22 04:12:12","type":"normal","driver":{"first_name":"Ahmed","last_name":"أثير n","email":"mr.aaahmed@stop-group.com","mobile":"00996633","image":"https://nashmit.com/public/uploads/no_avatar.jpg","driver":{"id":240,"driving_license_img":"https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-9161661-cb3d29bc8fd2ca95914ad9c566b4f6b3.png","car_license_img":"https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-1062974-c3eb803cbdb5dc13a5b8b0f9586ec25a.png","car":{"id":176,"year":2018,"brand_id":2,"brand_name":"Toyota","model_id":1,"model_name":"كامري","color_id":1,"color_name":"black","color":"#00f600","car_image":"https://nashmit.com/public/storage//public/uploads/no_avatar.jpg"},"online":true,"lat":"29.97417833333333","lng":"31.243393333333334","rate":"","statistics":{"today_trips":"0","today_earned":0,"online_hours":5}}},"category_id":1,"category_name":"Private Car","sub_category_id":53,"sub_category_name":"أثير N"}
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
         * id : 497
         * from_lat : 29.9741783
         * from_lng : 31.2433933
         * to_lat : null
         * to_lng : null
         * from_location : أحمد عبد الباقي، العيسوية، البساتين، محافظة القاهرة‬، مصر
         * to_location : أحمد عبد الباقي، العيسوية، البساتين، محافظة القاهرة‬، مصر
         * payment_method : 1
         * payment_method_text : cash
         * distance : 0
         * time_estimation : 0 Min
         * fare_estimation : 4
         * status : 7
         * status_text : Collected
         * trip_price : 4
         * commission : 0
         * tax : 0
         * country_tax : 0
         * discount : 0
         * total_price : 4
         * driver_rate : null
         * client_rate : null
         * driver_comment : null
         * client_comment : null
         * end_date : 2018-11-22 04:12:40
         * start_date : 2018-11-22 04:12:39
         * created_at : 2018-11-22 04:12:12
         * type : normal
         * driver : {"first_name":"Ahmed","last_name":"أثير n","email":"mr.aaahmed@stop-group.com","mobile":"00996633","image":"https://nashmit.com/public/uploads/no_avatar.jpg","driver":{"id":240,"driving_license_img":"https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-9161661-cb3d29bc8fd2ca95914ad9c566b4f6b3.png","car_license_img":"https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-1062974-c3eb803cbdb5dc13a5b8b0f9586ec25a.png","car":{"id":176,"year":2018,"brand_id":2,"brand_name":"Toyota","model_id":1,"model_name":"كامري","color_id":1,"color_name":"black","color":"#00f600","car_image":"https://nashmit.com/public/storage//public/uploads/no_avatar.jpg"},"online":true,"lat":"29.97417833333333","lng":"31.243393333333334","rate":"","statistics":{"today_trips":"0","today_earned":0,"online_hours":5}}}
         * category_id : 1
         * category_name : Private Car
         * sub_category_id : 53
         * sub_category_name : أثير N
         */

        public int id;
        public double from_lat;
        public double from_lng;
        public Object to_lat;
        public Object to_lng;
        public String from_location;
        public String to_location;
        public int payment_method;
        public String payment_method_text;
        public double distance;
        public String time_estimation;
        public String fare_estimation;
        public int status;
        public String status_text;
        public double trip_price;
        public double commission;
        public double tax;
        public double country_tax;
        public double discount;
        public int total_price;
        public double driver_rate;
        public double wallet;
        public double client_rate;
        public String driver_comment;
        public String client_comment;
        public String end_date;
        public String start_date;
        public String created_at;
        public String type;
        public DriverBeanX driver;
        public int category_id;
        public String category_name;
        public int sub_category_id;
        public String sub_category_name;

        public static class DriverBeanX {
            /**
             * first_name : Ahmed
             * last_name : أثير n
             * email : mr.aaahmed@stop-group.com
             * mobile : 00996633
             * image : https://nashmit.com/public/uploads/no_avatar.jpg
             * driver : {"id":240,"driving_license_img":"https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-9161661-cb3d29bc8fd2ca95914ad9c566b4f6b3.png","car_license_img":"https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-1062974-c3eb803cbdb5dc13a5b8b0f9586ec25a.png","car":{"id":176,"year":2018,"brand_id":2,"brand_name":"Toyota","model_id":1,"model_name":"كامري","color_id":1,"color_name":"black","color":"#00f600","car_image":"https://nashmit.com/public/storage//public/uploads/no_avatar.jpg"},"online":true,"lat":"29.97417833333333","lng":"31.243393333333334","rate":"","statistics":{"today_trips":"0","today_earned":0,"online_hours":5}}
             */

            public String first_name;
            public String last_name;
            public String email;
            public String mobile;
            public String image;
            public DriverBean driver;

            public static class DriverBean {
                /**
                 * id : 240
                 * driving_license_img : https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-9161661-cb3d29bc8fd2ca95914ad9c566b4f6b3.png
                 * car_license_img : https://nashmit.com/storage/app/public/uploads/2018-11-17-00-36-13-1062974-c3eb803cbdb5dc13a5b8b0f9586ec25a.png
                 * car : {"id":176,"year":2018,"brand_id":2,"brand_name":"Toyota","model_id":1,"model_name":"كامري","color_id":1,"color_name":"black","color":"#00f600","car_image":"https://nashmit.com/public/storage//public/uploads/no_avatar.jpg"}
                 * online : true
                 * lat : 29.97417833333333
                 * lng : 31.243393333333334
                 * rate :
                 * statistics : {"today_trips":"0","today_earned":0,"online_hours":5}
                 */

                public int id;
                public String driving_license_img;
                public String car_license_img;
                public CarBean car;
                public boolean online;
                public String lat;
                public String lng;
                public String rate;
                public StatisticsBean statistics;

                public static class CarBean {
                    /**
                     * id : 176
                     * year : 2018
                     * brand_id : 2
                     * brand_name : Toyota
                     * model_id : 1
                     * model_name : كامري
                     * color_id : 1
                     * color_name : black
                     * color : #00f600
                     * car_image : https://nashmit.com/public/storage//public/uploads/no_avatar.jpg
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
                    public String car_image;
                }

                public static class StatisticsBean {
                    /**
                     * today_trips : 0
                     * today_earned : 0
                     * online_hours : 5
                     */

                    public String today_trips;
                    public int today_earned;
                    public int online_hours;
                }
            }
        }
    }

    public static class ResultBean {
    }
}
