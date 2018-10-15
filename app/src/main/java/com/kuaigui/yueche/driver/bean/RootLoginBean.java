package com.kuaigui.yueche.driver.bean;

/**
 * 作者: zengxc on 2018/10/13 22:50.
 * 描述:
 */

public class RootLoginBean {

    /**
     * code : 200
     * message : operate successfully
     * data : {"id":1,"mobile":"15632323232","code":"5461321","name":"张三","licenseId":"9669669","level":5,"monthValue":386.77,"totalValue":386.77,"longitude":"106.6428967","latitude":"26.6386200"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message== null ? "" :message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * mobile : 15632323232
         * code : 5461321
         * name : 张三
         * licenseId : 9669669
         * level : 5
         * monthValue : 386.77
         * totalValue : 386.77
         * longitude : 106.6428967
         * latitude : 26.6386200
         */

        private int id;
        private String mobile;
        private String code;
        private String name;
        private String licenseId;
        private int level;
        private double monthValue;
        private double totalValue;
        private String longitude;
        private String latitude;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile== null ? "" :mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCode() {
            return code== null ? "" :code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name== null ? "" :name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLicenseId() {
            return licenseId== null ? "" :licenseId;
        }

        public void setLicenseId(String licenseId) {
            this.licenseId = licenseId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public double getMonthValue() {
            return monthValue;
        }

        public void setMonthValue(double monthValue) {
            this.monthValue = monthValue;
        }

        public double getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(double totalValue) {
            this.totalValue = totalValue;
        }

        public String getLongitude() {
            return longitude== null ? "" :longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude== null ? "" :latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
