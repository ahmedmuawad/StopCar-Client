package com.stopgroup.stopcar.client.Modules;

import java.util.List;

public class EmergencyContacts {

    /**
     * result : [{"id":10,"name":"Osta Wa2l","mobile":"0100 025 1933","image":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-09-06-01-12-05-1693612-bba7b6cb8e881da57fb48964720e5b50.jpeg"},{"id":11,"name":"Hady","mobile":"011-145-80903","image":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-09-06-01-12-10-4391454-c197a98df75fe7df4a81e4f414657b54.jpeg"},{"id":12,"name":"Hady","mobile":"011-512-92111","image":"https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-09-06-01-12-10-7903216-db9bc5483e270e3726faefd70d5dfffc.jpeg"}]
     * statusCode : 200
     * statusText : OK
     */

    private int statusCode;
    private String statusText;
    private List<ResultContactsModel> result;

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

    public List<ResultContactsModel> getResult() {
        return result;
    }

    public void setResult(List<ResultContactsModel> result) {
        this.result = result;
    }

    public static class ResultContactsModel {
        /**
         * id : 10
         * name : Osta Wa2l
         * mobile : 0100 025 1933
         * image : https://173.236.24.193/~nashmi/storage/app/public/uploads/2018-09-06-01-12-05-1693612-bba7b6cb8e881da57fb48964720e5b50.jpeg
         */

        private int id;
        private String name;
        private String mobile;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
