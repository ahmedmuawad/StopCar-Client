package com.stopgroup.stopcar.client.Modules;

public class SendOrder {


    /**
     * result : {"id":61,"from_lat":31.1969733,"from_lng":30.0093933,"to_lat":null,"to_lng":null,"from_location":null,"to_location":null,"payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"20","status":0,"status_text":"Opened","trip_price":0,"commission":20,"tax":0,"discount":null,"total_price":20,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":null,"created_at":"2018-08-08 23:54:55","type":"normal","category_id":1,"category_name":"Taxi Car","statistics":{"arriving_minutes":"0 Min","arriving_distance":"0 km","arriving_time":"11:54 pm"}}
     * statusCode : 200
     * statusText : OK
     */

    public ResultBean result;
    public int statusCode;
    public String statusText;

    public static class ResultBean {
        /**
         * id : 61
         * from_lat : 31.1969733
         * from_lng : 30.0093933
         * to_lat : null
         * to_lng : null
         * from_location : null
         * to_location : null
         * payment_method : 1
         * payment_method_text : cash
         * distance : 0
         * time_estimation : 0 Min
         * fare_estimation : 20
         * status : 0
         * status_text : Opened
         * trip_price : 0
         * commission : 20
         * tax : 0
         * discount : null
         * total_price : 20
         * driver_rate : null
         * client_rate : null
         * driver_comment : null
         * client_comment : null
         * end_date : null
         * created_at : 2018-08-08 23:54:55
         * type : normal
         * category_id : 1
         * category_name : Taxi Car
         * statistics : {"arriving_minutes":"0 Min","arriving_distance":"0 km","arriving_time":"11:54 pm"}
         */

        public int id;
        public double from_lat;
        public double from_lng;
        public Object to_lat;
        public Object to_lng;
        public Object from_location;
        public Object to_location;
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
        public double discount;
        public int total_price;
        public Object driver_rate;
        public Object client_rate;
        public Object driver_comment;
        public Object client_comment;
        public Object end_date;
        public String created_at;
        public String type;
        public int category_id;
        public String category_name;
        public StatisticsBean statistics;

        public static class StatisticsBean {
            /**
             * arriving_minutes : 0 Min
             * arriving_distance : 0 km
             * arriving_time : 11:54 pm
             */

            public String arriving_minutes;
            public String arriving_distance;
            public String arriving_time;
        }
    }
}
