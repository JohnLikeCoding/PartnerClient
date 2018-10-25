package com.kuaigui.yueche.driver.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
     * data : [{"orderNo":"3181735136","commericalType":1,"orderTime":1540427258000,"orderTimeStr":"2018-10-25 08:27:38","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区新安街道裕安一路3986号","destination":"深圳市宝安区人民医院","depLongitude":"113.8866","depLatitude":"22.563152","destLongitude":"113.914991","destLatitude":"22.563152","passengerMobile":"13421330966"},{"orderNo":"9337481962","commericalType":1,"orderTime":1540385749000,"orderTimeStr":"2018-10-24 20:55:49","state":4,"stateStr":"乘客撤销","departure":"广东省深圳市宝安区卡罗社区","destination":"广东省深圳市宝安区深圳机场","depLongitude":"113.890281","depLatitude":"22.561586","destLongitude":"113.825688","destLatitude":"22.640829","passengerName":"小三","passengerMobile":"17748698391"},{"orderNo":"6977484878","commericalType":1,"orderTime":1540379159000,"orderTimeStr":"2018-10-24 19:05:59","state":4,"stateStr":"乘客撤销","departure":"花果园M区","destination":"贵州师大附中","depLongitude":"106.68284069779578","depLatitude":"26.558818042498945","destLongitude":"106.720397","destLatitude":"26.558818042498945","passengerMobile":"13985010368"},{"orderNo":"1590458019","commericalType":1,"orderTime":1540372718000,"orderTimeStr":"2018-10-24 17:18:38","distance":15.97,"state":7,"stateStr":"扣款完成待评价","departure":"广东省深圳市宝安区新安街道宝安大道","destination":"深圳宝安国际机场T3航站楼","depLongitude":"113.885167","depLatitude":"22.564565","destLongitude":"113.811929","destLatitude":"22.564565","passengerMobile":"13421330966"},{"orderNo":"4326187176","commericalType":1,"orderTime":1540359731000,"orderTimeStr":"2018-10-24 13:42:11","distance":1.91,"state":8,"stateStr":"完成","departure":"贵州省贵阳市观山湖区逸景社区服务中心翠柳路","destination":"金阳医院(公交站)","depLongitude":"106.618593","depLatitude":"26.618252","destLongitude":"106.621277","destLatitude":"26.618252","passengerMobile":"13985010368"},{"orderNo":"7258580743","commericalType":1,"orderTime":1540359223000,"orderTimeStr":"2018-10-24 13:33:43","state":4,"stateStr":"乘客撤销","departure":"贵阳喀斯特公园","destination":"金阳医院(公交站)","depLongitude":"106.61867287152575","depLatitude":"26.617531099933828","destLongitude":"106.621277","destLatitude":"26.617531099933828","passengerMobile":"18096006272"},{"orderNo":"5819674184","commericalType":1,"orderTime":1540349349000,"orderTimeStr":"2018-10-24 10:49:09","state":4,"stateStr":"乘客撤销","departure":"德福中心","destination":"观山湖行政中心(公交站)","depLongitude":"106.647073","depLatitude":"26.612077","destLongitude":"106.621422","destLatitude":"26.612077","passengerMobile":"13985010368"},{"orderNo":"9811965784","commericalType":1,"orderTime":1540346646000,"orderTimeStr":"2018-10-24 10:04:06","distance":20.85,"state":7,"stateStr":"扣款完成待评价","departure":"中国石化广安加油二站","destination":"深圳北站西广场停车场","depLongitude":"113.883833","depLatitude":"22.564886","destLongitude":"114.027349","destLatitude":"22.564886","passengerName":"小三","passengerMobile":"17748698391"},{"orderNo":"5817152758","commericalType":1,"orderTime":1540346156000,"orderTimeStr":"2018-10-24 09:55:56","state":4,"stateStr":"乘客撤销","departure":"中国石化广安加油二站","destination":"深圳北站西广场停车场","depLongitude":"113.883833","depLatitude":"22.564886","destLongitude":"114.027349","destLatitude":"22.564886","passengerName":"小三","passengerMobile":"17748698391"},{"orderNo":"1363349012","commericalType":1,"orderTime":1540345936000,"orderTimeStr":"2018-10-24 09:52:16","state":4,"stateStr":"乘客撤销","departure":"中国石化广安加油二站","destination":"深圳北站西广场","depLongitude":"113.883833","depLatitude":"22.564886","destLongitude":"114.027352","destLatitude":"22.564886","passengerName":"小三","passengerMobile":"17748698391"}]
     */

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
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
         * orderNo : 3181735136
         * commericalType : 1
         * orderTime : 1540427258000
         * orderTimeStr : 2018-10-25 08:27:38
         * state : 4
         * stateStr : 乘客撤销
         * departure : 广东省深圳市宝安区新安街道裕安一路3986号
         * destination : 深圳市宝安区人民医院
         * depLongitude : 113.8866
         * depLatitude : 22.563152
         * destLongitude : 113.914991
         * destLatitude : 22.563152
         * passengerMobile : 13421330966
         * passengerName : 小三
         * distance : 15.97
         */

        @SerializedName("orderNo")
        private String orderNo;
        @SerializedName("commericalType")
        private int commericalType;
        @SerializedName("orderTime")
        private long orderTime;
        @SerializedName("orderTimeStr")
        private String orderTimeStr;
        @SerializedName("state")
        private int state;
        @SerializedName("stateStr")
        private String stateStr;
        @SerializedName("departure")
        private String departure;
        @SerializedName("destination")
        private String destination;
        @SerializedName("depLongitude")
        private String depLongitude;
        @SerializedName("depLatitude")
        private String depLatitude;
        @SerializedName("destLongitude")
        private String destLongitude;
        @SerializedName("destLatitude")
        private String destLatitude;
        @SerializedName("passengerMobile")
        private String passengerMobile;
        @SerializedName("passengerName")
        private String passengerName;
        @SerializedName("distance")
        private double distance;

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

        public String getPassengerMobile() {
            return passengerMobile;
        }

        public void setPassengerMobile(String passengerMobile) {
            this.passengerMobile = passengerMobile;
        }

        public String getPassengerName() {
            return passengerName;
        }

        public void setPassengerName(String passengerName) {
            this.passengerName = passengerName;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
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
            dest.writeInt(this.state);
            dest.writeString(this.stateStr);
            dest.writeString(this.departure);
            dest.writeString(this.destination);
            dest.writeString(this.depLongitude);
            dest.writeString(this.depLatitude);
            dest.writeString(this.destLongitude);
            dest.writeString(this.destLatitude);
            dest.writeString(this.passengerMobile);
            dest.writeString(this.passengerName);
            dest.writeDouble(this.distance);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.orderNo = in.readString();
            this.commericalType = in.readInt();
            this.orderTime = in.readLong();
            this.orderTimeStr = in.readString();
            this.state = in.readInt();
            this.stateStr = in.readString();
            this.departure = in.readString();
            this.destination = in.readString();
            this.depLongitude = in.readString();
            this.depLatitude = in.readString();
            this.destLongitude = in.readString();
            this.destLatitude = in.readString();
            this.passengerMobile = in.readString();
            this.passengerName = in.readString();
            this.distance = in.readDouble();
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
