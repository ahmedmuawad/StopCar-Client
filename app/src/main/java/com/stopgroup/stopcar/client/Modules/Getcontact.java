package com.stopgroup.stopcar.client.Modules;

import java.util.List;

public class Getcontact {

    /**
     * result : [{"id":3,"name":null,"mobile":"588-555","image":null},{"id":4,"name":null,"mobile":"588-555","image":null}]
     * statusCode : 200
     * statusText : OK
     */

    public int statusCode;
    public String statusText;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * id : 3
         * name : null
         * mobile : 588-555
         * image : null
         */

        public int id;
        public String name;
        public String mobile;
        public String image;
    }
}
