package com.kuaigui.yueche.driver.bean;

/**
 * @author: hekang
 * @description:
 * @date: 2018/10/22 16: 02
 */
public class ProgressOrderInfo {

    /**
     * code : 200
     * message : operate successfully
     * data : {"orderId":"175","orderNo":"4040938152","state":"3","departure":"宝民社区","depLongitude":"113.89889343436964","depLatitude":"22.56764472782974","destination":"洪浪北二路","destLongitude":"113.92145","destLatitude":"22.56764472782974","confirmLongitude":"106.6428967","confirmLatitude":"26.6386200","price":"20.91","driverName":"唐刚","driverMobile":"15632323232","driverLevel":"3","vehicleNo":"贵AG695L","vehicleType":"小型轿车","vehicleColor":"黑色","model":"SY7201Z","passengerName":"小三","passengerMobile":"17748698391","passengerSex":"0","passengerSexStr":"男"}
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
        return message;
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
         * orderId : 175
         * orderNo : 4040938152
         * state : 3
         * departure : 宝民社区
         * depLongitude : 113.89889343436964
         * depLatitude : 22.56764472782974
         * destination : 洪浪北二路
         * destLongitude : 113.92145
         * destLatitude : 22.56764472782974
         * confirmLongitude : 106.6428967
         * confirmLatitude : 26.6386200
         * price : 20.91
         * driverName : 唐刚
         * driverMobile : 15632323232
         * driverLevel : 3
         * vehicleNo : 贵AG695L
         * vehicleType : 小型轿车
         * vehicleColor : 黑色
         * model : SY7201Z
         * passengerName : 小三
         * passengerMobile : 17748698391
         * passengerSex : 0
         * passengerSexStr : 男
         */

        private String orderId;
        private String orderNo;
        private String state;
        private String departure;
        private String depLongitude;
        private String depLatitude;
        private String destination;
        private String destLongitude;
        private String destLatitude;
        private String confirmLongitude;
        private String confirmLatitude;
        private String price;
        private String driverName;
        private String driverMobile;
        private String driverLevel;
        private String vehicleNo;
        private String vehicleType;
        private String vehicleColor;
        private String model;
        private String passengerName;
        private String passengerMobile;
        private String passengerSex;
        private String passengerSexStr;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDeparture() {
            return departure;
        }

        public void setDeparture(String departure) {
            this.departure = departure;
        }

        public String getDepLongitude() {
            return depLongitude;
        }

        public void setDepLongitude(String depLongitude) {
            this.depLongitude = depLongitude;
        }

        public String getDepLatitude() {
            return depLatitude;
        }

        public void setDepLatitude(String depLatitude) {
            this.depLatitude = depLatitude;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDestLongitude() {
            return destLongitude;
        }

        public void setDestLongitude(String destLongitude) {
            this.destLongitude = destLongitude;
        }

        public String getDestLatitude() {
            return destLatitude;
        }

        public void setDestLatitude(String destLatitude) {
            this.destLatitude = destLatitude;
        }

        public String getConfirmLongitude() {
            return confirmLongitude;
        }

        public void setConfirmLongitude(String confirmLongitude) {
            this.confirmLongitude = confirmLongitude;
        }

        public String getConfirmLatitude() {
            return confirmLatitude;
        }

        public void setConfirmLatitude(String confirmLatitude) {
            this.confirmLatitude = confirmLatitude;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverMobile() {
            return driverMobile;
        }

        public void setDriverMobile(String driverMobile) {
            this.driverMobile = driverMobile;
        }

        public String getDriverLevel() {
            return driverLevel;
        }

        public void setDriverLevel(String driverLevel) {
            this.driverLevel = driverLevel;
        }

        public String getVehicleNo() {
            return vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getVehicleColor() {
            return vehicleColor;
        }

        public void setVehicleColor(String vehicleColor) {
            this.vehicleColor = vehicleColor;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPassengerName() {
            return passengerName;
        }

        public void setPassengerName(String passengerName) {
            this.passengerName = passengerName;
        }

        public String getPassengerMobile() {
            return passengerMobile;
        }

        public void setPassengerMobile(String passengerMobile) {
            this.passengerMobile = passengerMobile;
        }

        public String getPassengerSex() {
            return passengerSex;
        }

        public void setPassengerSex(String passengerSex) {
            this.passengerSex = passengerSex;
        }

        public String getPassengerSexStr() {
            return passengerSexStr;
        }

        public void setPassengerSexStr(String passengerSexStr) {
            this.passengerSexStr = passengerSexStr;
        }
    }
}
