package com.kuaigui.yueche.driver.bean;

/**
 * 作者: zengxc on 2018/10/13 22:50.
 * 描述:
 */

public class RootLoginBean {


    /**
     * code : 200
     * message : operate successfully
     * data : {"id":92,"mobile":"18885100646","password":"21218cca77804d2ba1922c33e0151105","code":"155592","name":"谷贤","licenseId":"520112198812012824","level":5,"monthValue":674.73,"totalValue":801.71,"longitude":"113.887673","latitude":"22.552199","carId":87,"vehicleNo":"贵A5E08F","token":"88ed35af3c5d4e9f9452d9f90ef6cd6b","pwdset":1,"illegalLoginCount":0}
     */

    private int      code;
    private String   message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
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
         * id : 92
         * mobile : 18885100646
         * password : 21218cca77804d2ba1922c33e0151105
         * code : 155592
         * name : 谷贤
         * licenseId : 520112198812012824
         * level : 5
         * monthValue : 674.73
         * totalValue : 801.71
         * longitude : 113.887673
         * latitude : 22.552199
         * carId : 87
         * vehicleNo : 贵A5E08F
         * token : 88ed35af3c5d4e9f9452d9f90ef6cd6b
         * pwdset : 1
         * illegalLoginCount : 0
         */

        private int    id;
        private String mobile;
        private String password;
        private String code;
        private String name;
        private String licenseId;
        private int    level;
        private double monthValue;
        private double totalValue;
        private String longitude;
        private String latitude;
        private int    carId;
        private String vehicleNo;
        private String token;
        private int    pwdset;
        private int    illegalLoginCount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password == null ? "" : password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLicenseId() {
            return licenseId == null ? "" : licenseId;
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
            return longitude == null ? "" : longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude == null ? "" : latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public String getVehicleNo() {
            return vehicleNo == null ? "" : vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPwdset() {
            return pwdset;
        }

        public void setPwdset(int pwdset) {
            this.pwdset = pwdset;
        }

        public int getIllegalLoginCount() {
            return illegalLoginCount;
        }

        public void setIllegalLoginCount(int illegalLoginCount) {
            this.illegalLoginCount = illegalLoginCount;
        }
    }
}
