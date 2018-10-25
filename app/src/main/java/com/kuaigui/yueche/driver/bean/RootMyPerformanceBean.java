package com.kuaigui.yueche.driver.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 15:39
 */

public class RootMyPerformanceBean {

    /**
     * code : 200
     * message : operate successfully
     * data : {"rewardTotal":0,"orderNums":4,"uncompletedOrderNums":2,"deductionTotal":0,"orders":[{"orderNo":6228035936,"commericalType":1,"orderTime":1539223673000,"state":4,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"},{"orderNo":9922073763,"commericalType":1,"orderTime":1539223762000,"state":4,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"},{"orderNo":1988252306,"commericalType":1,"orderTime":1539226737000,"state":7,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"},{"orderNo":6391390294,"commericalType":1,"orderTime":1539241456000,"state":7,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"}]}
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
         * rewardTotal : 0
         * orderNums : 4
         * uncompletedOrderNums : 2
         * deductionTotal : 0
         * orders : [{"orderNo":6228035936,"commericalType":1,"orderTime":1539223673000,"state":4,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"},{"orderNo":9922073763,"commericalType":1,"orderTime":1539223762000,"state":4,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"},{"orderNo":1988252306,"commericalType":1,"orderTime":1539226737000,"state":7,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"},{"orderNo":6391390294,"commericalType":1,"orderTime":1539241456000,"state":7,"departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","longitude":"113.882267","latitude":"22.564368"}]
         */

        private double rewardTotal;
        private int orderNums;
        private int uncompletedOrderNums;
        private double deductionTotal;
        private List<OrdersBean> orders;

        public double getRewardTotal() {
            return rewardTotal;
        }

        public void setRewardTotal(double rewardTotal) {
            this.rewardTotal = rewardTotal;
        }

        public int getOrderNums() {
            return orderNums;
        }

        public void setOrderNums(int orderNums) {
            this.orderNums = orderNums;
        }

        public int getUncompletedOrderNums() {
            return uncompletedOrderNums;
        }

        public void setUncompletedOrderNums(int uncompletedOrderNums) {
            this.uncompletedOrderNums = uncompletedOrderNums;
        }

        public double getDeductionTotal() {
            return deductionTotal;
        }

        public void setDeductionTotal(double deductionTotal) {
            this.deductionTotal = deductionTotal;
        }

        public List<OrdersBean> getOrders() {
            if (orders == null) {
                return new ArrayList<>();
            }
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class OrdersBean {
            /**
             * orderNo : 6228035936
             * commericalType : 1
             * orderTime : 1539223673000
             * state : 4
             * departure : 广东省深圳市宝安区花样年
             * destination : 广东省深圳市宝安区深圳机场
             * longitude : 113.882267
             * latitude : 22.564368
             */

            private String orderNo;
            private int commericalType;
            private long orderTime;
            private int state;
            private String orderTimeStr;
            private String stateStr;
            private String departure;
            private String destination;
            private String longitude;
            private String latitude;
            private String distance;

            public String getOrderNo() {
                return orderNo == null ? "" : orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getCommericalType() {
                return commericalType;
            }

            public void setCommericalType(int commericalType) {
                this.commericalType = commericalType;
            }

            public String getDistance() {
                return distance == null ? "0" : distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getStateStr() {
                return stateStr == null ? "" : stateStr;
            }

            public void setStateStr(String stateStr) {
                this.stateStr = stateStr;
            }

            public long getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(long orderTime) {
                this.orderTime = orderTime;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getOrderTimeStr() {
                return orderTimeStr == null ? "" : orderTimeStr;
            }

            public void setOrderTimeStr(String orderTimeStr) {
                this.orderTimeStr = orderTimeStr;
            }

            public String getDeparture() {
                return departure == null ? "" : departure;
            }

            public void setDeparture(String departure) {
                this.departure = departure;
            }

            public String getDestination() {
                return destination == null ? "" : destination;
            }

            public void setDestination(String destination) {
                this.destination = destination;
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
        }
    }
}
