package com.stopgroup.stopcar.client.Modules;

public class FareEstimate {

    /**
     * result : {"from_location":"صان الحجر القبلية، مركز الحسينية، الشرقية، مصر","to_location":"صان الحجر البحرية، مركز الحسينية، الشرقية، مصر","distance":3,"time_estimation":"8 Min","trip_price":6,"commission":10,"tax":12,"total_price":28,"fare_estimation":"28"}
     * statusCode : 200
     * statusText : OK
     */

    private ResultModel result;
    private int statusCode;
    private String statusText;

    public ResultModel getResult() {
        return result;
    }

    public void setResult(ResultModel result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public static class ResultModel {
        /**
         * from_location : صان الحجر القبلية، مركز الحسينية، الشرقية، مصر
         * to_location : صان الحجر البحرية، مركز الحسينية، الشرقية، مصر
         * distance : 3
         * time_estimation : 8 Min
         * trip_price : 6
         * commission : 10
         * tax : 12
         * total_price : 28
         * fare_estimation : 28
         */

//        private String from_location;
//        private String to_location;
//        private int distance;
//        private String time_estimation;
//        private double trip_price;
//        private int commission;
//        private int tax;
//        private double total_price;

        public String fare_estimation;

//        public String getFrom_location() {
//            return from_location;
//        }
//
//        public void setFrom_location(String from_location) {
//            this.from_location = from_location;
//        }
//
//        public String getTo_location() {
//            return to_location;
//        }
//
//        public void setTo_location(String to_location) {
//            this.to_location = to_location;
//        }
//
//        public int getDistance() {
//            return distance;
//        }
//
//        public void setDistance(int distance) {
//            this.distance = distance;
//        }
//
//        public String getTime_estimation() {
//            return time_estimation;
//        }
//
//        public void setTime_estimation(String time_estimation) {
//            this.time_estimation = time_estimation;
//        }
//
//        public int getTrip_price() {
//            return trip_price;
//        }
//
//        public void setTrip_price(int trip_price) {
//            this.trip_price = trip_price;
//        }
//
//        public int getCommission() {
//            return commission;
//        }
//
//        public void setCommission(int commission) {
//            this.commission = commission;
//        }
//
//        public int getTax() {
//            return tax;
//        }
//
//        public void setTax(int tax) {
//            this.tax = tax;
//        }
//
//        public int getTotal_price() {
//            return total_price;
//        }
//
//        public void setTotal_price(int total_price) {
//            this.total_price = total_price;
//        }

//        public String getFare_estimation() {
//            return fare_estimation;
//        }
//
//        public void setFare_estimation(String fare_estimation) {
//            this.fare_estimation = fare_estimation;
//        }
    }
}
