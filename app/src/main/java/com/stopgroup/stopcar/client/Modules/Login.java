package com.stopgroup.stopcar.client.Modules;

import java.util.List;

public class Login {

    /**
     * token_type : Bearer
     * expires_in : 129600000000
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijc5MTc0YmZiNzQ2ZTdhOGJmZmZmNTFiZTZmZjhjNzg3OWMyODAxYTU3NzViNDYxZTU4MTA2MGU5NDE4ZDg2MzA0NDZhY2VhOGFjMDIxNzEzIn0.eyJhdWQiOiIyIiwianRpIjoiNzkxNzRiZmI3NDZlN2E4YmZmZmY1MWJlNmZmOGM3ODc5YzI4MDFhNTc3NWI0NjFlNTgxMDYwZTk0MThkODYzMDQ0NmFjZWE4YWMwMjE3MTMiLCJpYXQiOjE1MzUzMDk5NzQsIm5iZiI6MTUzNTMwOTk3NCwiZXhwIjoxMzExMzUzMDk5NzQsInN1YiI6IjEwMSIsInNjb3BlcyI6W119.GKnChlcDEuS1VQ5Soq3qZAsNDr7-hMD-3isRJ08DhAV_00k6JydgWmevlJV8WvMtaw9j7Y1aeHoVr77btYizI3g6VZNI0-z1HMztnUw_TAmBOLu8ZQATv7mmZsb-kGPLD63qFWK6yjuJ3gC0qKsMLMGzCovnZQcWQKkCoETLp-_m5vrUM52pVTmLDSmlnw4WzEECrjin7X2CAhffJZxO2AszeFuQmztFn8AUkp4H3RwjEoA2uhHV9EPgKt_8F5a7g4XxoZRHAHApQ3MYZQN3npgNSX7vHJHqaT-jssqnlx7h3SBTpmFlMkw2kA4xmKY9umBa8MmldaiHyULGWbfkhsUN0idRcULq8CKKsfOanNUUiB1SLAURSRUwFI9DsLEPjAt4uKRJ0l9objNzJYMx7bDy5HyzdmdrrCn2wRlD_nMWS0ASp-PkGbxrt5vJE8IdHjSRMCxDP2B16AfjHhtTkyuOrqaGcx65WSzfU5sYc20Ya0_6zXoRKgFVhAk9o7Oz4QY3t_Efox0dP17rN-8xC8shAOWHUcPmsMrpErY9V9JANX7X6cNvy8uMLZWBPoSSm1D_7Y6LqEUoP-p2GCcpi_xDschiUiFP68dNPNj09Lyj99URX8QBrxJ45Hq2nZpezclKcOndmDzh7OMIsuH3PCjl9cG2mfBWGj9f7uWJ3EM
     * refresh_token : def5020015b759d6f9a7d0fa4457fa0658d58ad320b99ea71814e5453844dc93b59b91fbb24324efc82a68ebaf4fef17607e34ab6fbebaad8a5ed46b27f16c1e5b95e07ecdaee6a53f945bb4241375e3f1b2e0cb9a2fd054186153cf7057939516bab614e35db4f33abe51d00f5bcb3f4182d12240615f0cfcf1db6012cdc292e7c367966fcd29d598ea6d0067614855569ca12e7dd31db3fe6638ed31da2ad0735ab2cedd418d4b5f33797ead6f7cfdd3a9d0023321c6e6696007d8416edf229153ba544adb8a29bf0a8e8e9595a6fd54c87f3e5ef089ade7eb083f7a05b9b342045d60119eee71d5e485e0309679bcfe24febc2c6b5221fe14d3b688b79b368f1470e1c21dc1dd5396b0c9e81c54d80a9e09454530e42cb1bd98f704c6a38b52910cf337da84b55efa5739cb89cee6ef17a5e3e1cea894e58740aa0d27a8ab1aa9d40ed1d5635467d51810744fe374071dce99f2482abbbaa77127c1d97d7acb27
     * result : {"first_name":"client222","last_name":"client","email":"client@client.com","mobile":"1111111111","country_id":1,"country_code":"00966","currency":"SAR","image":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-16-03-40-27-2913867-3a97ee8893a981453b0cbced934b1c75.jpeg","activation_code":0,"active":"1","block":"0","client_credit":10,"payment_method":"1","payment_method_text":"cash","places":[{"id":16,"name":"Sheraton Al Matar","lat":30.1123631887,"lng":31.4002893493,"location":"مطار القاهرة الدولي (CAI)، طريق المطار، مصر الجديدة، محافظة القاهرة\u202c 11432، مصر"},{"id":17,"name":"Helena","lat":29.866282,"lng":31.3370074,"location":"Unnamed Road, Helwan Sharkeya, Qism Helwan, Cairo Governorate, مصر"},{"id":18,"name":"Helena","lat":29.866282,"lng":31.3370074,"location":"Unnamed Road, Helwan Sharkeya, Qism Helwan, Cairo Governorate, مصر"},{"id":20,"name":"Czechia","lat":29.8483192,"lng":31.3368529,"location":"56 شريف، حلوان الشرقية، قسم حلوان، محافظة القاهرة\u202c، مصر"}]}
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
         * first_name : client222
         * last_name : client
         * email : client@client.com
         * mobile : 1111111111
         * country_id : 1
         * country_code : 00966
         * currency : SAR
         * image : https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-08-16-03-40-27-2913867-3a97ee8893a981453b0cbced934b1c75.jpeg
         * activation_code : 0
         * active : 1
         * block : 0
         * client_credit : 10
         * payment_method : 1
         * payment_method_text : cash
         * places : [{"id":16,"name":"Sheraton Al Matar","lat":30.1123631887,"lng":31.4002893493,"location":"مطار القاهرة الدولي (CAI)، طريق المطار، مصر الجديدة، محافظة القاهرة\u202c 11432، مصر"},{"id":17,"name":"Helena","lat":29.866282,"lng":31.3370074,"location":"Unnamed Road, Helwan Sharkeya, Qism Helwan, Cairo Governorate, مصر"},{"id":18,"name":"Helena","lat":29.866282,"lng":31.3370074,"location":"Unnamed Road, Helwan Sharkeya, Qism Helwan, Cairo Governorate, مصر"},{"id":20,"name":"Czechia","lat":29.8483192,"lng":31.3368529,"location":"56 شريف، حلوان الشرقية، قسم حلوان، محافظة القاهرة\u202c، مصر"}]
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
             * id : 16
             * name : Sheraton Al Matar
             * lat : 30.1123631887
             * lng : 31.4002893493
             * location : مطار القاهرة الدولي (CAI)، طريق المطار، مصر الجديدة، محافظة القاهرة‬ 11432، مصر
             */

            public int id;
            public String name;
            public double lat;
            public double lng;
            public String location;
        }
    }
}
