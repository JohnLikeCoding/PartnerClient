package com.kuaigui.yueche.driver.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/12 10:06
 */

public class RootOrderListBean {


    /**
     * code : 200
     * message : operate successfully
     * data : [{"orderNo":6228035936,"commericalType":1,"orderTime":1539223673000,"orderTimeStr":"2018-10-11 10:07:53","distance":0,"state":7,"stateStr":"扣款完成待评价","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.825688","destLatitude":"22.640829"},{"orderNo":9922073763,"commericalType":1,"orderTime":1539223762000,"orderTimeStr":"2018-10-11 10:09:22","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.825688","destLatitude":"22.640829"},{"orderNo":1988252306,"commericalType":1,"orderTime":1539226737000,"orderTimeStr":"2018-10-11 10:58:57","distance":0,"state":7,"stateStr":"扣款完成待评价","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.825688","destLatitude":"22.640829"},{"orderNo":6391390294,"commericalType":1,"orderTime":1539241456000,"orderTimeStr":"2018-10-11 15:04:16","distance":0,"state":7,"stateStr":"扣款完成待评价","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.825688","destLatitude":"22.640829"},{"orderNo":7817029771,"commericalType":1,"orderTime":1539242916000,"orderTimeStr":"2018-10-11 15:28:36","distance":0,"state":7,"stateStr":"扣款完成待评价","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市南山区深圳大学","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.935097","destLatitude":"22.527939"},{"orderNo":4871739010,"commericalType":1,"orderTime":1539244740000,"orderTimeStr":"2018-10-11 15:59:00","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市南山区深圳大学","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.935097","destLatitude":"22.527939"},{"orderNo":8612125561,"commericalType":1,"orderTime":1539244908000,"orderTimeStr":"2018-10-11 16:01:48","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市南山区深圳大学","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.935097","destLatitude":"22.527939"},{"orderNo":4816798026,"commericalType":1,"orderTime":1539245122000,"orderTimeStr":"2018-10-11 16:05:22","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市南山区深圳大学","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.935097","destLatitude":"22.527939"},{"orderNo":6119984159,"commericalType":1,"orderTime":1539327081000,"orderTimeStr":"2018-10-12 14:51:21","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市宝安区深圳机场","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.825688","destLatitude":"22.640829"},{"orderNo":8836309973,"commericalType":1,"orderTime":1539328493000,"orderTimeStr":"2018-10-12 15:14:53","distance":6.26,"state":7,"stateStr":"扣款完成待评价","departure":"广东省深圳市宝安区花样年","destination":"广东省深圳市南山区深圳大学","depLongitude":"113.882267","depLatitude":"22.564368","destLongitude":"113.935097","destLatitude":"22.527939"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * orderNo : 6228035936
         * commericalType : 1
         * orderTime : 1539223673000
         * orderTimeStr : 2018-10-11 10:07:53
         * distance : 0.0
         * state : 7
         * stateStr : 扣款完成待评价
         * departure : 广东省深圳市宝安区花样年
         * destination : 广东省深圳市宝安区深圳机场
         * depLongitude : 113.882267
         * depLatitude : 22.564368
         * destLongitude : 113.825688
         * destLatitude : 22.640829
         */

        private String orderNo;
        private int commericalType;
        private long orderTime;
        private String orderTimeStr;
        private double distance;
        private int state;
        private String stateStr;
        private String departure;
        private String destination;
        private String depLongitude;
        private String depLatitude;
        private String destLongitude;
        private String destLatitude;

        public String getOrderNo() {
            return orderNo;
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

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
        }

        public String getOrderTimeStr() {
            return orderTimeStr;
        }

        public void setOrderTimeStr(String orderTimeStr) {
            this.orderTimeStr = orderTimeStr;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateStr() {
            return stateStr;
        }

        public void setStateStr(String stateStr) {
            this.stateStr = stateStr;
        }

        public String getDeparture() {
            return departure;
        }

        public void setDeparture(String departure) {
            this.departure = departure;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.orderNo);
            dest.writeInt(this.commericalType);
            dest.writeLong(this.orderTime);
            dest.writeString(this.orderTimeStr);
            dest.writeDouble(this.distance);
            dest.writeInt(this.state);
            dest.writeString(this.stateStr);
            dest.writeString(this.departure);
            dest.writeString(this.destination);
            dest.writeString(this.depLongitude);
            dest.writeString(this.depLatitude);
            dest.writeString(this.destLongitude);
            dest.writeString(this.destLatitude);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.orderNo = in.readString();
            this.commericalType = in.readInt();
            this.orderTime = in.readLong();
            this.orderTimeStr = in.readString();
            this.distance = in.readDouble();
            this.state = in.readInt();
            this.stateStr = in.readString();
            this.departure = in.readString();
            this.destination = in.readString();
            this.depLongitude = in.readString();
            this.depLatitude = in.readString();
            this.destLongitude = in.readString();
            this.destLatitude = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
