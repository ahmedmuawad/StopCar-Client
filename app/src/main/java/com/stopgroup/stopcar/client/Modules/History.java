package com.stopgroup.stopcar.client.Modules;

import java.util.List;

/**
 * Created by user on 8/1/18.
 */

public class History {

    /**
     * result : [{"id":35,"from_lat":31.1969733,"from_lng":30.0093933,"to_lat":null,"to_lng":null,"from_location":"الإسكندرية، مصر","to_location":"الإسكندرية، مصر","payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"0","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":0,"commission":null,"tax":0,"discount":0,"total_price":0,"driver_rate":5,"client_rate":5,"driver_comment":"hi","client_comment":"hi","end_date":"2018-08-03 18:47:50","created_at":"2018-08-03 18:29:49","type":"normal","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}},"category_id":1,"category_name":"سيارة خصوصي"},{"id":36,"from_lat":31.1969733,"from_lng":30.0093933,"to_lat":null,"to_lng":null,"from_location":null,"to_location":null,"payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"0","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":0,"commission":20,"tax":0,"discount":0,"total_price":20,"driver_rate":5,"client_rate":5,"driver_comment":"hi","client_comment":"r","end_date":"2018-08-04 18:25:18","created_at":"2018-08-03 21:13:36","type":"custom_price","driver":{"first_name":"u","last_name":"u","email":"ui@ui.com","mobile":"456789123","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":28,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-21-05-05-5372943-28bbc688e9ca4fb3891a0953b7c049ab.png","car_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-21-05-05-8007476-4b11c9e03063c92554ddb188e2e2b5f7.png","car":{"id":14,"year":2010,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":20,"total_credit":0,"rate":"5.0000","category_id":2,"category_name":"شاحنة نقل","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/pCIzxxxJRGWAJE9HhQd9lST0ujmAJDMX2kOw8UVA.png","sub_category_id":7,"sub_category_name":"تريلات جوانب","sub_category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/ztRhlJOZAkMfrpu7JmuQs2PTmYCIjUFZetWDLQbs.png","statistics":{"today_trips":1,"today_earned":10,"online_hours":-365}}},"category_id":2,"category_name":"شاحنة نقل","sub_category_id":7,"sub_category_name":"تريلات جوانب"},{"id":37,"from_lat":31.1969733,"from_lng":30.0093933,"to_lat":null,"to_lng":null,"from_location":null,"to_location":null,"payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"20","status":2,"status_text":"ملغاة","trip_price":0,"commission":20,"tax":0,"discount":0,"total_price":20,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":null,"created_at":"2018-08-04 19:27:30","type":"normal","cancel_reason_id":1,"cancel_reason_name":"غير مريح","category_id":1,"category_name":"سيارة خصوصي"},{"id":38,"from_lat":31.1969733,"from_lng":30.0093933,"to_lat":30.0130557,"to_lng":31.2088526,"from_location":"","to_location":"","payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"40","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":20,"commission":20,"tax":0,"discount":0,"total_price":50,"driver_rate":5,"client_rate":5,"driver_comment":"hix","client_comment":"ssd","end_date":"2018-08-05 03:30:55","created_at":"2018-08-05 00:17:13","type":"special","driver":{"first_name":"z","last_name":"z","email":"a@a.com","mobile":"453219687","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":29,"company_name":"atat","company_img":null,"company_contact":"ffgggg","online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":60,"total_credit":60,"rate":"5.0000","category_id":3,"category_name":"شركات نقل اثاث","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/L4S29KNf4scRLoGvUGxbQlULIRDqT6q3NWPAvUjA.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-339}}},"category_id":3,"category_name":"شركات نقل اثاث","requests":[{"id":31,"price":20,"accept":1,"driver":{"first_name":"z","last_name":"z","email":"a@a.com","mobile":"453219687","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg"}}]},{"id":39,"from_lat":31.1969733,"from_lng":30.0093933,"to_lat":30.0130557,"to_lng":31.2088526,"from_location":"","to_location":"","payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"40","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":20,"commission":20,"tax":0,"discount":0,"total_price":40,"driver_rate":5,"client_rate":5,"driver_comment":"hi","client_comment":"hi","end_date":"2018-08-05 20:54:00","created_at":"2018-08-05 05:15:31","type":"special","driver":{"first_name":"z","last_name":"z","email":"a@a.com","mobile":"453219687","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":29,"company_name":"atat","company_img":null,"company_contact":"ffgggg","online":false,"lat":"31.196973333333336","lng":"30.009393333333332","his_credit":0,"admin_credit":60,"total_credit":60,"rate":"5.0000","category_id":3,"category_name":"شركات نقل اثاث","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/L4S29KNf4scRLoGvUGxbQlULIRDqT6q3NWPAvUjA.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-339}}},"category_id":3,"category_name":"شركات نقل اثاث","requests":[]},{"id":40,"from_lat":37.4219983,"from_lng":-122.084,"to_lat":null,"to_lng":null,"from_location":null,"to_location":null,"payment_method":1,"payment_method_text":"cash","distance":0,"time_estimation":"0 Min","fare_estimation":"20","status":2,"status_text":"ملغاة","trip_price":0,"commission":20,"tax":0,"discount":0,"total_price":20,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":null,"created_at":"2018-08-05 22:12:50","type":"normal","cancel_reason_id":1,"cancel_reason_name":"غير مريح","category_id":1,"category_name":"سيارة خصوصي"},{"id":78,"from_lat":30.0059584,"from_lng":31.1783764,"to_lat":null,"to_lng":null,"from_location":"طريق كفر طهرمس، كفر طهرمس / ب، قسم بولاق الدكرور، الجيزة، مصر","to_location":"عاصم أحمد حموده، أولى الهرم، العمرانية، الجيزة، مصر","payment_method":1,"payment_method_text":"cash","distance":1,"time_estimation":"4 Min","fare_estimation":"16","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":2,"commission":10,"tax":4,"discount":0,"total_price":26,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":"2018-08-13 00:37:00","created_at":"2018-08-13 00:35:30","type":"normal","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}},"category_id":1,"category_name":"سيارة خصوصي"},{"id":79,"from_lat":30.0061898,"from_lng":31.1787343,"to_lat":null,"to_lng":null,"from_location":"طريق كفر طهرمس، كفر طهرمس / ب، قسم بولاق الدكرور، الجيزة، مصر","to_location":"عاصم أحمد حموده، أولى الهرم، العمرانية، الجيزة، مصر","payment_method":1,"payment_method_text":"cash","distance":1,"time_estimation":"4 Min","fare_estimation":"16","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":2,"commission":10,"tax":4,"discount":0,"total_price":16,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":"2018-08-13 00:51:43","created_at":"2018-08-13 00:50:57","type":"normal","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}},"category_id":1,"category_name":"سيارة خصوصي"},{"id":80,"from_lat":30.0058208,"from_lng":31.1776606,"to_lat":null,"to_lng":null,"from_location":"الشهيد السيد محمود، كفر طهرمس / ب، قسم بولاق الدكرور، الجيزة، مصر","to_location":"عاصم أحمد حموده، أولى الهرم، العمرانية، الجيزة، مصر","payment_method":1,"payment_method_text":"cash","distance":1,"time_estimation":"4 Min","fare_estimation":"16","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":2,"commission":10,"tax":4,"discount":0,"total_price":16,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":"2018-08-13 02:08:56","created_at":"2018-08-13 02:08:01","type":"normal","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}},"category_id":1,"category_name":"سيارة خصوصي"},{"id":81,"from_lat":30.0059584,"from_lng":31.1783764,"to_lat":null,"to_lng":null,"from_location":"طريق كفر طهرمس، كفر طهرمس / ب، قسم بولاق الدكرور، الجيزة، مصر","to_location":"عاصم أحمد حموده، أولى الهرم، العمرانية، الجيزة، مصر","payment_method":1,"payment_method_text":"cash","distance":1,"time_estimation":"4 Min","fare_estimation":"16","status":7,"status_text":"تم الانتهاء من الرحلة","trip_price":2,"commission":10,"tax":4,"discount":0,"total_price":16,"driver_rate":null,"client_rate":null,"driver_comment":null,"client_comment":null,"end_date":"2018-08-13 02:31:35","created_at":"2018-08-13 02:31:00","type":"normal","driver":{"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}},"category_id":1,"category_name":"سيارة خصوصي"}]
     * statusCode : 200
     * statusText : OK
     */

