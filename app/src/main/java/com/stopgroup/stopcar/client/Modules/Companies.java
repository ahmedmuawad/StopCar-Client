package com.stopgroup.stopcar.client.Modules;

import java.util.List;

public class Companies {

    /**
     * result : [{"id":62,"company_name":"wkelCompany11","company_description":"1wkelCompany","company_img":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/noTPe8dwUBrFOma5Zs2FFAsimtjHPeXGySWTBXTm.jpeg","company_contact":"123456","subCategories":[{"id":47,"name":"سيب","description":"سيب","image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/uEe4GiqMA0sNoTReDn3uavZx9ZjbePvCAOOHtRUe.jpeg","childs":[]},{"id":48,"name":"sdf","description":"dsf","image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/AS4my2XYaoBKbtJWqkUroruGZR31TApa9y8xIjXy.jpeg","childs":[]}],"online":true,"lat":null,"lng":null,"his_credit":0,"admin_credit":0,"total_credit":0,"rate":"","category_id":3,"category_name":"Truck Furniture","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/L4S29KNf4scRLoGvUGxbQlULIRDqT6q3NWPAvUjA.jpeg"},{"id":63,"company_name":"s","company_description":"see","company_img":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/maoZHLRzJu9fC7jjqUv2BaUYzVD7px9PFcWwrwE3.jpeg","company_contact":"12412412412","subCategories":[{"id":48,"name":"sdf","description":"dsf","image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/AS4my2XYaoBKbtJWqkUroruGZR31TApa9y8xIjXy.jpeg","childs":[]}],"online":true,"lat":null,"lng":null,"his_credit":200,"admin_credit":0,"total_credit":200,"rate":"","category_id":3,"category_name":"Truck Furniture","category_image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/L4S29KNf4scRLoGvUGxbQlULIRDqT6q3NWPAvUjA.jpeg"}]
     * statusCode : 200
     * statusText : OK
     */

    public int statusCode;
    public String statusText;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * id : 62
         * company_name : wkelCompany11
         * company_description : 1wkelCompany
         * company_img : https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/noTPe8dwUBrFOma5Zs2FFAsimtjHPeXGySWTBXTm.jpeg
         * company_contact : 123456
         * subCategories : [{"id":47,"name":"سيب","description":"سيب","image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/uEe4GiqMA0sNoTReDn3uavZx9ZjbePvCAOOHtRUe.jpeg","childs":[]},{"id":48,"name":"sdf","description":"dsf","image":"https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/AS4my2XYaoBKbtJWqkUroruGZR31TApa9y8xIjXy.jpeg","childs":[]}]
         * online : true
         * lat : null
         * lng : null
         * his_credit : 0
         * admin_credit : 0
         * total_credit : 0
         * rate :
         * category_id : 3
         * category_name : Truck Furniture
         * category_image : https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/L4S29KNf4scRLoGvUGxbQlULIRDqT6q3NWPAvUjA.jpeg
         */

        public int id;
        public String company_name;
        public String company_description;
        public String company_img;
        public String company_contact;
        public boolean online;
        public Object lat;
        public Object lng;
        public int his_credit;
        public int admin_credit;
        public int total_credit;
        public String rate;
        public int category_id;
        public String category_name;
        public String category_image;
        public List<SubCategoriesBean> subCategories;

        public static class SubCategoriesBean {
            /**
             * id : 47
             * name : سيب
             * description : سيب
             * image : https://173.236.24.193/~nashmi/storage/app/public/uploadsThum/uEe4GiqMA0sNoTReDn3uavZx9ZjbePvCAOOHtRUe.jpeg
             * childs : []
             */

            public int id;
            public String name;
            public String description;
            public String image;
            public List<?> childs;
        }
    }
}