    public int statusCode;
    public String statusText;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * id : 35
         * from_lat : 31.1969733
         * from_lng : 30.0093933
         * to_lat : null
         * to_lng : null
         * from_location : الإسكندرية، مصر
         * to_location : الإسكندرية، مصر
         * payment_method : 1
         * payment_method_text : cash
         * distance : 0
         * time_estimation : 0 Min
         * fare_estimation : 0
         * status : 7
         * status_text : تم الانتهاء من الرحلة
         * trip_price : 0
         * commission : null
         * tax : 0
         * discount : 0
         * total_price : 0
         * driver_rate : 5
         * client_rate : 5
         * driver_comment : hi
         * client_comment : hi
         * end_date : 2018-08-03 18:47:50
         * created_at : 2018-08-03 18:29:49
         * type : normal
         * driver : {"first_name":"mohamed","last_name":"y","email":"y@y.com","mobile":"230145675","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg","activation_code":0,"active":"1","block":"0","driver":{"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}}
         * category_id : 1
         * category_name : سيارة خصوصي
         * sub_category_id : 7
         * sub_category_name : تريلات جوانب
         * cancel_reason_id : 1
         * cancel_reason_name : غير مريح
         * requests : [{"id":31,"price":20,"accept":1,"driver":{"first_name":"z","last_name":"z","email":"a@a.com","mobile":"453219687","image":"https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg"}}]
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
        public Object commission;
        public double tax;
        public double discount;
        public int total_price;
        public int driver_rate;
        public int client_rate;
        public String driver_comment;
        public String client_comment;
        public String end_date;
        public String created_at;
        public String type;
        public DriverBeanX driver;
        public int category_id;
        public String category_name;
        public int sub_category_id;
        public String sub_category_name;
        public int cancel_reason_id;
        public String cancel_reason_name;
        public List<RequestsBean> requests;
        public String category_calculating_pricing;

        public static class DriverBeanX {
            /**
             * first_name : mohamed
             * last_name : y
             * email : y@y.com
             * mobile : 230145675
             * country_id : 1
             * country_code : 00966
             * currency : SAR
             * image : https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg
             * activation_code : 0
             * active : 1
             * block : 0
             * driver : {"id":26,"driving_license_img":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png","car_license_img":"https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg","car":{"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"},"online":true,"lat":"31.0260915","lng":"31.518594","his_credit":-30,"admin_credit":2094,"total_credit":2176,"rate":"4.1667","category_id":1,"category_name":"سيارة خصوصي","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg","statistics":{"today_trips":1,"today_earned":10,"online_hours":-10}}
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
                 * driving_license_img : https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-03-20-27-51-7065987-e57f3ddba1d11e5f103e846c77725754.png
                 * car_license_img : https://173.236.24.193/~nashmi/public/storage//public/uploads/no_avatar.jpg
                 * car : {"id":12,"year":2018,"brand_id":1,"brand_name":"بي إم دبليو","model_id":1,"model_name":"218i","color_id":1,"color_name":"black","color":"#00f600"}
                 * online : true
                 * lat : 31.0260915
                 * lng : 31.518594
                 * his_credit : -30
                 * admin_credit : 2094
                 * total_credit : 2176
                 * rate : 4.1667
                 * category_id : 1
                 * category_name : سيارة خصوصي
                 * category_image : https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/acnu405eeYRINcoLzGoP2TlM9W6t4rR3LeXgYOoq.jpeg
                 * statistics : {"today_trips":1,"today_earned":10,"online_hours":-10}
                 */

                public int id;
                public String driving_license_img;
                public String car_license_img;
                public CarBean car;
                public boolean online;
                public String lat;
                public String lng;
                public int his_credit;
                public int admin_credit;
                public int total_credit;
                public String rate;
                public int category_id;
                public String category_name;
                public String category_image;
                public StatisticsBean statistics;

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
                    public String plate_number;
                }

                public static class StatisticsBean {
                    /**
                     * today_trips : 1
                     * today_earned : 10
                     * online_hours : -10
                     */

                    public int today_trips;
                    public int today_earned;
                    public int online_hours;
                }
            }
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
            public DriverBeanXX driver;

            public static class DriverBeanXX {
                /**
                 * first_name : z
                 * last_name : z
                 * email : a@a.com
                 * mobile : 453219687
                 * image : https://173.236.24.193/~nashmi/public/uploads/no_avatar.jpg
                 */

                public String first_name;
                public String last_name;
                public String email;
                public String mobile;
                public String image;
            }
        }
    }
}
